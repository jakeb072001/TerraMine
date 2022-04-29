package terracraft.common.components;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import terracraft.TerraCraft;

public class SyncedIntegerComponent implements Component, AutoSyncedComponent {

	private final String name;
	protected int integer;
	protected boolean set = false;

	public SyncedIntegerComponent(String name) {
		this.name = name;
	}

	public int get() {
		return integer;
	}

	public void set(int integer) {
		this.integer = integer;
		this.set = true;
		TerraCraft.LOGGER.info("integer: " + this.integer);
		TerraCraft.LOGGER.info("boolean: " + this.set);
	}

	public boolean isSet() {
		return set;
	}

	@Override
	public void readFromNbt(CompoundTag tag) {
		this.integer = tag.contains(this.name) ? tag.getInt(this.name) : 0;
		this.set = tag.contains("isSet") && tag.getBoolean("isSet");
	}

	@Override
	public void writeToNbt(CompoundTag tag) {
		tag.putInt(this.name, this.integer);
		tag.putBoolean("isSet", this.set);
	}

	@Override
	public void writeSyncPacket(FriendlyByteBuf buf, ServerPlayer recipient) {
		buf.writeInt(this.integer);
		buf.writeBoolean(this.set);
	}

	@Override
	public void applySyncPacket(FriendlyByteBuf buf) {
		this.integer = buf.readInt();
		this.set = buf.readBoolean();
	}
}
