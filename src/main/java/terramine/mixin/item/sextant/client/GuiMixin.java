package terramine.mixin.item.sextant.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.TerraMine;
import terramine.common.init.ModItems;
import terramine.common.trinkets.TrinketsHelper;

@Mixin(Gui.class)
public abstract class GuiMixin {

	@Shadow private int screenHeight;
	@Shadow private int screenWidth;

	@Shadow protected abstract Player getCameraPlayer();
	@Shadow public abstract Font getFont();

	@Unique MutableComponent moonPhaseText = Component.translatable(TerraMine.MOD_ID + ".ui.moonPhase");
	@Unique MutableComponent fullMoonText = Component.translatable(TerraMine.MOD_ID + ".ui.fullMoon");
	@Unique MutableComponent waningGibbousMoonText = Component.translatable(TerraMine.MOD_ID + ".ui.waningGibbousMoon");
	@Unique MutableComponent thirdQuarterMoonText = Component.translatable(TerraMine.MOD_ID + ".ui.thirdQuarterMoon");
	@Unique MutableComponent waningCrescentMoonText = Component.translatable(TerraMine.MOD_ID + ".ui.waningCrescentMoon");
	@Unique MutableComponent newMoonText = Component.translatable(TerraMine.MOD_ID + ".ui.newMoon");
	@Unique MutableComponent waxingCrescentMoonText = Component.translatable(TerraMine.MOD_ID + ".ui.waxingCrescentMoon");
	@Unique MutableComponent firstQuarterMoonText = Component.translatable(TerraMine.MOD_ID + ".ui.firstQuarterMoon");
	@Unique MutableComponent waxingGibbousMoonText = Component.translatable(TerraMine.MOD_ID + ".ui.waxingGibbousMoon");
	@Unique MutableComponent unknownMoonPhaseText = Component.translatable(TerraMine.MOD_ID + ".ui.unknownMoonPhase");

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

		if (TrinketsHelper.isInInventory(ModItems.SEXTANT, player) || TrinketsHelper.isInInventory(ModItems.FISH_FINDER, player) || TrinketsHelper.isInInventory(ModItems.PDA, player)
				|| TrinketsHelper.isInInventory(ModItems.CELL_PHONE, player)) {
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
			sb.append(moonPhaseText.getString()).append(": ");
			if (moonPhase == 0) {
				sb.append(fullMoonText.getString());
			} else if (moonPhase == 1) {
				sb.append(waningGibbousMoonText.getString());
			} else if (moonPhase == 2) {
				sb.append(thirdQuarterMoonText.getString());
			} else if (moonPhase == 3) {
				sb.append(waningCrescentMoonText.getString());
			} else if (moonPhase == 4) {
				sb.append(newMoonText.getString());
			} else if (moonPhase == 5) {
				sb.append(waxingCrescentMoonText.getString());
			} else if (moonPhase == 6) {
				sb.append(firstQuarterMoonText.getString());
			} else if (moonPhase == 7) {
				sb.append(waxingGibbousMoonText.getString());
			} else {
				sb.append(unknownMoonPhaseText.getString());
			}
		}
		return sb.toString();
	}
}
