package terramine.common.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.io.FileUtils;
import terramine.TerraMine;

import java.io.IOException;
import java.nio.file.Path;

public class ConfigHelper {

    /**
     * Gets the config and if the config version is incompatible, reset to the default config.
     * Note: this does not reset files for removed categories.
     */
    public static ModConfig getConfigAndInvalidateOldVersions() {
        ConfigHolder<ModConfig> configHolder = tryGetConfigOrReset();

        int currentVersion = configHolder.getConfig().general.configVersion;
        int requiredVersion = TerraMine.CONFIG_VERSION;
        if (currentVersion != requiredVersion) {
            TerraMine.LOGGER.warn("Resetting incompatible config with version {} to version {}", currentVersion, requiredVersion);
            configHolder.resetToDefault();
            configHolder.save();
        }
        return configHolder.getConfig();
    }

    private static ConfigHolder<ModConfig> tryGetConfigOrReset() {
        try {
            return getConfigHolder();
        } catch (RuntimeException configEx) {
            TerraMine.LOGGER.error("Failed to load config", configEx);

            Path configDir = FabricLoader.getInstance().getConfigDir().resolve("terramine");
            try {
                FileUtils.deleteDirectory(configDir.toFile());
            } catch (IOException deletedEx) {
                throw new RuntimeException("Failed to delete invalid config", deletedEx);
            }

            TerraMine.LOGGER.warn("All configs were reset");
            return getConfigHolder();
        }
    }

    private static ConfigHolder<ModConfig> getConfigHolder() {
        return AutoConfig.register(ModConfig.class,
                PartitioningSerializer.wrap(Toml4jConfigSerializer::new));
    }
}
