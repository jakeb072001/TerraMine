package terramine.mixin.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.client.render.AccessoryFeatureRenderer;

@Environment(EnvType.CLIENT)
@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {
    @Invoker("addLayer")
    public abstract boolean invokeAddLayer(RenderLayer<?, ?> renderLayer);

    @Inject(at = @At("RETURN"), method = "<init>")
    public void init(EntityRendererProvider.Context context, EntityModel<?> model, float shadowRadius, CallbackInfo ci) {
        this.invokeAddLayer(new AccessoryFeatureRenderer<>((LivingEntityRenderer<?, ?>) (Object) this));
    }
}
