package terracraft.mixin.item.depthmeter.client;

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

		int left = this.screenWidth - 22 - this.getFont().width(getDepth());
		int top = this.screenHeight - 23;

		matrices.pushPose();
		Gui.drawString(matrices, Minecraft.getInstance().font, getDepth(), left, top, 0xffffff);
	}

	@Unique
	private boolean getEquippedTrinkets(Player player) {
		boolean equipped = false;

		if (TrinketsHelper.isEquipped(Items.DEPTH_METER, player) || TrinketsHelper.isEquipped(Items.GPS, player) || TrinketsHelper.isEquipped(Items.PDA, player)
				|| TrinketsHelper.isEquipped(Items.CELL_PHONE, player)) {
			equipped = true;
		}
		if (player.getInventory().contains(Items.DEPTH_METER.getDefaultInstance()) || player.getInventory().contains(Items.GPS.getDefaultInstance()) || player.getInventory().contains(Items.PDA.getDefaultInstance())
				|| player.getInventory().contains(Items.CELL_PHONE.getDefaultInstance())) {
			equipped = true;
		}

		return equipped;
	}

	@Unique
	private String getDepth() {
		Minecraft mc = Minecraft.getInstance();
		StringBuilder sb = new StringBuilder();
		if (mc.player != null) {
			int y = (int) mc.player.position().y();
			sb.append("Depth: ");
			sb.append(y);
		}
		return sb.toString();
	}
}
