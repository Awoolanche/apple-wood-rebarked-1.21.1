package net.awoolanche.applewoodrebarked.item;

import net.awoolanche.applewoodrebarked.AppleWoodRebarked;
import net.awoolanche.applewoodrebarked.block.ModBlocks;
import net.awoolanche.applewoodrebarked.entity.ModEntities;

import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.util.math.Vec3d;
import java.util.function.Predicate;
import java.util.List;

public class ModItems {

    public static final Item TEST_ITEM = registerItem("test_item",
            new Item(new Item.Settings()));

    public static final Item APPLE_SIGN = registerItem("apple_sign",
            new SignItem(new Item.Settings(), ModBlocks.APPLE_SIGN, ModBlocks.APPLE_WALL_SIGN));

    public static final Item APPLE_HANGING_SIGN = registerItem("apple_hanging_sign",
            new HangingSignItem(ModBlocks.APPLE_HANGING_SIGN, ModBlocks.APPLE_WALL_HANGING_SIGN, new Item.Settings()));

    public static final Item APPLE_BOAT = registerItem("apple_boat",
            new ModBoatItem(false, ModEntities.APPLE_BOAT, new Item.Settings().maxCount(1)));

    public static final Item APPLE_CHEST_BOAT = registerItem("apple_chest_boat",
            new ModBoatItem(true, ModEntities.APPLE_CHEST_BOAT, new Item.Settings().maxCount(1)));

    public static final Item SLINGSHOT = registerItem("slingshot",
            new ModSlingshotItem(new Item.Settings().maxDamage(128).maxCount(1)));


    public static class ModBoatItem extends Item {
        private final EntityType<? extends BoatEntity> type;
        private final boolean chest;

        public ModBoatItem(boolean chest, EntityType<? extends BoatEntity> type, Settings settings) {
            super(settings);
            this.chest = chest;
            this.type = type;
        }

        // Boat placement logic
        @Override
        public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
            ItemStack itemStack = user.getStackInHand(hand);
            HitResult hitResult = raycast(world, user, RaycastContext.FluidHandling.ANY);

            if (hitResult.getType() == HitResult.Type.MISS) {
                return TypedActionResult.pass(itemStack);
            } else {
                Vec3d vec3d = user.getRotationVec(1.0F);
                List<Entity> list = world.getOtherEntities(user, user.getBoundingBox().stretch(vec3d.multiply(5.0)).expand(1.0), ENTITY_PREDICATES);
                if (!list.isEmpty()) {
                    Vec3d vec3d2 = user.getEyePos();
                    for (Entity entity : list) {
                        if (entity.getBoundingBox().contains(vec3d2.add(vec3d.multiply(entity.getBoundingBox().contains(vec3d2) ? 0.0 : hitResult.getPos().distanceTo(vec3d2))))) {
                            return TypedActionResult.pass(itemStack);
                        }
                    }
                }

                if (hitResult.getType() == HitResult.Type.BLOCK) {
                    BoatEntity boat = this.type.create(world);
                    if (boat != null) {
                        boat.setPosition(hitResult.getPos().x, hitResult.getPos().y, hitResult.getPos().z);
                        boat.setYaw(user.getYaw());
                        if (!world.isSpaceEmpty(boat, boat.getBoundingBox())) {
                            return TypedActionResult.fail(itemStack);
                        } else {
                            if (!world.isClient) {
                                world.spawnEntity(boat);
                                if (!user.getAbilities().creativeMode) {
                                    itemStack.decrement(1);
                                }
                            }
                            return TypedActionResult.success(itemStack, world.isClient());
                        }
                    }
                }
                return TypedActionResult.pass(itemStack);
            }
        }
        private static final Predicate<Entity> ENTITY_PREDICATES = Entity::isSpectator;
    }



    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(AppleWoodRebarked.MOD_ID, name), item);
    }

    public static void registerModItems() {
        AppleWoodRebarked.LOGGER.info("Registering Mod Items for " + AppleWoodRebarked.MOD_ID);
    }
}
