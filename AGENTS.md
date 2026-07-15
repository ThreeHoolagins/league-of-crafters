# Item Classification Rules

Every item registered in `ModItems.java` must be classified as one of:

| Class | Behavior | Used for |
|-------|----------|----------|
| `BootItem` | `stacksTo(1)`, per-item uniqueness, cross-boot restriction, +movement speed | Boots |
| `FullItem` | `stacksTo(1)`, per-item uniqueness (can't equip 2 of the same) | Completed/fully-upgraded items (depth 3, empty `into[]`) |
| `ComponentItem` | No restrictions, can stack multiple | Component items, starter items |
| `PotionItem` | `stacksTo(16)`, right-click to drink, not equippable | Consumables (potions, elixirs) |
| `WardItem` | `stacksTo(64)`, right-click to place a ward block, not equippable | Wards, trinkets (Stealth Ward, Control Ward, Farsight Alteration, Oracle Lens, Scarecrow Effigy) |
| `Item` (plain) | Default 64 stack, not a trinket | Meme items, wardstones, unbucketed items |

## When editing an item registration

Always ask the user which class an item should use if it isn't already assigned to one of the above classes. Do not guess.

## Authoritative source for League of Legends information

Use https://wiki.leagueoflegends.com/en-us/ as the authoritative source for League item stats, ability descriptions, and game mechanics. Always validate game data against this wiki before making changes.

## Related documentation

The full item class hierarchy with Mermaid diagrams, class breakdown table, and a classification decision flowchart is documented in [README.md: Item Architecture](../README.md#item-architecture). Consult that section for the visual inheritance tree and per-class details.
