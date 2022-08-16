package terramine.common.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import terramine.TerraMine;

@Config(name = TerraMine.MOD_ID)
@Config.Gui.Background("terramine:textures/block/corruption/corrupt_stone.png")
public final class ModConfig extends PartitioningSerializer.GlobalData {
	@ConfigEntry.Category("general")
	@ConfigEntry.Gui.TransitiveObject
	public General general = new General();

	@ConfigEntry.Category("worldgen")
	@ConfigEntry.Gui.TransitiveObject
	public WorldGen worldgen = new WorldGen();

	@Config(name = "general")
	public static final class General implements ConfigData {
		@SuppressWarnings("unused")
		@ConfigEntry.Gui.Excluded
		public int configVersion = TerraMine.CONFIG_VERSION;
		@ConfigEntry.Gui.Tooltip(count = 2)
		public boolean showFirstPersonGloves = true;
		@ConfigEntry.Gui.Tooltip(count = 2)
		public boolean showTooltips = true;
		@ConfigEntry.Gui.Tooltip
		public boolean stopwatchMPH = false;
		@ConfigEntry.Gui.Tooltip(count = 3)
		public boolean disableEvilSpread = false;
		@ConfigEntry.Gui.Tooltip(count = 2)
		@ConfigEntry.BoundedDiscrete(max = 500, min = 0)
		public int evilSpreadRarity = 4;
		@ConfigEntry.Gui.Tooltip
		public boolean disableFallingStars = false;
		@ConfigEntry.Gui.Tooltip(count = 2)
		@ConfigEntry.BoundedDiscrete(max = 500, min = 0)
		public int fallingStarRarity = 20;
	}

	@Config(name = "worldgen")
	public static final class WorldGen implements ConfigData {
		@ConfigEntry.Gui.Tooltip(count = 6)
		public float accessoryRarity = 1;
		@ConfigEntry.Gui.Tooltip
		public boolean evilBiomeEnabled = true;
		@ConfigEntry.Gui.Tooltip
		public boolean forceCorruption = false;
		@ConfigEntry.Gui.Tooltip
		public boolean forceCrimson = false;
		@ConfigEntry.Gui.CollapsibleObject()
		public CaveChest caveChest = new CaveChest();
		@ConfigEntry.Gui.CollapsibleObject()
		public Structures structures = new Structures();

		public static final class CaveChest {
			@ConfigEntry.Gui.Tooltip
			public boolean disableChests = false;

			@ConfigEntry.Gui.Tooltip(count = 2)
			@ConfigEntry.BoundedDiscrete(max = 10, min = 1)
			public int chestRarity = 3;

			@ConfigEntry.Gui.Tooltip(count = 2)
			@ConfigEntry.BoundedDiscrete(max = 100)
			public int mimicChance = 15; // not in use right now

			@ConfigEntry.Gui.Tooltip
			@ConfigEntry.BoundedDiscrete(max = 319, min = -64)
			public int minSurfaceY = 25;

			@ConfigEntry.Gui.Tooltip
			@ConfigEntry.BoundedDiscrete(max = 319, min = -64)
			public int maxSurfaceY = 55;

			@ConfigEntry.Gui.Tooltip
			@ConfigEntry.BoundedDiscrete(max = 319, min = -64)
			public int minCaveY = -55;

			@ConfigEntry.Gui.Tooltip
			@ConfigEntry.BoundedDiscrete(max = 319, min = -64)
			public int maxCaveY = 25;

			@ConfigEntry.Gui.Tooltip
			@ConfigEntry.BoundedDiscrete(max = 319, min = -64)
			public int deepCaveY = -30;
		}

		public static final class Structures {
			@ConfigEntry.Gui.Tooltip
			public boolean disableDungeon = false;
		}
	}
}
