package terracraft.common.components;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class SyncedBooleanComponent implements Component, AutoSyncedComponent {

	private final String name;
	protected boolean bool;

	public SyncedBooleanComponent(String name) {
		this.name = name;
	}

	public boolean get() {
		return bool;
	}

	public void set(boolean bool) {
		this.bool = bool;
	}

	@Override
	public void readFromNbt(CompoundTag tag) {
		this.bool = tag.contains(this.name) && tag.getBoolean(this.name);
	}

	@Override
	public void writeToNbt(CompoundTag tag) {
		tag.putBoolean(this.name, this.bool);
	}

	@Override
	public void writeSyncPacket(FriendlyByteBuf buf, ServerPlayer recipient) {
		buf.writeBoolean(this.bool);
	}

	@Override
	public void applySyncPacket(FriendlyByteBuf buf) {
		this.bool = buf.readBoolean();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		} else if (obj instanceof SyncedBooleanComponent) {
			SyncedBooleanComponent other = (SyncedBooleanComponent) obj;
			return this.get() == other.get() && this.name.equals(other.name);
		}
		return false;
	}
}
