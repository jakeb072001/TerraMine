package terramine.client.render.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import terramine.common.init.ModComponents;

@Environment(EnvType.CLIENT)
public class ToggleImageButton extends Button {
    private final ResourceLocation resourceLocation;
    private final int xTexStart;
    private final int yTexStart;
    private final int xDiffTex;
    private final int yDiffTex;
    private final int textureWidth;
    private final int textureHeight;
    private final int slot;

    public ToggleImageButton(int i, int j, int k, int l, int m, int n, int o, int s, int g, ResourceLocation resourceLocation, int p, int q, Button.OnPress onPress) {
        this(i, j, k, l, m, n, o, s, g, resourceLocation, p, q, onPress, CommonComponents.EMPTY);
    }

    public ToggleImageButton(int i, int j, int k, int l, int m, int n, int o, int s, int g, ResourceLocation resourceLocation, int p, int q, Button.OnPress onPress, Component component) {
        super(i, j, k, l, component, onPress, DEFAULT_NARRATION);
        this.textureWidth = p;
        this.textureHeight = q;
        this.xTexStart = m;
        this.yTexStart = n;
        this.yDiffTex = o;
        this.xDiffTex = s;
        this.slot = g;
        this.resourceLocation = resourceLocation;
    }

    public void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
        int k = this.yTexStart;
        int g = this.xTexStart;
        if (!this.isActive()) {
            k += this.yDiffTex * 2;
        } else if (this.isHoveredOrFocused()) {
            k += this.yDiffTex;
        }

        if (!this.isActive()) {
            g += this.xDiffTex * 2;
        } else if (!ModComponents.ACCESSORY_VISIBILITY.get(Minecraft.getInstance().player).getSlotVisibility(slot)) {
            g += this.xDiffTex;
        }

        guiGraphics.blit(this.resourceLocation, this.getX(), this.getY(), (float)g, (float)k, this.width, this.height, this.textureWidth, this.textureHeight);
    }
}
