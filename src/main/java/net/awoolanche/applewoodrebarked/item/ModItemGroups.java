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

                        // Items
                        entries.add(ModItems.TEST_ITEM);
                        entries.add(ModItems.APPLE_BOAT);
                        entries.add(ModItems.APPLE_CHEST_BOAT);
                        entries.add(ModItems.SLINGSHOT);

                        // Blocks
                        entries.add(ModBlocks.TEST_BLOCK);
                        entries.add(ModBlocks.APPLE_PLANKS);
                        entries.add(ModBlocks.APPLE_STAIRS);
                        entries.add(ModBlocks.APPLE_SLAB);
                        entries.add(ModBlocks.STRIPPED_APPLE_LOG);
                        entries.add(ModBlocks.STRIPPED_APPLE_WOOD);
                        entries.add(ModBlocks.APPLE_TRAPDOOR);
                        entries.add(ModBlocks.APPLE_BUTTON);
                        entries.add(ModBlocks.APPLE_PRESSURE_PLATE);
                        entries.add(ModBlocks.APPLE_DOOR);
                        entries.add(ModBlocks.APPLE_FENCE);
                        entries.add(ModBlocks.APPLE_FENCE_GATE);

                        entries.add(ModBlocks.APPLE_SIGN);
                        entries.add(ModBlocks.APPLE_HANGING_SIGN);


                        // Vinery
                        var APPLE_LOG = Registries.ITEM.get(Identifier.of("vinery", "apple_log"));
                        entries.add(APPLE_LOG);
                        var APPLE_WOOD = Registries.ITEM.get(Identifier.of("vinery", "apple_wood"));
                        entries.add(APPLE_WOOD);
                        var APPLE_TREE_SAPLING = Registries.ITEM.get(Identifier.of("vinery", "apple_tree_sapling"));
                        entries.add(APPLE_TREE_SAPLING);
                        var APPLE_LEAVES = Registries.ITEM.get(Identifier.of("vinery", "apple_leaves"));
                        entries.add(APPLE_LEAVES);

                    })

                    .build());

    public static void registerModItemGroups() {
        AppleWoodRebarked.LOGGER.info("Registering Mod Item Groups for " + AppleWoodRebarked.MOD_ID);
    }
}