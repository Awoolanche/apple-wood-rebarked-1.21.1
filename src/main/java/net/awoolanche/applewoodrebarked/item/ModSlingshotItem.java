package net.awoolanche.applewoodrebarked.item;

import net.awoolanche.applewoodrebarked.entity.SlingshotProjectileEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import java.util.function.Predicate;

public class ModSlingshotItem extends Item {

    public ModSlingshotItem(Settings settings) {
        super(settings);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 36000; // Standard maximum use time 72000 for bow
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity player)) return;

        ItemStack ammo = findSlingshotAmmo(player);

        if (ammo.isEmpty() && player.getAbilities().creativeMode) {
            ammo = new ItemStack(Items.EGG);
        }

        if (ammo.isEmpty()) return;

        // Calculate charge time/power
        int useTime = this.getMaxUseTime(stack, user) - remainingUseTicks;
        float power = (float)useTime / 15.0f; // Scale time by 20 ticks (1 second)

        if (power < 0.1f) return;

        if (power > 1.0f) power = 1.0f;

        if (!world.isClient) {
            SlingshotProjectileEntity proj = new SlingshotProjectileEntity(world, player, ammo);

            // Projectile velocity
            proj.setVelocity(player, player.getPitch(), player.getYaw(), 0.0f, 1.4f * power, 0.0f);

            world.spawnEntity(proj);

            // Damage the item
            stack.damage(1, player, LivingEntity.getSlotForHand(player.getActiveHand()));
        }

        // Consume the ammo
        if (!player.getAbilities().creativeMode) {
            ammo.decrement(1);
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (findSlingshotAmmo(user).isEmpty() && !user.getAbilities().creativeMode) {
            return TypedActionResult.fail(itemStack);
        }

        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }

    @Override
    public boolean isUsedOnRelease(ItemStack stack) {
        return true;
    }

    // Ammo list predicate
    private static final Predicate<ItemStack> SLINGSHOT_AMMO_PREDICATE = stack ->
                    stack.isOf(Items.EGG) ||
                    stack.isOf(Items.CLAY_BALL) ||
                    stack.isOf(Items.FIRE_CHARGE) ||
                    stack.isOf(Items.SLIME_BALL)  ||
                    stack.isOf(Items.SNOWBALL);

    // Ammo check, offhand first, then inventory
    private ItemStack findSlingshotAmmo(PlayerEntity player) {

        ItemStack offhandStack = player.getOffHandStack();
        if (SLINGSHOT_AMMO_PREDICATE.test(offhandStack)) {
            return offhandStack;
        }

        for (int i = 0; i < player.getInventory().size(); ++i) {
            ItemStack inventoryStack = player.getInventory().getStack(i);
            if (SLINGSHOT_AMMO_PREDICATE.test(inventoryStack)) {
                return inventoryStack;
            }
        }

        return ItemStack.EMPTY;
    }
}