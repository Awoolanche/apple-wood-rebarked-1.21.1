package net.awoolanche.applewoodrebarked.item;

import net.awoolanche.applewoodrebarked.AppleWoodRebarked;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item TEST_ITEM = registerItem("test_item",
            new Item(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(AppleWoodRebarked.MOD_ID, name), item);
    }

    public static void registerModItems() {
        AppleWoodRebarked.LOGGER.info("Registering Mod Items for " + AppleWoodRebarked.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(TEST_ITEM);
        });
    }
}
