package terracraft.mixin.item.sextant.client;

import terracraft.common.init.Items;
import terracraft.common.trinkets.TrinketsHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {

	@Shadow @Final private Minecraft minecraft;
	@Shadow private int screenHeight;
	@Shadow private int screenWidth;

	@Shadow protected abstract Player getCameraPlayer();
	@Shadow protected abstract Font getFont();

	@Inject(method = "renderPlayerHealth", require = 0, at = @At(value = "TAIL"))
	private void renderGuiClock(PoseStack matrices, CallbackInfo ci) {
		Player player = this.getCameraPlayer();

		if (player == null || !getEquippedTrinkets(player)) {
			return;
		}

		int left = this.screenWidth - 22 - this.getFont().width(getMoonPhase());
		int top = this.screenHeight - 53;

		matrices.pushPose();
		Gui.drawString(matrices, Minecraft.getInstance().font, getMoonPhase(), left, top, 0xffffff);
	}

	@Unique
	private boolean getEquippedTrinkets(Player player) {
		boolean equipped = false;

		if (TrinketsHelper.isEquipped(Items.SEXTANT, player) || TrinketsHelper.isEquipped(Items.FISH_FINDER, player) || TrinketsHelper.isEquipped(Items.PDA, player)
				|| TrinketsHelper.isEquipped(Items.CELL_PHONE, player)) {
			equipped = true;
		}
		if (player.getInventory().contains(Items.SEXTANT.getDefaultInstance()) || player.getInventory().contains(Items.FISH_FINDER.getDefaultInstance()) || player.getInventory().contains(Items.PDA.getDefaultInstance())
				|| player.getInventory().contains(Items.CELL_PHONE.getDefaultInstance())) {
			equipped = true;
		}

		return equipped;
	}

	@Unique
	private String getMoonPhase() {
		Minecraft mc = Minecraft.getInstance();
		StringBuilder sb = new StringBuilder();
		if (mc.level != null) {
			int moonPhase = mc.level.getMoonPhase();
			sb.append("Moon Phase: ");
			if (moonPhase == 0) {
				sb.append("Full Moon");
			} else if (moonPhase == 1) {
				sb.append("Waning Gibbous");
			} else if (moonPhase == 2) {
				sb.append("Third Quarter");
			} else if (moonPhase == 3) {
				sb.append("Waning Crescent");
			} else if (moonPhase == 4) {
				sb.append("New Moon");
			} else if (moonPhase == 5) {
				sb.append("Waxing Crescent");
			} else if (moonPhase == 6) {
				sb.append("First Quarter");
			} else if (moonPhase == 7) {
				sb.append("Waxing Gibbous");
			} else {
				sb.append("Unknown Phase");
			}
		}
		return sb.toString();
	}
}
