package terramine.mixin.item.terrasparkboots;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.common.init.ModItems;
import terramine.common.init.ModSoundEvents;
import terramine.common.item.curio.feet.TerrasparkBootsItem;
import terramine.common.trinkets.TrinketsHelper;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow
    public Level level;

    @Shadow @Final
    protected RandomSource random;

    @Shadow private EntityDimensions dimensions;

    @Shadow public abstract double getX();

    @Shadow public abstract double getY();

    @Shadow public abstract double getZ();

    @Shadow public abstract Vec3 getDeltaMovement();

    @Inject(method = "playStepSound", at = @At("HEAD"))
    private void playSprintStepSound(BlockPos pos, BlockState blockState, CallbackInfo callbackInfo) {
        if ((Entity) (Object) this instanceof LivingEntity self) {
            if (blockState.getMaterial().isSolid() && self.isSprinting() && isRunningWithBoots(self)) {
                self.level.playSound(null, self.blockPosition(), ModSoundEvents.SPEEDBOOTS_RUN, SoundSource.PLAYERS, 0.5F, 1);
            }
        }
    }

    @Inject(method = "playStepSound", at = @At("HEAD"))
    private void playWaterStepSound(BlockPos pos, BlockState blockState, CallbackInfo callbackInfo) {
        if ((Entity) (Object) this instanceof LivingEntity self) {
            if (blockState.getMaterial().isLiquid() && isLiquidRunningWithBoots(self)) {
                self.level.playSound(null, self.blockPosition(), ModSoundEvents.WATER_STEP, SoundSource.PLAYERS, 0.15F, 1);
            }
        }
    }

    @Inject(method = "spawnSprintParticle", at = @At("HEAD"))
    private void spawnWaterSprintParticle(CallbackInfo callbackInfo) {
        if ((Entity) (Object) this instanceof LivingEntity self && self.isSprinting()) {
            if (isRunningWithBoots(self)) {
                if (self.isOnGround() && !self.isCrouching())
                {
                    Vec3 motion = getDeltaMovement();
                    level.addParticle(ParticleTypes.POOF, getX(), getY() + 0.2F, getZ(), motion.x, motion.y, motion.z);
                }
            }
            if (isLiquidRunningWithBoots(self)) {
                BlockPos pos = new BlockPos(Mth.floor(getX()), Mth.floor(getY() - 0.2), Mth.floor(getZ()));
                BlockState blockstate = level.getBlockState(pos);
                if (blockstate.getRenderShape() == RenderShape.INVISIBLE) {
                    ParticleOptions particle;
                    Vec3 motion = getDeltaMovement().multiply(-4, 0, -4);
                    if (blockstate.getFluidState().is(FluidTags.LAVA)) {
                        motion = motion.add(0, 1, 0);
                        if (random.nextInt(3) == 0) {
                            particle = ParticleTypes.LAVA;
                        } else {
                            particle = ParticleTypes.FLAME;
                            motion = motion.multiply(0.2, 0.03, 0.2);
                        }
                    } else if (!blockstate.getFluidState().isEmpty()) {
                        if (random.nextInt(3) == 0) {
                            particle = ParticleTypes.BUBBLE;
                        } else {
                            particle = ParticleTypes.SPLASH;
                            motion = motion.add(0, 1.5, 0);
                        }
                    } else {
                        return;
                    }
                    level.addParticle(particle, getX() + (random.nextDouble() - 0.5) * dimensions.width, getY(), getZ() + (random.nextDouble() - 0.5) * dimensions.width, motion.x, motion.y, motion.z);
                }
            }
        }
    }

    @Unique
    private boolean isRunningWithBoots(LivingEntity self) {
        return TrinketsHelper.isEquipped(ModItems.HERMES_BOOTS, self) || TrinketsHelper.isEquipped(ModItems.SPECTRE_BOOTS, self)
                || TrinketsHelper.isEquipped(ModItems.FAIRY_BOOTS, self) || TrinketsHelper.isEquipped(ModItems.LIGHTNING_BOOTS, self)
                || TrinketsHelper.isEquipped(ModItems.FROSTSPARK_BOOTS, self) || TrinketsHelper.isEquipped(ModItems.TERRASPARK_BOOTS, self);
    }

    @Unique
    private boolean isLiquidRunningWithBoots(LivingEntity self) {
        return (TrinketsHelper.isEquipped(ModItems.WATER_WALKING_BOOTS, self) || TrinketsHelper.isEquipped(ModItems.OBSIDIAN_WATER_WALKING_BOOTS, self)
        || TrinketsHelper.isEquipped(ModItems.LAVA_WADERS, self) || TrinketsHelper.isEquipped(ModItems.TERRASPARK_BOOTS, self)) && TerrasparkBootsItem.canSprintOnWater(self);
    }
}
