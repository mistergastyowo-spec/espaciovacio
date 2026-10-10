package com.espaciovacio;

import com.espaciovacio.command.VoidCommands;
import com.espaciovacio.registry.ModBlocks;
import com.espaciovacio.registry.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(EspacioVacio.MODID)
public class EspacioVacio {
    public static final String MODID = "espaciovacio";

    /** Clave de la dimension: espaciovacio:espacio (definida en data/espaciovacio/dimension/espacio.json). */
    public static final ResourceKey<Level> ESPACIO =
            ResourceKey.create(Registries.DIMENSION, new ResourceLocation(MODID, "espacio"));

    public EspacioVacio() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlocks.BLOCKS.register(modBus);
        ModItems.ITEMS.register(modBus);
        ModItems.TABS.register(modBus);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        VoidCommands.register(event.getDispatcher());
    }
}
