package com.leagueofcrafters.block;

import com.leagueofcrafters.item.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class WardBlock extends BaseEntityBlock {
    protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);
    private final int lifetime;

    public WardBlock(Properties properties, int lifetime) {
        super(properties);
        this.lifetime = lifetime;
    }

    public int getLifetime() {
        return lifetime;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos below = pos.below();
        return level.getBlockState(below).isSolid();
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN && !canSurvive(state, level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, net.minecraft.util.RandomSource random) {
        level.destroyBlock(pos, false);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock()) && level.getBlockEntity(pos) instanceof WardBlockEntity be) {
            be.removeAllGlowing();
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        if (this == ModBlocks.STEALTH_WARD_BLOCK) return new ItemStack(ModItems.STEALTH_WARD);
        if (this == ModBlocks.CONTROL_WARD_BLOCK) return new ItemStack(ModItems.CONTROL_WARD);
        if (this == ModBlocks.FARSIGHT_ALTERATION_BLOCK) return new ItemStack(ModItems.FARSIGHT_ALTERATION);
        if (this == ModBlocks.ORACLE_LENS_BLOCK) return new ItemStack(ModItems.ORACLE_LENS);
        if (this == ModBlocks.SCARECROW_EFFIGY_BLOCK) return new ItemStack(ModItems.SCARECROW_EFFIGY);
        return super.getCloneItemStack(level, pos, state);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(p -> new WardBlock(p, 2000));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WardBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) return null;
        return createTickerHelper(type, ModBlocks.WARD_BLOCK_ENTITY.get(), WardBlockEntity::serverTick);
    }
}
