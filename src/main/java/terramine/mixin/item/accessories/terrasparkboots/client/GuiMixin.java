package terramine.mixin.item.accessories.terrasparkboots.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
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

@Mixin(Gui.class)
public abstract class GuiMixin {

	@Unique private final ResourceLocation LAVACHARM_ICONS_TEXTURE = TerraMine.id("textures/gui/lavacharmbar.png");

	@Shadow private int screenHeight;
	@Shadow private int screenWidth;

	@Shadow protected abstract Player getCameraPlayer();

	@Inject(method = "renderPlayerHealth", require = 0, at = @At(value = "TAIL"))
	private void renderLavaCharm(GuiGraphics guiGraphics, CallbackInfo ci) {
		Player player = this.getCameraPlayer();

		if (player == null) {
			return;
		}

		float charge = (float) ModComponents.LAVA_IMMUNITY.get(player).getLavaImmunityTimer();

		if (charge < 140) {
			int left = this.screenWidth / 2 - 91;
			int top = this.screenHeight + getStatusBarHeightOffset(player);

			int count = (int) Math.floor(charge / 20F);
			if (count >= 7) {
				count = 7;
			}
			for (int i = 0; i < count + 1; i++) {
				if (i == count) {
					float countFloat = charge / 20F + 10;
					guiGraphics.setColor(1, 1, 1, (countFloat) % ((int) (countFloat)));
				}
				guiGraphics.blit(LAVACHARM_ICONS_TEXTURE, left + i * 9, top, -90, 0, 0, 9, 9, 9, 9);
				guiGraphics.setColor(1, 1, 1, 1);
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
