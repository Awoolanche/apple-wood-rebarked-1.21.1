package net.awoolanche.applewoodrebarked.render;

import net.awoolanche.applewoodrebarked.AppleWoodRebarked; // Your main class
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModEntityModelLayers {
    public static final EntityModelLayer APPLE_BOAT_LAYER =
            new EntityModelLayer(Identifier.of(AppleWoodRebarked.MOD_ID, "apple_boat"), "main");

    public static final EntityModelLayer APPLE_CHEST_BOAT_LAYER =
            new EntityModelLayer(Identifier.of(AppleWoodRebarked.MOD_ID, "apple_chest_boat"), "main");
}