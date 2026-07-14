# Player NBT & Trinket Attributes — Design Findings

## Project Context

- **Fabric mod** for **Minecraft 1.21** (Java 21)
- **Mod ID:** `league-of-crafters`
- **Goal:** Implement League of Legends items in Minecraft
- **Trinkets API v3.10.0** — 6 trinket slots (`lol/item_1` through `lol/item_6`) on the player
- **Data source:** `items_data.json` at project root (~10K+ lines of League item definitions)
- **Current state:** Skeleton — only a single test item registered, no player data handling

---

## Player NBT & Attributes Architecture

### Approach: Fabric API `EntityComponent` (Modern, Recommended)

Minecraft 1.21's Data Component system + Fabric's `EntityComponent` API for attaching data to players.

### Components to Create

#### `ModComponents` — DataComponentType registry
Register custom `DataComponentType` fields for each LoL stat:

| Component | Type | Maps To |
|---|---|---|
| `LOL_ARMOR` | `Integer` | `Attributes.ARMOR` |
| `LOL_MAGIC_RESIST` | `Integer` | `Attributes.ARMOR_TOUGHNESS` (or custom) |
| `LOL_ATTACK_DAMAGE` | `Integer` | `Attributes.ATTACK_DAMAGE` |
| `LOL_HEALTH` | `Integer` | `Attributes.MAX_HEALTH` |
| `LOL_ABILITY_POWER` | `Integer` | Custom attribute |
| `LOL_ABILITY_HASTE` | `Integer` | Custom attribute |
| `LOL_PERCENT_MAX_HP_DAMAGE` | `Float` | Damage calculation |
| `LOL_PERCENT_CURRENT_HP_DAMAGE` | `Float` | Damage calculation |
| `LOL_PERCENT_MAX_HP_BONUS` | `Float` | `ADD_MULTIPLIED_BASE` on `MAX_HEALTH` |

---

## Architecture: Player Stats via Trinket Slots

### Central `PlayerStatsComponent` (Fabric `EntityComponent`)

Attached to every player. Holds computed LoL stats and manages Minecraft attribute modifiers.

```
Item (in trinket slot)
  └─ DataComponent: "lolarmor": 50, "lol_ability_power": 80
      └─ onEquip/onUnequip → PlayerStatsComponent.recalculate()
          └─ iterates all 6 trinket slots
          └─ reads fresh DataComponents from each ItemStack
          └─ sums all stats
          └─ removes old AttributeModifiers, applies new ones
```

### Key Classes

| Class | Purpose |
|---|---|
| `ModComponents` | Register `DataComponentType<Integer/Float>` for each LoL stat |
| `PlayerStatsComponent` | Fabric `EntityComponent` on player, holds computed stats, has `recalculate()` |
| `LolTrinketItem` | Extends `TrinketItem`, triggers recalculation on equip/unequip |

### Stat → Minecraft Attribute Mapping

| LoL Stat | DataComponentType | Minecraft Attribute | Operation |
|---|---|---|---|
| `lol_armor` | `Integer` | `Attributes.ARMOR` | `ADD_VALUE` |
| `lol_magic_resist` | `Integer` | `Attributes.ARMOR_TOUGHNESS` (or custom) | `ADD_VALUE` |
| `lol_attack_damage` | `Integer` | `Attributes.ATTACK_DAMAGE` | `ADD_VALUE` |
| `lol_health` | `Integer` | `Attributes.MAX_HEALTH` | `ADD_VALUE` |
| `lol_attack_speed` | `Float` | `Attributes.ATTACK_SPEED` | `ADD_MULTIPLIED_BASE` |
| `lol_percent_max_hp_bonus` | `Float` | `Attributes.MAX_HEALTH` | `ADD_MULTIPLIED_BASE` |
| `lol_ability_power` | `Integer` | Custom attribute | `ADD_VALUE` |
| `lol_ability_haste` | `Integer` | Custom attribute | `ADD_VALUE` |
| `lol_percent_max_hp_damage` | `Float` | Damage calculation | — |
| `lol_percent_current_hp_damage` | `Float` | Damage calculation | — |
| `lol_percent_missing_hp_damage` | `Float` | Damage calculation | — |

---

## Architecture: Player Stats via Trinket Slots

### Central `PlayerStatsComponent` (Fabric `EntityComponent`)

Attached to every player. Single source of truth for all computed LoL stats.

```
Item (in trinket slot)
  └─ DataComponent: "lolarmor": 50, "lol_ability_power": 80
      └─ onEquip/onUnequip → PlayerStatsComponent.recalculate()
          └─ iterates all 6 trinket slots
          └─ reads fresh DataComponents from each ItemStack
          └─ sums all stats
          └─ removes old AttributeModifiers, applies new ones
```

### Key Classes

| Class | Purpose |
|---|---|
| `ModComponents` | Register `DataComponentType<Integer/Float>` for each LoL stat |
| `PlayerStatsComponent` | Fabric `EntityComponent` on player, holds computed stats, has `recalculate()` |
| `LolTrinketItem` | Extends `TrinketItem`, triggers recalculation on equip/unequip |

### `recalculate()` Flow

```
Any stat change (equip, unequip, item proc like Heartsteel)
  └─ calls: player.getComponent(PLAYER_STATS).recalculate()
      └─ iterates all 6 trinket slots via TrinketsApi
      └─ reads fresh DataComponents from each ItemStack
      └─ sums all stats
      └─ removes old AttributeModifiers, applies new ones
```

### For items with stats that change over time (e.g., Heartsteel stacks)

The item calls `recalculate()` when its internal state changes:

```java
// In HeartsteelTrinketItem.onProc():
public void onProc(Player player, ItemStack stack) {
    int stacks = stack.getOrDefault(ModComponents.HEARTSTEEL_STACKS, 0);
    stacks++;
    stack.set(ModComponents.HEARTSTEEL_STACKS, stacks);
    stack.set(ModComponents.LOL_HEALTH, 100 + stacks * 10);

    PlayerStatsComponent component = player.getComponent(ModComponents.PLAYER_STATS);
    component.recalculate(player);
}
```

---

## Percent HP Mechanics

### 1. Percent Max HP as a Stat Bonus (e.g., "+5% max health")

Use `AttributeModifier.Operation.ADD_MULTIPLIED_BASE` on `Attributes.MAX_HEALTH`:

```java
new AttributeModifier(id, 0.05, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
```

- `ADD_MULTIPLIED_BASE` — adds % of the **base** value
- `ADD_MULTIPLIED_TOTAL` — adds % of the **total** value (including other modifiers)

### 2. Percent HP as Damage (e.g., "deal 5% of target's max/current HP")

These are **damage calculations**, not attribute modifiers. Computed at damage time:

```java
// Percent max HP damage (Liandry's, BotRK)
float maxHpDmg = target.getMaxHealth() * 0.05f;

// Percent current HP damage (BotRK)
float currentHpDmg = target.getHealth() * 0.05f;

// Percent missing HP damage (Eclipse)
float missingHpDmg = (target.getMaxHealth() - target.getHealth()) * 0.05f;
```

### Representing as Item DataComponents

```java
// On the item's DataComponent:
LOL_PERCENT_MAX_HP_DAMAGE: 0.05f      // 5% of target's max HP
LOL_PERCENT_CURRENT_HP_DAMAGE: 0.07f  // 7% of target's current HP
LOL_PERCENT_MAX_HP_BONUS: 0.05f       // +5% max health (attribute modifier)
```

### Damage calculation system

```java
float bonusDmg = 0;
for (ItemStack stack : trinketSlots) {
    bonusDmg += target.getMaxHealth() * stack.getOrDefault(LOL_PERCENT_MAX_HP_DAMAGE, 0);
    bonusDmg += target.getHealth() * stack.getOrDefault(LOL_PERCENT_CURRENT_HP_DAMAGE, 0);
}
```

---

## Attribute Modifier Operations

| Operation | Effect | Use Case |
|---|---|---|
| `ADD_VALUE` (0) | +X flat | Flat armor, AD, AP |
| `ADD_MULTIPLIED_BASE` (1) | +X% of base value | % max health bonus, % attack speed |
| `ADD_MULTIPLIED_TOTAL` (2) | +X% of total value | Stacking % bonuses |

---

## Trinkets API Patterns

### Equip/Unequip Events

**Per-item (override on TrinketItem subclass):**
```java
@Override
public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
    // Apply modifiers
}

@Override
public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
    // Remove modifiers
}
```

**Global listener (for recalculation approach):**
```java
TrinketsApi.registerTrinketListener((stack, slot, entity, itemComponent) -> {
    // Called for any equip/unequip
    return TriState.DEFAULT;
});
```

### Querying trinket slots

```java
TrinketsApi.getTrinketComponent(player).ifPresent(component -> {
    for (ItemStack stack : component.getAllEquipped()) {
        int armor = stack.getOrDefault(ModComponents.LOL_ARMOR, 0);
    }
});
```

---

## Percent HP Mechanics

### 1. Percent Max HP as a Stat Bonus (e.g., "+5% max health")

Use `AttributeModifier.Operation.ADD_MULTIPLIED_BASE` on `Attributes.MAX_HEALTH`:

```java
new AttributeModifier(id, 0.05, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
```

- `ADD_MULTIPLIED_BASE` (0) — adds % of the **base** value
- `ADD_MULTIPLIED_TOTAL` (2) — adds % of the **total** value (including other modifiers)

### 2. Percent HP as Damage (e.g., "deal 5% of target's max/current HP")

These are **damage calculations**, not attribute modifiers. Computed at damage time:

```java
float maxHpDmg = target.getMaxHealth() * 0.05f;           // % max HP
float currentHpDmg = target.getHealth() * 0.05f;           // % current HP
float missingHpDmg = (target.getMaxHealth() - target.getHealth()) * 0.05f;  // % missing HP
```

### Representing as Item DataComponents

```java
// On the item's DataComponent:
LOL_PERCENT_MAX_HP_DAMAGE: 0.05f        // 5% of target's max HP
LOL_PERCENT_CURRENT_HP_DAMAGE: 0.07f    // 7% of target's current HP
LOL_PERCENT_MAX_HP_BONUS: 0.05f         // +5% max health (attribute modifier)
```

### Damage calculation system

```java
float bonusDmg = 0;
for (ItemStack stack : trinketSlots) {
    bonusDmg += target.getMaxHealth() * stack.getOrDefault(LOL_PERCENT_MAX_HP_DAMAGE, 0);
    bonusDmg += target.getHealth() * stack.getOrDefault(LOL_PERCENT_CURRENT_HP_DAMAGE, 0);
}
```

---

## Attribute Modifier Operations

| Operation | Value | Effect | Use Case |
|---|---|---|---|
| `ADD_VALUE` | 0 | +X flat | Flat armor, AD, AP, health |
| `ADD_MULTIPLIED_BASE` | 1 | +X% of base value | % max health bonus, % attack speed |
| `ADD_MULTIPLIED_TOTAL` | 2 | +X% of total value | Stacking % bonuses |

---

## Trinkets API Reference

- **Version:** 3.10.0 (`dev.emi:trinkets`)
- **Slots:** 6 slots (`lol/item_1` through `lol/item_6`), all accept any item
- **Group:** `lol`

### Key classes

| Class | Purpose |
|---|---|
| `TrinketsApi` | Main entry point, static methods |
| `TrinketComponent` | Attached to living entities, holds all trinket inventories |
| `TrinketInventory` | One slot's inventory (usually 1 slot) |
| `SlotReference` | Reference to a specific slot (group + slot name + index) |
| `TrinketItem` | Base class for trinket items, override `onEquip`/`onUnequip` |

### Getting equipped items

```java
TrinketsApi.getTrinketComponent(player).ifPresent(component -> {
    for (ItemStack stack : component.getAllEquipped()) {
        // Process each trinket item
    }
});
```

---

## Percent HP Mechanics

### 1. Percent Max HP as a Stat Bonus (e.g., "+5% max health")

Use `AttributeModifier.Operation.ADD_MULTIPLIED_BASE` on `Attributes.MAX_HEALTH`:

```java
new AttributeModifier(id, 0.05, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
```

- `ADD_MULTIPLIED_BASE` (0) — adds % of the **base** value
- `ADD_MULTIPLIED_TOTAL` (2) — adds % of the **total** value (including other modifiers)

### 2. Percent HP as Damage (e.g., "deal 5% of target's max/current HP")

These are **damage calculations**, not attribute modifiers. Computed at damage time:

```java
float maxHpDmg = target.getMaxHealth() * 0.05f;                              // % max HP
float currentHpDmg = target.getHealth() * 0.05f;                             // % current HP
float missingHpDmg = (target.getMaxHealth() - target.getHealth()) * 0.05f;    // % missing HP
```

### Representing as Item DataComponents

```java
// On the item's DataComponent:
LOL_PERCENT_MAX_HP_DAMAGE: 0.05f         // 5% of target's max HP
LOL_PERCENT_CURRENT_HP_DAMAGE: 0.07f     // 7% of target's current HP
LOL_PERCENT_MAX_HP_BONUS: 0.05f          // +5% max health (attribute modifier)
```

### Damage calculation system

```java
float bonusDmg = 0;
for (ItemStack stack : trinketSlots) {
    bonusDmg += target.getMaxHealth() * stack.getOrDefault(LOL_PERCENT_MAX_HP_DAMAGE, 0);
    bonusDmg += target.getHealth() * stack.getOrDefault(LOL_PERCENT_CURRENT_HP_DAMAGE, 0);
}
```

---

## Attribute Modifier Operations

| Operation | Value | Effect | Use Case |
|---|---|---|---|
| `ADD_VALUE` | 0 | +X flat | Flat armor, AD, AP, health |
| `ADD_MULTIPLIED_BASE` | 1 | +X% of base value | % max health bonus, % attack speed |
| `ADD_MULTIPLIED_TOTAL` | 2 | +X% of total value | Stacking % bonuses |

---

## Trinkets API Reference

- **Version:** `dev.emi:trinkets:3.10.0`
- **Slots:** 6 slots (`lol/item_1` through `lol/item_6`) in the `lol` group
- **Entity:** Player
- **Validator:** `trinkets:all` (any item accepted)

### Key classes

| Class | Purpose |
|---|---|
| `TrinketsApi` | Main entry point, static methods |
| `TrinketComponent` | Attached to living entities, holds all trinket inventories |
| `TrinketInventory` | One slot's inventory (usually 1 slot) |
| `SlotReference` | Reference to a specific slot (group + slot name + index) |
| `TrinketItem` | Base class for trinket items, override `onEquip`/`onUnequip` |

### Getting equipped items

```java
TrinketsApi.getTrinketComponent(player).ifPresent(component -> {
    for (ItemStack stack : component.getAllEquipped()) {
        int armor = stack.getOrDefault(ModComponents.LOL_ARMOR, 0);
    }
});
```

---

## Data Source

`items_data.json` at project root contains all League item definitions with stats like:

```json
{
  "328020": {
    "itemname": "Abyssal Mask",
    "stats": { "Magic Resist": 50, "Health": 350 },
    "tags": ["Health", "SpellBlock", "CooldownReduction", "MagicResist", "AbilityHaste"]
  }
}
```

Not yet consumed by Java code — needs a parser to bridge into the Trinkets system.

---

## Attribute Modifier Operations

| Operation | Value | Effect | Use Case |
|---|---|---|---|
| `ADD_VALUE` | 0 | +X flat | Flat armor, AD, AP, health |
| `ADD_MULTIPLIED_BASE` | 1 | +X% of base value | % max health bonus, % attack speed |
| `ADD_MULTIPLIED_TOTAL` | 2 | +X% of total value | Stacking % bonuses |

---

## Trinkets API Reference

- **Version:** `dev.emi:trinkets:3.10.0`
- **Slots:** 6 slots (`lol/item_1` through `lol/item_6`) in the `lol` group
- **Entity:** Player
- **Validator:** `trinkets:all` (any item accepted)
- **Maven:** `https://maven.terraformersmc.com/`

### Key classes

| Class | Package | Purpose |
|---|---|---|
| `TrinketsApi` | `dev.emi.trinkets.api` | Main entry point |
| `TrinketComponent` | `dev.emi.trinkets.api` | Attached to living entities, holds all trinket inventories |
| `TrinketInventory` | `dev.emi.trinkets.api` | One slot's inventory (usually 1 slot) |
| `SlotReference` | `dev.emi.trinkets.api` | Reference to a specific slot (group + slot name + index) |
| `TrinketItem` | `dev.emi.trinkets.api` | Base class for trinket items |

### Slot configuration

**Entity binding** (`data/trinkets/entities/leagueofcrafters.json`):
```json
{
  "replace": false,
  "entities": ["player"],
  "slots": ["lol/item_1", "lol/item_2", "lol/item_3", "lol/item_4", "lol/item_5", "lol/item_6"]
}
```

**Group** (`data/trinkets/slots/lol/group.json`):
```json
{ "slot_id": -1, "order": 0 }
```

**Individual slots** (`data/trinkets/slots/lol/item_1.json` through `item_6.json`):
```json
{
  "replace": false,
  "order": 0,
  "amount": 1,
  "validator_predicates": ["trinkets:all"]
}
```

---

## Data Source

`items_data.json` at project root contains League item definitions with stats:

```json
{
  "328020": {
    "itemname": "Abyssal Mask",
    "stats": { "Magic Resist": 50, "Health": 350 },
    "tags": ["Health", "SpellBlock", "CooldownReduction", "MagicResist", "AbilityHaste"]
  }
}
```

Not yet consumed by Java code — needs a parser.

---

## Implementation Order

1. **Create `ModComponents`** — register `DataComponentType` for each LoL stat
2. **Create `PlayerStatsComponent`** — Fabric `EntityComponent` with `recalculate()`
3. **Create `LolTrinketItem`** — extends `TrinketItem`, calls `recalculate()` on equip/unequip
4. **Create damage calculation system** — reads item DataComponents, computes percent HP damage
5. **Parse `items_data.json`** — bridge League item data into the Trinkets system
