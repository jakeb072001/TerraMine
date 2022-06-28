package terramine.common.components;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnstableApiUsage")
public class MovementOrderComponent implements PlayerComponent<Component>, AutoSyncedComponent {
    private final Player provider;
    private boolean cloud;
    private boolean wings;
    private boolean wallJump;

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

    public void setWallJumped(boolean wallJump) {
        this.wallJump = wallJump;
    }

    public boolean getWallJumped() {
        return wallJump;
    }

    @Override
    public void readFromNbt(@NotNull CompoundTag tag) {
    }

    @Override
    public void writeToNbt(@NotNull CompoundTag tag) {
    }
}
