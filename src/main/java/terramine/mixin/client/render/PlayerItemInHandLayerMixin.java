package terramine.mixin.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import terramine.common.item.accessories.ShieldAccessoryItem;
import terramine.extensions.PlayerStorages;

@Mixin(PlayerItemInHandLayer.class)
public class PlayerItemInHandLayerMixin {

    // todo: block pose could use some work to look a bit better
    // todo: no clue if other players see correctly, check into this
    @ModifyVariable(method = "renderArmWithItem", at = @At("HEAD"), argsOnly = true)
    private ItemStack vanityArmor(ItemStack itemStack, LivingEntity livingEntity, ItemStack itemStack2, ItemDisplayContext itemDisplayContext, HumanoidArm humanoidArm, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        if (livingEntity instanceof Player player && humanoidArm.equals(HumanoidArm.LEFT) && (itemStack.getItem() instanceof ShieldItem || itemStack.getItem() instanceof ShieldAccessoryItem)) {
            if (((PlayerStorages)player).getTerrariaInventory().getItem(21) != ItemStack.EMPTY) {
                if (player.isUsingItem() && player.getUsedItemHand() == InteractionHand.OFF_HAND) { // manually set the animation to look like blocking, don't know why I have to, but I do
                    poseStack.mulPose(Axis.YP.rotationDegrees(40F));
                    poseStack.mulPose(Axis.XP.rotationDegrees(-40F));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(30F));
                    poseStack.translate(0.3F, -0.2F, 0.4F);
                }
                return ((PlayerStorages)player).getTerrariaInventory().getItem(21);
            }
        }
        return itemStack;
    }
}
