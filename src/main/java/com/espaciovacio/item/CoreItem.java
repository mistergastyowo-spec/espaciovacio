package com.espaciovacio.item;

import com.espaciovacio.registry.ModBlocks;
import com.espaciovacio.world.PortalFrames;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

/** Clic derecho sobre un marco bien construido para activar el portal. */
public class CoreItem extends Item {
    public CoreItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if (!level.getBlockState(pos).is(ModBlocks.FRAME.get())) {
            return InteractionResult.PASS;
        }
        if (!level.isClientSide) {
            boolean ok = PortalFrames.tryLight(level, pos);
            if (ok) {
                level.playSound(null, pos, SoundEvents.END_PORTAL_SPAWN, SoundSource.BLOCKS, 1.0F, 1.0F);
            } else {
                Player player = context.getPlayer();
                if (player != null) {
                    player.displayClientMessage(Component.translatable("msg.espaciovacio.marco_invalido"), true);
                }
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
