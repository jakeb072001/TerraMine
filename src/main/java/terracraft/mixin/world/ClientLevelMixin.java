package terracraft.mixin.world;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terracraft.common.init.ModBiomes;
import terracraft.common.utility.CorruptionHelper;
import terracraft.common.world.biome.CorruptionBiome;
import terracraft.common.world.biome.CorruptionDesertBiome;

import java.util.function.Supplier;

@Mixin(ClientLevel.class)
public class ClientLevelMixin {
    Minecraft mc = Minecraft.getInstance();

    // todo: make work better, i want colour to gradient still but without potential pallet crash
    @Inject(at = @At("RETURN"), method = "calculateBlockTint", cancellable = true)
    public void corruptionCrashFix(BlockPos blockPos, ColorResolver colorResolver, CallbackInfoReturnable<Integer> info) {
        if (mc.level != null) {
            if (mc.level.getBiome(blockPos).value().equals(CorruptionBiome.CORRUPTION) || mc.level.getBiome(blockPos).value().equals(CorruptionDesertBiome.CORRUPTION_DESERT)) {
                info.setReturnValue(0x9966ff);
            }
        }
    }
}
