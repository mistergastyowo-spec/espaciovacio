package com.espaciovacio.block;

import com.espaciovacio.EspacioVacio;
import com.espaciovacio.world.VoidTravel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

/**
 * El bloque unico de la dimension. Shift + clic derecho con las manos vacias
 * te devuelve al lugar de donde viniste (salida de emergencia).
 */
public class AnchorBlock extends Block {
    public AnchorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        if (hand == InteractionHand.MAIN_HAND && player.isShiftKeyDown() && player.getMainHandItem().isEmpty()) {
            if (!level.isClientSide && player instanceof ServerPlayer sp
                    && level.dimension() == EspacioVacio.ESPACIO) {
                VoidTravel.volver(sp);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }
}
