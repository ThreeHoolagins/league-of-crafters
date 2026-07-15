package com.leagueofcrafters.item;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.LinkedHashMap;
import java.util.Map;

public class LeagueItem extends TrinketItem {

    @FunctionalInterface
    private interface StatApplier {
        void apply(float value, ResourceLocation slotId, Multimap<Holder<Attribute>, AttributeModifier> target);
    }

    private static final Map<String, StatApplier> CONVERTERS = new LinkedHashMap<>();

    static {
        CONVERTERS.put("Move Speed", (value, slotId, target) ->
            target.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(
                slotId.withSuffix("/move_speed"),
                value * 0.001f,
                AttributeModifier.Operation.ADD_VALUE)));

        CONVERTERS.put("Ability Power", (value, slotId, target) -> {});
        CONVERTERS.put("Armor", (value, slotId, target) -> {});
        CONVERTERS.put("Attack Damage", (value, slotId, target) -> {});
        CONVERTERS.put("Attack Speed", (value, slotId, target) -> {});
        CONVERTERS.put("Critical Strike Chance", (value, slotId, target) -> {});
        CONVERTERS.put("Health", (value, slotId, target) -> {});
        CONVERTERS.put("Health Regen", (value, slotId, target) -> {});
        CONVERTERS.put("Life Steal", (value, slotId, target) -> {});
        CONVERTERS.put("Magic Resist", (value, slotId, target) -> {});
        CONVERTERS.put("Mana", (value, slotId, target) -> {});
    }

    private final Map<String, Float> stats;

    public LeagueItem(Properties properties, Map<String, Float> stats) {
        super(properties);
        this.stats = Map.copyOf(stats);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getModifiers(
            ItemStack stack, SlotReference slot, LivingEntity entity, ResourceLocation slotIdentifier) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = super.getModifiers(stack, slot, entity, slotIdentifier);

        for (Map.Entry<String, Float> entry : stats.entrySet()) {
            StatApplier applier = CONVERTERS.get(entry.getKey());
            if (applier != null) {
                applier.apply(entry.getValue(), slotIdentifier, modifiers);
            }
        }

        return modifiers;
    }
}
