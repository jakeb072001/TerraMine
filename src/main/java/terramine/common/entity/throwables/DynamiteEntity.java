package terramine.common.entity.throwables;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import terramine.common.init.ModSoundEvents;
import terramine.common.utility.ExplosionConfigurable;

public class DynamiteEntity extends ThrowableProjectile {
    private int timer = 0;

    public DynamiteEntity(EntityType<? extends ThrowableProjectile> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    public void tick() {
        super.tick();
        timer ++;

        if (timer >= 60 && !this.level.isClientSide) {
            new ExplosionConfigurable(level, this, this.position().x(), this.position().y(), this.position().z(), 6F, 2.5f, Explosion.BlockInteraction.BREAK);
            level.playSound(null, this.blockPosition(), ModSoundEvents.BOMB, SoundSource.AMBIENT, 1f, 1f);
            this.discard();
        }
    }

    @Override
    protected void updateRotation() {
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult blockHitResult) { // need to stop dynamite for going through wall horizontally
        setDeltaMovement(getDeltaMovement().x / 1.5f, 0, getDeltaMovement().z / 1.5f);
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        Entity entity = this.getOwner();
        return new ClientboundAddEntityPacket(this, entity == null ? 0 : entity.getId());
    }
}
