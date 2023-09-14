package terramine.mixin.item.accessories.neptuneshell.client;

import com.mojang.authlib.GameProfile;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terramine.common.init.ModItems;
import terramine.common.misc.AccessoriesHelper;

@Environment(EnvType.CLIENT)
@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer {

    public LocalPlayerMixin(ClientLevel clientLevel, GameProfile gameProfile) {
        super(clientLevel, gameProfile);
    }

    @Inject(at = @At("HEAD"), method = "getWaterVision", cancellable = true)
    private void getUnderwaterVisibility(CallbackInfoReturnable<Float> info) {
        if(AccessoriesHelper.isEquipped(ModItems.NEPTUNE_SHELL, this) || AccessoriesHelper.isEquipped(ModItems.MOON_SHELL, this)
                || AccessoriesHelper.isEquipped(ModItems.CELESTIAL_SHELL, this)) {
            info.setReturnValue(1.0F);
        }
    }
}
