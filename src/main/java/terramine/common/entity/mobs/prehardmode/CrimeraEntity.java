package terramine.common.entity.mobs.prehardmode;

import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.NotNull;
import terramine.common.entity.mobs.FlyingEntityAI;
import terramine.common.init.ModLootTables;

import java.util.Optional;

public class CrimeraEntity extends FlyingEntityAI {

    public CrimeraEntity(EntityType<? extends CrimeraEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
        this.lootTable = Optional.of(ModLootTables.CRIMERA);
    }

    @Override
    public boolean checkSpawnRules(@NotNull LevelAccessor world, @NotNull EntitySpawnReason spawnReason) {
        return true;
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.ARMOR, 8)
                .add(Attributes.FOLLOW_RANGE, 24)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
                .add(Attributes.MOVEMENT_SPEED, 0.7)
                .add(Attributes.ATTACK_DAMAGE, 2);
    }
}
