package com.leagueofcrafters.item;

import com.leagueofcrafters.Leagueofcrafters;
import com.leagueofcrafters.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import java.util.*;
import java.util.function.BiConsumer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ModItems {
    private static final Map<String, Item> ITEMS = new LinkedHashMap<>();

    public static final Item LEAGUE_COIN = registerItem("league_coin");
    public static final Item GUIDEBOOK = registerCustomItem("guidebook", new GuidebookItem(new Item.Properties()));
    public static final Item ABYSSAL_MASK = registerCustomItem("abyssal_mask", new FullItem(new Item.Properties(), Map.of("Magic Resist", 50f, "Health", 350f)));
    public static final Item ACTUALIZER = registerCustomItem("actualizer", new FullItem(new Item.Properties(), Map.of("Mana", 300f, "Ability Power", 90f)));
    public static final Item AETHER_WISP = registerCustomItem("aether_wisp", new ComponentItem(new Item.Properties(), Map.of("Ability Power", 30f, "Move Speed", 0.04f)));
    public static final Item AMPLIFYING_TOME = registerCustomItem("amplifying_tome", new ComponentItem(new Item.Properties(), Map.of("Ability Power", 20f)));
    public static final Item ANATHEMA_S_CHAINS = registerCustomItem("anathema_s_chains", new FullItem(new Item.Properties(), Map.of("Health", 650f)));
    public static final Item ARCHANGEL_S_STAFF = registerCustomItem("archangel_s_staff", new FullItem(new Item.Properties(), Map.of("Mana", 600f, "Ability Power", 70f)));
    public static final Item ARDENT_CENSER = registerCustomItem("ardent_censer", new FullItem(new Item.Properties(), Map.of("Ability Power", 55f, "Move Speed", 0.06f)));
    public static final Item ARMORED_ADVANCE = registerCustomItem("armored_advance", new BootItem(45f, new Item.Properties()));
    public static final Item ATMA_S_RECKONING = registerCustomItem("atma_s_reckoning", new FullItem(new Item.Properties(), Map.of("Health", 700f, "Critical Strike Chance", 0.2f)));
    public static final Item AXIOM_ARC = registerCustomItem("axiom_arc", new FullItem(new Item.Properties(), Map.of("Attack Damage", 55f)));
    public static final Item B_F_SWORD = registerCustomItem("b_f_sword", new ComponentItem(new Item.Properties(), Map.of("Attack Damage", 40f)));
    public static final Item BAMI_S_CINDER = registerCustomItem("bami_s_cinder", new ComponentItem(new Item.Properties(), Map.of("Health", 150f)));
    public static final Item BANDLEGLASS_MIRROR = registerCustomItem("bandleglass_mirror", new ComponentItem(new Item.Properties(), Map.of("Ability Power", 20f)));
    public static final Item BANDLEPIPES = registerCustomItem("bandlepipes", new FullItem(new Item.Properties(), Map.of("Magic Resist", 20f, "Health", 200f, "Armor", 20f)));
    public static final Item BANSHEE_S_VEIL = registerCustomItem("banshee_s_veil", new FullItem(new Item.Properties(), Map.of("Magic Resist", 40f, "Ability Power", 105f)));
    public static final Item BASTIONBREAKER = registerCustomItem("bastionbreaker", new FullItem(new Item.Properties(), Map.of("Attack Damage", 55f)));
    public static final Item BERSERKER_S_GREAVES = registerCustomItem("berserker_s_greaves", new BootItem(45f, new Item.Properties()));
    public static final Item BLACK_CLEAVER = registerCustomItem("black_cleaver", new FullItem(new Item.Properties(), Map.of("Health", 400f, "Attack Damage", 40f)));
    public static final Item BLACK_MIST_SCYTHE = registerItem("black_mist_scythe");
    public static final Item BLACKFIRE_TORCH = registerCustomItem("blackfire_torch", new FullItem(new Item.Properties(), Map.of("Mana", 600f, "Ability Power", 80f)));
    public static final Item BLADE_OF_THE_RUINED_KING = registerCustomItem("blade_of_the_ruined_king", new FullItem(new Item.Properties(), Map.of("Attack Damage", 40f, "Attack Speed", 0.25f, "Life Steal", 0.1f)));
    public static final Item BLASTING_WAND = registerCustomItem("blasting_wand", new ComponentItem(new Item.Properties(), Map.of("Ability Power", 45f)));
    public static final Item BLIGHTING_JEWEL = registerCustomItem("blighting_jewel", new ComponentItem(new Item.Properties(), Map.of("Ability Power", 25f)));
    public static final Item BLOODLETTER_S_CURSE = registerCustomItem("bloodletter_s_curse", new FullItem(new Item.Properties(), Map.of("Health", 400f, "Ability Power", 65f)));
    public static final Item BLOODSONG = registerCustomItem("bloodsong", new ComponentItem(new Item.Properties(), Map.of("Health", 200f)));
    public static final Item BLOODTHIRSTER = registerCustomItem("bloodthirster", new FullItem(new Item.Properties(), Map.of("Attack Damage", 80f, "Life Steal", 0.15f)));
    public static final Item BOOTS = registerCustomItem("boots", new BootItem(25f, new Item.Properties()));
    public static final Item BOOTS_OF_SWIFTNESS = registerCustomItem("boots_of_swiftness", new BootItem(55f, new Item.Properties()));
    public static final Item BOUNTY_OF_WORLDS = registerItem("bounty_of_worlds");
    public static final Item BRAMBLE_VEST = registerCustomItem("bramble_vest", new ComponentItem(new Item.Properties(), Map.of("Armor", 30f)));
    public static final Item BULWARK_OF_THE_MOUNTAIN = registerItem("bulwark_of_the_mountain");
    public static final Item CAPPA_JUICE = registerCustomItem("cappa_juice", new PotionItem(new Item.Properties(), (level, player) -> {
        // Does nothing — Cappa Juice
    }));
    public static final Item CATALYST_OF_AEONS = registerCustomItem("catalyst_of_aeons", new ComponentItem(new Item.Properties(), Map.of("Mana", 375f, "Health", 300f)));
    public static final Item CAULFIELD_S_WARHAMMER = registerCustomItem("caulfield_s_warhammer", new ComponentItem(new Item.Properties(), Map.of("Attack Damage", 20f)));
    public static final Item CELESTIAL_OPPOSITION = registerCustomItem("celestial_opposition", new ComponentItem(new Item.Properties(), Map.of("Health", 200f)));
    public static final Item CHAIN_VEST = registerCustomItem("chain_vest", new ComponentItem(new Item.Properties(), Map.of("Armor", 40f)));
    public static final Item CHAINLACED_CRUSHERS = registerCustomItem("chainlaced_crushers", new BootItem(45f, new Item.Properties()));
    public static final Item CHALICE_OF_BLESSING = registerCustomItem("chalice_of_blessing", new ComponentItem(new Item.Properties(), Map.of("Health", 200f)));
    public static final Item CHEMPUNK_CHAINSWORD = registerCustomItem("chempunk_chainsword", new FullItem(new Item.Properties(), Map.of("Health", 450f, "Attack Damage", 45f)));
    public static final Item CHEMTECH_PUTRIFIER = registerCustomItem("chemtech_putrifier", new FullItem(new Item.Properties(), Map.of("Ability Power", 35f)));
    public static final Item CLOAK_OF_AGILITY = registerCustomItem("cloak_of_agility", new ComponentItem(new Item.Properties(), Map.of("Critical Strike Chance", 0.15f)));
    public static final Item CLOAK_OF_STARRY_NIGHT = registerItem("cloak_of_starry_night");
    public static final Item CLOTH_ARMOR = registerCustomItem("cloth_armor", new ComponentItem(new Item.Properties(), Map.of("Armor", 15f)));
    public static final Item CONTROL_WARD = registerCustomItem("control_ward", new WardItem(new Item.Properties(), ModBlocks.CONTROL_WARD_BLOCK));
    public static final Item CORRUPTING_POTION = registerCustomItem("corrupting_potion", new PotionItem(new Item.Properties(), (level, player) -> {
        player.heal(4.0f);
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 240, 1));
    }));
    public static final Item COSMIC_DRIVE = registerCustomItem("cosmic_drive", new FullItem(new Item.Properties(), Map.of("Health", 350f, "Ability Power", 70f, "Move Speed", 0.04f)));
    public static final Item CRIMSON_LUCIDITY = registerCustomItem("crimson_lucidity", new BootItem(45f, new Item.Properties()));
    public static final Item CROWN_OF_THE_SHATTERED_QUEEN = registerItem("crown_of_the_shattered_queen");
    public static final Item CRUELTY = registerItem("cruelty");
    public static final Item CRYPTBLOOM = registerCustomItem("cryptbloom", new FullItem(new Item.Properties(), Map.of("Ability Power", 75f)));
    public static final Item CRYSTALLINE_BRACER = registerCustomItem("crystalline_bracer", new ComponentItem(new Item.Properties(), Map.of("Health", 200f)));
    public static final Item CULL = registerItem("cull");
    public static final Item DAGGER = registerCustomItem("dagger", new ComponentItem(new Item.Properties(), Map.of("Attack Speed", 0.1f)));
    public static final Item DARK_SEAL = registerItem("dark_seal");
    public static final Item DAWNCORE = registerCustomItem("dawncore", new FullItem(new Item.Properties(), Map.of("Ability Power", 50f)));
    public static final Item DEAD_MAN_S_PLATE = registerCustomItem("dead_man_s_plate", new FullItem(new Item.Properties(), Map.of("Health", 350f, "Armor", 55f, "Move Speed", 0.04f)));
    public static final Item DEATH_S_DANCE = registerCustomItem("death_s_dance", new FullItem(new Item.Properties(), Map.of("Armor", 50f, "Attack Damage", 60f)));
    public static final Item DEATHFIRE_GRASP = registerCustomItem("deathfire_grasp", new FullItem(new Item.Properties(), Map.of("Ability Power", 120f)));
    public static final Item DEMONIC_EMBRACE = registerCustomItem("demonic_embrace", new FullItem(new Item.Properties(), Map.of("Health", 350f, "Ability Power", 75f)));
    public static final Item DIADEM_OF_SONGS = registerItem("diadem_of_songs");
    public static final Item DIVINE_SUNDERER = registerCustomItem("divine_sunderer", new FullItem(new Item.Properties(), Map.of("Health", 300f, "Attack Damage", 40f)));
    public static final Item DORAN_S_BLADE = registerItem("doran_s_blade");
    public static final Item DORAN_S_BOW = registerItem("doran_s_bow");
    public static final Item DORAN_S_HELM = registerItem("doran_s_helm");
    public static final Item DORAN_S_RING = registerItem("doran_s_ring");
    public static final Item DORAN_S_SHIELD = registerItem("doran_s_shield");
    public static final Item DREAM_MAKER = registerCustomItem("dream_maker", new ComponentItem(new Item.Properties(), Map.of("Health", 200f)));
    public static final Item DUSK_AND_DAWN = registerCustomItem("dusk_and_dawn", new FullItem(new Item.Properties(), Map.of("Health", 300f, "Ability Power", 60f, "Attack Speed", 0.2f)));
    public static final Item DUSKBLADE_OF_DRAKTHARR = registerCustomItem("duskblade_of_draktharr", new FullItem(new Item.Properties(), Map.of("Attack Damage", 60f)));
    public static final Item ECHOES_OF_HELIA = registerCustomItem("echoes_of_helia", new FullItem(new Item.Properties(), Map.of("Health", 250f, "Ability Power", 35f)));
    public static final Item ECLIPSE = registerCustomItem("eclipse", new FullItem(new Item.Properties(), Map.of("Attack Damage", 60f)));
    public static final Item EDGE_OF_NIGHT = registerCustomItem("edge_of_night", new FullItem(new Item.Properties(), Map.of("Health", 250f, "Attack Damage", 50f)));
    public static final Item ELIXIR_OF_AVARICE = registerCustomItem("elixir_of_avarice", new PotionItem(new Item.Properties(), (level, player) -> {
        player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 1200, 1));
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 1));
    }));
    public static final Item ELIXIR_OF_FORCE = registerCustomItem("elixir_of_force", new PotionItem(new Item.Properties(), (level, player) -> {
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 1));
    }));
    public static final Item ELIXIR_OF_IRON = registerCustomItem("elixir_of_iron", new PotionItem(new Item.Properties(), (level, player) -> {
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 3600, 0));
        player.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 3600, 3));
        player.heal(8.0f);
    }));
    public static final Item ELIXIR_OF_SORCERY = registerCustomItem("elixir_of_sorcery", new PotionItem(new Item.Properties(), (level, player) -> {
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 3600, 0));
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3600, 0));
    }));
    public static final Item ELIXIR_OF_WRATH = registerCustomItem("elixir_of_wrath", new PotionItem(new Item.Properties(), (level, player) -> {
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 3600, 1));
        player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 3600, 0));
    }));
    public static final Item EMBERKNIFE = registerItem("emberknife");
    public static final Item ENDLESS_HUNGER = registerCustomItem("endless_hunger", new FullItem(new Item.Properties(), Map.of("Attack Damage", 65f)));
    public static final Item ESSENCE_REAVER = registerCustomItem("essence_reaver", new FullItem(new Item.Properties(), Map.of("Critical Strike Chance", 0.25f, "Attack Damage", 50f)));
    public static final Item EVENSHROUD = registerItem("evenshroud");
    public static final Item EVERFROST = registerCustomItem("everfrost", new FullItem(new Item.Properties(), Map.of("Mana", 600f, "Health", 250f, "Ability Power", 70f)));
    public static final Item EXECUTIONER_S_CALLING = registerCustomItem("executioner_s_calling", new ComponentItem(new Item.Properties(), Map.of("Attack Damage", 15f)));
    public static final Item EXPERIMENTAL_HEXPLATE = registerCustomItem("experimental_hexplate", new FullItem(new Item.Properties(), Map.of("Health", 450f, "Attack Damage", 40f, "Attack Speed", 0.2f)));
    public static final Item FAERIE_CHARM = registerCustomItem("faerie_charm", new ComponentItem(new Item.Properties(), Map.of("Mana Regeneration", 0.5f)));
    public static final Item FARSIGHT_ALTERATION = registerCustomItem("farsight_alteration", new WardItem(new Item.Properties(), ModBlocks.FARSIGHT_ALTERATION_BLOCK));
    public static final Item FATED_ASHES = registerCustomItem("fated_ashes", new ComponentItem(new Item.Properties(), Map.of("Ability Power", 30f)));
    public static final Item FIENDHUNTER_BOLTS = registerCustomItem("fiendhunter_bolts", new FullItem(new Item.Properties(), Map.of("Critical Strike Chance", 0.25f, "Attack Speed", 0.45f, "Move Speed", 0.04f)));
    public static final Item FIENDISH_CODEX = registerCustomItem("fiendish_codex", new ComponentItem(new Item.Properties(), Map.of("Ability Power", 25f)));
    public static final Item FIMBULWINTER = registerItem("fimbulwinter");
    public static final Item FLESHEATER = registerItem("flesheater");
    public static final Item FORBIDDEN_IDOL = registerCustomItem("forbidden_idol", new ComponentItem(new Item.Properties()));
    public static final Item FORCE_OF_NATURE = registerCustomItem("force_of_nature", new FullItem(new Item.Properties(), Map.of("Magic Resist", 55f, "Health", 400f, "Move Speed", 0.04f)));
    public static final Item FOREVER_FORWARD = registerCustomItem("forever_forward", new BootItem(55f, new Item.Properties()));
    public static final Item FROSTFANG = registerItem("frostfang");
    public static final Item FROZEN_HEART = registerCustomItem("frozen_heart", new FullItem(new Item.Properties(), Map.of("Mana", 500f, "Armor", 75f)));

    public static final Item GALEFORCE = registerCustomItem("galeforce", new FullItem(new Item.Properties(), Map.of("Critical Strike Chance", 0.2f, "Attack Damage", 50f, "Attack Speed", 0.15f, "Move Speed", 0.07f)));
    public static final Item GAMBLER_S_BLADE = registerItem("gambler_s_blade");
    public static final Item GARGOYLE_STONEPLATE = registerItem("gargoyle_stoneplate");
    public static final Item GHOSTCRAWLERS = registerCustomItem("ghostcrawlers", new BootItem(45f, new Item.Properties()));
    public static final Item GIANT_S_BELT = registerCustomItem("giant_s_belt", new ComponentItem(new Item.Properties(), Map.of("Health", 350f)));
    public static final Item GLACIAL_BUCKLER = registerCustomItem("glacial_buckler", new ComponentItem(new Item.Properties(), Map.of("Mana", 300f, "Armor", 25f)));
    public static final Item GLOWING_MOTE = registerCustomItem("glowing_mote", new ComponentItem(new Item.Properties(), Map.of("Ability Haste", 5f)));
    public static final Item GLUTTONOUS_GREAVES = registerCustomItem("gluttonous_greaves", new BootItem(45f, new Item.Properties()));
    public static final Item GOLDEN_SPATULA = registerItem("golden_spatula");
    public static final Item GOREDRINKER = registerCustomItem("goredrinker", new FullItem(new Item.Properties(), Map.of("Health", 400f, "Attack Damage", 55f)));
    public static final Item GUARDIAN_ANGEL = registerCustomItem("guardian_angel", new FullItem(new Item.Properties(), Map.of("Armor", 45f, "Attack Damage", 55f)));
    public static final Item GUARDIAN_S_AMULET = registerItem("guardian_s_amulet");
    public static final Item GUARDIAN_S_BLADE = registerItem("guardian_s_blade");
    public static final Item GUARDIAN_S_DIRK = registerItem("guardian_s_dirk");
    public static final Item GUARDIAN_S_HAMMER = registerItem("guardian_s_hammer");
    public static final Item GUARDIAN_S_HORN = registerItem("guardian_s_horn");
    public static final Item GUARDIAN_S_ORB = registerItem("guardian_s_orb");
    public static final Item GUARDIAN_S_SHROUD = registerItem("guardian_s_shroud");
    public static final Item GUINSOO_S_RAGEBLADE = registerCustomItem("guinsoo_s_rageblade", new FullItem(new Item.Properties(), Map.of("Ability Power", 30f, "Attack Damage", 30f, "Attack Speed", 0.25f)));
    public static final Item GUNMETAL_GREAVES = registerCustomItem("gunmetal_greaves", new BootItem(45f, new Item.Properties()));

    public static final Item GUSTWALKER_HATCHLING = registerItem("gustwalker_hatchling");
    public static final Item HAILBLADE = registerItem("hailblade");
    public static final Item HARROWING_CRESCENT = registerItem("harrowing_crescent");
    public static final Item HAUNTING_GUISE = registerCustomItem("haunting_guise", new ComponentItem(new Item.Properties(), Map.of("Health", 200f, "Ability Power", 30f)));
    public static final Item HEALTH_POTION = registerCustomItem("health_potion", new PotionItem(new Item.Properties(), (level, player) -> {
        player.heal(8.0f);
    }));
    public static final Item HEARTHBOUND_AXE = registerCustomItem("hearthbound_axe", new ComponentItem(new Item.Properties(), Map.of("Attack Damage", 20f, "Attack Speed", 0.2f)));
    public static final Item HEARTSTEEL = registerCustomItem("heartsteel", new FullItem(new Item.Properties(), Map.of("Health", 900f)));
    public static final Item HEXDRINKER = registerCustomItem("hexdrinker", new ComponentItem(new Item.Properties(), Map.of("Magic Resist", 25f, "Attack Damage", 25f)));
    public static final Item HEXOPTICS_C44 = registerCustomItem("hexoptics_c44", new FullItem(new Item.Properties(), Map.of("Critical Strike Chance", 0.25f, "Attack Damage", 55f)));
    public static final Item HEXTECH_ALTERNATOR = registerCustomItem("hextech_alternator", new ComponentItem(new Item.Properties(), Map.of("Ability Power", 45f)));
    public static final Item HEXTECH_GUNBLADE = registerCustomItem("hextech_gunblade", new FullItem(new Item.Properties(), Map.of("Ability Power", 80f, "Attack Damage", 40f)));
    public static final Item HEXTECH_ROCKETBELT = registerCustomItem("hextech_rocketbelt", new FullItem(new Item.Properties(), Map.of("Health", 300f, "Ability Power", 70f)));
    public static final Item HOLLOW_RADIANCE = registerCustomItem("hollow_radiance", new FullItem(new Item.Properties(), Map.of("Magic Resist", 40f, "Health", 400f)));
    public static final Item HORIZON_FOCUS = registerCustomItem("horizon_focus", new FullItem(new Item.Properties(), Map.of("Ability Power", 75f)));
    public static final Item HUBRIS = registerCustomItem("hubris", new FullItem(new Item.Properties(), Map.of("Attack Damage", 55f)));
    public static final Item HULLBREAKER = registerCustomItem("hullbreaker", new FullItem(new Item.Properties(), Map.of("Health", 500f, "Attack Damage", 40f, "Move Speed", 0.04f)));
    public static final Item ICEBORN_GAUNTLET = registerCustomItem("iceborn_gauntlet", new FullItem(new Item.Properties(), Map.of("Health", 300f, "Armor", 50f)));
    public static final Item IMMORTAL_PATH = registerCustomItem("immortal_path", new BootItem(45f, new Item.Properties()));
    public static final Item IMMORTAL_SHIELDBOW = registerCustomItem("immortal_shieldbow", new FullItem(new Item.Properties(), Map.of("Critical Strike Chance", 0.25f, "Attack Damage", 55f)));
    public static final Item IMPERIAL_MANDATE = registerCustomItem("imperial_mandate", new FullItem(new Item.Properties(), Map.of("Ability Power", 60f)));
    public static final Item INFINITY_EDGE = registerCustomItem("infinity_edge", new ComponentItem(new Item.Properties(), Map.of("Critical Strike Chance", 0.25f, "Attack Damage", 75f)));
    public static final Item INNERVATING_LOCKET = registerCustomItem("innervating_locket", new FullItem(new Item.Properties(), Map.of("Mana", 300f, "Health", 400f, "Attack Damage", 30f)));
    public static final Item IONIAN_BOOTS_OF_LUCIDITY = registerCustomItem("ionian_boots_of_lucidity", new BootItem(45f, new Item.Properties()));
    public static final Item IRONSPIKE_WHIP = registerCustomItem("ironspike_whip", new ComponentItem(new Item.Properties(), Map.of("Attack Damage", 30f)));
    public static final Item JAK_SHO_THE_PROTEAN = registerCustomItem("jak_sho_the_protean", new FullItem(new Item.Properties(), Map.of("Magic Resist", 45f, "Health", 350f, "Armor", 45f)));
    public static final Item KAENIC_ROOKERN = registerCustomItem("kaenic_rookern", new FullItem(new Item.Properties(), Map.of("Magic Resist", 80f, "Health", 400f)));

    public static final Item KINDLEGEM = registerCustomItem("kindlegem", new ComponentItem(new Item.Properties(), Map.of("Health", 200f)));
    public static final Item KIRCHEIS_SHARD = registerCustomItem("kircheis_shard", new ComponentItem(new Item.Properties(), Map.of("Attack Damage", 15f)));
    public static final Item KNIGHT_S_VOW = registerCustomItem("knight_s_vow", new FullItem(new Item.Properties(), Map.of("Health", 300f, "Armor", 50f)));
    public static final Item KRAKEN_SLAYER = registerCustomItem("kraken_slayer", new FullItem(new Item.Properties(), Map.of("Attack Damage", 45f, "Attack Speed", 0.4f, "Move Speed", 0.04f)));
    public static final Item LAST_WHISPER = registerCustomItem("last_whisper", new ComponentItem(new Item.Properties(), Map.of("Attack Damage", 20f)));
    public static final Item LEECHING_LEER = registerCustomItem("leeching_leer", new ComponentItem(new Item.Properties(), Map.of("Health", 250f, "Ability Power", 20f)));

    public static final Item LIANDRY_S_TORMENT = registerCustomItem("liandry_s_torment", new FullItem(new Item.Properties(), Map.of("Health", 300f, "Ability Power", 60f)));
    public static final Item LICH_BANE = registerCustomItem("lich_bane", new FullItem(new Item.Properties(), Map.of("Ability Power", 100f, "Move Speed", 0.06f)));
    public static final Item LIFELINE = registerCustomItem("lifeline", new ComponentItem(new Item.Properties(), Map.of("Attack Damage", 25f, "Move Speed", 0.04f)));
    public static final Item LIFEWELL_PENDANT = registerCustomItem("lifewell_pendant", new ComponentItem(new Item.Properties(), Map.of("Health", 150f, "Armor", 25f)));

    public static final Item LOCKET_OF_THE_IRON_SOLARI = registerCustomItem("locket_of_the_iron_solari", new FullItem(new Item.Properties(), Map.of("Magic Resist", 30f, "Health", 250f, "Armor", 30f)));
    public static final Item LONG_SWORD = registerCustomItem("long_sword", new ComponentItem(new Item.Properties(), Map.of("Attack Damage", 10f)));
    public static final Item LORD_DOMINIK_S_REGARDS = registerCustomItem("lord_dominik_s_regards", new FullItem(new Item.Properties(), Map.of("Critical Strike Chance", 0.25f, "Attack Damage", 35f)));
    public static final Item LOST_CHAPTER = registerCustomItem("lost_chapter", new ComponentItem(new Item.Properties(), Map.of("Mana", 300f, "Ability Power", 40f)));
    public static final Item LUDEN_S_ECHO = registerCustomItem("luden_s_echo", new FullItem(new Item.Properties(), Map.of("Mana", 600f, "Ability Power", 100f)));
    public static final Item MALIGNANCE = registerCustomItem("malignance", new FullItem(new Item.Properties(), Map.of("Mana", 600f, "Ability Power", 90f)));
    public static final Item MANAMUNE = registerCustomItem("manamune", new FullItem(new Item.Properties(), Map.of("Mana", 500f, "Attack Damage", 35f)));
    public static final Item MAW_OF_MALMORTIUS = registerCustomItem("maw_of_malmortius", new FullItem(new Item.Properties(), Map.of("Magic Resist", 40f, "Attack Damage", 60f)));
    public static final Item MEJAI_S_SOULSTEALER = registerCustomItem("mejai_s_soulstealer", new ComponentItem(new Item.Properties(), Map.of("Health", 100f, "Ability Power", 20f)));
    public static final Item MERCURIAL_SCIMITAR = registerCustomItem("mercurial_scimitar", new FullItem(new Item.Properties(), Map.of("Magic Resist", 35f, "Attack Damage", 50f, "Life Steal", 0.1f)));
    public static final Item MERCURY_S_TREADS = registerCustomItem("mercury_s_treads", new BootItem(45f, new Item.Properties()));
    public static final Item MIKAEL_S_BLESSING = registerCustomItem("mikael_s_blessing", new FullItem(new Item.Properties(), Map.of("Health", 400f)));
    public static final Item MOBILITY_BOOTS = registerCustomItem("mobility_boots", new BootItem(45f, new Item.Properties()));
    public static final Item MOONSTONE_RENEWER = registerCustomItem("moonstone_renewer", new FullItem(new Item.Properties(), Map.of("Health", 400f, "Ability Power", 35f)));
    public static final Item MORELLONOMICON = registerCustomItem("morellonomicon", new FullItem(new Item.Properties(), Map.of("Health", 350f, "Ability Power", 75f)));
    public static final Item MORTAL_REMINDER = registerCustomItem("mortal_reminder", new FullItem(new Item.Properties(), Map.of("Critical Strike Chance", 0.25f, "Attack Damage", 35f)));
    public static final Item MOSSTOMPER_SEEDLING = registerItem("mosstomper_seedling");
    public static final Item MULTITOOL = registerItem("multitool");
    public static final Item MURAMANA = registerItem("muramana");
    public static final Item NASHOR_S_TOOTH = registerCustomItem("nashor_s_tooth", new FullItem(new Item.Properties(), Map.of("Ability Power", 80f, "Attack Speed", 0.5f)));
    public static final Item NAVORI_FLICKERBLADE = registerCustomItem("navori_flickerblade", new FullItem(new Item.Properties(), Map.of("Critical Strike Chance", 0.25f, "Attack Speed", 0.4f, "Move Speed", 0.04f)));
    public static final Item NEEDLESSLY_LARGE_ROD = registerCustomItem("needlessly_large_rod", new ComponentItem(new Item.Properties(), Map.of("Ability Power", 65f)));
    public static final Item NEGATRON_CLOAK = registerCustomItem("negatron_cloak", new ComponentItem(new Item.Properties(), Map.of("Magic Resist", 45f)));
    public static final Item NIGHT_HARVESTER = registerCustomItem("night_harvester", new FullItem(new Item.Properties(), Map.of("Health", 300f, "Ability Power", 90f)));
    public static final Item NOONQUIVER = registerCustomItem("noonquiver", new ComponentItem(new Item.Properties(), Map.of("Critical Strike Chance", 0.2f, "Attack Damage", 15f)));
    public static final Item NULLMAGIC_MANTLE = registerCustomItem("nullmagic_mantle", new ComponentItem(new Item.Properties(), Map.of("Magic Resist", 20f)));
    public static final Item OBLIVION_ORB = registerCustomItem("oblivion_orb", new ComponentItem(new Item.Properties(), Map.of("Ability Power", 25f)));
    public static final Item OBSIDIAN_EDGE = registerItem("obsidian_edge");
    public static final Item OPPORTUNITY = registerCustomItem("opportunity", new FullItem(new Item.Properties(), Map.of("Attack Damage", 55f)));
    public static final Item ORACLE_LENS = registerCustomItem("oracle_lens", new WardItem(new Item.Properties(), ModBlocks.ORACLE_LENS_BLOCK));

    public static final Item OVERLORD_S_BLOODMAIL = registerCustomItem("overlord_s_bloodmail", new FullItem(new Item.Properties(), Map.of("Health", 550f, "Attack Damage", 30f)));
    public static final Item PAULDRONS_OF_WHITEROCK = registerItem("pauldrons_of_whiterock");

    public static final Item PHAGE = registerCustomItem("phage", new ComponentItem(new Item.Properties(), Map.of("Health", 200f, "Attack Damage", 15f)));
    public static final Item PHANTOM_DANCER = registerCustomItem("phantom_dancer", new FullItem(new Item.Properties(), Map.of("Critical Strike Chance", 0.25f, "Attack Speed", 0.65f, "Move Speed", 0.1f)));

    public static final Item PICKAXE = registerCustomItem("pickaxe", new ComponentItem(new Item.Properties(), Map.of("Attack Damage", 25f)));
    public static final Item PLATED_STEELCAPS = registerCustomItem("plated_steelcaps", new BootItem(45f, new Item.Properties()));
    public static final Item PROFANE_HYDRA = registerCustomItem("profane_hydra", new FullItem(new Item.Properties(), Map.of("Attack Damage", 55f)));
    public static final Item PROTOPLASM_HARNESS = registerCustomItem("protoplasm_harness", new FullItem(new Item.Properties(), Map.of("Health", 600f)));
    public static final Item PROWLER_S_CLAW = registerCustomItem("prowler_s_claw", new FullItem(new Item.Properties(), Map.of("Attack Damage", 55f)));
    public static final Item QUICKSILVER_SASH = registerCustomItem("quicksilver_sash", new ComponentItem(new Item.Properties(), Map.of("Magic Resist", 30f)));
    public static final Item RABADON_S_DEATHCAP = registerCustomItem("rabadon_s_deathcap", new ComponentItem(new Item.Properties(), Map.of("Ability Power", 130f)));
    public static final Item RADIANT_VIRTUE = registerCustomItem("radiant_virtue", new FullItem(new Item.Properties(), Map.of("Magic Resist", 30f, "Health", 350f, "Armor", 30f)));
    public static final Item RAGEKNIFE = registerCustomItem("rageknife", new ComponentItem(new Item.Properties(), Map.of("Attack Speed", 0.25f)));
    public static final Item RANDUIN_S_OMEN = registerCustomItem("randuin_s_omen", new FullItem(new Item.Properties(), Map.of("Health", 350f, "Armor", 75f)));
    public static final Item RAPID_FIRECANNON = registerCustomItem("rapid_firecannon", new FullItem(new Item.Properties(), Map.of("Critical Strike Chance", 0.25f, "Attack Speed", 0.35f, "Move Speed", 0.04f)));
    public static final Item RAVENOUS_HYDRA = registerCustomItem("ravenous_hydra", new FullItem(new Item.Properties(), Map.of("Attack Damage", 65f, "Life Steal", 0.12f)));
    public static final Item RECTRIX = registerCustomItem("rectrix", new ComponentItem(new Item.Properties(), Map.of("Attack Damage", 15f, "Move Speed", 0.04f)));
    public static final Item RECURVE_BOW = registerCustomItem("recurve_bow", new ComponentItem(new Item.Properties(), Map.of("Attack Speed", 0.15f)));
    public static final Item REDEMPTION = registerCustomItem("redemption", new FullItem(new Item.Properties(), Map.of("Health", 400f, "Ability Power", 30f)));
    public static final Item REFILLABLE_POTION = registerCustomItem("refillable_potion", new PotionItem(new Item.Properties(), (level, player) -> {
        player.heal(4.0f);
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0));
    }));

    public static final Item REJUVENATION_BEAD = registerCustomItem("rejuvenation_bead", new ComponentItem(new Item.Properties(), Map.of("Health Regen", 1.0f)));
    public static final Item RELIC_SHIELD = registerItem("relic_shield");
    public static final Item RIFTMAKER = registerCustomItem("riftmaker", new FullItem(new Item.Properties(), Map.of("Health", 350f, "Ability Power", 70f)));
    public static final Item RITE_OF_RUIN = registerCustomItem("rite_of_ruin", new FullItem(new Item.Properties(), Map.of("Ability Power", 55f, "Critical Strike Chance", 0.25f, "Move Speed", 0.04f)));
    public static final Item ROD_OF_AGES = registerCustomItem("rod_of_ages", new FullItem(new Item.Properties(), Map.of("Mana", 500f, "Health", 350f, "Ability Power", 45f)));
    public static final Item RUBY_CRYSTAL = registerCustomItem("ruby_crystal", new ComponentItem(new Item.Properties(), Map.of("Health", 150f)));
    public static final Item RUNAAN_S_HURRICANE = registerCustomItem("runaan_s_hurricane", new FullItem(new Item.Properties(), Map.of("Critical Strike Chance", 0.25f, "Attack Speed", 0.4f, "Move Speed", 0.04f)));
    public static final Item RUNESTEEL_SPAULDERS = registerItem("runesteel_spaulders");
    public static final Item RUNIC_COMPASS = registerItem("runic_compass");
    public static final Item RYLAI_S_CRYSTAL_SCEPTER = registerCustomItem("rylai_s_crystal_scepter", new FullItem(new Item.Properties(), Map.of("Health", 400f, "Ability Power", 65f)));
    public static final Item SAPPHIRE_CRYSTAL = registerCustomItem("sapphire_crystal", new ComponentItem(new Item.Properties(), Map.of("Mana", 300f)));

    public static final Item SCORCHCLAW_PUP = registerItem("scorchclaw_pup");
    public static final Item SCOUT_S_SLINGSHOT = registerCustomItem("scout_s_slingshot", new ComponentItem(new Item.Properties(), Map.of("Attack Speed", 0.2f)));
    public static final Item SEEKER_S_ARMGUARD = registerCustomItem("seeker_s_armguard", new ComponentItem(new Item.Properties(), Map.of("Ability Power", 40f, "Armor", 25f)));
    public static final Item SERAPH_S_EMBRACE = registerItem("seraph_s_embrace");
    public static final Item SERPENT_S_FANG = registerCustomItem("serpent_s_fang", new FullItem(new Item.Properties(), Map.of("Attack Damage", 55f)));
    public static final Item SERRATED_DIRK = registerCustomItem("serrated_dirk", new ComponentItem(new Item.Properties(), Map.of("Attack Damage", 20f)));
    public static final Item SERYLDA_S_GRUDGE = registerCustomItem("serylda_s_grudge", new FullItem(new Item.Properties(), Map.of("Attack Damage", 45f)));
    public static final Item SHADOWFLAME = registerCustomItem("shadowflame", new FullItem(new Item.Properties(), Map.of("Ability Power", 110f)));

    public static final Item SHATTERED_ARMGUARD = registerCustomItem("shattered_armguard", new ComponentItem(new Item.Properties(), Map.of("Ability Power", 40f, "Armor", 25f)));
    public static final Item SHEEN = registerCustomItem("sheen", new ComponentItem(new Item.Properties()));
    public static final Item SHIELD_OF_MOLTEN_STONE = registerItem("shield_of_molten_stone");
    public static final Item SHIELD_OF_THE_RAKKOR = registerCustomItem("shield_of_the_rakkor", new FullItem(new Item.Properties(), Map.of("Armor", 30f, "Attack Damage", 50f, "Move Speed", 0.05f)));

    public static final Item SHURELYA_S_BATTLESONG = registerCustomItem("shurelya_s_battlesong", new FullItem(new Item.Properties(), Map.of("Ability Power", 65f, "Move Speed", 0.06f)));
    public static final Item SILVERMERE_DAWN = registerCustomItem("silvermere_dawn", new FullItem(new Item.Properties(), Map.of("Magic Resist", 40f, "Health", 300f, "Attack Damage", 40f)));

    public static final Item SLIGHTLY_MAGICAL_FOOTWEAR = registerCustomItem("slightly_magical_footwear", new BootItem(25f, new Item.Properties()));
    public static final Item SOLSTICE_SLEIGH = registerCustomItem("solstice_sleigh", new ComponentItem(new Item.Properties(), Map.of("Health", 200f)));
    public static final Item SORCERER_S_SHOES = registerCustomItem("sorcerer_s_shoes", new BootItem(45f, new Item.Properties()));
    public static final Item SPEAR_OF_SHOJIN = registerCustomItem("spear_of_shojin", new FullItem(new Item.Properties(), Map.of("Health", 450f, "Attack Damage", 45f)));
    public static final Item SPECTRAL_CUTLASS = registerItem("spectral_cutlass");
    public static final Item SPECTRAL_SICKLE = registerItem("spectral_sickle");
    public static final Item SPECTRE_S_COWL = registerCustomItem("spectre_s_cowl", new ComponentItem(new Item.Properties(), Map.of("Magic Resist", 35f, "Health", 200f)));
    public static final Item SPELLSLINGER_S_SHOES = registerCustomItem("spellslinger_s_shoes", new BootItem(45f, new Item.Properties()));
    public static final Item SPELLTHIEF_S_EDGE = registerItem("spellthief_s_edge");
    public static final Item SPIRIT_VISAGE = registerCustomItem("spirit_visage", new FullItem(new Item.Properties(), Map.of("Magic Resist", 50f, "Health", 400f)));
    public static final Item STAFF_OF_FLOWING_WATER = registerCustomItem("staff_of_flowing_water", new FullItem(new Item.Properties(), Map.of("Ability Power", 45f)));

    public static final Item STATIKK_SHIV = registerCustomItem("statikk_shiv", new FullItem(new Item.Properties(), Map.of("Ability Power", 45f, "Attack Damage", 45f, "Attack Speed", 0.3f, "Move Speed", 0.04f)));
    public static final Item STEALTH_WARD = registerCustomItem("stealth_ward", new WardItem(new Item.Properties(), ModBlocks.STEALTH_WARD_BLOCK));
    public static final Item STEEL_SHOULDERGUARDS = registerItem("steel_shoulderguards");
    public static final Item STEEL_SIGIL = registerCustomItem("steel_sigil", new ComponentItem(new Item.Properties(), Map.of("Armor", 30f, "Attack Damage", 15f)));
    public static final Item STERAK_S_GAGE = registerCustomItem("sterak_s_gage", new FullItem(new Item.Properties(), Map.of("Health", 400f)));
    public static final Item STIRRING_WARDSTONE = registerItem("stirring_wardstone");
    public static final Item STORMRAZOR = registerCustomItem("stormrazor", new FullItem(new Item.Properties(), Map.of("Critical Strike Chance", 0.25f, "Attack Damage", 50f, "Attack Speed", 0.2f)));
    public static final Item STORMSURGE = registerCustomItem("stormsurge", new FullItem(new Item.Properties(), Map.of("Ability Power", 90f, "Move Speed", 0.06f)));
    public static final Item STRIDEBREAKER = registerCustomItem("stridebreaker", new FullItem(new Item.Properties(), Map.of("Health", 450f, "Attack Damage", 40f, "Attack Speed", 0.25f)));
    public static final Item SUNDERED_SKY = registerCustomItem("sundered_sky", new FullItem(new Item.Properties(), Map.of("Health", 400f, "Attack Damage", 45f)));
    public static final Item SUNFIRE_AEGIS = registerCustomItem("sunfire_aegis", new FullItem(new Item.Properties(), Map.of("Health", 350f, "Armor", 50f)));

    public static final Item SWIFTMARCH = registerCustomItem("swiftmarch", new BootItem(65f, new Item.Properties()));
    public static final Item SWORD_OF_BLOSSOMING_DAWN = registerItem("sword_of_blossoming_dawn");
    public static final Item SWORD_OF_THE_DIVINE = registerItem("sword_of_the_divine");
    public static final Item SYMBIOTIC_SOLES = registerCustomItem("symbiotic_soles", new BootItem(40f, new Item.Properties()));
    public static final Item SYNCHRONIZED_SOULS = registerCustomItem("synchronized_souls", new BootItem(45f, new Item.Properties()));
    public static final Item TARGON_S_BUCKLER = registerItem("targon_s_buckler");
    public static final Item TEAR_OF_THE_GODDESS = registerItem("tear_of_the_goddess");
    public static final Item TERMINUS = registerCustomItem("terminus", new FullItem(new Item.Properties(), Map.of("Attack Damage", 30f, "Attack Speed", 0.35f)));
    public static final Item THE_BRUTALIZER = registerCustomItem("the_brutalizer", new ComponentItem(new Item.Properties(), Map.of("Attack Damage", 25f)));
    public static final Item THE_COLLECTOR = registerCustomItem("the_collector", new FullItem(new Item.Properties(), Map.of("Critical Strike Chance", 0.25f, "Attack Damage", 50f)));
    public static final Item THE_GOLDEN_SPATULA = registerItem("the_golden_spatula");
    public static final Item THORNMAIL = registerCustomItem("thornmail", new FullItem(new Item.Properties(), Map.of("Health", 200f, "Armor", 85f)));
    public static final Item TIAMAT = registerCustomItem("tiamat", new ComponentItem(new Item.Properties(), Map.of("Attack Damage", 20f)));
    public static final Item TITANIC_HYDRA = registerCustomItem("titanic_hydra", new FullItem(new Item.Properties(), Map.of("Health", 600f, "Attack Damage", 40f)));
    public static final Item TOTAL_BISCUIT_OF_EVERLASTING_WILL = registerItem("total_biscuit_of_everlasting_will");
    public static final Item TRAILBLAZER = registerCustomItem("trailblazer", new FullItem(new Item.Properties(), Map.of("Health", 300f, "Armor", 45f, "Move Speed", 0.06f)));
    public static final Item TRINITY_FORCE = registerCustomItem("trinity_force", new FullItem(new Item.Properties(), Map.of("Health", 333f, "Attack Damage", 36f, "Attack Speed", 0.3f)));
    public static final Item TUNNELER = registerCustomItem("tunneler", new ComponentItem(new Item.Properties(), Map.of("Health", 250f, "Attack Damage", 15f)));
    public static final Item TWIN_MASK = registerItem("twin_mask");
    public static final Item UMBRAL_GLAIVE = registerCustomItem("umbral_glaive", new FullItem(new Item.Properties(), Map.of("Attack Damage", 60f)));
    public static final Item UNENDING_DESPAIR = registerCustomItem("unending_despair", new FullItem(new Item.Properties(), Map.of("Health", 400f, "Armor", 50f)));
    public static final Item VAMPIRIC_SCEPTER = registerCustomItem("vampiric_scepter", new ComponentItem(new Item.Properties(), Map.of("Attack Damage", 15f, "Life Steal", 0.07f)));
    public static final Item VEIGAR_S_TALISMAN_OF_ASCENSION = registerItem("veigar_s_talisman_of_ascension");
    public static final Item VERDANT_BARRIER = registerCustomItem("verdant_barrier", new ComponentItem(new Item.Properties(), Map.of("Magic Resist", 25f, "Ability Power", 40f)));
    public static final Item VIGILANT_WARDSTONE = registerCustomItem("vigilant_wardstone", new ComponentItem(new Item.Properties(), Map.of("Magic Resist", 30f, "Health", 250f, "Armor", 25f)));
    public static final Item VOID_IMMOLATION = registerItem("void_immolation");
    public static final Item VOID_STAFF = registerCustomItem("void_staff", new FullItem(new Item.Properties(), Map.of("Ability Power", 95f)));
    public static final Item VOLTAIC_CYCLOSWORD = registerCustomItem("voltaic_cyclosword", new FullItem(new Item.Properties(), Map.of("Attack Damage", 55f)));

    public static final Item WARDEN_S_MAIL = registerCustomItem("warden_s_mail", new ComponentItem(new Item.Properties(), Map.of("Armor", 40f)));
    public static final Item WARMOG_S_ARMOR = registerCustomItem("warmog_s_armor", new FullItem(new Item.Properties(), Map.of("Health", 1000f)));
    public static final Item WATCHFUL_WARDSTONE = registerItem("watchful_wardstone");
    public static final Item WHISPERING_CIRCLET = registerCustomItem("whispering_circlet", new ComponentItem(new Item.Properties(), Map.of("Mana", 300f, "Health", 200f)));
    public static final Item WINGED_MOONPLATE = registerCustomItem("winged_moonplate", new ComponentItem(new Item.Properties(), Map.of("Health", 200f, "Move Speed", 0.04f)));
    public static final Item WINTER_S_APPROACH = registerCustomItem("winter_s_approach", new FullItem(new Item.Properties(), Map.of("Mana", 500f, "Health", 550f)));
    public static final Item WIT_S_END = registerCustomItem("wit_s_end", new FullItem(new Item.Properties(), Map.of("Magic Resist", 45f, "Attack Speed", 0.5f)));
    public static final Item WOOGLET_S_WITCHCAP = registerItem("wooglet_s_witchcap");
    public static final Item WORLD_ATLAS = registerItem("world_atlas");
    public static final Item YOUMUU_S_GHOSTBLADE = registerCustomItem("youmuu_s_ghostblade", new FullItem(new Item.Properties(), Map.of("Attack Damage", 55f, "Move Speed", 0.04f)));

    public static final Item YUN_TAL_WILDARROWS = registerCustomItem("yun_tal_wildarrows", new FullItem(new Item.Properties(), Map.of("Attack Damage", 50f, "Attack Speed", 0.4f)));
    public static final Item ZAZ_ZAK_S_REALMSPIKE = registerCustomItem("zaz_zak_s_realmspike", new ComponentItem(new Item.Properties(), Map.of("Health", 200f)));
    public static final Item ZEAL = registerCustomItem("zeal", new ComponentItem(new Item.Properties(), Map.of("Critical Strike Chance", 0.15f, "Attack Speed", 0.15f, "Move Speed", 0.04f)));
    public static final Item ZEKE_S_CONVERGENCE = registerCustomItem("zeke_s_convergence", new FullItem(new Item.Properties(), Map.of("Magic Resist", 30f, "Health", 350f, "Armor", 30f)));
    public static final Item ZEPHYR = registerItem("zephyr");
    public static final Item ZHONYA_S_HOURGLASS = registerCustomItem("zhonya_s_hourglass", new FullItem(new Item.Properties(), Map.of("Ability Power", 105f, "Armor", 50f)));

    private static Item registerItem(String name) {
        Item item = Registry.register(BuiltInRegistries.ITEM,
            ResourceLocation.fromNamespaceAndPath(Leagueofcrafters.MOD_ID, name),
            new Item(new Item.Properties()));
        ITEMS.put(name, item);
        return item;
    }

    private static Item registerCustomItem(String name, Item item) {
        Item registered = Registry.register(BuiltInRegistries.ITEM,
            ResourceLocation.fromNamespaceAndPath(Leagueofcrafters.MOD_ID, name), item);
        ITEMS.put(name, registered);
        return registered;
    }

    public static Collection<Item> getAllItems() {
        return ITEMS.values();
    }

    public static void registerModItems() {
        Leagueofcrafters.LOGGER.info("Registered " + ITEMS.size() + " items");
    }
}