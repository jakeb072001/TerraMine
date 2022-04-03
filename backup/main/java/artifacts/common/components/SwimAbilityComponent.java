package terracraft.common.components;

import terracraft.common.item.curio.old.HeliumFlamingoItem;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

@SuppressWarnings("UnstableApiUsage")
public class SwimAbilityComponent implements PlayerComponent<Component>, AutoSyncedComponent {

	private boolean shouldSwim;
	private boolean shouldSink;
	private boolean hasTouchedWater;
	private int swimTime;
	private final Player provider;

	public SwimAbilityComponent(Player provider) {
		this.provider = provider;
	}

	public boolean isSwimming() {
		return shouldSwim;
	}

	public boolean isSinking() {
		return shouldSink;
	}

	public boolean isWet() {
		return hasTouchedWater;
	}

	public int getSwimTime() {
		return swimTime;
	}

	public void setSwimming(boolean shouldSwim) {
		if (this.shouldSwim && !shouldSwim) {
			setSwimTime((int) (-HeliumFlamingoItem.RECHARGE_TIME * getSwimTime() / (float) HeliumFlamingoItem.MAX_FLIGHT_TIME));
		}

		this.shouldSwim = shouldSwim;
	}

	public void setSinking(boolean shouldSink) {
		this.shouldSink = shouldSink;
	}

	public void setWet(boolean hasTouchedWater) {
		this.hasTouchedWater = hasTouchedWater;
	}

	public void setSwimTime(int swimTime) {
		this.swimTime = swimTime;
	}

	@Override
	public void readFromNbt(CompoundTag tag) {
		this.setSwimming(tag.getBoolean("ShouldSwim"));
		this.setSinking(tag.getBoolean("ShouldSink"));
		this.setWet(tag.getBoolean("IsWet"));
		this.setSwimTime(tag.getInt("SwimTime"));
	}

	@Override
	public void writeToNbt(CompoundTag tag) {
		tag.putBoolean("ShouldSwim", this.isSwimming());
		tag.putBoolean("ShouldSink", this.isSinking());
		tag.putBoolean("IsWet", this.isWet());
		tag.putInt("SwimTime", this.getSwimTime());
	}

	@Override
	public boolean shouldSyncWith(ServerPlayer player) {
		return player == provider;
	}

	@Override
	public void writeSyncPacket(FriendlyByteBuf buf, ServerPlayer recipient) {
		buf.writeBoolean(this.isSwimming());
		buf.writeBoolean(this.isSinking());
		buf.writeBoolean(this.isWet());
		buf.writeInt(this.getSwimTime());
	}

	@Override
	public void applySyncPacket(FriendlyByteBuf buf) {
		this.setSwimming(buf.readBoolean());
		this.setSinking(buf.readBoolean());
		this.setWet(buf.readBoolean());
		this.setSwimTime(buf.readInt());
	}

	// Swimming needs C2S syncing, which is not covered by AutoSyncedComponent
	@Environment(EnvType.CLIENT)
	public void syncSwimming() {
		FriendlyByteBuf byteBuf = PacketByteBufs.create();
		byteBuf.writeBoolean(this.isSwimming());
		ClientPlayNetworking.send(HeliumFlamingoItem.C2S_AIR_SWIMMING_ID, byteBuf);
	}
}
