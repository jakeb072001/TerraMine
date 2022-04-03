package terracraft.common.components;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

@SuppressWarnings("UnstableApiUsage")
public class DPSDamageCounterComponent implements PlayerComponent<Component>, AutoSyncedComponent {

	private float lastDamageTaken = 0;
	private final Player provider;

	public DPSDamageCounterComponent(Player provider) {
		this.provider = provider;
	}

	public void setDamageTaken(float damage) {
		lastDamageTaken += damage;
	}

	public void resetDamageTaken() {
		lastDamageTaken = 0;
	}

	public float getDamageTaken() {
		return lastDamageTaken;
	}

	@Override
	public void readFromNbt(CompoundTag tag) {
	}

	@Override
	public void writeToNbt(CompoundTag tag) {
	}
}
