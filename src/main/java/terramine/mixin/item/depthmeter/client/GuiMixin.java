package terramine.mixin.item.depthmeter.client;

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

	@Unique MutableComponent depthText = Component.translatable(TerraMine.MOD_ID + ".ui.depth");

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

		if (TrinketsHelper.isInInventory(ModItems.DEPTH_METER, player) || TrinketsHelper.isInInventory(ModItems.GPS, player) || TrinketsHelper.isInInventory(ModItems.PDA, player)
				|| TrinketsHelper.isInInventory(ModItems.CELL_PHONE, player)) {
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
			sb.append(depthText.getString()).append(": ");
			sb.append(y);
		}
		return sb.toString();
	}
}
