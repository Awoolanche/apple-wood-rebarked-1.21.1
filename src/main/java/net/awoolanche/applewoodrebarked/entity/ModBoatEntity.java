package net.awoolanche.applewoodrebarked.entity;

import net.awoolanche.applewoodrebarked.item.ModItems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class ModBoatEntity extends BoatEntity {
    public ModBoatEntity(EntityType<? extends BoatEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public Item asItem() {
        return ModItems.APPLE_BOAT;
    }
}