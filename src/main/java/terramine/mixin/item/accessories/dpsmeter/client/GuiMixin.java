package terramine.mixin.item.accessories.dpsmeter.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
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
import terramine.common.misc.AccessoriesHelper;
import terramine.common.utility.dps.DPSManager;
import terramine.common.utility.dps.DPSTracker;

import java.text.DecimalFormat;

@Mixin(Gui.class)
public abstract class GuiMixin {
	@Unique private static final DecimalFormat df = new DecimalFormat("0.00");
	@Unique MutableComponent dpsText = Component.translatable(TerraMine.MOD_ID + ".ui.dps");

	@Shadow protected abstract Player getCameraPlayer();
	@Shadow public abstract Font getFont();

	//todo: probably needs work, need to test the Terraria DPS meter to figure out exactly how it works (because I'm just going off memory)
	@Inject(method = "renderPlayerHealth", require = 0, at = @At(value = "TAIL"))
	private void renderGuiDPS(GuiGraphics guiGraphics, CallbackInfo ci) {
		Player player = this.getCameraPlayer();

		if (player == null || !getEquippedAccessories(player)) {
			return;
		}

		int left = guiGraphics.guiWidth() - 22 - this.getFont().width(getDPS(player));
		int top = guiGraphics.guiHeight() - 83;

		guiGraphics.drawString(Minecraft.getInstance().font, getDPS(player), left, top, 0xffffff);
	}

	@Unique
	private boolean getEquippedAccessories(Player player) {
		return AccessoriesHelper.isInInventory(ModItems.DPS_METER, player) || AccessoriesHelper.isInInventory(ModItems.GOBLIN_TECH, player) || AccessoriesHelper.isInInventory(ModItems.PDA, player)
				|| AccessoriesHelper.isInInventory(ModItems.CELL_PHONE, player);
	}

	@Unique
	private String getDPS(Player player) {
		DPSTracker tracker = DPSManager.getTracker(player);
		float dps = tracker.getDps();
        return dpsText.getString() + ": " + df.format(dps);
	}
}
