package terramine.mixin.world;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ColorResolver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terramine.common.world.biome.CorruptionBiome;
import terramine.common.world.biome.CorruptionDesertBiome;
import terramine.common.world.biome.CrimsonBiome;
import terramine.common.world.biome.CrimsonDesertBiome;

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
            if (mc.level.getBiome(blockPos).value().equals(CrimsonBiome.CRIMSON) || mc.level.getBiome(blockPos).value().equals(CrimsonDesertBiome.CRIMSON_DESERT)) {
                info.setReturnValue(0xdc143c);
            }
        }
    }
}
