package terramine.mixin.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.TerraMine;
import terramine.client.render.AccessoryRenderRegistry;
import terramine.client.render.accessory.renderer.GloveCurioRenderer;
import terramine.common.item.accessories.AccessoryTerrariaItem;
import terramine.extensions.PlayerStorages;

import java.util.ArrayList;
import java.util.List;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin {

	@Inject(method = "renderLeftHand", at = @At("TAIL"))
	private void renderLeftGlove(PoseStack matrixStack, MultiBufferSource buffer, int light, AbstractClientPlayer player, CallbackInfo callbackInfo) {
		renderArm(matrixStack, buffer, light, player, HumanoidArm.LEFT);
	}

	@Inject(method = "renderRightHand", at = @At("TAIL"))
	private void renderRightGlove(PoseStack matrixStack, MultiBufferSource buffer, int light, AbstractClientPlayer player, CallbackInfo callbackInfo) {
		renderArm(matrixStack, buffer, light, player, HumanoidArm.RIGHT);
	}

	@Unique
	private static void renderArm(PoseStack matrixStack, MultiBufferSource buffer, int light, AbstractClientPlayer player, HumanoidArm handSide) {
		if (!TerraMine.CONFIG.general.showFirstPersonGloves) {
			return;
		}

		List<ItemStack> allEquippedGloves = new ArrayList<>();
		for (int i = 0; i < 7; i++) {
			allEquippedGloves.add(i, ((PlayerStorages)player).getTerrariaInventory().getItem(i));
		}

		for (ItemStack stack : allEquippedGloves) {
			if (stack.getItem() instanceof AccessoryTerrariaItem accessory && accessory.isGlove()) {
				AccessoryRenderRegistry.getRenderer(stack.getItem()).ifPresent(renderer -> {
					if (renderer instanceof GloveCurioRenderer gloveRenderer) {
						gloveRenderer.renderFirstPersonArm(matrixStack, buffer, light, player, handSide, stack.hasFoil());
					}
				});
			}
		}
	}
}
