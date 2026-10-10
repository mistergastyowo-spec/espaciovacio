package com.espaciovacio.registry;

import com.espaciovacio.EspacioVacio;
import com.espaciovacio.item.CoreItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, EspacioVacio.MODID);
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EspacioVacio.MODID);

    public static final RegistryObject<Item> CORE = ITEMS.register("nucleo_dimensional",
            () -> new CoreItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> FRAME_ITEM = ITEMS.register("marco_dimensional",
            () -> new BlockItem(ModBlocks.FRAME.get(), new Item.Properties()));

    public static final RegistryObject<Item> ANCHOR_ITEM = ITEMS.register("bloque_ancla",
            () -> new BlockItem(ModBlocks.ANCHOR.get(), new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistryObject<CreativeModeTab> TAB = TABS.register("espaciovacio",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.espaciovacio"))
                    .icon(() -> new ItemStack(CORE.get()))
                    .displayItems((params, output) -> {
                        output.accept(CORE.get());
                        output.accept(FRAME_ITEM.get());
                        output.accept(ANCHOR_ITEM.get());
                    })
                    .build());
}
