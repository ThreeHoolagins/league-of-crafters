package com.leagueofcrafters.block;

import com.leagueofcrafters.Leagueofcrafters;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModBlocks {
    private static final Map<String, Block> BLOCKS = new LinkedHashMap<>();
    public static final Supplier<BlockEntityType<WardBlockEntity>> WARD_BLOCK_ENTITY;

    public static final WardBlock STEALTH_WARD_BLOCK = registerBlock("stealth_ward_block",
        new WardBlock(BlockBehaviour.Properties.of()
            .noCollission().strength(0.0f).noOcclusion().pushReaction(PushReaction.DESTROY), 2400));

    public static final WardBlock CONTROL_WARD_BLOCK = registerBlock("control_ward_block",
        new WardBlock(BlockBehaviour.Properties.of()
            .noCollission().strength(0.0f).noOcclusion().pushReaction(PushReaction.DESTROY), 1200));

    public static final WardBlock FARSIGHT_ALTERATION_BLOCK = registerBlock("farsight_alteration_block",
        new WardBlock(BlockBehaviour.Properties.of()
            .noCollission().strength(0.0f).noOcclusion().pushReaction(PushReaction.DESTROY), 3000));

    public static final WardBlock ORACLE_LENS_BLOCK = registerBlock("oracle_lens_block",
        new WardBlock(BlockBehaviour.Properties.of()
            .noCollission().strength(0.0f).noOcclusion().pushReaction(PushReaction.DESTROY), 600));

    static {
        WARD_BLOCK_ENTITY = registerBlockEntity("ward_block_entity",
            () -> BlockEntityType.Builder.of(WardBlockEntity::new,
                STEALTH_WARD_BLOCK, CONTROL_WARD_BLOCK, FARSIGHT_ALTERATION_BLOCK, ORACLE_LENS_BLOCK
            ).build());
    }

    private static <T extends Block> T registerBlock(String name, T block) {
        Registry.register(BuiltInRegistries.BLOCK,
            ResourceLocation.fromNamespaceAndPath(Leagueofcrafters.MOD_ID, name), block);
        BLOCKS.put(name, block);
        return block;
    }

    private static <T extends BlockEntityType<?>> Supplier<T> registerBlockEntity(String name, Supplier<T> supplier) {
        T be = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(Leagueofcrafters.MOD_ID, name), supplier.get());
        return () -> be;
    }

    public static void registerModBlocks() {
        Leagueofcrafters.LOGGER.info("Registered " + BLOCKS.size() + " blocks");
    }
}
