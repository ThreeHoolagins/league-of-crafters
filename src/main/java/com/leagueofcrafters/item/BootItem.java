package com.leagueofcrafters.item;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public class BootItem extends FullItem {

    public BootItem(float lolMoveSpeed, Properties properties) {
        super(properties, Map.of("Move Speed", lolMoveSpeed));
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (!super.canEquip(stack, slot, entity)) {
            return false;
        }
        return TrinketsApi.getTrinketComponent(entity)
            .map(comp -> !comp.isEquipped(s -> s.getItem() instanceof BootItem))
            .orElse(true);
    }
}
