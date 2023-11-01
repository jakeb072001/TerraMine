package terramine.mixin.client.render;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ElytraModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import terramine.common.item.armor.vanity.FamiliarVanity;
import terramine.common.item.dye.BasicDye;
import terramine.extensions.PlayerStorages;

@Mixin(ElytraLayer.class)
public abstract class ElytraLayerMixin<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    @Unique
    private BasicDye dyeItem;

    public ElytraLayerMixin(RenderLayerParent<T, M> renderLayerParent) {
        super(renderLayerParent);
    }

    @ModifyVariable(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V", at = @At("STORE"), ordinal = 0)
    private ItemStack hideElytra(ItemStack itemStack, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
        if (livingEntity instanceof Player player) {
            if ((((PlayerStorages) player).getTerrariaInventory().getItem(25).getItem() instanceof FamiliarVanity)) {
                return ItemStack.EMPTY;
            }
            if (((PlayerStorages) player).getTerrariaInventory().getItem(29).getItem() instanceof BasicDye dye) {
                this.dyeItem = dye;
            } else {
                this.dyeItem = null;
            }
            if ((((PlayerStorages) player).getTerrariaInventory().getItem(25).getItem() instanceof ElytraItem)) {
                return ((PlayerStorages) player).getTerrariaInventory().getItem(25);
            }
        }

        return itemStack;
    }

    @WrapOperation(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ElytraModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;IIFFFF)V")
    )
    private void elytraDye(ElytraModel<T> instance, PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j, float f, float g, float h, float k, Operation<Void> original) {
        if (dyeItem != null) {
            Vector3f colour = dyeItem.getColour();
            original.call(instance, poseStack, vertexConsumer, i, j, colour.x(), colour.y(), colour.z(), k);
            return;
        }
        original.call(instance, poseStack, vertexConsumer, i, j, f, g, h, k);
    }
}