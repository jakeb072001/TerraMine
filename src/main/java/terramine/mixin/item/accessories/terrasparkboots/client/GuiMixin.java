package terramine.mixin.item.accessories.terrasparkboots.client;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.TerraMine;
import terramine.common.init.ModComponents;
import terramine.common.utility.Utilities;

@Mixin(Gui.class)
public abstract class GuiMixin {

	@Unique private final ResourceLocation LAVACHARM_ICONS_TEXTURE = TerraMine.id("textures/gui/sprites/hud/lavacharmbar.png");

	@Shadow protected abstract Player getCameraPlayer();

	@Inject(method = "renderPlayerHealth", at = @At(value = "TAIL"))
	private void renderLavaCharm(GuiGraphics guiGraphics, CallbackInfo ci) {
		Player player = this.getCameraPlayer();

		if (player == null) {
			return;
		}

		float charge = (float) ModComponents.LAVA_IMMUNITY.get(player).getLavaImmunityTimer();

		if (charge < 140) {
			int left = guiGraphics.guiWidth() / 2 - 91;
			int top = guiGraphics.guiHeight() + getStatusBarHeightOffset(player);

			int count = (int) Math.floor(charge / 20F);
			if (count >= 7) {
				count = 7;
			}
			for (int i = 0; i < count + 1; i++) {
				if (i == count) {
					float countFloat = charge / 20F + 10;
					Utilities.alphaBlit(guiGraphics, RenderType::guiTextured, LAVACHARM_ICONS_TEXTURE, left + i * 9, top, -90, 0, 9, 9, 9, 9, (countFloat) % ((int) (countFloat)));
				} else {
					guiGraphics.blit(RenderType::guiTextured, LAVACHARM_ICONS_TEXTURE, left + i * 9, top, -90, 0, 9, 9, 9, 9);
				}
			}
		}
	}

	/**
	 * Calculate offset for our status bar height, taking rendering of other status bars into account
	 * This might take (What did he mean by this?)
	 */
	@Unique
	private int getStatusBarHeightOffset(Player player) {
		int offset = -49; // Base offset

		// Offset if any armour is worn
		if (player.getArmorValue() > 0) {
			offset -= 10;
		}

		return offset;
	}
}
