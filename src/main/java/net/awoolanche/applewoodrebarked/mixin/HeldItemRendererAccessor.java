package net.awoolanche.applewoodrebarked.mixin;

import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(HeldItemRenderer.class)
public interface HeldItemRendererAccessor {

    @Invoker("applyEquipOffset")
    void applewood_invokeApplyEquipOffset(MatrixStack matrices, Arm arm, float equipProgress);
}
