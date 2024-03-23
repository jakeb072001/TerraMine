package terramine.common.init;

import net.fabricmc.fabric.api.loot.v2.FabricLootTableBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import terramine.TerraMine;

import java.util.Arrays;
import java.util.List;

public class ModLootTables {

	// Mobs
	public static final ResourceLocation MIMIC = TerraMine.id("entities/mimic");
	public static final ResourceLocation DEMON_EYE = TerraMine.id("entities/demon_eye");
	public static final ResourceLocation EATER_OF_SOULS = TerraMine.id("entities/eater_of_souls");
	public static final ResourceLocation DEVOURER = TerraMine.id("entities/devourer");
	public static final ResourceLocation CRIMERA = TerraMine.id("entities/crimera");

	// Treasure Bags
	public static final ResourceLocation EYE_OF_CTHULHU = TerraMine.id("items/treasure_bag/eye_of_cthulhu");

	// Chests
	public static final ResourceLocation SURFACE_CHEST = TerraMine.id("chests/surface_chest");
	public static final ResourceLocation OCEAN_CHEST = TerraMine.id("chests/ocean_chest");
	public static final ResourceLocation CAVE_CHEST = TerraMine.id("chests/cave_chest");
	public static final ResourceLocation DEEP_CAVE_CHEST = TerraMine.id("chests/deep_cave_chest");
	public static final ResourceLocation FROZEN_CAVE_CHEST = TerraMine.id("chests/frozen_cave_chest");
	public static final ResourceLocation IVY_CAVE_CHEST = TerraMine.id("chests/ivy_cave_chest");
	public static final ResourceLocation SANDSTONE_CAVE_CHEST = TerraMine.id("chests/sandstone_cave_chest");
	public static final ResourceLocation SHADOW_CHEST = TerraMine.id("chests/shadow_chest");

	public static final List<ResourceLocation> INJECT_TABLE_IDS = Arrays.asList(
			new ResourceLocation("chests/village/village_armorer"),
			new ResourceLocation("chests/village/village_butcher"),
			new ResourceLocation("chests/village/village_tannery"),
			new ResourceLocation("chests/village/village_temple"),
			new ResourceLocation("chests/village/village_toolsmith"),
			new ResourceLocation("chests/village/village_weaponsmith"),
			new ResourceLocation("chests/village/village_desert_house"),
			new ResourceLocation("chests/village/village_plains_house"),
			new ResourceLocation("chests/village/village_savanna_house"),
			new ResourceLocation("chests/village/village_snowy_house"),
			new ResourceLocation("chests/village/village_taiga_house"),
			new ResourceLocation("chests/abandoned_mineshaft"),
			new ResourceLocation("chests/bastion_hoglin_stable"),
			new ResourceLocation("chests/bastion_treasure"),
			new ResourceLocation("chests/buried_treasure"),
			new ResourceLocation("chests/desert_pyramid"),
			new ResourceLocation("chests/end_city_treasure"),
			new ResourceLocation("chests/jungle_temple"),
			new ResourceLocation("chests/nether_bridge"),
			new ResourceLocation("chests/pillager_outpost"),
			new ResourceLocation("chests/ruined_portal"),
			new ResourceLocation("chests/shipwreck_treasure"),
			new ResourceLocation("chests/spawn_bonus_chest"),
			new ResourceLocation("chests/stronghold_corridor"),
			new ResourceLocation("chests/underwater_ruin_big"),
			new ResourceLocation("chests/woodland_mansion"),
			new ResourceLocation("entities/bat"),
			new ResourceLocation("entities/blaze"),
			new ResourceLocation("entities/ghast"),
			new ResourceLocation("entities/zombie")
	);

	public static void onLootTableLoad(ResourceLocation id, FabricLootTableBuilder supplier) {
		if (INJECT_TABLE_IDS.contains(id)) {
			supplier.pool(LootPool.lootPool().add(getInjectEntry(id.getPath())).build());
		}
	}

	private static LootPoolEntryContainer.Builder<?> getInjectEntry(String name) {
		ResourceLocation table = TerraMine.id("inject/" + name);
		return LootTableReference.lootTableReference(table).setWeight(1);
	}

	private ModLootTables() {
	}
}
