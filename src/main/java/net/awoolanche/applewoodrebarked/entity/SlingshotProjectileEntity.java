package net.awoolanche.applewoodrebarked.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.random.Random;
import net.minecraft.registry.Registries;
import net.minecraft.world.World;

public class SlingshotProjectileEntity extends ThrownItemEntity {

    private boolean hasBounced = false;

    public SlingshotProjectileEntity(EntityType<? extends SlingshotProjectileEntity> type, World world) {
        super(type, world);
        if (this.getStack().isEmpty()) this.setItem(new ItemStack(Items.STONE));
    }

    public SlingshotProjectileEntity(World world, LivingEntity owner, ItemStack thrownItem) {
        super(ModEntities.SLINGSHOT_PROJECTILE, owner, world);
        this.setItem(thrownItem.copy());
    }

    @Override
    protected Item getDefaultItem() {
        return Items.EGG;
    }

    @Override
    protected void onBlockHit(BlockHitResult hitResult) {
        World world = getWorld();
        ItemStack ammo = this.getStack();

        var pos = hitResult.getBlockPos();

        var appleLeaves = Registries.BLOCK.get(Identifier.of("vinery", "apple_leaves"));
        var cherryLeaves = Registries.BLOCK.get(Identifier.of("vinery", "dark_cherry_leaves"));

        var block = world.getBlockState(pos).getBlock();

        // Vinery projectile harvest interaction
        if (block == appleLeaves || block == cherryLeaves) {
            if (this.getOwner() instanceof PlayerEntity player) {
                BlockHitResult fakeHit = new BlockHitResult(
                        getPos(),
                        hitResult.getSide(),
                        pos,
                        false
                );
                world.getBlockState(pos).onUse(world, player, fakeHit);
            }
        }

        // Slime ball bounce
        if (ammo.isOf(Items.SLIME_BALL) && !hasBounced) {
            bounceProjectile(hitResult);
            spawnSlimeParticles();
            return; // Continue flying
        }

        super.onBlockHit(hitResult);

        if (!world.isClient) {
            applySlingshotEffect(hitResult);
            world.sendEntityStatus(this, (byte)3);
            this.discard();
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        World world = getWorld();
        Entity target = entityHitResult.getEntity();
        ItemStack ammo = this.getStack();

        boolean shouldBounce = ammo.isOf(Items.SLIME_BALL) && !hasBounced;
        float damage = 0f;
        boolean setOnFire = false;

        // Damage for ammo types
             if (ammo.isOf(Items.CLAY_BALL)) damage = 2f;
        else if (ammo.isOf(Items.FIRE_CHARGE)) { damage = 5f; setOnFire = true;}
        else if (ammo.isOf(Items.SLIME_BALL)) damage = 1f;
        else if (ammo.isOf(Items.EGG)) damage = 0f;
        else if (ammo.isOf(Items.SNOWBALL)) damage = 0f;

        if (damage > 0f) {
            Entity owner = this.getOwner();
            if (owner instanceof PlayerEntity playerOwner) {
                target.damage(world.getDamageSources().playerAttack(playerOwner), damage);
            } else if (owner instanceof LivingEntity livingOwner) {
                target.damage(world.getDamageSources().mobProjectile(this, livingOwner), damage);
            } else {
                target.damage(world.getDamageSources().generic(), damage);
            }
        }

        if (setOnFire) target.setOnFireFor(5);

        // Slime ball bounce off entity
        if (shouldBounce) {
            this.setVelocity(this.getVelocity().multiply(-0.8));
            hasBounced = true;
            world.playSound(null, getX(), getY(), getZ(),
                    SoundEvents.BLOCK_SLIME_BLOCK_HIT, SoundCategory.NEUTRAL, 0.5F, 1.0F);
            spawnSlimeParticles();
            return;
        }

        super.onEntityHit(entityHitResult);

        if (!world.isClient) {
            applySlingshotEffect(entityHitResult);
            world.sendEntityStatus(this, (byte)3);
            this.discard();
        }
    }

    // Slime ball bounce logic
    private void bounceProjectile(HitResult hitResult) {
        switch (hitResult.getType()) {
            case BLOCK -> {
                BlockHitResult blockHit = (BlockHitResult) hitResult;
                switch (blockHit.getSide()) {
                    case UP, DOWN -> this.setVelocity(this.getVelocity().x, -this.getVelocity().y * 0.8, this.getVelocity().z);
                    case NORTH, SOUTH -> this.setVelocity(this.getVelocity().x, this.getVelocity().y, -this.getVelocity().z * 0.8);
                    case EAST, WEST -> this.setVelocity(-this.getVelocity().x * 0.8, this.getVelocity().y, this.getVelocity().z);
                }
            }
            case ENTITY -> this.setVelocity(this.getVelocity().multiply(-0.8));
        }
        hasBounced = true;
        getWorld().playSound(null, getX(), getY(), getZ(),
                SoundEvents.BLOCK_SLIME_BLOCK_HIT, SoundCategory.NEUTRAL, 0.5F, 1.0F);
    }

    private void spawnSlimeParticles() {
        World world = getWorld();
        if (world.isClient) {
            Random rng = world.random;
            for (int i = 0; i < 10; i++) {
                double dx = (rng.nextDouble() - 0.5) * 0.3;
                double dy = (rng.nextDouble() - 0.5) * 0.3;
                double dz = (rng.nextDouble() - 0.5) * 0.3;
                world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, this.getStack()),
                        this.getX(), this.getY(), this.getZ(),
                        dx, dy, dz);
            }
        }
    }

    // Particle effect on impact with velocity (shit values, adjust before releasing)
    @Override
    public void handleStatus(byte status) {
        if (status == 3) {
            Random rng = this.getWorld().random;
            double baseX = this.getVelocity().x;
            double baseY = this.getVelocity().y;
            double baseZ = this.getVelocity().z;

            for (int i = 0; i < 12; ++i) {
                double randX = (rng.nextDouble() - 0.5) * 0.3;
                double randY = (rng.nextDouble() - 0.5) * 0.3;
                double randZ = (rng.nextDouble() - 0.5) * 0.3;

                double velX = baseX * 0.4 + randX;
                double velY = baseY * 0.4 + randY;
                double velZ = baseZ * 0.4 + randZ;

                this.getWorld().addParticle(
                        new ItemStackParticleEffect(
                                ParticleTypes.ITEM,
                                this.getStack()
                        ),
                        this.getX(),
                        this.getY(),
                        this.getZ(),
                        velX,
                        velY,
                        velZ
                );
            }
        } else super.handleStatus(status);
    }

    // Custom ammo effects (to sort out later)
    private void applySlingshotEffect(HitResult hitResult) {
        World world = this.getWorld();
        ItemStack ammoStack = this.getStack();
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();

        // Egg
        if (ammoStack.isOf(Items.EGG)) {
            world.playSound(null, x, y, z,
                    SoundEvents.ENTITY_EGG_THROW, SoundCategory.NEUTRAL,
                    0.8F, 0.8F + world.getRandom().nextFloat() * 0.4F);
            if (world.getRandom().nextInt(8) == 0) {
                ChickenEntity chicken = EntityType.CHICKEN.create(world);
                if (chicken != null) {
                    chicken.setBreedingAge(-24000);
                    chicken.setPos(x, y, z);
                    world.spawnEntity(chicken);
                }
            }

            // Fire Charge
        } else if (ammoStack.isOf(Items.FIRE_CHARGE)) {
            world.createExplosion(this, x, y, z, 0.1f, World.ExplosionSourceType.NONE);
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                var blockHit = (BlockHitResult) hitResult;
                world.setBlockState(blockHit.getBlockPos().offset(blockHit.getSide()), net.minecraft.block.Blocks.FIRE.getDefaultState());
            }
            world.playSound(null, x, y, z,
                    SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS,
                    1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);

            // Clay Ball
        } else if (ammoStack.isOf(Items.CLAY_BALL)) {
            world.playSound(null, x, y, z,
                    SoundEvents.BLOCK_SLIME_BLOCK_FALL, SoundCategory.NEUTRAL,
                    0.5F, 0.5F + world.getRandom().nextFloat() * 0.4F);

            // Slime Ball
        } else if (ammoStack.isOf(Items.SLIME_BALL)) {
            world.playSound(null, x, y, z,
                    SoundEvents.BLOCK_SLIME_BLOCK_HIT, SoundCategory.NEUTRAL,
                    0.5F, 1.0F);

            // Snowball
        } else if (ammoStack.isOf(Items.SNOWBALL)) {
            world.playSound(null, x, y, z,
                    SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL,
                    0.5F, 1.0F);
        }
    }
}
