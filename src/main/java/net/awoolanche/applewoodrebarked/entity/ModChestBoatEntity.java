package net.awoolanche.applewoodrebarked.entity;

import net.awoolanche.applewoodrebarked.item.ModItems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;


public class ModChestBoatEntity extends ChestBoatEntity {
    public ModChestBoatEntity(EntityType<? extends ChestBoatEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public Item asItem() {
        return ModItems.APPLE_CHEST_BOAT;
    }
}