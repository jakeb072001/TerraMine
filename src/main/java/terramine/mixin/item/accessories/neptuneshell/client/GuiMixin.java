package terramine.mixin.item.accessories.neptuneshell.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import terramine.common.init.ModItems;
import terramine.common.misc.AccessoriesHelper;

@Mixin(Gui.class)
public abstract class GuiMixin {

	@Shadow
	protected abstract Player getCameraPlayer();

	@ModifyExpressionValue(method = "renderPlayerHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isEyeInFluid(Lnet/minecraft/tags/TagKey;)Z"))
	private boolean doNotShowAir(boolean inWater) {
		Player player = this.getCameraPlayer();
		return inWater && !(AccessoriesHelper.isEquipped(ModItems.NEPTUNE_SHELL, player) || AccessoriesHelper.isEquipped(ModItems.MOON_SHELL, player) || AccessoriesHelper.isEquipped(ModItems.CELESTIAL_SHELL, player));
	}
}
