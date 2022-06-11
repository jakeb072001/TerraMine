package terramine.mixin.item.dpsmeter.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.TerraMine;
import terramine.common.components.DPSDamageCounterComponent;
import terramine.common.init.ModComponents;
import terramine.common.init.ModItems;
import terramine.common.trinkets.TrinketsHelper;

import java.text.DecimalFormat;

@Mixin(Gui.class)
public abstract class GuiMixin {

	@Shadow private int screenHeight;
	@Shadow private int screenWidth;
	@Unique private int timer;
	@Unique private int seconds = 1;
	@Unique private static final DecimalFormat df = new DecimalFormat("0.00");
	@Unique TranslatableComponent dpsText = new TranslatableComponent(TerraMine.MOD_ID + ".ui.dps");

	@Shadow protected abstract Player getCameraPlayer();
	@Shadow public abstract Font getFont();

	//todo: make DPS Meter actually work, right now it doesn't really, probably something with packets
	@Inject(method = "renderPlayerHealth", require = 0, at = @At(value = "TAIL"))
	private void renderGuiDPS(PoseStack matrices, CallbackInfo ci) {
		Player player = this.getCameraPlayer();

		if (player == null || !getEquippedTrinkets(player)) {
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

		if (TrinketsHelper.isInInventory(ModItems.DPS_METER, player) || TrinketsHelper.isInInventory(ModItems.GOBLIN_TECH, player) || TrinketsHelper.isInInventory(ModItems.PDA, player)
				|| TrinketsHelper.isInInventory(ModItems.CELL_PHONE, player)) {
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
			float totalDamageTakenInCombat = dpsDamage.getDamageTaken();
			if (totalDamageTakenInCombat > 0) {
				timer++;
				if (timer >= 400) {
					timer = 0;
					seconds += 1;
				}
				dps = totalDamageTakenInCombat;
				if (seconds >= 10) {
					timer = 0;
					seconds = 1;
					dpsDamage.resetDamageTaken();
				}
			}
			sb.append(dpsText.getString()).append(": ");
			sb.append(df.format(dps));
		}
		return sb.toString();
	}
}
