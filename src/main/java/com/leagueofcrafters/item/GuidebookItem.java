package com.leagueofcrafters.item;

import com.leagueofcrafters.Leagueofcrafters;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vazkii.patchouli.api.PatchouliAPI;

public class GuidebookItem extends Item {
    private static final ResourceLocation BOOK_ID =
            ResourceLocation.fromNamespaceAndPath(Leagueofcrafters.MOD_ID, "guidebook");

    public GuidebookItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (level.isClientSide()) {
            PatchouliAPI.get().openBookGUI(BOOK_ID);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
