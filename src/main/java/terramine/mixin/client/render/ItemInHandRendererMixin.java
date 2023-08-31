package terramine.mixin.client.render;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import terramine.common.item.accessories.ShieldAccessoryItem;
import terramine.extensions.PlayerStorages;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {

    // todo: not correctly working yet. when blocking, the shield doesn't correctly set the pose
    /**
    @WrapOperation(
            method = "renderHandsWithItems",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderArmWithItem(Lnet/minecraft/client/player/AbstractClientPlayer;FFLnet/minecraft/world/InteractionHand;FLnet/minecraft/world/item/ItemStack;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V")
    )
    private void vanityShield(ItemInHandRenderer instance, AbstractClientPlayer abstractClientPlayer, float f, float g, InteractionHand interactionHand, float h, ItemStack itemStack, float i, PoseStack poseStack, MultiBufferSource multiBufferSource, int j, Operation<Void> original) {
        ItemStack itemStack2 = itemStack;
        if (interactionHand == InteractionHand.OFF_HAND && (itemStack.getItem() instanceof ShieldItem || itemStack.getItem() instanceof ShieldAccessoryItem)) {
            if (((PlayerStorages)abstractClientPlayer).getTerrariaInventory().getItem(21) != ItemStack.EMPTY) {
                itemStack2 = ((PlayerStorages)abstractClientPlayer).getTerrariaInventory().getItem(21);
            }
        }
        original.call(instance, abstractClientPlayer, f, g, interactionHand, h, itemStack2, i, poseStack, multiBufferSource, j);
    }
    **/
}
