package terracraft.mixin.item.stopwatch.client;

import terracraft.Artifacts;
import terracraft.common.init.Items;
import terracraft.common.trinkets.TrinketsHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.text.DecimalFormat;
import java.util.ArrayList;

@Mixin(Gui.class)
public abstract class GuiMixin {

	@Shadow @Final private Minecraft minecraft;
	@Shadow private int screenHeight;
	@Shadow private int screenWidth;
	private static final DecimalFormat df = new DecimalFormat("0.00");
	private static final ArrayList<Double> speeds = new ArrayList<Double>();

	@Shadow protected abstract Player getCameraPlayer();
	@Shadow protected abstract Font getFont();

	@Inject(method = "renderPlayerHealth", require = 0, at = @At(value = "TAIL"))
	private void renderGuiClock(PoseStack matrices, CallbackInfo ci) {
		Player player = this.getCameraPlayer();

		if (player == null || !getEquippedTrinkets(player)) {
			return;
		}

		int left = this.screenWidth - 22 - this.getFont().width(getSpeed());
		int top = this.screenHeight - 73;

		matrices.pushPose();
		Gui.drawString(matrices, Minecraft.getInstance().font, getSpeed(), left, top, 0xffffff);
	}

	@Unique
	private boolean getEquippedTrinkets(Player player) {
		boolean equipped = false;

		if (TrinketsHelper.isEquipped(Items.STOPWATCH, player) || TrinketsHelper.isEquipped(Items.GOBLIN_TECH, player) || TrinketsHelper.isEquipped(Items.PDA, player)
				|| TrinketsHelper.isEquipped(Items.CELL_PHONE, player)) {
			equipped = true;
		}
		if (player.getInventory().contains(Items.STOPWATCH.getDefaultInstance()) || player.getInventory().contains(Items.GOBLIN_TECH.getDefaultInstance()) || player.getInventory().contains(Items.PDA.getDefaultInstance())
				|| player.getInventory().contains(Items.CELL_PHONE.getDefaultInstance())) {
			equipped = true;
		}

		return equipped;
	}

	@Unique
	private String getSpeed() {
		Minecraft mc = Minecraft.getInstance();
		StringBuilder sb = new StringBuilder();
		if (mc.player != null) {
			Vec3 vec = mc.player.getDeltaMovement();
			//double speed = Math.sqrt(Math.pow(vec.x, 2) + Math.pow(vec.y + 0.0784000015258789, 2) + Math.pow(vec.z, 2)) * 20; // includes y speed, a bit messy looking though
			double speed = Math.sqrt(Math.pow(vec.x, 2) + Math.pow(vec.z, 2)) * 36.55f;
			if (speeds.size() >= 15) {
				speeds.remove(0);
			}
			speeds.add(speed);
			speed = 0;
			for (int i = 0; i < speeds.size(); i++) {
				speed += speeds.get(i);
			}
			speed = speed / speeds.size();
			sb.append("Speed: ");
			if (Artifacts.CONFIG.general.stopwatchMPH) {
				sb.append(df.format(speed * 2.23693629));
				sb.append(" mph");
			} else {
				sb.append(df.format(speed * 3.6));
				sb.append(" km/h");
			}
		}
		return sb.toString();
	}
}
