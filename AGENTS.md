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

# Work on Items

## Goal

Systematically migrate every item in `ModItems.java` from plain `Item` to the proper class, add its crafting recipe, and add a recipe page to the Patchouli guidebook. Track progress in the README items table.

## Automation

Use the helper script to generate all registration code, recipe JSON, and Patchouli entry JSON instantly:

```bash
python3 .opencode/scripts/item_helper.py <slug>
# Example:
python3 .opencode/scripts/item_helper.py abyssal_mask
```

The script outputs ready-to-paste code for steps 2-6 below. If the script's output looks correct, you can skip most of the manual steps and just apply the changes.

## Workflow (per item)

When the user says "I want to work on items" or names a specific item:

1. **Run the helper script**:
   ```
   python3 .opencode/scripts/item_helper.py <slug>
   ```
   This prints the classification (FullItem/ComponentItem), ingredient slugs, registration code, recipe JSON, and Patchouli entry JSON.

2. **Classify** (confirm or adjust from script output):
   - Boot → `BootItem(speed, stats)` (already done for ~22 boots)
   - Potion → `PotionItem` (already done for ~9 potions)
   - Ward/trinket → `WardItem` (already done for 4 wards)
   - `depth == 3` and `into[]` is empty → `FullItem`
   - `depth == 0` through `depth == 2` or has non-empty `into[]` → `ComponentItem`
   - Special case (meme item, wardstone, etc.) → plain `Item`
   - Ask the user to confirm

3. **Update `ModItems.java`**: Paste the script's registration line, replacing the old `registerItem("slug")` line.

4. **Create recipe** at `recipe/<slug>.json`: Use the script's recipe JSON output if provided. If the script shows "(No recipe — depth-0 starter item with no from[] ingredients)", ask the user for recipe ingredients before proceeding. Do not skip asking — wait for the user to specify what ingredients/materials to use.

5. **Add Patchouli entry** at `patchouli_books/guidebook/en_us/entries/items/<slug>.json`: Use the script's entry JSON output. Always ask the user whether they want a crafting page added if the item has a recipe.

6. **Update README.md**: Change "Draft" to "Partially Implemented" (or "Fully Implemented" if the item has both a crafting recipe and stats — see convention below).

## Completion status conventions

| Status | Meaning |
|--------|---------|
| Draft | Plain `registerItem("slug")`, no stats, no recipe |
| Partially Implemented | Registered with proper class + stats, no recipe yet (e.g. FullItems) |
| Fully Implemented | Registered with proper class + stats + recipe (relevant for ComponentItems) |

A `ComponentItem` is **Fully Implemented** once it has both a `ComponentItem` registration with stats and a crafting recipe JSON. A `FullItem` (depth 3, terminal) is **Partially Implemented** once it has a `FullItem` registration with stats, and becomes **Fully Implemented** once it also has a recipe.

**Always fetch the wiki** to validate stats. Use the script output for code generation, but double-check the stats against the wiki page.

## Item Checklist

Below is the full list of items. As each is completed, either remove it from this list or check it off.

| Item | Status | Notes |
|------|--------|-------|
| Actualizer | Pending | depth=3, into=[], From: Lost Chapter + Blasting Wand |
| Aether Wisp | Pending | depth=2, into: Ardent Censer/Cosmic Drive/etc, From: Amplifying Tome |
| Amplifying Tome | Fully Implemented | depth=0, into: many |
| Anathema's Chains | Pending | depth=3, into=[], From: Kindlegem + Giant's Belt |
| Archangel's Staff | Pending | depth=3, into=[], From: Tear + Lost Chapter + Fiendish Codex |
| Ardent Censer | Pending | depth=3, into=[], From: Aether Wisp + Amplifying Tome + Forbidden Idol |
| Atma's Reckoning | Pending | depth=3, into=[], From: Giant's Belt + Cloak of Agility + Giant's Belt |
| Axiom Arc | Pending | depth=3, into=[], From: Caulfield's + Serrated Dirk |
| B. F. Sword | Fully Implemented | depth=0, into: many |
| Bami's Cinder | Pending | depth=2, into: Sunfire/Hollow Radiance, From: Ruby Crystal + ? |
| Bandleglass Mirror | Pending | depth=2, into: many, From: Faerie Charm + Amplifying Tome + ? |
| Bandlepipes | Pending | depth=3, into=[], From: Kindlegem + Cloth Armor + Null-Magic Mantle |
| Banshee's Veil | Pending | depth=3, into=[], From: Needlessly Large Rod + Negatron Cloak |
| Bastionbreaker | Pending | depth=3, into=[], From: Caulfield's + Pickaxe |
| Black Cleaver | Pending | depth=3, into=[], From: Phage + Kindlegem + Pickaxe |
| Black Mist Scythe | Pending | depth=0, into=[], support starter |
| Blackfire Torch | Pending | depth=3, into=[], From: Lost Chapter + Fated Ashes |
| Blade of the Ruined King | Pending | depth=3, into=[], From: Vamp Scepter + Recurve Bow + Pickaxe |
| Blasting Wand | Pending | depth=0, into: many |
| Blighting Jewel | Pending | depth=2, into: Void Staff/Cryptbloom, From: Amplifying Tome |
| Bloodletter's Curse | Pending | depth=3, into=[], From: Haunting Guise + Fiendish Codex |
| Bloodsong | Pending | depth=2, from: Bounty of Worlds |
| Bloodthirster | Pending | depth=3, into=[], From: B.F. Sword + Pickaxe + Vamp Scepter |
| Bounty of Worlds | Pending | depth=0, into: Bloodsong/Celestial/etc |
| Bramble Vest | Pending | depth=2, into: Thornmail, From: Cloth Armor x2 |
| Bulwark of the Mountain | Pending | depth=0, into: |
| Catalyst of Aeons | Pending | depth=2, into: RoA/..., From: Ruby Crystal x2 + Sapphire Crystal |
| Caulfield's Warhammer | Pending | depth=2, into: many, From: Long Sword x2 + ? |
| Celestial Opposition | Pending | depth=2, from: Bounty of Worlds |
| Chain Vest | Pending | depth=2, into: many, From: Cloth Armor |
| Chalice of Blessing | Pending | depth=2, From: Ruby Crystal + Faerie Charm |
| Chempunk Chainsword | Pending | depth=3, into=[], From: Executioner's + Giant's Belt + Caulfield's |
| Chemtech Putrifier | Pending | depth=3, into=[], From: Oblivion Orb + Forbidden Idol |
| Cloak of Agility | Pending | depth=0, into: many |
| Cloak of Starry Night | Pending | depth=0, into=[] |
| Cloth Armor | Pending | depth=0, into: many |
| Cosmic Drive | Pending | depth=3, into=[], From: Kindlegem + Aether Wisp + Fiendish Codex |
| Crystalline Bracer | Pending | depth=2, into: Warmog's/..., From: Ruby Crystal + Rejuvenation Bead |
| Cull | Pending | depth=0, into=[] |
| Dagger | Pending | depth=0, into: many |
| Dark Seal | Pending | depth=0, into: Mejai's |
| Dawncore | Pending | depth=3, into=[], From: Blasting Wand + Forbidden Idol x2 |
| Dead Man's Plate | Pending | depth=3, into=[], From: ? + Ruby Crystal + Chain Vest |
| Death's Dance | Pending | depth=3, into=[], From: ? + Pickaxe + Caulfield's |
| Demonic Embrace | Pending | depth=3, into=[], From: Blasting Wand + Giant's Belt + Amplifying Tome |
| Diadem of Songs | Pending | depth=0, into=[] |
| Divine Sunderer | Pending | depth=3, into=[], From: Caulfield's + Sheen + Kindlegem |
| Doran's Blade | Pending | depth=0, into=[] |
| Doran's Bow | Pending | depth=0, into=[] |
| Doran's Helm | Pending | depth=0, into=[] |
| Doran's Ring | Pending | depth=0, into=[] |
| Doran's Shield | Pending | depth=0, into=[] |
| Dream Maker | Pending | depth=2, from: Bounty of Worlds |
| Dusk and Dawn | Pending | depth=3, into=[] |
| Duskblade of Draktharr | Pending | depth=3, into=[] |
| Echoes of Helia | Pending | depth=3, into=[] |
| Eclipse | Pending | depth=3, into=[] |
| Edge of Night | Pending | depth=3, into=[] |
| Emberknife | Pending | depth=0, into=[] |
| Endless Hunger | Pending | depth=3, into=[] |
| Essence Reaver | Pending | depth=3, into=[] |
| Evenshroud | Pending | depth=3, into=[] |
| Everfrost | Pending | depth=3, into=[] |
| Executioner's Calling | Pending | depth=2, into: Chempunk Chainsword/Mortal Reminder |
| Experimental Hexplate | Pending | depth=3, into=[] |
| Faerie Charm | Pending | depth=0, into: many |
| Fated Ashes | Pending | depth=2, into: Liandry's/Blackfire Torch, From: Amplifying Tome |
| Fiendhunter Bolts | Pending | depth=3, into=[] |
| Fiendish Codex | Pending | depth=2, into: many |
| Fimbulwinter | Pending | depth=3, into=[] |
| Flesheater | Pending | depth=0, into=[] |
| Forbidden Idol | Pending | depth=2, into: many |
| Force of Nature | Pending | depth=3, into=[] |
| Frostfang | Pending | depth=0, into=[] |
| Frozen Heart | Pending | depth=3, into=[] |
| Galeforce | Pending | depth=3, into=[] |
| Gambler's Blade | Pending | depth=0, into=[] |
| Gargoyle Stoneplate | Pending | depth=0, into=[] |
| Giant's Belt | Pending | depth=0, into: many |
| Glacial Buckler | Pending | depth=2, into: Frozen Heart/Iceborn Gauntlet/etc |
| Glowing Mote | Pending | depth=0, into: many |
| Golden Spatula | Pending | depth=0, into=[] |
| Goredrinker | Pending | depth=3, into=[] |
| Guardian Angel | Pending | depth=3, into=[] |
| Guardian's Amulet | Pending | depth=0, into=[] |
| Guardian's Blade | Pending | depth=0, into=[] |
| Guardian's Dirk | Pending | depth=0, into=[] |
| Guardian's Hammer | Pending | depth=0, into=[] |
| Guardian's Horn | Pending | depth=0, into=[] |
| Guardian's Orb | Pending | depth=0, into=[] |
| Guardian's Shroud | Pending | depth=0, into=[] |
| Guinsoo's Rageblade | Pending | depth=3, into=[] |
| Gustwalker Hatchling | Pending | depth=0, into=[] |
| Hailblade | Pending | depth=0, into=[] |
| Harrowing Crescent | Pending | depth=3, into=[] |
| Haunting Guise | Pending | depth=2, into: Liandry's/Demonic Embrace/etc |
| Hearthbound Axe | Pending | depth=2, into: Trinity Force/Stridebreaker/etc |
| Heartsteel | Pending | depth=3, into=[] |
| Hexdrinker | Pending | depth=2, into: Maw of Malmortius |
| Hexoptics C44 | Pending | depth=3, into=[] |
| Hextech Alternator | Pending | depth=2, into: Hextech Rocketbelt/Night Harvester/etc |
| Hextech Gunblade | Pending | depth=3, into=[] |
| Hextech Rocketbelt | Pending | depth=3, into=[] |
| Hollow Radiance | Pending | depth=3, into=[] |
| Horizon Focus | Pending | depth=3, into=[] |
| Hubris | Pending | depth=3, into=[] |
| Hullbreaker | Pending | depth=3, into=[] |
| Iceborn Gauntlet | Pending | depth=3, into=[] |
| Immortal Shieldbow | Pending | depth=3, into=[] |
| Imperial Mandate | Pending | depth=3, into=[] |
| Infinity Edge | Pending | depth=3, into=[] |
| Innervating Locket | Pending | depth=3, into=[] |
| Ironspike Whip | Pending | depth=2, into: Goredrinker/Stridebreaker/etc |
| Jak'Sho, The Protean | Pending | depth=3, into=[] |
| Kaenic Rookern | Pending | depth=3, into=[] |
| Kindlegem | Pending | depth=2, into: many |
| Kircheis Shard | Pending | depth=2, into: Statikk Shiv/Stormrazor/RFC |
| Knight's Vow | Pending | depth=3, into=[] |
| Kraken Slayer | Pending | depth=3, into=[] |
| Last Whisper | Pending | depth=2, into: Lord Dom's/Mortal Reminder |
| Liandry's Torment | Pending | depth=3, into=[] |
| Lich Bane | Pending | depth=3, into=[] |
| Lifeline | Pending | depth=2, into: Sterak's/Immortal Shieldbow/etc |
| Lifewell Pendant | Pending | depth=0?, into=[] |
| Locket of the Iron Solari | Pending | depth=3, into=[] |
| Long Sword | Fully Implemented | depth=0, into: many |
| Lord Dominik's Regards | Pending | depth=3, into=[] |
| Lost Chapter | Pending | depth=2, into: Luden's/Archangel's/Blackfire Torch/etc |
| Luden's Echo | Pending | depth=3, into=[] |
| Malignance | Pending | depth=3, into=[] |
| Manamune | Pending | depth=3, into=[] |
| Maw of Malmortius | Pending | depth=3, into=[] |
| Mejai's Soulstealer | Pending | depth=3, into=[] |
| Mercurial Scimitar | Pending | depth=3, into=[] |
| Mikael's Blessing | Pending | depth=3, into=[] |
| Moonstone Renewer | Pending | depth=3, into=[] |
| Morellonomicon | Pending | depth=3, into=[] |
| Mortal Reminder | Pending | depth=3, into=[] |
| Mosstomper Seedling | Pending | depth=0, into=[] |
| Multitool | Pending | depth=0, into=[] |
| Muramana | Pending | depth=3, into=[] |
| Nashor's Tooth | Pending | depth=3, into=[] |
| Navori Flickerblade | Pending | depth=3, into=[] |
| Needlessly Large Rod | Pending | depth=0, into: many |
| Negatron Cloak | Pending | depth=0, into: many |
| Night Harvester | Pending | depth=3, into=[] |
| Noonquiver | Pending | depth=2, into: many |
| Null-Magic Mantle | Pending | depth=0, into: many |
| Oblivion Orb | Pending | depth=2, into: Morellonomicon/Chemtech Putrifier |
| Obsidian Edge | Pending | depth=0, into=[] |
| Opportunity | Pending | depth=3, into=[] |
| Overlord's Bloodmail | Pending | depth=3, into=[] |
| Pauldrons of Whiterock | Pending | depth=3, into=[] |
| Phage | Pending | depth=2, into: Trinity Force/Black Cleaver/etc |
| Phantom Dancer | Pending | depth=3, into=[] |
| Pickaxe | Pending | depth=0, into: many |
| Profane Hydra | Pending | depth=3, into=[] |
| Protoplasm Harness | Pending | depth=3, into=[] |
| Prowler's Claw | Pending | depth=3, into=[] |
| Quicksilver Sash | Pending | depth=2, into: Mercurial Scimitar/Silvermere Dawn |
| Rabadon's Deathcap | Pending | depth=3, into=[] |
| Radiant Virtue | Pending | depth=3, into=[] |
| Rageknife | Pending | depth=2, into: Guinsoo's |
| Randuin's Omen | Pending | depth=3, into=[] |
| Rapid Firecannon | Pending | depth=3, into=[] |
| Ravenous Hydra | Pending | depth=3, into=[] |
| Rectrix | Pending | depth=2, into: many |
| Recurve Bow | Pending | depth=2, into: many |
| Redemption | Pending | depth=3, into=[] |
| Rejuvenation Bead | Pending | depth=0, into: many |
| Relic Shield | Pending | depth=0, into=[] |
| Riftmaker | Pending | depth=3, into=[] |
| Rite of Ruin | Pending | depth=3, into=[] |
| Rod of Ages | Pending | depth=3, into=[] |
| Ruby Crystal | Pending | depth=0, into: many |
| Runaan's Hurricane | Pending | depth=3, into=[] |
| Runesteel Spaulders | Pending | depth=0, into=[] |
| Runic Compass | Pending | depth=0, into=[] |
| Rylai's Crystal Scepter | Pending | depth=3, into=[] |
| Sapphire Crystal | Pending | depth=0, into: many |
| Scorchclaw Pup | Pending | depth=0, into=[] |
| Scout's Slingshot | Pending | depth=2, into: RFC/Statikk/etc |
| Seeker's Armguard | Pending | depth=2, into: Zhonya's |
| Seraph's Embrace | Pending | depth=3, into=[] |
| Serpent's Fang | Pending | depth=3, into=[] |
| Serrated Dirk | Pending | depth=2, into: many |
| Serylda's Grudge | Pending | depth=3, into=[] |
| Shadowflame | Pending | depth=3, into=[] |
| Shattered Armguard | Pending | depth=0, into=[] |
| Sheen | Pending | depth=2, into: Trinity Force/Lich Bane/Essence Reaver/etc |
| Shield of Molten Stone | Pending | depth=2, into=[] |
| Shurelya's Battlesong | Pending | depth=3, into=[] |
| Silvermere Dawn | Pending | depth=3, into=[] |
| Solstice Sleigh | Pending | depth=2, from: Bounty of Worlds |
| Spear of Shojin | Pending | depth=3, into=[] |
| Spectral Cutlass | Pending | depth=0? |
| Spectral Sickle | Pending | depth=0, into=[] |
| Spectre's Cowl | Pending | depth=2, into: Spirit Visage/etc |
| Spellthief's Edge | Pending | depth=0, into=[] |
| Spirit Visage | Pending | depth=3, into=[] |
| Staff of Flowing Water | Pending | depth=3, into=[] |
| Statikk Shiv | Pending | depth=3, into=[] |
| Steel Shoulderguards | Pending | depth=0, into=[] |
| Steel Sigil | Pending | depth=2, into: ? |
| Sterak's Gage | Pending | depth=3, into=[] |
| Stirring Wardstone | Pending | depth=0, into=[] |
| Stormrazor | Pending | depth=3, into=[] |
| Stormsurge | Pending | depth=3, into=[] |
| Stridebreaker | Pending | depth=3, into=[] |
| Sundered Sky | Pending | depth=3, into=[] |
| Sunfire Aegis | Pending | depth=3, into=[] |
| Sword of Blossoming Dawn | Pending | depth=3, into=[] |
| Sword of the Divine | Pending | depth=0, into=[] |
| Targon's Buckler | Pending | depth=0, into=[] |
| Tear of the Goddess | Pending | depth=0, into: Archangel's/Manamune |
| Terminus | Pending | depth=3, into=[] |
| The Brutalizer | Pending | depth=2, into: ? |
| The Collector | Pending | depth=3, into=[] |
| The Golden Spatula | Pending | depth=3?, into=[] |
| Thornmail | Pending | depth=3, into=[] |
| Tiamat | Pending | depth=2, into: Ravenous/Titanic/Profane Hydra |
| Titanic Hydra | Pending | depth=3, into=[] |
| Total Biscuit of Everlasting Will | Pending | depth=0, into=[] |
| Trailblazer | Pending | depth=3, into=[] |
| Trinity Force | Pending | depth=3, into=[] |
| Tunneler | Pending | depth=3, into=[] |
| Twin Mask | Pending | depth=0, into=[] |
| Umbral Glaive | Pending | depth=3, into=[] |
| Unending Despair | Pending | depth=3, into=[] |
| Vampiric Scepter | Pending | depth=2, into: many |
| Veigar's Talisman of Ascension | Pending | depth=0, into=[] |
| Verdant Barrier | Pending | depth=0, into=[] |
| Vigilant Wardstone | Pending | depth=0, into=[] |
| Void Immolation | Pending | depth=3, into=[] |
| Void Staff | Pending | depth=3, into=[] |
| Voltaic Cyclosword | Pending | depth=3, into=[] |
| Warden's Mail | Pending | depth=2, into: Randuin's/etc |
| Warmog's Armor | Pending | depth=3, into=[] |
| Watchful Wardstone | Pending | depth=0, into=[] |
| Whispering Circlet | Pending | depth=0?, into=[] |
| Winged Moonplate | Pending | depth=2, into: Dead Man's/Force of Nature/etc |
| Winter's Approach | Pending | depth=3, into=[] |
| Wit's End | Pending | depth=3, into=[] |
| Wooglet's Witchcap | Pending | depth=0, into=[] |
| World Atlas | Pending | depth=0, into=[] |
| Youmuu's Ghostblade | Pending | depth=3, into=[] |
| Yun Tal Wildarrows | Pending | depth=3, into=[] |
| Zaz'Zak's Realmspike | Pending | depth=2, from: Bounty of Worlds |
| Zeal | Pending | depth=2, into: many |
| Zeke's Convergence | Pending | depth=3, into=[] |
| Zephyr | Pending | depth=0, into=[] |
| Zhonya's Hourglass | Pending | depth=3, into=[] |
