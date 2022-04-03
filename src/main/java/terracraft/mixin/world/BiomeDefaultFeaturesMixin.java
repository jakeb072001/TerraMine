package terracraft.mixin.world;

import net.minecraft.client.Minecraft;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terracraft.common.init.ModEntities;

@Mixin(BiomeDefaultFeatures.class)
public class BiomeDefaultFeaturesMixin {

    private static int checkNewMoon() {
        int i = 80;
        Minecraft mc = Minecraft.getInstance();
        if (mc != null && mc.level != null) {
            if (Minecraft.getInstance().level.getMoonPhase() == 4) {
                i = 100;
            }
        }
        return i;
    }
    @Inject(method = "monsters", at = @At("HEAD"), cancellable = true)
    private static void spawnNaturalMonsters(MobSpawnSettings.Builder builder, int i, int j, int k, boolean bl, CallbackInfo info) {
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntities.DEMON_EYE, checkNewMoon(), 2, 6));
    }
}
