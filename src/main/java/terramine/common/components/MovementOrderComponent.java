package terramine.common.components;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

@SuppressWarnings("UnstableApiUsage")
public class MovementOrderComponent implements PlayerComponent<Component>, AutoSyncedComponent {
    private final Player provider;
    private boolean cloud;
    private boolean wings;

    public MovementOrderComponent(Player provider) {
        this.provider = provider;
    }

    public void setCloudFinished(boolean cloud) {
        this.cloud = cloud;
    }

    public void setWingsFinished(boolean wings) {
        this.wings = wings;
    }

    public boolean getCloudFinished() {
        return cloud;
    }

    public boolean getWingsFinished() {
        return wings;
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
    }
}
