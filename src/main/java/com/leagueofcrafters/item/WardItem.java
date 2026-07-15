package com.leagueofcrafters.item;

import com.leagueofcrafters.block.WardBlock;
import com.leagueofcrafters.block.WardBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class WardItem extends Item {
    private final WardBlock wardBlock;

    public WardItem(Properties properties, WardBlock wardBlock) {
        super(properties.stacksTo(64));
        this.wardBlock = wardBlock;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        BlockPos placePos = clickedPos.relative(context.getClickedFace());
        ItemStack stack = context.getItemInHand();

        if (!level.getBlockState(placePos).canBeReplaced()) {
            return InteractionResult.FAIL;
        }

        level.setBlock(placePos, wardBlock.defaultBlockState(), 3);

        if (level.getBlockEntity(placePos) instanceof WardBlockEntity be) {
            be.setPlacer(context.getPlayer().getUUID());
        }

        if (level instanceof ServerLevel serverLevel) {
            serverLevel.scheduleTick(placePos, wardBlock, wardBlock.getLifetime());
        }

        stack.consume(1, context.getPlayer());
        return InteractionResult.SUCCESS;
    }
}
