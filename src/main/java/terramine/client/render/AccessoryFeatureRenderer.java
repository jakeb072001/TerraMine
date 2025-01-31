package terramine.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import terramine.extensions.EntityRenderStateExtensions;
import terramine.extensions.PlayerStorages;

public class AccessoryFeatureRenderer<S extends EntityRenderState, M extends EntityModel<? super S>> extends RenderLayer<S, M> {
    public AccessoryFeatureRenderer(RenderLayerParent<S, M> context) {
        super(context);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int light, S entityRenderState, float f, float g) {
        if (((EntityRenderStateExtensions)entityRenderState).terrariaCraft$getLivingEntity() instanceof Player player) {
            for (int i = 0; i < 7; i++) {
                ItemStack itemStack = ((PlayerStorages) player).getTerrariaInventory().getItem(i);
                ItemStack itemStack2 = ((PlayerStorages) player).getTerrariaInventory().getItem(i + 7);
                int dyeSlot = i;
                int realSlot = i + 7;
                AccessoryRenderRegistry.getRenderer(itemStack.getItem()).ifPresent(renderer -> {
                    poseStack.pushPose();
                    renderer.render(itemStack, dyeSlot, dyeSlot, (EntityModel<? extends HumanoidRenderState>) this.getParentModel(), poseStack, multiBufferSource,
                            light, player, f, g);
                    poseStack.popPose();
                });

                AccessoryRenderRegistry.getRenderer(itemStack2.getItem()).ifPresent(renderer -> {
                    poseStack.pushPose();
                    renderer.render(itemStack2, dyeSlot, realSlot, (EntityModel<? extends HumanoidRenderState>) this.getParentModel(), poseStack, multiBufferSource,
                            light, player, f, g);
                    poseStack.popPose();
                });
            }
        }
    }
}
