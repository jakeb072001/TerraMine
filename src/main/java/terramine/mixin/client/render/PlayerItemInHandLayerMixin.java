package terramine.mixin.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import terramine.common.item.accessories.ShieldAccessoryLikeItem;
import terramine.extensions.EntityRenderStateExtensions;
import terramine.extensions.PlayerStorages;

@Mixin(PlayerItemInHandLayer.class)
public class PlayerItemInHandLayerMixin<S extends PlayerRenderState, M extends EntityModel<S> & ArmedModel & HeadedModel> extends ItemInHandLayer<S, M> {

    public PlayerItemInHandLayerMixin(RenderLayerParent<S, M> renderLayerParent) {
        super(renderLayerParent);
    }

    // todo: find a way to dye shields, have to dye the item, if i can figure this out then i can use it for dying pumpkin on head
    @ModifyVariable(method = "renderArmWithItem(Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;Lnet/minecraft/client/renderer/item/ItemStackRenderState;Lnet/minecraft/world/entity/HumanoidArm;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"), argsOnly = true)
    private ItemStackRenderState renderCustomShield(ItemStackRenderState itemStackRenderState, S playerRenderState, ItemStackRenderState itemStackRenderState1, HumanoidArm humanoidArm, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        if (((EntityRenderStateExtensions) playerRenderState).terrariaCraft$getLivingEntity() instanceof Player player && humanoidArm.equals(playerRenderState.mainArm.getOpposite())) {
            Item checkItem = player.getUseItem().getItem();
            if (checkItem == player.getOffhandItem().getItem()) {
                if (checkItem instanceof ShieldItem || checkItem instanceof ShieldAccessoryLikeItem) {
                    if (((PlayerStorages) player).getTerrariaInventory().getItem(21) != ItemStack.EMPTY) {
                        player.useItem = ((PlayerStorages) player).getTerrariaInventory().getItem(21);
                    }
                }
            }
            checkItem = player.getOffhandItem().getItem();
            if (checkItem instanceof ShieldItem || checkItem instanceof ShieldAccessoryLikeItem) {
                if (((PlayerStorages) player).getTerrariaInventory().getItem(21) != ItemStack.EMPTY) {
                    new ItemModelResolver(Minecraft.getInstance().getModelManager()).updateForLiving(itemStackRenderState, ((PlayerStorages) player).getTerrariaInventory().getItem(21), ItemDisplayContext.THIRD_PERSON_LEFT_HAND, true, player);
                }
            }
        }

        return itemStackRenderState;
    }
}
