package terracraft.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terracraft.TerraCraft;
import terracraft.common.init.ModComponents;

@Mixin(Gui.class)
public abstract class GuiMixin {

	@Unique private final ResourceLocation MANA_ICONS_TEXTURE = TerraCraft.id("textures/gui/manabar.png");

	@Shadow private int screenHeight;
	@Shadow private int screenWidth;

	@Shadow protected abstract Player getCameraPlayer();

	@Inject(method = "renderPlayerHealth", require = 0, at = @At(value = "TAIL"))
	private void renderManaBar(PoseStack matrices, CallbackInfo ci) {
		Player player = this.getCameraPlayer();

		if (player != null) {
			int left = this.screenWidth - 15;
			int top = this.screenHeight -15;
			int currentMana = ModComponents.MANA_HANDLER.get(player).getCurrentMana();
			int maxMana = ModComponents.MANA_HANDLER.get(player).getMaxMana();

			int count = (int) Math.floor(currentMana / 20F);
			if (count >= maxMana) {
				count = maxMana;
			}

			matrices.pushPose();
			RenderSystem.setShaderTexture(0, MANA_ICONS_TEXTURE);
			for (int i = 0; i < count + 1; i++) {
				if (i == count) {
					if (currentMana <= maxMana) {
						float countFloat = currentMana / 20F + 10;
						RenderSystem.setShaderColor(1, 1, 1, (countFloat) % ((int) (countFloat)));
					}
				}
				GuiComponent.blit(matrices, left, top - i * 10, -90, 0, 0, 10, 11, 10, 11);
				RenderSystem.setShaderColor(1, 1, 1, 1);
			}
			matrices.popPose();
		};
	}
}
