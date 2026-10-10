package com.espaciovacio.world;

import com.espaciovacio.EspacioVacio;
import com.espaciovacio.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public final class VoidTravel {
    /** Donde esta el unico bloque de la dimension. */
    public static final BlockPos ANCHOR_POS = new BlockPos(0, 64, 0);
    private static final String RETURN_KEY = "EspacioVacioRetorno";

    private VoidTravel() {}

    /** Desde el portal: si estas en el Espacio Vacio vuelves, y si no, vas hacia alla. */
    public static void travel(ServerPlayer player) {
        if (player.level().dimension() == EspacioVacio.ESPACIO) {
            volver(player);
        } else {
            ir(player);
        }
    }

    public static void ir(ServerPlayer player) {
        ServerLevel dest = player.server.getLevel(EspacioVacio.ESPACIO);
        if (dest == null) {
            player.displayClientMessage(Component.translatable("msg.espaciovacio.no_dimension"), false);
            return;
        }
        if (player.level().dimension() != EspacioVacio.ESPACIO) {
            CompoundTag tag = new CompoundTag();
            tag.putString("dim", player.level().dimension().location().toString());
            tag.putDouble("x", player.getX());
            tag.putDouble("y", player.getY());
            tag.putDouble("z", player.getZ());
            player.getPersistentData().put(RETURN_KEY, tag);
        }
        if (dest.getBlockState(ANCHOR_POS).isAir()) {
            dest.setBlockAndUpdate(ANCHOR_POS, ModBlocks.ANCHOR.get().defaultBlockState());
        }
        teleport(player, dest, new Vec3(ANCHOR_POS.getX() + 0.5D, ANCHOR_POS.getY() + 1.0D, ANCHOR_POS.getZ() + 0.5D));
    }

    public static void volver(ServerPlayer player) {
        CompoundTag tag = player.getPersistentData().getCompound(RETURN_KEY);
        if (tag.contains("dim")) {
            ResourceLocation id = ResourceLocation.tryParse(tag.getString("dim"));
            ServerLevel dest = id == null ? null
                    : player.server.getLevel(ResourceKey.create(Registries.DIMENSION, id));
            if (dest != null) {
                teleport(player, dest, new Vec3(tag.getDouble("x"), tag.getDouble("y"), tag.getDouble("z")));
                return;
            }
        }
        ServerLevel overworld = player.server.getLevel(Level.OVERWORLD);
        if (overworld == null) {
            return;
        }
        BlockPos top = overworld.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, overworld.getSharedSpawnPos());
        teleport(player, overworld, Vec3.atBottomCenterOf(top));
    }

    private static void teleport(ServerPlayer player, ServerLevel dest, Vec3 pos) {
        player.changeDimension(dest, new ITeleporter() {
            @Override
            public PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld,
                                            Function<ServerLevel, PortalInfo> defaultPortalInfo) {
                return new PortalInfo(pos, Vec3.ZERO, entity.getYRot(), entity.getXRot());
            }
        });
        player.fallDistance = 0.0F;
    }
}
