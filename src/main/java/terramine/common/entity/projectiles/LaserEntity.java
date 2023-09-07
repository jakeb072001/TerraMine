package terramine.common.entity.projectiles;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import terramine.common.init.ModDamageSource;

import static terramine.common.init.ModAttributes.damageMultiplier;

public class LaserEntity extends FallingProjectileEntity {
    private int timer, hitCount = 0;
    private Item weapon;

    public LaserEntity(EntityType<? extends FallingProjectileEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();
        this.setNoGravity(true);
        timer++;

        if (timer >= 120) {
            this.discard();
        }
    }

    @Override
    public void shootFromRotation(@NotNull Entity entity, float f, float g, float h, float i, float j) {
        float k = -Mth.sin(g * 0.017453292F) * Mth.cos(f * 0.017453292F);
        float l = -Mth.sin((f + h) * 0.017453292F);
        float m = Mth.cos(g * 0.017453292F) * Mth.cos(f * 0.017453292F);
        this.shoot(k, l, m, i, j);
        // removed adding player movement, makes aiming easier
    }

    @Override
    public void shoot(double d, double e, double f, float g, float h) {
        // override used to stop bullet spread
        Vec3 vec3 = (new Vec3(d, e, f)).normalize().scale(g);
        this.setDeltaMovement(vec3);
        double i = vec3.horizontalDistance();
        this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * 57.2957763671875));
        this.setXRot((float)(Mth.atan2(vec3.y, i) * 57.2957763671875));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
    }

    public void setGun(Item item) {
        weapon = item;
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) { // todo: reduce invulnerability time when hit with laser, need mixin for this, use extension.
        super.onHitEntity(entityHitResult);
        if (this.getOwner() != null && weapon != null) {
            entityHitResult.getEntity().hurt(ModDamageSource.indirectLaserProjectile(this.getOwner(), weapon), 1.5f * damageMultiplier(this.getOwner()));
            hitCount++;
            if (hitCount == 3) {
                this.discard();
            }
        }
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        this.discard();
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        timer = compoundTag.getInt("fadeTime");
        hitCount = compoundTag.getInt("hitCount");
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        compoundTag.putInt("fadeTime", timer);
        compoundTag.putInt("hitCount", hitCount);
    }
}
