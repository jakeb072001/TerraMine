package terramine.mixin.client.render;

import com.mojang.blaze3d.shaders.FogShape;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.FogParameters;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terramine.common.init.ModBlocks;

// todo: need star particles to make fox look like night sky
@Mixin(FogRenderer.class)
public abstract class FogRendererMixin {
    @Unique
    private static float fogIntensity = 0.0f;

    @Inject(method = "computeFogColor", at = @At("HEAD"))
    private static void modifyFogDensity(Camera camera, float f, ClientLevel clientLevel, int i, float g, CallbackInfoReturnable<Vector4f> cir) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null && isNearShimmer(player, 8)) {
            fogIntensity = Math.min(fogIntensity + 0.003f, 1.0f);
        } else {
            fogIntensity = Math.max(fogIntensity - 0.005f, 0.0f);
        }
    }

    @Unique
    private static boolean isNearShimmer(Player player, int range) {
        BlockPos playerPos = player.blockPosition();
        for (BlockPos pos : BlockPos.betweenClosed(playerPos.offset(-range, -range, -range), playerPos.offset(range, range, range))) {
            if (player.level().getBlockState(pos).getBlock() == ModBlocks.SHIMMER_BLOCK) {
                return true;
            }
        }
        return false;
    }

    @Inject(method = "setupFog", at = @At("HEAD"), cancellable = true)
    private static void modifyFogDensity(Camera camera, FogRenderer.FogMode fogMode, Vector4f vector4f, float f, boolean bl, float g, CallbackInfoReturnable<FogParameters> cir) {
        if (fogIntensity > 0.0f) {
            FogParameters fogParameters = new FogParameters(
                    0,
                    15,
                    FogShape.SPHERE,
                    -1,
                    -1,
                    -1,
                    1 * (fogIntensity * 0.4f)
            );
            cir.setReturnValue(fogParameters);
        }
    }
}
