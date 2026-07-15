# Testing Exploration — Fabric Mod Testing Approaches

## Project Context

- **Fabric mod** for **Minecraft 1.21** (Java 21)
- **Mod ID:** `league-of-crafters`
- **Gradle build** with Fabric Loom

---

## Testing Approaches Overview

| Approach | What it tests | Needs Minecraft Runtime? | Complexity |
|---|---|---|---|
| **JUnit 5** | Pure logic, data parsing, calculations | No | Low |
| **Fabric GameTest** | In-game mechanics (item equipping, damage, attributes) | Yes (spawns a fake world) | Medium |
| **TestMod (`src/testmod/`)** | Full mod integration with Fabric APIs | Yes | Medium-High |

---

## Approach 1: JUnit 5 — Unit Tests (No Minecraft)

Best for testing code that doesn't depend on Minecraft classes — e.g. parsing `items_data.json`, calculating damage formulas, validating stat values.

### Setup

Add to `build.gradle`:

```gradle
dependencies {
    // ... existing dependencies ...

    testImplementation "org.junit.jupiter:junit-jupiter:5.11.4"
    testRuntimeOnly "org.junit.platform:junit-platform-launcher"
}

test {
    useJUnitPlatform()
}
```

### Example: Test a stat calculation function

Assuming you have a utility method like `DamageCalculator.calculateDamage(float baseDmg, int armor)`:

```java
package com.leagueofcrafters;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DamageCalculatorTest {
    @Test
    void calculateDamage_withZeroArmor_returnsBaseDamage() {
        float result = DamageCalculator.calculateDamage(100, 0);
        assertEquals(100, result, 0.001);
    }

    @Test
    void calculateDamage_reducesByArmor() {
        float result = DamageCalculator.calculateDamage(100, 50);
        assertTrue(result < 100);
    }
}
```

Limitation: You **cannot** instantiate `ArmorItem`, `Player`, `ItemStack`, etc. without bootstrapping Minecraft.

---

## Approach 2: Fabric GameTest — In-Game Integration Tests

GameTest runs tests inside a Minecraft world (simulated server). This is what you need for "equip a chestplate → check armor value."

Fabric GameTest ships with Fabric Loom — no extra dependencies needed. Tests go in:

```
src/test/java/com/leagueofcrafters/test/
```

### Documentation & Links

- [Fabric Wiki: GameTest](https://fabricmc.net/wiki/tutorial:gametest)
- [Fabric GameTest API Javadoc](https://maven.fabricmc.net/docs/fabric-api/latest/net/fabricmc/fabric/api/gametest/v1/package-summary.html)
- [Minecraft Wiki: GameTest](https://minecraft.wiki/w/GameTest)
- [Example Repo: Fabric Gametest Examples](https://github.com/FabricMC/fabric-gametest-api-v1)

### How It Works

1. Create a test class with methods annotated `@GameTest`
2. Each method receives a `TestContext` with helper methods
3. Use `.spawnEntity()`, `.useBlock()`, `.killEntity()` etc.
4. Assert with `.assertEntityHasComponent()`, `.assertEntityProperty()` etc.
5. Run via Gradle: `./gradlew runGametest`

### Concrete Example: Equip Chestplate & Check Armor

This requires:
- `ArmorMaterial` registration + an `ArmorItem` that uses it
- A test that equips the item on a player and checks `Attributes.ARMOR`

**Step 1 — Register an ArmorMaterial (in your main source set):**

```java
package com.leagueofcrafters.item;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.sounds.SoundEvents;
import java.util.List;
import java.util.Map;

public class ModArmorMaterials {
    public static final Holder<ArmorMaterial> CLOTH = ArmorMaterial.register(
        "league-of-crafters:cloth",
        Map.of(
            ArmorItem.Type.CHESTPLATE, 3,
            ArmorItem.Type.LEGGINGS, 2,
            ArmorItem.Type.HELMET, 1,
            ArmorItem.Type.BOOTS, 1
        ),
        15,
        SoundEvents.ARMOR_EQUIP_LEATHER,
        () -> Ingredient.of(Items.LEATHER),
        List.of(new ArmorMaterial.Layer(
            net.minecraft.resources.ResourceLocation.parse("league-of-crafters:cloth")
        )),
        0,
        0
    );
}
```

**Step 2 — Register the Chestplate as an ArmorItem (in ModItems):**

```java
public static final Item CLOTH_CHESTPLATE = registerCustomItem("cloth_chestplate",
    new ArmorItem(ModArmorMaterials.CLOTH, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
```

**Step 3 — Create a GameTest class:**

```java
package com.leagueofcrafters.test;

import com.leagueofcrafters.item.ModItems;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ArmorItem;

public class ArmorTest implements FabricGameTest {

    // The GameTest framework spawns a player for us
    @GameTest(template = "fabric-gametest-api-v1:empty")
    public void clothChestplateGivesCorrectArmor(GameTestHelper helper) {
        // 1. Get the player
        Player player = helper.makeMockPlayer();

        // 2. Give them the chestplate
        ItemStack chestplate = new ItemStack(ModItems.CLOTH_CHESTPLATE);
        player.setItemSlot(EquipmentSlot.CHEST, chestplate);

        // 3. Check the armor attribute
        double armorValue = player.getArmorValue();

        // 4. Assert it matches our material definition (3 for chestplate)
        helper.assertTrue(armorValue == 3,
            "Expected armor value 3, got " + armorValue);

        helper.succeed();
    }

    @GameTest(template = "fabric-gametest-api-v1:empty")
    public void multipleArmorPiecesStack(GameTestHelper helper) {
        Player player = helper.makeMockPlayer();

        player.setItemSlot(EquipmentSlot.CHEST,
            new ItemStack(ModItems.CLOTH_CHESTPLATE)); // 3 armor
        player.setItemSlot(EquipmentSlot.LEGS,
            new ItemStack(ModItems.CLOTH_LEGGINGS));   // 2 armor

        // Total should be 5
        helper.assertTrue(player.getArmorValue() == 5,
            "Expected 5 armor, got " + player.getArmorValue());

        helper.succeed();
    }
}
```

### Running GameTests

```bash
./gradlew runGametest
```

This boots a headless server, runs all `@GameTest` methods, and reports pass/fail.

### Test Template Explanation

The `template` parameter in `@GameTest(template = "fabric-gametest-api-v1:empty")` refers to a structure block file (a `.nbt` template). `fabric-gametest-api-v1:empty` is a built-in empty 1×1×1 template provided by the Fabric API — no world setup needed.

---

## Approach 3: TestMod (`src/testmod/`) — Full Integration

For tests that need to register their own blocks/items/entities or use Fabric APIs heavily. Uses a separate source set with its own `fabric.mod.json`.

### Setup

In `build.gradle`:

```gradle
loom {
    // ... existing loom config ...
    runs {
        gametest {
            inherit server
            name "Game Test"
            vmArg "-Dfabric-api.gametest"
            source sourceSets.testmod
        }
    }
}

sourceSets {
    testmod {
        compileClasspath += sourceSets.main.output
        runtimeClasspath += sourceSets.main.output
    }
}
```

### Structure

```
src/testmod/
  java/com/leagueofcrafters/test/
      ArmorGameTest.java
  resources/
      fabric.mod.json
```

---

## Recommendation

Start with **JUnit 5** for any isolated logic (parsing, calculations, data validation). When you need to test in-game behavior like equipping armor or applying attributes, add **Fabric GameTest** — it's the closest to your "equip chestplate → check armor" scenario without needing to launch a full Minecraft client.
