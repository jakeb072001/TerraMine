package terracraft.mixin.world;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terracraft.common.init.ModBiomes;
import terracraft.common.utility.CorruptionHelper;

@Mixin(BiomeManager.class)
public class BiomeManagerMixin {

    @Inject(at = @At("RETURN"), method = "getBiome", cancellable = true)
    public void spreadCorruptionBiome(BlockPos pos, CallbackInfoReturnable<Holder<Biome>> info) {
        Minecraft mc = Minecraft.getInstance();
        if (mc != null && mc.level != null) {
            for (int i = 45; i <= 100; i++) { // checks for blocks between y 45 and 100
                Block block = mc.level.getBlockState(pos.atY(i)).getBlock();
                if (block instanceof CorruptionHelper && pos.getY() > i) {
                    Holder<Biome> biome;
                    if (info.getReturnValue().is(net.minecraft.world.level.biome.Biomes.DESERT) || info.getReturnValue().is(ModBiomes.CORRUPTION_DESERT)) {
                        biome = BuiltinRegistries.BIOME.getHolderOrThrow(ModBiomes.CORRUPTION_DESERT);
                        info.setReturnValue(biome);
                    } else {
                        biome = BuiltinRegistries.BIOME.getHolderOrThrow(ModBiomes.CORRUPTION);
                        info.setReturnValue(biome);
                    }
                }
            }
        }
    }
}
