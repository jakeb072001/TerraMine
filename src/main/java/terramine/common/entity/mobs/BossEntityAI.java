package terramine.common.entity.mobs;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import terramine.common.init.ModSoundEvents;

// TODO: Boss Stuff
/**
 * Heavy WIP, needs to:
 * Display a boss health bar (that looks like it's from Terraria),
 * Boss music,
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
    private final ServerBossEvent bossEvent; // TODO: make custom class that has terraria looking boss bar

    protected BossEntityAI(EntityType<? extends BossEntityAI> entityType, Level level) {
        super(entityType, level);
        this.bossEvent = new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.GREEN, BossEvent.BossBarOverlay.PROGRESS);
        //level.playLocalSound(this.blockPosition(), ModSoundEvents.BOSS_MUSIC_1, SoundSource.HOSTILE, 1f, 1f, false); // TODO: sound doesn't loop, sound replays when entity reloads, sound doesn't fade out when boss dies
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

    protected void customServerAiStep() {
        super.customServerAiStep();
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }

    public void die(DamageSource damageSource) {
        super.die(damageSource);
        this.bossEvent.setProgress(0);
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

    public void startSeenByPlayer(ServerPlayer serverPlayer) {
        super.startSeenByPlayer(serverPlayer);
        this.bossEvent.addPlayer(serverPlayer);
    }

    public void stopSeenByPlayer(ServerPlayer serverPlayer) {
        super.stopSeenByPlayer(serverPlayer);
        this.bossEvent.removePlayer(serverPlayer);
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        if (this.hasCustomName()) {
            this.bossEvent.setName(this.getDisplayName());
        }
    }

    public void setCustomName(@Nullable Component component) {
        super.setCustomName(component);
        this.bossEvent.setName(this.getDisplayName());
    }

    public boolean shouldDropExperience() {
        return true;
    }

    protected boolean shouldDropLoot() {
        return true;
    }

    public boolean canHoldItem(@NotNull ItemStack itemStack) {
        return false;
    }

    public boolean wantsToPickUp(@NotNull ItemStack itemStack) {
        return false;
    }

    public void checkDespawn() {}

    protected boolean canRide(Entity entity) {
        return false;
    }

    public boolean canChangeDimensions() {
        return false;
    }

    @Override
    public boolean checkSpawnRules(@NotNull LevelAccessor world, @NotNull MobSpawnType spawnReason) {
        return true;
    }
}
