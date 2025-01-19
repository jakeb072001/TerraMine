package terramine.mixin.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import terramine.common.item.accessories.ShieldAccessoryLikeItem;
import terramine.extensions.EntityRenderStateExtensions;
import terramine.extensions.PlayerStorages;

@Mixin(PlayerItemInHandLayer.class)
public class PlayerItemInHandLayerMixin {

    @Shadow @Final private ItemRenderer itemRenderer;

    @ModifyVariable(method = "renderArmWithItem(Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;Lnet/minecraft/client/resources/model/BakedModel;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lnet/minecraft/world/entity/HumanoidArm;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"), argsOnly = true)
    private ItemStack renderCustomShield(ItemStack itemStack, PlayerRenderState playerRenderState, BakedModel bakedModel, ItemStack itemStack2, ItemDisplayContext itemDisplayContext, HumanoidArm humanoidArm, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        if (((EntityRenderStateExtensions) playerRenderState).terrariaCraft$getLivingEntity() instanceof Player player && humanoidArm.equals(playerRenderState.mainArm.getOpposite())) {
            Item checkItem = player.getUseItem().getItem();
            if (checkItem instanceof ShieldItem || checkItem instanceof ShieldAccessoryLikeItem) {
                if (((PlayerStorages) player).getTerrariaInventory().getItem(21) != ItemStack.EMPTY) {
                    player.useItem = ((PlayerStorages) player).getTerrariaInventory().getItem(21);
                }
            }
            checkItem = itemStack.getItem();
            if (checkItem instanceof ShieldItem || checkItem instanceof ShieldAccessoryLikeItem) {
                if (((PlayerStorages) player).getTerrariaInventory().getItem(21) != ItemStack.EMPTY) {
                    return ((PlayerStorages) player).getTerrariaInventory().getItem(21);
                }
            }
        }

        return itemStack;
    }

    // todo: find a way to dye shields, have to dye the item, if i can figure this out then i can use it for dying pumpkin on head
    @ModifyVariable(method = "renderArmWithItem(Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;Lnet/minecraft/client/resources/model/BakedModel;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lnet/minecraft/world/entity/HumanoidArm;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"), argsOnly = true)
    private BakedModel renderCustomShield(BakedModel bakedModel, PlayerRenderState playerRenderState, BakedModel bakedModel2, ItemStack itemStack, ItemDisplayContext itemDisplayContext, HumanoidArm humanoidArm, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        if (((EntityRenderStateExtensions)playerRenderState).terrariaCraft$getLivingEntity() instanceof Player player && humanoidArm.equals(playerRenderState.mainArm.getOpposite()) && (itemStack.getItem() instanceof ShieldItem || itemStack.getItem() instanceof ShieldAccessoryLikeItem)) {
            //if (((PlayerStorages)player).getTerrariaInventory().getItem(22).getItem() instanceof BasicDye dye) {
            //    this.itemRenderer.itemColors.register((itemStack3, i1) -> dye.getColourInt(), itemStack.getItem());
            //}
            if (((PlayerStorages)player).getTerrariaInventory().getItem(21) != ItemStack.EMPTY) {
                return this.itemRenderer.getModel(itemStack, player.level(), player, 0);
            }
        }

        return bakedModel;
    }
}
