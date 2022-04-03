package terracraft.mixin.item.heliumflamingo.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terracraft.TerraCraft;
import terracraft.common.init.ModComponents;
import terracraft.common.init.ModItems;
import terracraft.common.item.curio.old.HeliumFlamingoItem;
import terracraft.common.trinkets.TrinketsHelper;

@Mixin(Gui.class)
public abstract class GuiMixin {

	@Unique private final ResourceLocation FLAMINGO_ICONS_TEXTURE = TerraCraft.id("textures/gui/icons.png");

	@Shadow @Final private Minecraft minecraft;
	@Shadow private int screenHeight;
	@Shadow private int screenWidth;

	@Shadow protected abstract int getVisibleVehicleHeartRows(int i);

	@Shadow protected abstract LivingEntity getPlayerVehicleWithHealth();

	@Shadow protected abstract int getVehicleMaxHearts(LivingEntity livingEntity);

	@Shadow protected abstract Player getCameraPlayer();

	@Inject(method = "renderPlayerHealth", require = 0, at = @At(value = "TAIL"))
	private void renderFlamingoAir(PoseStack matrices, CallbackInfo ci) {
		Player player = this.getCameraPlayer();

		if (/*TODO: ModConfig.server.isCosmetic(HeliumFlamingoItem.this) ||*/ player == null || !TrinketsHelper.isEquipped(ModItems.HELIUM_FLAMINGO, player)) {
			return;
		}

		ModComponents.SWIM_ABILITIES.maybeGet(player).ifPresent(swimAbilities -> {
			int left = this.screenWidth / 2 + 91;
			int top = this.screenHeight + getStatusBarHeightOffset(player);

			int swimTime = Math.abs(swimAbilities.getSwimTime());
			if (swimTime == 0) {
				return;
			}
			int maxProgressTime = swimAbilities.getSwimTime() > 0 ? HeliumFlamingoItem.MAX_FLIGHT_TIME : HeliumFlamingoItem.RECHARGE_TIME;

			float progress = 1 - swimTime / (float) maxProgressTime;

			matrices.pushPose();
			RenderSystem.setShaderTexture(0, FLAMINGO_ICONS_TEXTURE);
			int full = Mth.ceil((progress - 2D / maxProgressTime) * 10);
			int partial = Mth.ceil(progress * 10) - full;

			for (int i = 0; i < full + partial; ++i) {
				GuiComponent.blit(matrices, left - i * 8 - 9, top, -90, (i < full ? 0 : 9), 0, 9, 9, 16, 32);
			}
			matrices.popPose();
		});
	}

	/**
	 * Calculate offset for our status bar height, taking rendering of other status bars into account
	 * This might take
	 */
	@Unique
	private int getStatusBarHeightOffset(Player player) {
		int offset = -49; // Base offset

		LivingEntity livingEntity = this.getPlayerVehicleWithHealth();
		int maxHearts = this.getVehicleMaxHearts(livingEntity);

		// Offset if hunger is rendered
		if (maxHearts == 0) {
			offset -= 10;
		}

		// Ofset if mount health is rendered
		offset -= (this.getVisibleVehicleHeartRows(maxHearts) - 1) * 10;

		// Offset if air is rendered
		int maxAir = player.getMaxAirSupply();
		int playerAir = Math.min(player.getAirSupply(), maxAir);
		if (player.isEyeInFluid(FluidTags.WATER) || playerAir < maxAir) {
			offset -= 10;
		}

		return offset;
	}
}
