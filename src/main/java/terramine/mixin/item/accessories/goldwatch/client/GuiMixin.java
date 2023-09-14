package terramine.mixin.item.accessories.goldwatch.client;

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

@Mixin(Gui.class)
public abstract class GuiMixin {

	@Shadow private int screenHeight;
	@Shadow private int screenWidth;

	@Shadow protected abstract Player getCameraPlayer();
	@Shadow public abstract Font getFont();

	@Inject(method = "renderPlayerHealth", require = 0, at = @At(value = "TAIL"))
	private void renderGuiClock(GuiGraphics guiGraphics, CallbackInfo ci) {
		Player player = this.getCameraPlayer();

		if (player == null || !getEquippedAccessories(player)) {
			return;
		}

		int left = this.screenWidth - 22 - this.getFont().width(getTime());
		int top = this.screenHeight - 13;

		guiGraphics.drawString(Minecraft.getInstance().font, getTime(), left, top, 0xffffff);
	}

	@Unique
	private boolean getEquippedAccessories(Player player) {
		return AccessoriesHelper.isInInventory(ModItems.GOLD_WATCH, player) || AccessoriesHelper.isInInventory(ModItems.GPS, player) || AccessoriesHelper.isInInventory(ModItems.PDA, player)
				|| AccessoriesHelper.isInInventory(ModItems.CELL_PHONE, player);
	}

	@Unique
	private String getTime() {
		Minecraft mc = Minecraft.getInstance();
		long time = mc.player.level().getDayTime();
		long day = mc.player.level().getDayTime() / 24000L;
		long currentTime = time - (24000L * day);
		long currentHour = (currentTime / 1000L) + 6L;
		double currentTimeMin = currentTime - ((currentHour - 6L) * 1000L);
		currentTimeMin = currentTimeMin / (1000.0D / 60.0D);
		int currentMin = (int) currentTimeMin;
		if (currentHour > 24)
			currentHour -= 24L;
		//if (this.settings.getStringValue(Settings.clock_time_format) == "time.24") {
		//	return get24HourTimeForString(currentHour, currentMin);
		//}
		return get12HourTimeForString(currentHour, currentMin);
	}

	@Unique
	private static String get12HourTimeForString(long currentHour, long currentMin) {
		StringBuilder sb = new StringBuilder();
		String period = "am";
		if (currentHour == 12) {
			period = "pm";
		}
		if (currentHour == 24) {
			currentHour = 12;
			period = "am";
		}
		if (currentHour > 12) {
			currentHour -= 12;
			period = "pm";
		}
		if (currentHour < 10)
			sb.append(0);
		sb.append(currentHour);
		return sb + ":" + getMinuteForString(currentMin) + " " + period;
	}

	@Unique
	private static String getMinuteForString(long currentMin) {
		StringBuilder sb = new StringBuilder();
		if (currentMin < 10)
			sb.append("0");
		sb.append(currentMin);
		return sb.toString();
	}
}
