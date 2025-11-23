package net.awoolanche.applewoodrebarked.render;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.ChestBoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Quaternionf;

public class ModBoatEntityRenderer extends BoatEntityRenderer {
    private final BoatEntityModel model;
    private final Identifier TEXTURE;

    public ModBoatEntityRenderer(EntityRendererFactory.Context ctx, boolean chest) {
        super(ctx, chest);
        EntityModelLayer layer = chest ? ModEntityModelLayers.APPLE_CHEST_BOAT_LAYER : ModEntityModelLayers.APPLE_BOAT_LAYER;
        this.TEXTURE = Identifier.of("apple-wood-rebarked",
                chest ? "textures/entity/chest_boat/apple.png" : "textures/entity/boat/apple.png");

        if (chest) {
            this.model = new ChestBoatEntityModel(ctx.getPart(ModEntityModelLayers.APPLE_CHEST_BOAT_LAYER));
        } else {
            this.model = new BoatEntityModel(ctx.getPart(ModEntityModelLayers.APPLE_BOAT_LAYER));
        }
    }

    @Override
    public Identifier getTexture(BoatEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(BoatEntity boatEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.translate(0.0f, 0.375f, 0.0f);
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f - f));

        float h = (float)boatEntity.getDamageWobbleTicks() - g;
        float j = boatEntity.getDamageWobbleStrength() - g;
        if (j < 0.0f) {
            j = 0.0f;
        }
        if (h > 0.0f) {
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(MathHelper.sin(h) * h * j / 10.0f * (float)boatEntity.getDamageWobbleSide()));
        }

        float k = boatEntity.interpolateBubbleWobble(g);
        if (!MathHelper.approximatelyEquals(k, 0.0f)) {
            matrixStack.multiply(new Quaternionf().setAngleAxis(boatEntity.interpolateBubbleWobble(g) * ((float)Math.PI / 180), 1.0f, 0.0f, 1.0f));
        }

        matrixStack.scale(-1.0f, -1.0f, 1.0f);
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0f));

        // Use YOUR model and YOUR texture
        this.model.setAngles(boatEntity, g, 0.0f, -0.1f, 0.0f, 0.0f);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.getLayer(this.getTexture(boatEntity)));
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV);

        matrixStack.pop();

        // Note: We do NOT call super.render() because that would render the Oak boat on top of ours!
    }
}

