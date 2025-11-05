package net.awoolanche.applewoodrebarked;

import net.awoolanche.applewoodrebarked.block.ModBlocks;
import net.awoolanche.applewoodrebarked.item.ModItemGroups;
import net.awoolanche.applewoodrebarked.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppleWoodRebarked implements ModInitializer {
	public static final String MOD_ID = "apple-wood-rebarked";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModItemGroups.registerModItemGroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

		LOGGER.info("Hello Fabric world!");
	}
}