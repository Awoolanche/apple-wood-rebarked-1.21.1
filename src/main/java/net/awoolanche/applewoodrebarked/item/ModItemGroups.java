package net.awoolanche.applewoodrebarked.item;

import net.awoolanche.applewoodrebarked.AppleWoodRebarked;
import net.awoolanche.applewoodrebarked.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;


public class ModItemGroups {

    public static final ItemGroup APPLE_WOOD_REBARKED_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(AppleWoodRebarked.MOD_ID, "apple_wood_rebarked_group"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModItems.TEST_ITEM))
                    .displayName(Text.translatable("itemgroup.apple_wood_rebarked.apple_wood_rebarked_group"))
                    .entries((displayContext, entries) -> {

                        //Items
                        entries.add(ModItems.TEST_ITEM);

                        //Blocks
                        entries.add(ModBlocks.TEST_BLOCK);

                    })
                    .build());

    public static void registerModItemGroups() {
        AppleWoodRebarked.LOGGER.info("Registering Mod Item Groups for " + AppleWoodRebarked.MOD_ID);
    }
}