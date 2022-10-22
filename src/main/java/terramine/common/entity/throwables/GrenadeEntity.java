package terramine.common.entity.throwables;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class GrenadeEntity extends ExplosiveThrowableEntity {
    public GrenadeEntity(EntityType<? extends ThrowableProjectile> entityType, Level level) {
        super(entityType, level);
        setStats(3, 2f, 0.6f);
        explosionType = BlockInteraction.NONE;
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        explode();
    }
}
