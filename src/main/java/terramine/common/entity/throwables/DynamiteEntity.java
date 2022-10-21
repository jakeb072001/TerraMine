package terramine.common.entity.throwables;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;

public class DynamiteEntity extends ExplosiveThrowableEntity {
    public DynamiteEntity(EntityType<? extends ThrowableProjectile> entityType, Level level) {
        super(entityType, level);
        setStats(5, 6f, 2.5f);
    }
}
