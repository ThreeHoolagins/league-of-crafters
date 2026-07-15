package com.leagueofcrafters.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.function.BiConsumer;

public class PotionItem extends Item {
    private final BiConsumer<Level, Player> effect;

    public PotionItem(Properties properties, BiConsumer<Level, Player> effect) {
        super(properties.stacksTo(16));
        this.effect = effect;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, net.minecraft.world.entity.LivingEntity livingEntity) {
        if (livingEntity instanceof Player player) {
            if (!level.isClientSide()) {
                effect.accept(level, player);
            }
            stack.consume(1, player);
            player.getCooldowns().addCooldown(this, 5);
        }
        level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(),
            SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 1.0F, 1.0F);
        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack, net.minecraft.world.entity.LivingEntity entity) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }
}
