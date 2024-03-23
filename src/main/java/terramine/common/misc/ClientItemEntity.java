package terramine.common.misc;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

// todo: Add beacon type effect, https://github.com/shiroroku/LootBeams is a good example of how to do this
public class ClientItemEntity extends ItemEntity {
    private static final EntityDataAccessor<Optional<UUID>> DATA_PLAYER = SynchedEntityData.defineId(ClientItemEntity.class, EntityDataSerializers.OPTIONAL_UUID);;

    public ClientItemEntity(EntityType<? extends ClientItemEntity> entityType, Level level) {
        super(entityType, level);
    }

    public void setValues(Level level, ItemStack itemStack, double d, double e, double f) {
        this.setPos(d, e, f);
        this.setDeltaMovement(level.random.nextDouble() * 0.2 - 0.1, 0.2, level.random.nextDouble() * 0.2 - 0.1);
        this.setItem(itemStack);
    }

    public void setClientPlayer(@NotNull UUID playerUUID) {
        this.getEntityData().set(DATA_PLAYER, Optional.of(playerUUID));
    }

    public UUID getClientPlayer() {
        return this.getEntityData().get(DATA_PLAYER).orElseThrow();
    }

    public void playerTouch(@NotNull Player player) {
        if (player.getUUID() == getClientPlayer()) {
            super.playerTouch(player);
        }
    }

    protected void defineSynchedData() {
        this.getEntityData().define(DATA_PLAYER, Optional.empty());
        super.defineSynchedData();
    }

    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        if (getClientPlayer() != null) {
            compoundTag.putUUID("client_player", getClientPlayer());
        }
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        if (compoundTag.hasUUID("client_player")) {
            setClientPlayer(compoundTag.getUUID("client_player"));
        }
    }
}
