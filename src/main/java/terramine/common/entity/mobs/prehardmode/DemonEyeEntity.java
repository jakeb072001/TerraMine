package terramine.common.entity.mobs.prehardmode;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import terramine.common.entity.mobs.FlyingEntityAI;
import terramine.common.init.ModLootTables;

import java.util.Optional;

public class DemonEyeEntity extends FlyingEntityAI {
    public static final EntityDataAccessor<Integer> typed_data = SynchedEntityData.defineId(DemonEyeEntity.class, EntityDataSerializers.INT);

    public DemonEyeEntity(EntityType<? extends DemonEyeEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
        dayEscape = true;
        setEyeType(random.nextInt(6));
        this.lootTable = Optional.of(ModLootTables.DEMON_EYE);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(typed_data, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("eyeType", getEyeType());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setEyeType(tag.getInt("eyeType"));
    }

    @SuppressWarnings("ConstantConditions")
    private void setEyeType(int eyeType) {
        this.entityData.set(typed_data, eyeType);
        switch (eyeType) {
            case 0 -> {
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10);
                this.getAttribute(Attributes.ARMOR).setBaseValue(2);
                this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.2);
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(1.5);
            }
            case 1 -> {
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(15);
                this.getAttribute(Attributes.ARMOR).setBaseValue(4);
                this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.3);
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(1.5);
            }
            case 2 -> {
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20);
                this.getAttribute(Attributes.ARMOR).setBaseValue(2);
                this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.2);
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(1.5);
            }
            case 3 -> {
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(15);
                this.getAttribute(Attributes.ARMOR).setBaseValue(0);
                this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.2);
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(2);
            }
            case 4 -> {
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(15);
                this.getAttribute(Attributes.ARMOR).setBaseValue(4);
                this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.2);
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(1);
            }
            case 5 -> {
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(15);
                this.getAttribute(Attributes.ARMOR).setBaseValue(2);
                this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.15);
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(1);
            }
        }
    }

    private int getEyeType() {
        return this.entityData.get(typed_data);
    }

    @Override
    public boolean checkSpawnRules(@NotNull LevelAccessor world, @NotNull EntitySpawnReason spawnReason) {
        if (isDarkEnoughToSpawn((ServerLevelAccessor) world, this.blockPosition(), random) && random.nextFloat() < world.getMoonBrightness()) {
            return this.blockPosition().getY() > 50 && !this.level().isDay();
        }

        return false;
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15)
                .add(Attributes.ARMOR, 2)
                .add(Attributes.FOLLOW_RANGE, 24)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.2)
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.ATTACK_DAMAGE, 1.5);
    }
}
