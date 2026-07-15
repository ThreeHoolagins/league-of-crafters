package com.leagueofcrafters.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.AABB;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class WardBlockEntity extends BlockEntity {
    private UUID placerUuid;
    private final Set<Integer> glowingMobs = new HashSet<>();
    private int tickCounter = 0;
    private static final int TICK_INTERVAL = 20;
    private static final double RADIUS = 8.0;

    public WardBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.WARD_BLOCK_ENTITY.get(), pos, state);
    }

    public void setPlacer(UUID uuid) {
        this.placerUuid = uuid;
        setChanged();
    }

    public void removeAllGlowing() {
        if (level == null || level.isClientSide() || placerUuid == null) return;

        ServerPlayer placer = (ServerPlayer) ((ServerLevel) level).getPlayerByUUID(placerUuid);
        if (placer == null) return;

        for (int entityId : glowingMobs) {
            placer.connection.send(new ClientboundRemoveMobEffectPacket(entityId, MobEffects.GLOWING));
        }
        glowingMobs.clear();
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, WardBlockEntity be) {
        if (be.placerUuid == null) return;
        be.tickCounter++;
        if (be.tickCounter < TICK_INTERVAL) return;
        be.tickCounter = 0;

        if (!(level instanceof ServerLevel serverLevel)) return;

        ServerPlayer placer = (ServerPlayer) serverLevel.getPlayerByUUID(be.placerUuid);
        if (placer == null) return;

        AABB area = new AABB(pos).inflate(RADIUS);
        Set<Integer> currentMobs = new HashSet<>();

        for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, area)) {
            if (entity == placer) continue;
            currentMobs.add(entity.getId());

            if (!be.glowingMobs.contains(entity.getId())) {
                var effect = new MobEffectInstance(MobEffects.GLOWING, 999999, 0, false, false, false);
                placer.connection.send(new ClientboundUpdateMobEffectPacket(
                    entity.getId(), effect, false
                ));
            }
        }

        for (int entityId : be.glowingMobs) {
            if (!currentMobs.contains(entityId)) {
                placer.connection.send(new ClientboundRemoveMobEffectPacket(entityId, MobEffects.GLOWING));
            }
        }

        be.glowingMobs.clear();
        be.glowingMobs.addAll(currentMobs);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (placerUuid != null) {
            tag.putUUID("Placer", placerUuid);
        }
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.hasUUID("Placer")) {
            placerUuid = tag.getUUID("Placer");
        }
    }
}
