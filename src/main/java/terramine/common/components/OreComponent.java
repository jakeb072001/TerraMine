package terramine.common.components;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.LevelData;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.component.Component;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

public class OreComponent implements Component, AutoSyncedComponent {

	private final String name;
	protected boolean copper_not_tin;
	protected boolean iron_not_lead;
	protected boolean silver_not_tungsten;
	protected boolean gold_not_platinum;
	protected boolean cobalt_not_palladium;
	protected boolean mythril_not_orichalcum;
	protected boolean adamantite_not_titanium;
	private static LevelData levelData;

	public OreComponent(String name) {
		this.name = name;
	}

	public boolean getIfCopper() {
		return copper_not_tin;
	}
	public boolean getIfIron() {
		return iron_not_lead;
	}
	public boolean getIfSilver() {
		return silver_not_tungsten;
	}
	public boolean getIfGold() {
		return gold_not_platinum;
	}
	public boolean getIfCobalt() {
		return cobalt_not_palladium;
	}
	public boolean getIfMythril() {
		return mythril_not_orichalcum;
	}
	public boolean getIfAdamantite() {
		return adamantite_not_titanium;
	}

	public void setIsCopper(boolean bool) {
		this.copper_not_tin = bool;
	}
	public void setIsIron(boolean bool) {
		this.iron_not_lead = bool;
	}
	public void setIsSilver(boolean bool) {
		this.silver_not_tungsten = bool;
	}
	public void setIsGold(boolean bool) {
		this.gold_not_platinum = bool;
	}
	public void setIsCobalt(boolean bool) {
		this.cobalt_not_palladium = bool;
	}
	public void setIsMythril(boolean bool) {
		this.mythril_not_orichalcum = bool;
	}
	public void setIsAdamantite(boolean bool) {
		this.adamantite_not_titanium = bool;
	}

	public static void setLevelData(LevelData mcLevelData) {
		levelData = mcLevelData;
	}

	@NotNull
	public static LevelData getLevelData() {
		if (levelData != null) {
			return levelData;
		}
		throw new UnsupportedOperationException("Accessed server level too early!");
	}

	@Override
	public void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		this.copper_not_tin = tag.contains(this.name + "_copper") && tag.getBoolean(this.name + "_copper");
		this.iron_not_lead = tag.contains(this.name + "_iron") && tag.getBoolean(this.name + "_iron");
		this.silver_not_tungsten = tag.contains(this.name + "_silver") && tag.getBoolean(this.name + "_silver");
		this.gold_not_platinum = tag.contains(this.name + "_gold") && tag.getBoolean(this.name + "_gold");
		this.cobalt_not_palladium = tag.contains(this.name + "_cobalt") && tag.getBoolean(this.name + "_cobalt");
		this.mythril_not_orichalcum = tag.contains(this.name + "_mythril") && tag.getBoolean(this.name + "_mythril");
		this.adamantite_not_titanium = tag.contains(this.name + "_adamantite") && tag.getBoolean(this.name + "_adamantite");
	}

	@Override
	public void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		tag.putBoolean(this.name + "_copper", this.copper_not_tin);
		tag.putBoolean(this.name + "_iron", this.iron_not_lead);
		tag.putBoolean(this.name + "_silver", this.silver_not_tungsten);
		tag.putBoolean(this.name + "_gold", this.gold_not_platinum);
		tag.putBoolean(this.name + "_cobalt", this.cobalt_not_palladium);
		tag.putBoolean(this.name + "_mythril", this.mythril_not_orichalcum);
		tag.putBoolean(this.name + "_adamantite", this.adamantite_not_titanium);
	}

	@Override
	public void writeSyncPacket(RegistryFriendlyByteBuf buf, ServerPlayer recipient) {
		buf.writeBoolean(this.copper_not_tin);
		buf.writeBoolean(this.iron_not_lead);
		buf.writeBoolean(this.silver_not_tungsten);
		buf.writeBoolean(this.gold_not_platinum);
		buf.writeBoolean(this.cobalt_not_palladium);
		buf.writeBoolean(this.mythril_not_orichalcum);
		buf.writeBoolean(this.adamantite_not_titanium);
	}

	@Override
	public void applySyncPacket(RegistryFriendlyByteBuf buf) {
		this.copper_not_tin = buf.readBoolean();
		this.iron_not_lead = buf.readBoolean();
		this.silver_not_tungsten = buf.readBoolean();
		this.gold_not_platinum = buf.readBoolean();
		this.cobalt_not_palladium = buf.readBoolean();
		this.mythril_not_orichalcum = buf.readBoolean();
		this.adamantite_not_titanium = buf.readBoolean();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		} else if (obj instanceof OreComponent other) {
            return this.getIfCopper() == other.getIfCopper() && this.name.equals(other.name);
		}
		return false;
	}
}
