package net.awoolanche.applewoodrebarked.block;

import net.awoolanche.applewoodrebarked.AppleWoodRebarked;

import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.WoodType;

public interface ModWoodTypes {
    BlockSetType APPLE_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.OAK).register(AppleWoodRebarked.id("apple"));
    WoodType APPLE = WoodTypeBuilder.copyOf(WoodType.OAK).register(AppleWoodRebarked.id("apple"), APPLE_BLOCK_SET_TYPE);
}





