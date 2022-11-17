package terramine.mixin.item.accessories.weatherradio.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.common.init.ModItems;
import terramine.common.trinkets.TrinketsHelper;

import java.text.DecimalFormat;

@Mixin(Gui.class)
public abstract class GuiMixin {

	@Shadow private int screenHeight;
	@Shadow private int screenWidth;
	private static final DecimalFormat df = new DecimalFormat("0.00");

	@Shadow protected abstract Player getCameraPlayer();
	@Shadow public abstract Font getFont();

	@Inject(method = "renderPlayerHealth", require = 0, at = @At(value = "TAIL"))
	private void renderGuiWeather(PoseStack matrices, CallbackInfo ci) {
		Player player = this.getCameraPlayer();

		if (player == null || !getEquippedTrinkets(player)) {
			return;
		}

		int left = this.screenWidth - 22 - this.getFont().width(getWeather());
		int top = this.screenHeight - 43;

		matrices.pushPose();
		Gui.drawString(matrices, Minecraft.getInstance().font, getWeather(), left, top, 0xffffff);
	}

	@Unique
	private boolean getEquippedTrinkets(Player player) {
		return TrinketsHelper.isInInventory(ModItems.WEATHER_RADIO, player) || TrinketsHelper.isInInventory(ModItems.FISH_FINDER, player) || TrinketsHelper.isInInventory(ModItems.PDA, player)
				|| TrinketsHelper.isInInventory(ModItems.CELL_PHONE, player);
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
