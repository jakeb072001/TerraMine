package terracraft.mixin.item.terrasparkboots.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terracraft.TerraCraft;
import terracraft.common.init.ModItems;
import terracraft.common.trinkets.TrinketsHelper;

import java.util.Objects;

@Mixin(Gui.class)
public abstract class GuiMixin {

	@Unique private final ResourceLocation LAVACHARM_ICONS_TEXTURE = TerraCraft.id("textures/gui/lavacharmbar.png");

	@Shadow private int screenHeight;
	@Shadow private int screenWidth;

	@Shadow protected abstract Player getCameraPlayer();

	@Inject(method = "renderPlayerHealth", require = 0, at = @At(value = "TAIL"))
	private void renderLavaCharm(PoseStack matrices, CallbackInfo ci) {
		Player player = this.getCameraPlayer();

		if (player == null || !getEquippedTrinkets(player)) {
			return;
		}

		if (player.isInLava() && player.hasEffect(MobEffects.FIRE_RESISTANCE)) {
			int left = this.screenWidth / 2 - 91;
			int top = this.screenHeight + getStatusBarHeightOffset(player);

			matrices.pushPose();
			RenderSystem.setShaderTexture(0, LAVACHARM_ICONS_TEXTURE);
			float charge = Objects.requireNonNull(player.getEffect(MobEffects.FIRE_RESISTANCE)).getDuration();
			int count = (int) Math.floor(charge / 20F);
			if (count >= 7) {
				count = 7;
			}
			for (int i = 0; i < count + 1; i++) {
				if (i == count) {
					if (charge <= 140) {
						float countFloat = charge / 20F + 10;
						RenderSystem.setShaderColor(1, 1, 1, (countFloat) % ((int) (countFloat)));
					}
				}
				GuiComponent.blit(matrices, left + i * 9, top, -90, 0, 0, 9, 9, 9, 9);
				RenderSystem.setShaderColor(1, 1, 1, 1);
			}
		}
	}

	/**
	 * Calculate offset for our status bar height, taking rendering of other status bars into account
	 * This might take
	 */
	@Unique
	private int getStatusBarHeightOffset(Player player) {
		int offset = -49; // Base offset

		// Offset if any armour is worn
		if (player.getArmorValue() > 0) {
			offset -= 10;
		}

		return offset;
	}

	@Unique
	private boolean getEquippedTrinkets(Player player) {
		boolean equipped = false;

		if (TrinketsHelper.isEquipped(ModItems.TERRASPARK_BOOTS, player) || TrinketsHelper.isEquipped(ModItems.LAVA_WADERS, player) ||
				TrinketsHelper.isEquipped(ModItems.MOLTEN_CHARM, player) || TrinketsHelper.isEquipped(ModItems.LAVA_CHARM, player)) {
			equipped = true;
		}

		return equipped;
	}
}
