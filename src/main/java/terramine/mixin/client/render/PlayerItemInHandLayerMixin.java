package terramine.mixin.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import terramine.common.item.accessories.ShieldAccessoryItem;
import terramine.extensions.PlayerStorages;

@Mixin(PlayerItemInHandLayer.class)
public class PlayerItemInHandLayerMixin {

    // todo: no clue if other players see correctly, check into this
    @ModifyVariable(method = "renderArmWithItem", at = @At("HEAD"), argsOnly = true)
    private ItemStack vanityArmor(ItemStack itemStack, LivingEntity livingEntity, ItemStack itemStack2, ItemTransforms.TransformType transformType, HumanoidArm humanoidArm, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        if (livingEntity instanceof Player player && humanoidArm.equals(HumanoidArm.LEFT) && (itemStack.getItem() instanceof ShieldItem || itemStack.getItem() instanceof ShieldAccessoryItem)) {
            if (((PlayerStorages)player).getTerrariaInventory().getItem(21) != ItemStack.EMPTY) {
                return ((PlayerStorages)player).getTerrariaInventory().getItem(21);
            }
        }
        return itemStack;
    }
}
