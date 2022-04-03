package terracraft.mixin.item.dpsmeter.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terracraft.common.components.DPSDamageCounterComponent;
import terracraft.common.init.ModComponents;
import terracraft.common.init.ModItems;
import terracraft.common.trinkets.TrinketsHelper;

import java.text.DecimalFormat;

@Mixin(Gui.class)
public abstract class GuiMixin {

	@Shadow @Final private Minecraft minecraft;
	@Shadow private int screenHeight;
	@Shadow private int screenWidth;
	private int timer;
	private int seconds = 1;
	private static final DecimalFormat df = new DecimalFormat("0.00");
	private static final Logger LOGGER = LogManager.getLogger("terracraft");

	@Shadow protected abstract Player getCameraPlayer();
	@Shadow protected abstract Font getFont();

	@Inject(method = "renderPlayerHealth", require = 0, at = @At(value = "TAIL"))
	private void renderGuiClock(PoseStack matrices, CallbackInfo ci) {
		Player player = this.getCameraPlayer();

		if (player == null || !getEquippedTrinkets(player) || true) { // temporary disable as it doesnt really work yet
			return;
		}

		int left = this.screenWidth - 22 - this.getFont().width(getDPS());
		int top = this.screenHeight - 83;

		matrices.pushPose();
		Gui.drawString(matrices, Minecraft.getInstance().font, getDPS(), left, top, 0xffffff);
	}

	@Unique
	private boolean getEquippedTrinkets(Player player) {
		boolean equipped = false;

		if (TrinketsHelper.isEquipped(ModItems.DPS_METER, player) || TrinketsHelper.isEquipped(ModItems.GOBLIN_TECH, player) || TrinketsHelper.isEquipped(ModItems.PDA, player)
				|| TrinketsHelper.isEquipped(ModItems.CELL_PHONE, player)) {
			equipped = true;
		}
		if (player.getInventory().contains(ModItems.DPS_METER.getDefaultInstance()) || player.getInventory().contains(ModItems.GOBLIN_TECH.getDefaultInstance()) || player.getInventory().contains(ModItems.PDA.getDefaultInstance())
				|| player.getInventory().contains(ModItems.CELL_PHONE.getDefaultInstance())) {
			equipped = true;
		}

		return equipped;
	}

	@Unique
	private String getDPS() {
		Player player = this.getCameraPlayer();
		StringBuilder sb = new StringBuilder();
		float dps = 0f;
		if (player != null) {
			DPSDamageCounterComponent dpsDamage = ModComponents.DPS_METER_DAMAGE.get(player);
			float totalDamageTakenInCombat = dpsDamage.getDamageTaken() * 3;
			if (totalDamageTakenInCombat > 0) {
				timer++;
				if (timer >= 400) {
					timer = 0;
					seconds += 1;
				}
				dps = totalDamageTakenInCombat / seconds;
				if (seconds >= 10) {
					timer = 0;
					seconds = 1;
					dpsDamage.resetDamageTaken();
				}
			}
			sb.append("DPS: ");
			sb.append(df.format(dps));
		}
		return sb.toString();
	}
}
