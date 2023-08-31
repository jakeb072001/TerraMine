package terramine.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import terramine.common.item.accessories.AccessoryTerrariaItem;
import terramine.extensions.PlayerStorages;

public class AccessoryFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    public AccessoryFeatureRenderer(RenderLayerParent<T, M> context) {
        super(context);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        for (int i = 0; i < 7; i++) {
            if (entity instanceof Player player) {
                ItemStack itemStack = ((PlayerStorages) player).getTerrariaInventory().getItem(i);
                if ((((PlayerStorages) player).getTerrariaInventory().getItem(i + 7).getItem() instanceof AccessoryTerrariaItem)) {
                    itemStack = ((PlayerStorages) player).getTerrariaInventory().getItem(i + 7);
                }
                ItemStack finalItemStack = itemStack;
                AccessoryRenderRegistry.getRenderer(finalItemStack.getItem()).ifPresent(renderer -> {
                    poseStack.pushPose();
                    renderer.render(finalItemStack, this.getParentModel(), poseStack, multiBufferSource,
                            light, player, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch);
                    poseStack.popPose();
                });
            }
        }
    }
}
