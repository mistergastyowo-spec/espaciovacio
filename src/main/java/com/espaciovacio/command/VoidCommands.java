package com.espaciovacio.command;

import com.espaciovacio.world.VoidTravel;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

/** /espaciovacio ir  |  /espaciovacio volver   (para operadores; util para probar). */
public class VoidCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("espaciovacio")
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("ir").executes(ctx -> {
                    VoidTravel.ir(ctx.getSource().getPlayerOrException());
                    return 1;
                }))
                .then(Commands.literal("volver").executes(ctx -> {
                    VoidTravel.volver(ctx.getSource().getPlayerOrException());
                    return 1;
                })));
    }
}
