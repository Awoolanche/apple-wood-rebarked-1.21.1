package net.awoolanche.applewoodrebarked.render;

import net.awoolanche.applewoodrebarked.item.ModItems;

import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

// Big thanks to Kaupenjoe for saving my ass on this one.
// https://github.com/Tutorials-By-Kaupenjoe/Fabric-Tutorial-1.21.X/blob/22-customBow/src/main/java/net/kaupenjoe/tutorialmod/util/ModModelPredicates.java
public class ModModelPredicates {
    public static void registerModelPredicates() {
        registerCustomBow(ModItems.SLINGSHOT);
    }

    private static void registerCustomBow(Item item) {
        // "pull" predicate
        ModelPredicateProviderRegistry.register(item, Identifier.ofVanilla("pull"), (stack, world, entity, seed) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                if (entity.getActiveItem() != stack) return 0.0f;
                float use = (stack.getMaxUseTime(entity) - entity.getItemUseTimeLeft()) / 20.0f;
                return Math.min(use, 1.0f);
            }
        });

        // "pulling" predicate
        ModelPredicateProviderRegistry.register(item, Identifier.ofVanilla("pulling"),
                (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F
        );
    }
}