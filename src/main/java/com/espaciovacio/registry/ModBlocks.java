package com.espaciovacio.registry;

import com.espaciovacio.EspacioVacio;
import com.espaciovacio.block.AnchorBlock;
import com.espaciovacio.block.VoidPortalBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, EspacioVacio.MODID);

    /** El unico bloque que existe en la dimension. Indestructible para no perder nunca la base. */
    public static final RegistryObject<Block> ANCHOR = BLOCKS.register("bloque_ancla",
            () -> new AnchorBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLACK)
                    .strength(-1.0F, 3600000.0F)
                    .sound(SoundType.AMETHYST)
                    .lightLevel(state -> 6)
                    .noLootTable()));

    /** Bloque con el que se construye el marco del portal (4x5 sin esquinas = 10 bloques). */
    public static final RegistryObject<Block> FRAME = BLOCKS.register("marco_dimensional",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PURPLE)
                    .strength(50.0F, 1200.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.NETHERITE_BLOCK)
                    .lightLevel(state -> 3)));

    /** El portal en si (se genera al activar el marco con el Nucleo Dimensional). */
    public static final RegistryObject<Block> PORTAL = BLOCKS.register("portal_vacio",
            () -> new VoidPortalBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PURPLE)
                    .noCollission()
                    .strength(-1.0F)
                    .sound(SoundType.GLASS)
                    .lightLevel(state -> 11)
                    .noLootTable()
                    .pushReaction(PushReaction.BLOCK)));
}
