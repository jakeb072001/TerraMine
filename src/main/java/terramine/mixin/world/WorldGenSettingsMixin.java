package terramine.mixin.world;

import net.minecraft.core.RegistryAccess;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldGenSettings.class)
public class WorldGenSettingsMixin {

    @Unique
    final private static RandomSource rand = RandomSource.create();

    @Inject(at = @At("HEAD"), method = "replaceOverworldGenerator")
    private static void setTerramineGlobalValues(RegistryAccess registryAccess, WorldGenSettings worldGenSettings, ChunkGenerator chunkGenerator, CallbackInfoReturnable<WorldGenSettings> cir) {
        rand.setSeed(worldGenSettings.seed());
        //Level level = SyncedBooleanComponent.getServer().getLevel(Level.OVERWORLD);
        //if (level != null) {
        //    ModComponents.HARDMODE.get(level).set(rand.nextBoolean());
        //}
    }
}