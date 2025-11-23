package net.awoolanche.applewoodrebarked.mixin;

import net.minecraft.block.Block;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ProjectileEntity.class)
public abstract class ProjectileEntityMixin {

	@Inject(method = "onBlockHit", at = @At("TAIL"))
	private void onLeafHit(BlockHitResult hit, CallbackInfo ci) {
		ProjectileEntity projectile = (ProjectileEntity)(Object)this;
		World world = projectile.getWorld();
		if (world.isClient) return;

		BlockPos pos = hit.getBlockPos();
		BlockState state = world.getBlockState(pos);

		// Vinery
		var APPLE_LEAVES = Registries.BLOCK.get(Identifier.of("vinery","apple_leaves"));
		var CHERRY_LEAVES = Registries.BLOCK.get(Identifier.of("vinery","dark_cherry_leaves"));

		if (state.isOf(APPLE_LEAVES) || state.isOf(CHERRY_LEAVES)) {
			Block.dropStacks(state, world, pos, null);
		}
	}
}