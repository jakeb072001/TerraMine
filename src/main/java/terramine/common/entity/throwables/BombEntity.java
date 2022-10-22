package terramine.common.entity.throwables;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;

public class BombEntity extends ExplosiveThrowableEntity {
    public BombEntity(EntityType<? extends ThrowableProjectile> entityType, Level level) {
        super(entityType, level);
        setStats(3, 4f, 1f);
    }
}
