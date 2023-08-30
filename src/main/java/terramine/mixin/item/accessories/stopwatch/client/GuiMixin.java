package terramine.mixin.item.accessories.stopwatch.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.TerraMine;
import terramine.common.init.ModItems;
import terramine.common.misc.AccessoriesHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;

@Mixin(Gui.class)
public abstract class GuiMixin {

	@Shadow private int screenHeight;
	@Shadow private int screenWidth;
	private static final DecimalFormat df = new DecimalFormat("0.00");
	private static final ArrayList<Double> speeds = new ArrayList<Double>();

	@Shadow protected abstract Player getCameraPlayer();
	@Shadow public abstract Font getFont();

	@Unique MutableComponent speedText = Component.translatable(TerraMine.MOD_ID + ".ui.speed");

	@Inject(method = "renderPlayerHealth", require = 0, at = @At(value = "TAIL"))
	private void renderGuiClock(PoseStack matrices, CallbackInfo ci) {
		Player player = this.getCameraPlayer();

		if (player == null || !getEquippedAccessories(player)) {
			return;
		}

		int left = this.screenWidth - 22 - this.getFont().width(getSpeed());
		int top = this.screenHeight - 73;

		matrices.pushPose();
		Gui.drawString(matrices, Minecraft.getInstance().font, getSpeed(), left, top, 0xffffff);
	}

	@Unique
	private boolean getEquippedAccessories(Player player) {
		return AccessoriesHelper.isInInventory(ModItems.STOPWATCH, player) || AccessoriesHelper.isInInventory(ModItems.GOBLIN_TECH, player) || AccessoriesHelper.isInInventory(ModItems.PDA, player)
				|| AccessoriesHelper.isInInventory(ModItems.CELL_PHONE, player);
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
			for (Double aDouble : speeds) {
				speed += aDouble;
			}
			speed = speed / speeds.size();
			sb.append(speedText.getString()).append(": ");
			if (TerraMine.CONFIG.general.stopwatchMPH) {
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
