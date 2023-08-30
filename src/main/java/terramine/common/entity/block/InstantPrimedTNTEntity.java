package terramine.common.entity.block;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import terramine.common.utility.ExplosionConfigurable;

public class InstantPrimedTNTEntity extends PrimedTnt {

    public InstantPrimedTNTEntity(EntityType<? extends InstantPrimedTNTEntity> entityType, Level level) {
        super(entityType, level);
    }

    public void setValues(Level level, double d, double e, double f, @Nullable LivingEntity livingEntity) {
        this.setPos(d, e, f);
        double g = level.random.nextDouble() * 6.2831854820251465;
        this.setDeltaMovement(-Math.sin(g) * 0.02, 0.2f, -Math.cos(g) * 0.02);
        this.setFuse(80);
        this.xo = d;
        this.yo = e;
        this.zo = f;
    }

    @Override
    public void tick() {
        this.discard();
        if (!this.level.isClientSide) {
            this.explode();
        }
    }

    private void explode() {
        new ExplosionConfigurable(level, this, this.position().x(), this.position().y(), this.position().z(), 20F, 100f, Explosion.BlockInteraction.BREAK);
    }
}
