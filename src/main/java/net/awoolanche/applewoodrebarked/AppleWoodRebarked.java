package net.awoolanche.applewoodrebarked;

import net.awoolanche.applewoodrebarked.block.ModBlocks;
import net.awoolanche.applewoodrebarked.item.ModItemGroups;
import net.awoolanche.applewoodrebarked.item.ModItems;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppleWoodRebarked implements ModInitializer {
	public static final String MOD_ID = "apple-wood-rebarked";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	@Contract("_ -> new")
	public static @NotNull Identifier id(String string) {
		return Identifier.of(MOD_ID, string);
	}

	public static final Identifier VINERY_APPLE_LOG_ID = Identifier.of("vinery", "apple_log");
	public static final Identifier VINERY_APPLE_WOOD_ID = Identifier.of("vinery", "apple_wood");

	@Override
	public void onInitialize() {

		ModItemGroups.registerModItemGroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

		BlockEntityType.SIGN.addSupportedBlock(ModBlocks.APPLE_SIGN);
		BlockEntityType.SIGN.addSupportedBlock(ModBlocks.APPLE_WALL_SIGN);
		BlockEntityType.HANGING_SIGN.addSupportedBlock(ModBlocks.APPLE_HANGING_SIGN);
		BlockEntityType.HANGING_SIGN.addSupportedBlock(ModBlocks.APPLE_WALL_HANGING_SIGN);

		Block vineryAppleLog = Registries.BLOCK.get(VINERY_APPLE_LOG_ID);
		Block vineryAppleWood = Registries.BLOCK.get(VINERY_APPLE_WOOD_ID);

		if (vineryAppleLog == null || vineryAppleLog == Blocks.AIR) {
			return;
		}
		if (vineryAppleWood == null || vineryAppleWood == Blocks.AIR) {
			LOGGER.warn("[Let's Do] Apple Wood Rebarked - vinery:apple_wood not found, skipping registration.");
			return;
		}

		if (!vineryAppleLog.getStateManager().getStates().iterator().hasNext()
				|| !vineryAppleLog.getDefaultState().contains(Properties.AXIS)) {
			LOGGER.warn("[Let's Do] Apple Wood Rebarked - vinery:apple_log does not have AXIS property — cannot register strip mapping.");
			return;
		}
		if (!vineryAppleWood.getStateManager().getStates().iterator().hasNext()
				|| !vineryAppleWood.getDefaultState().contains(Properties.AXIS)) {
			LOGGER.warn("[Let's Do] Apple Wood Rebarked - vinery:apple_wood does not have AXIS property — cannot register strip mapping.");
			return;
		}

		if (!ModBlocks.STRIPPED_APPLE_LOG.getDefaultState().contains(Properties.AXIS)) {
			LOGGER.warn("[Let's Do] Apple Wood Rebarked - Your stripped_apple_log must have the AXIS property.");
			return;
		}
		if (!ModBlocks.STRIPPED_APPLE_WOOD.getDefaultState().contains(Properties.AXIS)) {
			LOGGER.warn("[Let's Do] Apple Wood Rebarked - Your stripped_apple_wood must have the AXIS property.");
			return;
		}

		StrippableBlockRegistry.register(vineryAppleLog, ModBlocks.STRIPPED_APPLE_LOG);
		StrippableBlockRegistry.register(vineryAppleWood, ModBlocks.STRIPPED_APPLE_WOOD);

		LOGGER.info("[Let's Do] Apple Wood Rebarked - Registered strippable mapping");;

		LOGGER.info("[Let's Do] Apple Wood Rebarked Mod Initialized!");
	}
}