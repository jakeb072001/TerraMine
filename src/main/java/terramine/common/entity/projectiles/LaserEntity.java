package terramine.common.entity.projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;
import terramine.common.init.ModDamageSource;

import static terramine.common.init.ModAttributes.damageMultiplier;

public class LaserEntity extends FallingProjectileEntity {
    private int timer = 0;
    private Item weapon;

    public LaserEntity(EntityType<? extends FallingProjectileEntity> entityType, Level level) {
        super(entityType, level);
    }

    public void tick() {
        super.tick();
        this.setNoGravity(true);
        timer++;

        if (timer >= 120) {
            this.discard();
        }
    }

    public void setGun(Item item) {
        weapon = item;
    }

    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (this.getOwner() != null && weapon != null) {
            entityHitResult.getEntity().hurt(ModDamageSource.indirectLaserProjectile(this.getOwner(), weapon), 1.5f * damageMultiplier(this.getOwner()));
        }
    }

    protected void onHitBlock(@NotNull BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        this.discard();
    }
}
