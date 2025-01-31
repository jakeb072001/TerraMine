package terramine.mixin.client.render;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import terramine.common.item.accessories.ShieldAccessoryLikeItem;
import terramine.common.item.equipment.UmbrellaItem;
import terramine.extensions.PlayerStorages;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {

    @WrapOperation(
            method = "renderHandsWithItems",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderArmWithItem(Lnet/minecraft/client/player/AbstractClientPlayer;FFLnet/minecraft/world/InteractionHand;FLnet/minecraft/world/item/ItemStack;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V")
    )
    private void vanityShield(ItemInHandRenderer instance, AbstractClientPlayer abstractClientPlayer, float f, float g, InteractionHand interactionHand, float h, ItemStack itemStack, float i, PoseStack poseStack, MultiBufferSource multiBufferSource, int j, Operation<Void> original) {
        ItemStack itemStack2 = itemStack;

        if (interactionHand == InteractionHand.OFF_HAND) {
            Item checkItem = itemStack.getItem();
            if (checkItem instanceof ShieldItem || checkItem instanceof ShieldAccessoryLikeItem) {
                if (((PlayerStorages) abstractClientPlayer).getTerrariaInventory().getItem(21) != ItemStack.EMPTY) {
                    itemStack2 = ((PlayerStorages) abstractClientPlayer).getTerrariaInventory().getItem(21);
                }
            }
        }
        if (abstractClientPlayer.getUsedItemHand() == InteractionHand.OFF_HAND) {
            Item checkItem = abstractClientPlayer.getUseItem().getItem();
            if (checkItem instanceof ShieldItem || checkItem instanceof ShieldAccessoryLikeItem) {
                if (((PlayerStorages) abstractClientPlayer).getTerrariaInventory().getItem(21) != ItemStack.EMPTY) {
                    abstractClientPlayer.useItem = ((PlayerStorages) abstractClientPlayer).getTerrariaInventory().getItem(21);
                }
            }
        }

        original.call(instance, abstractClientPlayer, f, g, interactionHand, h, itemStack2, i, poseStack, multiBufferSource, j);
    }

    // Copied from FabricShieldLib, credits to Starexify (Nova on disc) for the fix (I was going to change the json rotation which would probably be better but that would have taken forever)
    @WrapOperation(
            method = "renderArmWithItem",
            constant = @Constant(classValue = ShieldItem.class)
    )
    private boolean wrapInstanceCheck(Object instance, Operation<Boolean> original) {
        return original.call(instance) || instance instanceof ShieldAccessoryLikeItem || instance instanceof UmbrellaItem;
    }
}
