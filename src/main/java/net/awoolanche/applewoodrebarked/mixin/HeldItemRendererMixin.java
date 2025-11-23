package net.awoolanche.applewoodrebarked.mixin;

import net.awoolanche.applewoodrebarked.item.ModItems;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {

    @Inject(
            method = "renderFirstPersonItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;getUseAction()Lnet/minecraft/util/UseAction;"
            ),
            cancellable = true
    )
    private void applewood_slingshotTransform(
            AbstractClientPlayerEntity player,
            float tickDelta,
            float pitch,
            Hand hand,
            float swingProgress,
            ItemStack stack,
            float equipProgress,
            MatrixStack matrices,
            VertexConsumerProvider providers,
            int light,
            CallbackInfo ci
    ) {
        if (!stack.isOf(ModItems.SLINGSHOT)) return;
        if (!player.isUsingItem() || player.getActiveHand() != hand) return;

        boolean right = hand == Hand.MAIN_HAND
                ? player.getMainArm() == Arm.RIGHT
                : player.getMainArm() == Arm.LEFT;

        Arm arm = right ? Arm.RIGHT : Arm.LEFT;
        int sign = right ? 1 : -1;

        HeldItemRenderer renderer = (HeldItemRenderer) (Object) this;

        matrices.push();

        // Equip offset (vanilla bow/crossbow behavior)
        ((HeldItemRendererAccessor) renderer).applewood_invokeApplyEquipOffset(matrices, arm, equipProgress);

        // Base translation for centered slingshot (custom)
        matrices.translate(
                sign * -0.41F,   // small side offset (centers the model)
                0.10F,              // height
                -0.10F              // slight forward placement
        );

        // Base rotation (neutral)
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-5.0F)); // steep downward angle
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(sign * -92.5F)); // slight outward angle
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(sign * -2.0F));  // slight inward roll

        // Pull progress (vanilla bow formula)
        float used = stack.getMaxUseTime(player) - (player.getItemUseTimeLeft() - tickDelta + 1.0F);
        float pull = used / 20.0F;
        pull = (pull * pull + pull * 2.0F) / 3.0F;
        pull = Math.min(pull, 1.0F);

        // Small wobble animation
        if (pull > 0.1F) {
            float wobble = MathHelper.sin((used - 0.1F) * 1.3F);
            float amt = wobble * (pull - 0.1F);
            matrices.translate(0.0F, amt * 0.002F, 0.0F);
        }

        // Forward pull effect
        matrices.translate(pull * 0.05F, 0.0F , 0.0F);

        // Forward stretching effect (tension)
        matrices.scale(1.0F, 1.0F, 1.0F /*+ pull * 0.05F*/);

        renderer.renderItem(
                player,
                stack,
                right ? ModelTransformationMode.FIRST_PERSON_RIGHT_HAND : ModelTransformationMode.FIRST_PERSON_LEFT_HAND,
                !right,
                matrices,
                providers,
                light
        );

        matrices.pop();
        ci.cancel();
    }
}
