package terramine;

import dev.architectury.event.events.common.PlayerEvent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import terrablender.api.RegionType;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;
import terrablender.worldgen.TBSurfaceRuleData;
import terramine.common.compat.CompatHandler;
import terramine.common.config.ConfigHelper;
import terramine.common.config.ModConfig;
import terramine.common.init.*;
import terramine.common.misc.TeamColours;
import terramine.common.misc.TerrariaInventory;
import terramine.common.network.ServerPacketHandler;
import terramine.common.utility.InputHandler;
import terramine.common.world.biome.BiomeAdder;
import terramine.common.world.biome.BiomeAdderCrimsonForced;
import terramine.common.world.biome.BiomeSurfaceRules;
import terramine.extensions.PlayerStorages;

import java.util.Optional;

public class TerraMine implements ModInitializer, TerraBlenderApi {

	public static final String MOD_ID = "terramine";
	public static final Logger LOGGER = LoggerFactory.getLogger(TerraMine.class);
	public static ModConfig CONFIG;
	public static final int CONFIG_VERSION = 2; // Increase if config changed in an incompatible way
	//private static final Map<String, Runnable> COMPAT_HANDLERS = Map.of(
	//		"origins", new OriginsCompat(),
	//		"haema", new HaemaCompat());

	@Override
	@SuppressWarnings("ResultOfMethodCallIgnored")
	public void onInitialize() {
		// Config
		CONFIG = ConfigHelper.getConfigAndInvalidateOldVersions();

		// Packets
		ServerPacketHandler.register();

		// Loot table setup
		ModLootConditions.register();
		LootTableEvents.MODIFY.register((resourceManager, manager, id, supplier, setter) -> ModLootTables.onLootTableLoad(id, supplier));

		// Force loading init classes
		// Entities is loaded by items, loot tables can load lazily (no registration)
		ModItems.TERRASPARK_BOOTS.toString();
		ModItemGroups.ITEM_GROUP_EQUIPMENT.toString();
		ModItemGroups.registerItemGroups();
		ModEntities.addToSpawn();
		ModSoundEvents.SPECTRE_BOOTS.toString();
		ModPotions.LESSER_MANA_POTION.toString();
		Stats.CUSTOM.get(ModStatistics.MANA_USED, StatFormatter.DEFAULT);
		ModScreenHandlerType.register();
		ModBlockEntityType.register();
		ModFeatures.register();
		ModProfessions.GOBLIN_TINKERER_POI.toString();
		ModProfessions.fillTradeData();
		ModParticles.BLUE_POOF.toString();
		ModCommands.registerRules();
		CommandRegistrationCallback.EVENT.register((dispatcher, context, selection) -> {
			ModCommands.registerCommands(dispatcher);
		});

		// Events
		ServerLifecycleEvents.SERVER_STOPPING.register(server -> InputHandler.clear());
		PlayerEvent.CHANGE_DIMENSION.register((player, oldLevel, newLevel) -> InputHandler.onChangeDimension(player));
		PlayerEvent.PLAYER_QUIT.register(InputHandler::onLogout);
		ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
			if (oldPlayer instanceof PlayerStorages) {
				// Copy inventories from the player entity that died to the one that respawned
				((PlayerStorages) newPlayer).setTerrariaInventory(((PlayerStorages) oldPlayer).getTerrariaInventory());
				((PlayerStorages) newPlayer).setPiggyBankInventory(((PlayerStorages) oldPlayer).getPiggyBankInventory());
				((PlayerStorages) newPlayer).setSafeInventory(((PlayerStorages) oldPlayer).getSafeInventory());
				for (int i = 0; i < 7; i++) {
					((PlayerStorages) newPlayer).setSlotVisibility(i, ((PlayerStorages) oldPlayer).getSlotVisibility(i));
				}
			}
			ModComponents.TEAMS.get(newPlayer).setTeamColour(ModComponents.TEAMS.get(oldPlayer).getTeamColour());
		});
		PlayerEvent.CHANGE_DIMENSION.register((player, oldLevel, newLevel) -> syncInventory(player));
		PlayerEvent.PLAYER_RESPAWN.register((player, bl) -> syncInventory(player));
		PlayerEvent.PLAYER_JOIN.register(this::syncInventory);
		PlayerEvent.PLAYER_JOIN.register(this::onPlayerJoin);

		// Compat Handlers
		for (CompatHandler handler : FabricLoader.getInstance().getEntrypoints("terramine:compat_handlers", CompatHandler.class)) {
			if (FabricLoader.getInstance().isModLoaded(handler.modId())) {
				Optional<ModContainer> modContainer = FabricLoader.getInstance().getModContainer(handler.modId());
				String modName = modContainer.map(c -> c.getMetadata().getName()).orElse(handler.modId());

				LOGGER.info("Running compat handler for " + modName);
				handler.run();
			}
		}

		LOGGER.info("Finished initialization");
	}

	// maybe move into inventory itself or something? works perfectly like this though, so I'll just leave it for now
	// probably not the best way of doing this, but it works for now, maybe look into improving later though
	private void syncInventory(ServerPlayer player) {
		TerrariaInventory terrariaInventory = ((PlayerStorages)player).getTerrariaInventory();
		FriendlyByteBuf buf = PacketByteBufs.create();
		FriendlyByteBuf buf2;
		buf.writeUUID(player.getUUID());
		for (int i = 0; i < terrariaInventory.getContainerSize(); i++) {
			buf.writeItem(terrariaInventory.getItem(i));
		}
		for (ServerPlayer otherPlayer : player.serverLevel().players()) {
			TerrariaInventory otherTerrariaInventory = ((PlayerStorages)otherPlayer).getTerrariaInventory();
			buf2 = PacketByteBufs.create();
			buf2.writeUUID(otherPlayer.getUUID());
			for (int i = 0; i < otherTerrariaInventory.getContainerSize(); i++) {
				buf2.writeItem(otherTerrariaInventory.getItem(i));
			}
			ServerPlayNetworking.send(player, ServerPacketHandler.SETUP_INVENTORY_PACKET_ID, buf2);
			ServerPlayNetworking.send(otherPlayer, ServerPacketHandler.SETUP_INVENTORY_PACKET_ID, buf);

			for (int i = 0; i < 7; i++) {
				((PlayerStorages) otherPlayer).setSlotVisibility(i, ((PlayerStorages) otherPlayer).getSlotVisibility(i));
			}
		}
	}

	private void onPlayerJoin(Player player) {
		// set extra slots manually
		if (CONFIG.general.setExtraAccessorySlots > 0) {
			ModComponents.ACCESSORY_SLOTS_ADDER.get(player).set(CONFIG.general.setExtraAccessorySlots);
			if (ModComponents.ACCESSORY_DEMON_HEART_CHECK.get(player).get() && CONFIG.general.setExtraAccessorySlots < 2) {
				ModComponents.ACCESSORY_SLOTS_ADDER.get(player).add(1);
			}
			ModComponents.ACCESSORY_SLOTS_ADDER.sync(player);
		} else {
			// check if hardcore and add an extra accessory slot if true, also checks the config to see if the slot should be removed or not
			if (player.level().getLevelData().isHardcore() && ModComponents.ACCESSORY_SLOTS_ADDER.get(player).get() < 2 && !ModComponents.ACCESSORY_HARDCORE_CHECK.get(player).get() && !CONFIG.general.disableHardcoreExtraAccessory) {
				ModComponents.ACCESSORY_HARDCORE_CHECK.get(player).set(true);
				ModComponents.ACCESSORY_HARDCORE_CHECK.sync(player);
				ModComponents.ACCESSORY_SLOTS_ADDER.get(player).add(1);
				ModComponents.ACCESSORY_SLOTS_ADDER.sync(player);
			} else if ((player.level().getLevelData().isHardcore() && ModComponents.ACCESSORY_HARDCORE_CHECK.get(player).get() && CONFIG.general.disableHardcoreExtraAccessory)) {
				ModComponents.ACCESSORY_HARDCORE_CHECK.get(player).set(false);
				ModComponents.ACCESSORY_HARDCORE_CHECK.sync(player);
				ModComponents.ACCESSORY_SLOTS_ADDER.get(player).remove(1);
				ModComponents.ACCESSORY_SLOTS_ADDER.sync(player);
			}
		}
	}

	@Override
	public void onTerraBlenderInitialized() {
		Regions.register(new BiomeAdder(id("terraria_biomes"), RegionType.OVERWORLD, 15));
		Regions.register(new BiomeAdderCrimsonForced(id("terraria_biomes_crimson_forced"), RegionType.OVERWORLD, 15));

		SurfaceRuleManager.addToDefaultSurfaceRulesAtStage(SurfaceRuleManager.RuleCategory.OVERWORLD, SurfaceRuleManager.RuleStage.AFTER_BEDROCK,
				1, BiomeSurfaceRules.makeRules());
		SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID,
				TBSurfaceRuleData.overworld());
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MOD_ID, path);
	}
}
