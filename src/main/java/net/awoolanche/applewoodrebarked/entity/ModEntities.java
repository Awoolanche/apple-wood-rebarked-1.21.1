package net.awoolanche.applewoodrebarked.entity;

import net.awoolanche.applewoodrebarked.AppleWoodRebarked;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<ModBoatEntity> APPLE_BOAT = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(AppleWoodRebarked.MOD_ID, "apple_boat"),
            EntityType.Builder.<ModBoatEntity>create(ModBoatEntity::new, SpawnGroup.MISC)
                    .dimensions(1.375f, 0.5625f).build()
    );
    public static final EntityType<ModChestBoatEntity> APPLE_CHEST_BOAT = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(AppleWoodRebarked.MOD_ID, "apple_chest_boat"),
            EntityType.Builder.<ModChestBoatEntity>create(ModChestBoatEntity::new, SpawnGroup.MISC)
                    .dimensions(1.375f, 0.5625f).build()
    );
    public static final EntityType<SlingshotProjectileEntity> SLINGSHOT_PROJECTILE =
            Registry.register(
                    Registries.ENTITY_TYPE,
                    Identifier.of(AppleWoodRebarked.MOD_ID, "slingshot_projectile"),
                    FabricEntityTypeBuilder.<SlingshotProjectileEntity>create(SpawnGroup.MISC, SlingshotProjectileEntity::new)
                            .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
                            .trackRangeBlocks(64)
                            .trackedUpdateRate(10)
                            .build()
            );
}
