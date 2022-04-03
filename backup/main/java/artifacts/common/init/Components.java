package terracraft.common.init;

import terracraft.Artifacts;
import terracraft.common.components.DPSDamageCounterComponent;
import terracraft.common.components.SwimAbilityComponent;
import terracraft.common.components.SyncedBooleanComponent;
import terracraft.common.mana.ManaHandler;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.world.entity.item.ItemEntity;

public class Components implements EntityComponentInitializer {

	public static final ComponentKey<SyncedBooleanComponent> DROPPED_ITEM_ENTITY =
			ComponentRegistryV3.INSTANCE.getOrCreate(Artifacts.id("dropped_item_entity"), SyncedBooleanComponent.class);
	public static final ComponentKey<SwimAbilityComponent> SWIM_ABILITIES =
			ComponentRegistryV3.INSTANCE.getOrCreate(Artifacts.id("swim_abilities"), SwimAbilityComponent.class);
	public static final ComponentKey<ManaHandler> MANA_HANDLER =
			ComponentRegistryV3.INSTANCE.getOrCreate(Artifacts.id("mana_handler"), ManaHandler.class);
	public static final ComponentKey<DPSDamageCounterComponent> DPS_METER_DAMAGE =
			ComponentRegistryV3.INSTANCE.getOrCreate(Artifacts.id("dps_meter_damage"), DPSDamageCounterComponent.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.registerFor(ItemEntity.class, DROPPED_ITEM_ENTITY, entity -> new SyncedBooleanComponent("wasDropped"));
		registry.registerForPlayers(SWIM_ABILITIES, SwimAbilityComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
		registry.registerForPlayers(MANA_HANDLER, ManaHandler::new, RespawnCopyStrategy.ALWAYS_COPY);
		registry.registerForPlayers(DPS_METER_DAMAGE, DPSDamageCounterComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
	}
}
