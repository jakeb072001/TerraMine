package terramine.common.entity.mobs;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.*;
import net.minecraft.world.level.dimension.DimensionType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;
// TODO: Boss Stuff
/**
 * Heavy WIP, needs to:
 * Display a boss health bar,
 * Track the bosses total health (eg, devourer and twins aren't one entity),
 * Attack only players on the same team if player that started the combat was on a team, otherwise only target the player that spawned the boss, and for natural spawns target everyone that was around where the boss spawned,
 * Only allow players that are targeted to attack boss (if it can hit you, you can hit it, otherwise you can not interact with it),
 * Drop loot bag for players that where targeted by boss (which will be a custom entity that's client sided, once picked up is a normal item though and is a storage container you can open and take items from but not put items in),
 * Make the boss leave once it is day.
 * <p>
 * First, make a team system, by default it will just use preset colours that each player can select just like in Terraria. But if FTB Teams or similar is installed then allow for proper team creation (best for bigger servers).
 * If a player joins a team or leaved the game during combat with a boss, they will not be targeted and will be unable to attack the boss, thus not receiving loot (when the boss spawns it will add players to a list, this list will only update to remove players that have left the game).
 */
public class BossEntityAI extends PathfinderMob implements Enemy {
    protected BossEntityAI(EntityType<? extends BossEntityAI> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 5;
    }

    public @NotNull SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    public void aiStep() {
        this.updateSwingTime();
        this.updateNoActionTime();
        super.aiStep();
    }

    protected void updateNoActionTime() {
        float f = this.getLightLevelDependentMagicValue();
        if (f > 0.5F) {
            this.noActionTime += 2;
        }

    }

    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    protected @NotNull SoundEvent getSwimSound() {
        return SoundEvents.HOSTILE_SWIM;
    }

    protected @NotNull SoundEvent getSwimSplashSound() {
        return SoundEvents.HOSTILE_SPLASH;
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return SoundEvents.HOSTILE_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.HOSTILE_DEATH;
    }

    public LivingEntity.@NotNull Fallsounds getFallSounds() {
        return new LivingEntity.Fallsounds(SoundEvents.HOSTILE_SMALL_FALL, SoundEvents.HOSTILE_BIG_FALL);
    }

    public float getWalkTargetValue(@NotNull BlockPos blockPos, LevelReader levelReader) {
        return -levelReader.getPathfindingCostFromLightLevels(blockPos);
    }

    public boolean shouldDropExperience() {
        return true;
    }

    protected boolean shouldDropLoot() {
        return true;
    }

    @Override
    public boolean checkSpawnRules(@NotNull LevelAccessor world, @NotNull MobSpawnType spawnReason) {
        return true;
    }
}
