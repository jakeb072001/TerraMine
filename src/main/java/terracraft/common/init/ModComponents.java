package terracraft.common.init;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.world.entity.item.ItemEntity;
import terracraft.TerraCraft;
import terracraft.common.components.DPSDamageCounterComponent;
import terracraft.common.components.SwimAbilityComponent;
import terracraft.common.components.SyncedBooleanComponent;
import terracraft.common.mana.ManaHandler;

public class ModComponents implements EntityComponentInitializer {

	public static final ComponentKey<SyncedBooleanComponent> DROPPED_ITEM_ENTITY =
			ComponentRegistryV3.INSTANCE.getOrCreate(TerraCraft.id("dropped_item_entity"), SyncedBooleanComponent.class);
	public static final ComponentKey<SwimAbilityComponent> SWIM_ABILITIES =
			ComponentRegistryV3.INSTANCE.getOrCreate(TerraCraft.id("swim_abilities"), SwimAbilityComponent.class);
	public static final ComponentKey<ManaHandler> MANA_HANDLER =
			ComponentRegistryV3.INSTANCE.getOrCreate(TerraCraft.id("mana_handler"), ManaHandler.class);
	public static final ComponentKey<DPSDamageCounterComponent> DPS_METER_DAMAGE =
			ComponentRegistryV3.INSTANCE.getOrCreate(TerraCraft.id("dps_meter_damage"), DPSDamageCounterComponent.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.registerFor(ItemEntity.class, DROPPED_ITEM_ENTITY, entity -> new SyncedBooleanComponent("wasDropped"));
		registry.registerForPlayers(SWIM_ABILITIES, SwimAbilityComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
		registry.registerForPlayers(MANA_HANDLER, ManaHandler::new, RespawnCopyStrategy.ALWAYS_COPY);
		registry.registerForPlayers(DPS_METER_DAMAGE, DPSDamageCounterComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
	}
}
