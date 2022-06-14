package terramine.common.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import terramine.TerraMine;

@Config(name = TerraMine.MOD_ID)
@Config.Gui.Background("terramine:textures/block/corrupt_stone.png")
public final class ModConfig extends PartitioningSerializer.GlobalData {
	@ConfigEntry.Category("general")
	@ConfigEntry.Gui.TransitiveObject
	public General general = new General();

	@ConfigEntry.Category("worldgen")
	@ConfigEntry.Gui.TransitiveObject
	public WorldGen worldgen = new WorldGen();

	private ModConfig() {
	}

	@Config(name = "general")
	public static final class General implements ConfigData {
		// The current/required version of the config file format
		// Increase this if the config format has changed in an incompatible way
		// When the game is loaded with an older config version, it will reset all values to their defaults
		@ConfigEntry.Gui.Excluded
		public static final int CONFIG_VERSION = 2;

		@SuppressWarnings("unused")
		@ConfigEntry.Gui.Excluded
		public int configVersion = CONFIG_VERSION;
		@ConfigEntry.Gui.Tooltip(count = 2)
		public boolean showFirstPersonGloves = true;
		@ConfigEntry.Gui.Tooltip(count = 2)
		public boolean showTooltips = true;
		@ConfigEntry.Gui.Tooltip
		public boolean stopwatchMPH = false;
		@ConfigEntry.Gui.Tooltip(count = 3)
		public boolean disableCorruptionSpread = false;
		@ConfigEntry.Gui.Tooltip(count = 2)
		@ConfigEntry.BoundedDiscrete(max = 500, min = 0)
		public int corruptionSpreadRarity = 4;

		private General() {
		}
	}

	@Config(name = "worldgen")
	public static final class WorldGen implements ConfigData {
		@ConfigEntry.Gui.Tooltip(count = 6)
		public float accessoryRarity = 1;
		@ConfigEntry.Gui.Tooltip
		public boolean corruptionEnabled = true;
		@ConfigEntry.Gui.CollapsibleObject(startExpanded = false)
		public CaveChest caveChest = new CaveChest();
		@ConfigEntry.Gui.CollapsibleObject(startExpanded = false)
		public Structures structures = new Structures();

		private WorldGen() {
		}

		public static final class CaveChest {
			@ConfigEntry.Gui.RequiresRestart
			@ConfigEntry.Gui.Tooltip(count = 2)
			@ConfigEntry.BoundedDiscrete(max = 10, min = 1)
			public int chestRarity = 3;

			@ConfigEntry.Gui.Tooltip(count = 2)
			@ConfigEntry.BoundedDiscrete(max = 100)
			public int mimicChance = 15;

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

			private CaveChest() {
			}
		}

		public static final class Structures {
			@ConfigEntry.Gui.RequiresRestart
			@ConfigEntry.Gui.Tooltip
			@ConfigEntry.BoundedDiscrete(max = 280, min = 80)
			public int floatingIslandHeight = 180; // currently unused
		}
	}
}
