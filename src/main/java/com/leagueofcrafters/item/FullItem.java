package com.leagueofcrafters.item;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public class FullItem extends LeagueItem {
    public FullItem(Properties properties) {
        this(properties, Map.of());
    }

    public FullItem(Properties properties, Map<String, Float> stats) {
        super(properties.stacksTo(1), stats);
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (!super.canEquip(stack, slot, entity)) {
            return false;
        }
        return TrinketsApi.getTrinketComponent(entity)
            .map(comp -> !comp.isEquipped(s -> s.getItem() == stack.getItem()))
            .orElse(true);
    }
}
