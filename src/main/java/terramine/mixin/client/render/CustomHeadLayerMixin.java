package terramine.mixin.client.render;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AbstractSkullBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.common.item.dye.BasicDye;
import terramine.extensions.EntityRenderStateExtensions;
import terramine.extensions.PlayerStorages;

@Mixin(CustomHeadLayer.class)
public abstract class CustomHeadLayerMixin<S extends LivingEntityRenderState, M extends EntityModel<S> & HeadedModel> extends RenderLayer<S, M> {
    @Unique
    private BasicDye dyeItem;

    public CustomHeadLayerMixin(RenderLayerParent<S, M> renderLayerParent) {
        super(renderLayerParent);
    }

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;FF)V", at = @At(value = "HEAD"))
    private void vanityArmor(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, S livingEntityRenderState, float f, float g, CallbackInfo ci) {
        if (((EntityRenderStateExtensions)livingEntityRenderState).terrariaCraft$getLivingEntity() instanceof Player player) {
            if (((PlayerStorages) player).getTerrariaInventory().getItem(30).getItem() instanceof BasicDye dye) {
                this.dyeItem = dye;
            } else {
                this.dyeItem = null;
            }

            ItemStack vanityItem = ((PlayerStorages)player).getTerrariaInventory().getItem(26);
            if (vanityItem != ItemStack.EMPTY) {
                if (!(vanityItem.getItem() instanceof ArmorItem)) {
                    if (vanityItem.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof AbstractSkullBlock skullBlock) {
                        livingEntityRenderState.wornHeadType = skullBlock.getType();
                    } else {
                        new ItemModelResolver(Minecraft.getInstance().getModelManager()).updateForLiving(livingEntityRenderState.headItem, vanityItem, ItemDisplayContext.HEAD, false, player);
                        livingEntityRenderState.wornHeadType = null;
                    }
                } else {
                    new ItemModelResolver(Minecraft.getInstance().getModelManager()).updateForLiving(livingEntityRenderState.headItem, ItemStack.EMPTY, ItemDisplayContext.HEAD, false, player);
                    livingEntityRenderState.wornHeadType = null;
                }
            }
        }
    }

    @WrapOperation(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;FF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/SkullBlockRenderer;renderSkull(Lnet/minecraft/core/Direction;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/model/SkullModelBase;Lnet/minecraft/client/renderer/RenderType;)V")
    )
    private void headSkullDye(Direction direction, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, SkullModelBase skullModelBase, RenderType renderType, Operation<Void> original) {
        if (dyeItem != null) {
            poseStack.pushPose();
            poseStack.translate(0.5F, 0.0F, 0.5F);
            poseStack.scale(-1.0F, -1.0F, 1.0F);
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(renderType);
            skullModelBase.setupAnim(g, f, 0.0F);
            skullModelBase.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, dyeItem.getColourInt());
            poseStack.popPose();
            return;
        }
        original.call(direction, f, g, poseStack, multiBufferSource, i, skullModelBase, renderType);
    }

    // todo: add dye support to items displayed on head, should be possible but a lot of work, also use the same method for shield dye
    /**
    @WrapOperation(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;FF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/item/ItemStackRenderState;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V")
    )
    private void headSkullDye(ItemStackRenderState instance, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, Operation<Void> original) {
        if (dyeItem != null) {
            // almost works, but can't control colour, probably need to do some custom stuff in a helper class
            original.call(instance, poseStack, multiBufferSource, i, 2);
            return;
        }
        original.call(instance, poseStack, multiBufferSource, i, j);
    }
    **/
}
