package terramine.mixin.world;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terramine.common.init.ModBiomes;
import terramine.common.utility.CorruptionHelper;

@Mixin(BiomeManager.class) // https://github.com/Glitchfiend/TerraBlender/blob/TB-1.19.x-2.x.x/Common/src/main/java/terrablender/mixin/MixinMultiNoiseBiomeSource.java maybe?
public class BiomeManagerMixin { // also for getting access to level, maybe use an interface or something, can get level using a mixin to MinecraftServer or something, similar to how SyncBooleanComponent gets LevelData
    @Unique
    private Level level;

    //todo: currently can cause a crash randomly, has to do with block tint for grass and foliage, ClientLevelMixin may temporarily fix this
    //todo: causes game to run like poop if ran on server, mixin to something else to more permanently change biome
    @Inject(at = @At("RETURN"), method = "getBiome", cancellable = true)
    public void spreadCorruptionBiome(BlockPos pos, CallbackInfoReturnable<Holder<Biome>> info) {
        if (level != null && pos != null) {
            for (int i = 45; i <= 100; i++) { // checks for blocks between y 45 and 100
                Block block = level.getBlockState(pos.atY(i)).getBlock();

                if (block instanceof CorruptionHelper && pos.getY() > i) { // if block is a corruption block and position is above i (y range)
                    Holder<Biome> biome = BuiltinRegistries.BIOME.getOrCreateHolder(ModBiomes.CORRUPTION).get().orThrow(); // default biome
                    if (info.getReturnValue().is(Biomes.DESERT) || info.getReturnValue().is(ModBiomes.CORRUPTION_DESERT)) {
                        biome = BuiltinRegistries.BIOME.getOrCreateHolder(ModBiomes.CORRUPTION_DESERT).get().orThrow(); // desert biome
                    }
                    info.setReturnValue(biome);
                    break;
                }
            }
        }
    }

    @Environment(EnvType.CLIENT)
    @Inject(at = @At("HEAD"), method = "getBiome")
    public void test(BlockPos blockPos, CallbackInfoReturnable<Holder<Biome>> cir) {
        level = Minecraft.getInstance().level;
    }
}
