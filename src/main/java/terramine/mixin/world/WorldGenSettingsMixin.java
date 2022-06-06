package terramine.mixin.world;

import net.minecraft.core.RegistryAccess;
import net.minecraft.server.dedicated.DedicatedServerProperties;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terramine.TerraMine;
import terramine.common.components.SyncedBooleanComponent;
import terramine.common.init.ModComponents;

import java.util.OptionalLong;
import java.util.Random;

@Mixin(WorldGenSettings.class)
public class WorldGenSettingsMixin {

    @Shadow
    public static OptionalLong parseSeed(String string) {
        string = string.trim();
        if (StringUtils.isEmpty(string)) {
            return OptionalLong.empty();
        } else {
            try {
                return OptionalLong.of(Long.parseLong(string));
            } catch (NumberFormatException var2) {
                return OptionalLong.of(string.hashCode());
            }
        }
    }

    @Unique
    final private static Random rand = new Random();

    @Inject(at = @At("HEAD"), method = "create")
    private static void setTerramineGlobalValues(RegistryAccess registryAccess, DedicatedServerProperties.WorldGenProperties worldGenProperties, CallbackInfoReturnable<WorldGenSettings> cir) {
        rand.setSeed(parseSeed(worldGenProperties.levelSeed()).orElse(rand.nextLong()));
        SyncedBooleanComponent.instance(ModComponents.HARDMODE).set(rand.nextBoolean());
    }
}