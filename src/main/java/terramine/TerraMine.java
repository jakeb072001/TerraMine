package terramine;

import dev.architectury.event.events.common.PlayerEvent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
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
import terramine.common.config.ConfigHelper;
import terramine.common.config.ModConfig;
import terramine.common.init.*;
import terramine.common.network.ServerPacketHandler;
import terramine.common.utility.InputHandler;
import terramine.common.world.biome.BiomeAdder;
import terramine.common.world.biome.BiomeSurfaceRules;
import terramine.extensions.PlayerStorages;

import java.util.Optional;

public class TerraMine implements ModInitializer, TerraBlenderApi {

	public static final String MOD_ID = "terramine";
	public static final Logger LOGGER = LoggerFactory.getLogger(TerraMine.class);
	public static final CreativeModeTab ITEM_GROUP_EQUIPMENT = FabricItemGroupBuilder.build(id("item_group_equipment"), () -> new ItemStack(ModItems.DEMONITE_SWORD));
	public static final CreativeModeTab ITEM_GROUP_ARMOR = FabricItemGroupBuilder.build(id("item_group_armor"), () -> new ItemStack(ModItems.DEMONITE_SWORD));
	public static final CreativeModeTab ITEM_GROUP_ACCESSORIES = FabricItemGroupBuilder.build(id("item_group_accessories"), () -> new ItemStack(ModItems.TERRASPARK_BOOTS));
	public static final CreativeModeTab ITEM_GROUP_BLOCKS = FabricItemGroupBuilder.build(id("item_group_blocks"), () -> new ItemStack(ModItems.RAW_DEMONITE_BLOCK));
	public static final CreativeModeTab ITEM_GROUP_STUFF = FabricItemGroupBuilder.build(id("item_group_stuff"), () -> new ItemStack(ModItems.RAW_DEMONITE));
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
		ModEntities.addToSpawn();
		ModSoundEvents.SPECTRE_BOOTS.toString();
		ModPotions.LESSER_MANA_POTION.toString();
		Stats.CUSTOM.get(ModStatistics.MANA_USED, StatFormatter.DEFAULT);
		ModScreenHandlerType.register();
		ModBlockEntityType.register();
		ModFeatures.register();
		ModBiomes.registerAll();
		ModProfessions.GOBLIN_TINKERER_POI.toString();
		ModProfessions.fillTradeData();
		ModParticles.registerServer();
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
				((PlayerStorages) newPlayer).setPiggyBankInventory(((PlayerStorages) oldPlayer).getPiggyBankInventory());
				((PlayerStorages) newPlayer).setSafeInventory(((PlayerStorages) oldPlayer).getSafeInventory());
			}
		});

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
	public void onTerraBlenderInitialized() {
		Regions.register(new BiomeAdder(id("terraria_biomes"), RegionType.OVERWORLD, 1));

		SurfaceRuleManager.addToDefaultSurfaceRulesAtStage(SurfaceRuleManager.RuleCategory.OVERWORLD, SurfaceRuleManager.RuleStage.AFTER_BEDROCK,
				1, BiomeSurfaceRules.makeRules());
		SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID,
				TBSurfaceRuleData.overworld());
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MOD_ID, path);
	}
}
