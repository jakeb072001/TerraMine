package terracraft.common.config;

import terracraft.Artifacts;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(name = Artifacts.MOD_ID)
@Config.Gui.Background("minecraft:textures/block/mossy_cobblestone.png")
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
		public static final int CONFIG_VERSION = 1;

		@SuppressWarnings("unused")
		@ConfigEntry.Gui.Excluded
		public int configVersion = CONFIG_VERSION;
		@ConfigEntry.Gui.Tooltip(count = 2)
		public int everlastingFoodCooldown = 300;
		@ConfigEntry.Gui.Tooltip(count = 2)
		public boolean playExtraHurtSounds = true;
		@ConfigEntry.Gui.Tooltip(count = 2)
		public boolean showFirstPersonGloves = true;
		@ConfigEntry.Gui.Tooltip(count = 2)
		public boolean showTooltips = true;
		@ConfigEntry.Gui.Tooltip(count = 1)
		public boolean stopwatchMPH = false;
		@ConfigEntry.Gui.Tooltip(count = 3)
		public boolean disableCorruptionSpread = false;

		private General() {
		}
	}

	@Config(name = "worldgen")
	public static final class WorldGen implements ConfigData {
		@ConfigEntry.Gui.Tooltip(count = 6)
		public float artifactRarity = 1;
		@ConfigEntry.Gui.Tooltip
		public boolean corruptionEnabled = true;
		@ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
		public CaveChest caveChest = new CaveChest();

		private WorldGen() {
		}

		public static final class CaveChest {
			@ConfigEntry.Gui.RequiresRestart
			@ConfigEntry.Gui.Tooltip(count = 2)
			@ConfigEntry.BoundedDiscrete(max = 10_000, min = 1)
			public int caveChestRarity = 5;

			@ConfigEntry.Gui.Tooltip(count = 2)
			@ConfigEntry.BoundedDiscrete(max = 100)
			public int mimicChance = 15;

			// TODO: this should probably be higher so we don't break any bedrock
			@ConfigEntry.Gui.Tooltip
			@ConfigEntry.BoundedDiscrete(max = 256)
			public int minY = 0;

			@ConfigEntry.Gui.Tooltip
			@ConfigEntry.BoundedDiscrete(max = 256)
			public int maxY = 60;

			private CaveChest() {
			}
		}
	}
}
