package terramine.mixin.item.accessories.weatherradio.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.common.init.ModItems;
import terramine.common.misc.AccessoriesHelper;

import java.text.DecimalFormat;

@Mixin(Gui.class)
public abstract class GuiMixin {

	@Shadow private int screenHeight;
	@Shadow private int screenWidth;
	private static final DecimalFormat df = new DecimalFormat("0.00");

	@Shadow protected abstract Player getCameraPlayer();
	@Shadow public abstract Font getFont();

	@Inject(method = "renderPlayerHealth", require = 0, at = @At(value = "TAIL"))
	private void renderGuiWeather(GuiGraphics guiGraphics, CallbackInfo ci) {
		Player player = this.getCameraPlayer();

		if (player == null || !getEquippedAccessories(player)) {
			return;
		}

		int left = this.screenWidth - 22 - this.getFont().width(getWeather());
		int top = this.screenHeight - 43;

		guiGraphics.drawString(Minecraft.getInstance().font, getWeather(), left, top, 0xffffff);
	}

	@Unique
	private boolean getEquippedAccessories(Player player) {
		return AccessoriesHelper.isInInventory(ModItems.WEATHER_RADIO, player) || AccessoriesHelper.isInInventory(ModItems.FISH_FINDER, player) || AccessoriesHelper.isInInventory(ModItems.PDA, player)
				|| AccessoriesHelper.isInInventory(ModItems.CELL_PHONE, player);
	}

	@Unique
	private String getWeather() {
		Minecraft mc = Minecraft.getInstance();
		StringBuilder sb = new StringBuilder();
		if (mc.level != null && mc.player != null) {
			float rainLevel = mc.level.getRainLevel(0);
			if (mc.level.getBiome(mc.player.getOnPos()).value().coldEnoughToSnow(mc.player.getOnPos())) {
				sb.append("Snow Level: ");
			} else {
				sb.append("Rain Level: ");
			}
			sb.append(df.format(rainLevel));
		}
		return sb.toString();
	}
}
