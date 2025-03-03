package terramine.common.init;

import net.minecraft.world.entity.item.ItemEntity;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistryV3;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;
import org.ladysnake.cca.api.v3.level.LevelComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.level.LevelComponentInitializer;
import terramine.TerraMine;
import terramine.common.components.*;
import terramine.common.entity.mobs.prehardmode.DemonEyeEntity;
import terramine.common.misc.ManaHandler;

public class ModComponents implements EntityComponentInitializer, LevelComponentInitializer {

	// Entity Data
	public static final ComponentKey<SyncedBooleanComponent> DROPPED_ITEM_ENTITY =
			ComponentRegistryV3.INSTANCE.getOrCreate(TerraMine.id("dropped_item_entity"), SyncedBooleanComponent.class);
	public static final ComponentKey<SyncedIntegerComponent> DEMON_EYE_ENTITY =
			ComponentRegistryV3.INSTANCE.getOrCreate(TerraMine.id("demon_eye_entity"), SyncedIntegerComponent.class);

	// Player Data
	public static final ComponentKey<SwimAbilityComponent> SWIM_ABILITIES =
			ComponentRegistryV3.INSTANCE.getOrCreate(TerraMine.id("swim_abilities"), SwimAbilityComponent.class);
	public static final ComponentKey<ManaHandler> MANA_HANDLER =
			ComponentRegistryV3.INSTANCE.getOrCreate(TerraMine.id("mana_handler"), ManaHandler.class);
	public static final ComponentKey<MovementOrderComponent> MOVEMENT_ORDER =
			ComponentRegistryV3.INSTANCE.getOrCreate(TerraMine.id("movement_order"), MovementOrderComponent.class);
	public static final ComponentKey<LavaImmunityComponent> LAVA_IMMUNITY =
			ComponentRegistryV3.INSTANCE.getOrCreate(TerraMine.id("lava_immunity"), LavaImmunityComponent.class);
	public static final ComponentKey<TeamsComponent> TEAMS =
			ComponentRegistryV3.INSTANCE.getOrCreate(TerraMine.id("teams"), TeamsComponent.class);
	public static final ComponentKey<SyncedBooleanComponent> SPACE_GUN_FREE =
			ComponentRegistryV3.INSTANCE.getOrCreate(TerraMine.id("space_gun_free"), SyncedBooleanComponent.class);
	public static final ComponentKey<SyncedIntegerComponent> ACCESSORY_SLOTS_ADDER =
			ComponentRegistryV3.INSTANCE.getOrCreate(TerraMine.id("accessory_slots_adder"), SyncedIntegerComponent.class);
	public static final ComponentKey<SyncedBooleanComponent> ACCESSORY_HARDCORE_CHECK =
			ComponentRegistryV3.INSTANCE.getOrCreate(TerraMine.id("accessory_hardcore_check"), SyncedBooleanComponent.class);
	public static final ComponentKey<SyncedBooleanComponent> ACCESSORY_DEMON_HEART_CHECK =
			ComponentRegistryV3.INSTANCE.getOrCreate(TerraMine.id("accessory_demon_heart_check"), SyncedBooleanComponent.class);

	// todo: replace with scoreboard components, will need slight rewrite but will be needed
	// Level Data
	public static final ComponentKey<SyncedBooleanComponent> HARDMODE =
			ComponentRegistryV3.INSTANCE.getOrCreate(TerraMine.id("hardmode"), SyncedBooleanComponent.class);
	public static final ComponentKey<SyncedBooleanComponent> EVIL_TYPE =
			ComponentRegistryV3.INSTANCE.getOrCreate(TerraMine.id("evil_type"), SyncedBooleanComponent.class);
	public static final ComponentKey<OreComponent> ORE_TYPES =
			ComponentRegistryV3.INSTANCE.getOrCreate(TerraMine.id("ore_types"), OreComponent.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		// Entity
		registry.registerFor(ItemEntity.class, DROPPED_ITEM_ENTITY, entity -> new SyncedBooleanComponent("wasDropped"));
		registry.registerFor(DemonEyeEntity.class, DEMON_EYE_ENTITY, entity -> new SyncedIntegerComponent("eyeType"));

		// Player
		registry.registerForPlayers(SWIM_ABILITIES, SwimAbilityComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
		registry.registerForPlayers(MANA_HANDLER, ManaHandler::new, RespawnCopyStrategy.CHARACTER);
		registry.registerForPlayers(MOVEMENT_ORDER, MovementOrderComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
		registry.registerForPlayers(LAVA_IMMUNITY, LavaImmunityComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
		registry.registerForPlayers(TEAMS, TeamsComponent::new, RespawnCopyStrategy.CHARACTER);
		registry.registerForPlayers(SPACE_GUN_FREE, player -> new SyncedBooleanComponent("space_gun_free"), RespawnCopyStrategy.LOSSLESS_ONLY);
		registry.registerForPlayers(ACCESSORY_SLOTS_ADDER, player -> new SyncedIntegerComponent("extraSlots"), RespawnCopyStrategy.CHARACTER);
		registry.registerForPlayers(ACCESSORY_HARDCORE_CHECK, player -> new SyncedBooleanComponent("addedHardcoreSlot"), RespawnCopyStrategy.CHARACTER);
		registry.registerForPlayers(ACCESSORY_DEMON_HEART_CHECK, player -> new SyncedBooleanComponent("addedDemonHeartSlot"), RespawnCopyStrategy.CHARACTER);
	}

	@Override
	public void registerLevelComponentFactories(LevelComponentFactoryRegistry registry) {
		registry.register(HARDMODE, levelData -> new SyncedBooleanComponent("hardmode"));
		registry.register(EVIL_TYPE, levelData -> new SyncedBooleanComponent("evil_type"));
		registry.register(ORE_TYPES, levelData -> new OreComponent("ore_types"));
	}
}
