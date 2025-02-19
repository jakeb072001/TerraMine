package terramine.client.integrations.REI;

import com.mojang.blaze3d.systems.RenderSystem;
import me.shedaniel.clothconfig2.api.animator.NumberAnimator;
import me.shedaniel.clothconfig2.api.animator.ValueAnimator;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.REIRuntime;
import me.shedaniel.rei.api.client.gui.widgets.Arrow;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.ApiStatus;
import terramine.TerraMine;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class TwoWayArrowWidget extends Arrow {
    private static final ResourceLocation DISPLAY_TEXTURE = TerraMine.id("textures/gui/two_way_arrow.png");
    private static final ResourceLocation DISPLAY_TEXTURE_DARK = TerraMine.id("textures/gui/two_way_arrow_dark.png");
    private Rectangle bounds;
    private double animationDuration = -1.0;
    private NumberAnimator<Float> darkBackgroundAlpha = ValueAnimator.ofFloat().withConvention(() -> {
        return REIRuntime.getInstance().isDarkThemeEnabled() ? 1.0F : 0.0F;
    }, ValueAnimator.typicalTransitionTime()).asFloat();

    public TwoWayArrowWidget(Rectangle bounds) {
        this.bounds = new Rectangle(Objects.requireNonNull(bounds));
    }

    public double getAnimationDuration() {
        return this.animationDuration;
    }

    public void setAnimationDuration(double animationDurationMS) {
        this.animationDuration = animationDurationMS;
        if (this.animationDuration <= 0.0) {
            this.animationDuration = -1.0;
        }

    }

    @ApiStatus.Internal
    public void setDarkBackgroundAlpha(NumberAnimator<Float> darkBackgroundAlpha) {
        this.darkBackgroundAlpha = darkBackgroundAlpha;
    }

    public Rectangle getBounds() {
        return this.bounds;
    }

    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        this.darkBackgroundAlpha.update(delta);
        this.renderBackground(graphics, false, 1.0F);
        if (this.darkBackgroundAlpha.value() > 0.0F) {
            this.renderBackground(graphics, true, this.darkBackgroundAlpha.value());
        }

    }

    public void renderBackground(GuiGraphics graphics, boolean dark, float alpha) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(770, 771, 1, 0);
        RenderSystem.blendFunc(770, 771);
        ResourceLocation texture = getArrowTexture(dark);
        if (this.getAnimationDuration() > 0.0) {
            int width = Mth.ceil((double)System.currentTimeMillis() / (this.animationDuration / 24.0) % 24.0);
            graphics.blit(RenderType::guiTextured, texture, this.getX() + width, this.getY(), (float)(106 + width), 91.0F, 24 - width, 17, 256, 256);
            graphics.blit(RenderType::guiTextured, texture, this.getX(), this.getY(), 82.0F, 91.0F, width, 17, 256, 256);
        } else {
            graphics.blit(RenderType::guiTextured, texture, this.getX(), this.getY(), 0, 0, 22, 16, 22, 16);
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public ResourceLocation getArrowTexture(boolean darkTheme) {
        return darkTheme ? DISPLAY_TEXTURE_DARK : DISPLAY_TEXTURE;
    }

    public List<? extends GuiEventListener> children() {
        return Collections.emptyList();
    }
}
