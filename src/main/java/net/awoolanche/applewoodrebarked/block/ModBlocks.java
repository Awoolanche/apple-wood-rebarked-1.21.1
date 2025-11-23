package net.awoolanche.applewoodrebarked.block;

import net.awoolanche.applewoodrebarked.AppleWoodRebarked;

import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block TEST_BLOCK = registerBlock("test_block",
            new Block(AbstractBlock.Settings.create()
                    .strength(4f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.ANVIL)));

    public static final Block APPLE_PLANKS = registerBlock("apple_planks",
            new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)));

    public static final Block APPLE_STAIRS = registerBlock("apple_stairs",
            new StairsBlock(APPLE_PLANKS.getDefaultState(),
                    AbstractBlock.Settings.copy(Blocks.OAK_STAIRS)));

    public static final Block APPLE_SLAB = registerBlock("apple_slab",
            new SlabBlock(AbstractBlock.Settings.copy(Blocks.OAK_SLAB)));

    public static final Block STRIPPED_APPLE_LOG = registerBlock("stripped_apple_log",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_LOG)));

    public static final Block STRIPPED_APPLE_WOOD = registerBlock("stripped_apple_wood",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_WOOD)));

    public static final Block APPLE_TRAPDOOR = registerBlock("apple_trapdoor",
            new TrapdoorBlock(ModWoodTypes.APPLE_BLOCK_SET_TYPE, AbstractBlock.Settings.copy(Blocks.OAK_TRAPDOOR)));

    public static final Block APPLE_BUTTON = registerBlock("apple_button",
            new ButtonBlock(ModWoodTypes.APPLE_BLOCK_SET_TYPE, 30, AbstractBlock.Settings.copy(Blocks.OAK_BUTTON)));

    public static final Block APPLE_PRESSURE_PLATE = registerBlock("apple_pressure_plate",
            new PressurePlateBlock(ModWoodTypes.APPLE_BLOCK_SET_TYPE, AbstractBlock.Settings.copy(Blocks.OAK_PRESSURE_PLATE)));

    public static final Block APPLE_DOOR = registerBlock("apple_door",
            new DoorBlock(ModWoodTypes.APPLE_BLOCK_SET_TYPE, AbstractBlock.Settings.copy(Blocks.OAK_DOOR)));

    public static final Block APPLE_FENCE = registerBlock("apple_fence",
            new FenceBlock(AbstractBlock.Settings.copy(Blocks.OAK_FENCE)));

    public static final Block APPLE_FENCE_GATE = registerBlock("apple_fence_gate",
            new FenceGateBlock(ModWoodTypes.APPLE, AbstractBlock.Settings.copy(Blocks.OAK_FENCE_GATE)));

    public static final Block APPLE_SIGN = registerBlockWithoutItem("apple_sign",
            new SignBlock(ModWoodTypes.APPLE, AbstractBlock.Settings.copy(Blocks.OAK_SIGN)));

    public static final Block APPLE_WALL_SIGN = registerBlockWithoutItem("apple_wall_sign",
            new WallSignBlock(ModWoodTypes.APPLE, AbstractBlock.Settings.copy(Blocks.OAK_WALL_SIGN)));

    public static final Block APPLE_HANGING_SIGN = registerBlockWithoutItem("apple_hanging_sign",
            new HangingSignBlock(ModWoodTypes.APPLE, AbstractBlock.Settings.copy(Blocks.OAK_HANGING_SIGN)));

    public static final Block APPLE_WALL_HANGING_SIGN = registerBlockWithoutItem("apple_wall_hanging_sign",
            new WallHangingSignBlock(ModWoodTypes.APPLE, AbstractBlock.Settings.copy(Blocks.OAK_WALL_HANGING_SIGN)));


    // Registration methods
    private static Block registerBlockWithoutItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, Identifier.of(AppleWoodRebarked.MOD_ID, name), block);
    }

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(AppleWoodRebarked.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(AppleWoodRebarked.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        AppleWoodRebarked.LOGGER.info("Registering Mod Blocks for " + AppleWoodRebarked.MOD_ID);
    }
}
