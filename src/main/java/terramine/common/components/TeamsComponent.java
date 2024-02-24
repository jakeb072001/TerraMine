package terramine.common.components;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import terramine.TerraMine;
import terramine.common.misc.TeamColours;

@SuppressWarnings("UnstableApiUsage")
public class TeamsComponent implements PlayerComponent<Component>, AutoSyncedComponent {
    private final Player provider;
    private TeamColours teamColour;

    public TeamsComponent(Player provider) {
        this.provider = provider;
        teamColour = TeamColours.NONE;
    }

    public void setTeamColour(TeamColours teamColour) {
        this.teamColour = teamColour;
    }

    public TeamColours getTeamColour() {
        return teamColour;
    }

    @Override
    public void readFromNbt(@NotNull CompoundTag tag) {
        if (tag.contains("team") && TerraMine.CONFIG.client.rememberTeam) {
            teamColour = TeamColours.getTeam(tag.getInt("team"));
        }
    }

    @Override
    public void writeToNbt(@NotNull CompoundTag tag) {
        if (teamColour != null && TerraMine.CONFIG.client.rememberTeam) {
            tag.putInt("team", teamColour.getIndex());
        }
    }

    @Override
    public boolean shouldSyncWith(ServerPlayer player) {
        return player == provider;
    }

    @Override
    public void writeSyncPacket(FriendlyByteBuf buf, ServerPlayer recipient) {
        if (teamColour != null) {
            buf.writeInt(teamColour.getIndex());
        } else {
            buf.writeInt(0);
        }
    }

    @Override
    public void applySyncPacket(FriendlyByteBuf buf) {
        teamColour = TeamColours.getTeam(buf.readInt());
    }
}
