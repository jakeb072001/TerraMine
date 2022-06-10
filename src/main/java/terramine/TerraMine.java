package terramine;

import dev.architectury.event.events.common.PlayerEvent;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import terrablender.api.RegionType;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;
import terrablender.worldgen.TBSurfaceRuleData;
import terramine.common.compat.CompatHandler;
import terramine.common.components.SyncedBooleanComponent;
import terramine.common.config.ModConfig;
import terramine.common.init.*;
import terramine.common.network.UpdateInputNetworkHandler;
import terramine.common.network.packet.BoneMealPacket;
import terramine.common.network.packet.UpdateInputPacket;
import terramine.common.utility.InputHandler;
import terramine.common.world.biome.BiomeAdder;
import terramine.common.world.biome.BiomeSurfaceRules;

import java.util.Optional;

public class TerraMine implements ModInitializer, TerraBlenderApi {

	public static final String MOD_ID = "terramine";
	public static final Logger LOGGER = LoggerFactory.getLogger(TerraMine.class);
	public static final SimpleParticleType BLUE_POOF = FabricParticleTypes.simple();
	public static final SimpleParticleType GREEN_SPARK = FabricParticleTypes.simple();
	public static final CreativeModeTab ITEM_GROUP = FabricItemGroupBuilder.build(id("item_group"), () -> new ItemStack(ModItems.TERRASPARK_BOOTS));
	public static final ResourceLocation WALL_JUMP_PACKET_ID = new ResourceLocation(MOD_ID, "walljump");
	public static final ResourceLocation FALL_DISTANCE_PACKET_ID = new ResourceLocation(MOD_ID, "falldistance");
	public static ModConfig CONFIG;
	//private static final Map<String, Runnable> COMPAT_HANDLERS = Map.of(
	//		"origins", new OriginsCompat(),
	//		"haema", new HaemaCompat());

	@Override
	@SuppressWarnings("ResultOfMethodCallIgnored")
	public void onInitialize() {
		// Config
		CONFIG = getConfigAndInvalidateOldVersions();

		// Packets
		ServerPlayNetworking.registerGlobalReceiver(BoneMealPacket.ID, BoneMealPacket::receive);
		ServerPlayNetworking.registerGlobalReceiver(UpdateInputNetworkHandler.PACKET_ID, (server, player, handler, buf, responseSender) -> {
			UpdateInputPacket.onMessage(UpdateInputPacket.read(buf), server, player);
		});

		ServerPlayNetworking.registerGlobalReceiver(FALL_DISTANCE_PACKET_ID, (server, player, handler, buf, responseSender) -> {
			float fallDistance = buf.readFloat();
			server.execute(() -> {
				player.fallDistance = fallDistance;
			});
		});

		// Loot table setup
		ModLootConditions.register();
		LootTableEvents.MODIFY.register((resourceManager, manager, id, supplier, setter) -> ModLootTables.onLootTableLoad(id, supplier));

		// Force loading init classes
		// Entities is loaded by items, loot tables can load lazily (no registration)
		ModItems.TERRASPARK_BOOTS.toString();
		ModEntities.addToSpawn();
		ModSoundEvents.SPECTRE_BOOTS.toString();
		ModPotions.LESSER_MANA_POTION.toString();
		Stats.CUSTOM.get(ModStatistics.MANA_USED, StatFormatter.DEFAULT);
		ModScreenHandlerType.register();
		ModBlockEntityType.register();
		ModFeatures.register();
		ModBiomes.registerAll();
		ModProfessions.fillTradeData();
		ModCommands.registerRules();
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			ModCommands.registerCommands(dispatcher);
		});

		Registry.register(Registry.PARTICLE_TYPE, TerraMine.id("blue_poof"), BLUE_POOF);
		Registry.register(Registry.PARTICLE_TYPE, TerraMine.id("green_spark"), GREEN_SPARK);

		ServerLifecycleEvents.SERVER_STOPPING.register(server -> InputHandler.clear());
		PlayerEvent.CHANGE_DIMENSION.register((player, oldLevel, newLevel) -> InputHandler.onChangeDimension(player));
		PlayerEvent.PLAYER_QUIT.register(InputHandler::onLogout);
		ServerWorldEvents.LOAD.register((server, level) -> SyncedBooleanComponent.setServer(server));

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

	@Override
	public void onTerraBlenderInitialized()
	{
		Regions.register(new BiomeAdder(id("terraria_biomes"), RegionType.OVERWORLD, 1));

		SurfaceRuleManager.addToDefaultSurfaceRulesAtStage(SurfaceRuleManager.RuleCategory.OVERWORLD, SurfaceRuleManager.RuleStage.AFTER_BEDROCK,
				1, BiomeSurfaceRules.makeRules());
		SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID,
				TBSurfaceRuleData.overworld());
	}

	/**
	 * Gets the config and if the config version is incompatible, reset to the default config.
	 * Note: this does not reset files for removed categories.
	 */
	private ModConfig getConfigAndInvalidateOldVersions() {
		ConfigHolder<ModConfig> configHolder = AutoConfig.register(ModConfig.class,
				PartitioningSerializer.wrap(Toml4jConfigSerializer::new));
		int currentVersion = configHolder.getConfig().general.configVersion;
		int requiredVersion = ModConfig.General.CONFIG_VERSION;
		if (currentVersion != requiredVersion) {
			LOGGER.warn("Resetting incompatible config with version {} to version {}", currentVersion, requiredVersion);
			configHolder.resetToDefault();
			configHolder.save();
		}
		return configHolder.getConfig();
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MOD_ID, path);
	}
}
