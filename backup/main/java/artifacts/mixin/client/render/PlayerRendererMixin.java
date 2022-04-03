package terracraft.mixin.client.render;

import terracraft.Artifacts;
import terracraft.client.render.trinket.renderer.GloveCurioRenderer;
import terracraft.common.trinkets.TrinketsHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
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
		if (!Artifacts.CONFIG.general.showFirstPersonGloves) {
			return;
		}

		String groupId = handSide == player.getMainArm() ? "hand" : "offhand";
		List<ItemStack> allEquippedGloves = TrinketsHelper.getAllEquippedForSlot(player, groupId, "glove", true);

		for (ItemStack stack : allEquippedGloves) {
			TrinketRendererRegistry.getRenderer(stack.getItem()).ifPresent(renderer -> {
				if (renderer instanceof GloveCurioRenderer gloveRenderer) {
					gloveRenderer.renderFirstPersonArm(matrixStack, buffer, light, player, handSide, stack.hasFoil());
				}
			});
		}
	}
}
