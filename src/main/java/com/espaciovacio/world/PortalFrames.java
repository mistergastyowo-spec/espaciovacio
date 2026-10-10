package com.espaciovacio.world;

import com.espaciovacio.block.VoidPortalBlock;
import com.espaciovacio.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Marco fijo de 4 de ancho x 5 de alto (como el minimo del Nether), sin esquinas:
 * 10 Marcos Dimensionales y un interior libre de 2x3.
 */
public final class PortalFrames {
    private static final int W = 4;
    private static final int H = 5;

    private PortalFrames() {}

    public static boolean tryLight(Level level, BlockPos clicked) {
        for (Direction.Axis axis : new Direction.Axis[]{Direction.Axis.X, Direction.Axis.Z}) {
            Direction right = axis == Direction.Axis.X ? Direction.EAST : Direction.SOUTH;
            for (int dx = 0; dx < W; dx++) {
                for (int dy = 0; dy < H; dy++) {
                    BlockPos origin = clicked.relative(right.getOpposite(), dx).below(dy);
                    if (isValid(level, origin, right)) {
                        fill(level, origin, right, axis);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isValid(Level level, BlockPos origin, Direction right) {
        for (int x = 0; x < W; x++) {
            for (int y = 0; y < H; y++) {
                boolean edgeX = x == 0 || x == W - 1;
                boolean edgeY = y == 0 || y == H - 1;
                if (edgeX && edgeY) {
                    continue; // esquinas: no importan
                }
                BlockState state = level.getBlockState(origin.relative(right, x).above(y));
                if (edgeX || edgeY) {
                    if (!state.is(ModBlocks.FRAME.get())) {
                        return false;
                    }
                } else if (!state.isAir()) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void fill(Level level, BlockPos origin, Direction right, Direction.Axis axis) {
        BlockState portal = ModBlocks.PORTAL.get().defaultBlockState().setValue(VoidPortalBlock.AXIS, axis);
        for (int x = 1; x < W - 1; x++) {
            for (int y = 1; y < H - 1; y++) {
                level.setBlock(origin.relative(right, x).above(y), portal, 18);
            }
        }
    }
}
