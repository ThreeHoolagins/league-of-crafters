package com.leagueofcrafters;

import com.leagueofcrafters.item.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTab {
    public static final CreativeModeTab LEAGUE_TAB = Registry.register(
            BuiltInRegistries.CREATIVE_MODE_TAB,
            ResourceLocation.fromNamespaceAndPath(Leagueofcrafters.MOD_ID, "league_tab"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModItems.INFINITY_EDGE))
                    .title(Component.translatable("creativetab." + Leagueofcrafters.MOD_ID))
                    .displayItems((params, output) -> {
                        for (var item : ModItems.getAllItems()) {
                            output.accept(item);
                        }
                    })
                    .build()
    );

    public static void register() {}
}
