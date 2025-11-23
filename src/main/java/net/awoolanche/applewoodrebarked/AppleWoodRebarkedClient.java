package net.awoolanche.applewoodrebarked;

import net.awoolanche.applewoodrebarked.entity.ModEntities;
import net.awoolanche.applewoodrebarked.render.ModBoatEntityRenderer;
import net.awoolanche.applewoodrebarked.render.ModEntityModelLayers;
import net.awoolanche.applewoodrebarked.render.ModModelPredicates;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.ChestBoatEntityModel;

public class AppleWoodRebarkedClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        EntityRendererRegistry.register(ModEntities.APPLE_BOAT, (ctx) -> new ModBoatEntityRenderer(ctx, false));
        EntityRendererRegistry.register(ModEntities.APPLE_CHEST_BOAT, (ctx) -> new ModBoatEntityRenderer(ctx, true));

        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.APPLE_BOAT_LAYER, BoatEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.APPLE_CHEST_BOAT_LAYER, ChestBoatEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.SLINGSHOT_PROJECTILE, ctx -> new FlyingItemEntityRenderer(ctx));

        ModModelPredicates.registerModelPredicates();

        }
}
