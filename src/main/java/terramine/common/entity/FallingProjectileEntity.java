package terramine.common.entity;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.util.Mth.rotlerp;

public abstract class FallingProjectileEntity extends ThrowableProjectile {
    public FallingProjectileEntity(EntityType<? extends ThrowableProjectile> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void updateRotation() { // leave blank, makes rotation work
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    public void tick() { // todo: rotate so that its sideways toward the direction its moving and tilts down at the angle its falling at
        super.tick();

        //this.setYRot(rotlerp(getYRot(), (float)Math.toDegrees(Math.atan2(getDeltaMovement().z(), getDeltaMovement().x())) - 90, 360));
        //this.setXRot((float)(-(Mth.atan2(-getDeltaMovement().y(), Math.sqrt(getDeltaMovement().x() * getDeltaMovement().x() + getDeltaMovement().z() * getDeltaMovement().z())) * 180.0F / (float)Math.PI)));
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult blockHitResult) {
        setOnGround(true);
    }
}
