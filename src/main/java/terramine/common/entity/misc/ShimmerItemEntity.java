package terramine.common.entity.misc;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import terramine.common.init.ModEntities;

import java.util.Optional;
import java.util.UUID;

// todo: add config option to allow creative players to see and pickup all treasure bags
public class ShimmerItemEntity extends ItemEntity {
    public ShimmerItemEntity(EntityType<? extends ShimmerItemEntity> entityType, Level level) {
        super(entityType, level);
    }

    public void setValues(Level level, double d, double e, double f, ItemStack itemStack) {
        this.setPos(d, e, f);
        this.setDeltaMovement(level.random.nextDouble() * 0.2 - 0.1, 0.2, level.random.nextDouble() * 0.2 - 0.1);
        this.setItem(itemStack);
    }

    @Override
    public void tick() {
        super.tick();
        if (level() instanceof ServerLevel) {
            double xOffset = (level().random.nextDouble() - 0.5) * 0.01;
            double yOffset = (level().random.nextDouble() - 0.5) * 0.01;
            double zOffset = (level().random.nextDouble() - 0.5) * 0.01;

            double x = position().x() + xOffset;
            double y = position().y() + yOffset;
            double z = position().z() + zOffset;

            ((ServerLevel) level()).sendParticles(ParticleTypes.END_ROD, x, y, z, 1, 0, 0, 0, 0.1);
        }
    }
}
