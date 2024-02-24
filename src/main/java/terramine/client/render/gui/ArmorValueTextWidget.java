package terramine.client.render.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractStringWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import terramine.common.init.ModComponents;
import terramine.common.misc.TeamColours;
import terramine.extensions.PlayerStorages;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class ArmorValueTextWidget extends AbstractStringWidget {
    private float alignX;

    public ArmorValueTextWidget(int i, int j, int k, int l, Font font) {
        super(i, j, k, l, Component.literal(""), font);
        this.alignX = 0.5F;
        this.active = false;
    }

    public void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
        Component component = Component.literal("" + Minecraft.getInstance().player.getArmorValue());
        Font font = this.getFont();
        int k = this.getX() + Math.round(this.alignX * (float)(this.getWidth() - font.width(component)));
        int var10000 = this.getY();
        int var10001 = this.getHeight();
        Objects.requireNonNull(font);
        int l = var10000 + (var10001 - 9) / 2;
        guiGraphics.drawString(font, component, k - 1, l - 1, 0);
        guiGraphics.drawString(font, component, k + 1, l - 1, 0);
        guiGraphics.drawString(font, component, k - 1, l, 0);
        guiGraphics.drawString(font, component, k, l, this.getColor());
    }
}
