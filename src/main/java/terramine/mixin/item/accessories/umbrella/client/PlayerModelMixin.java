package terramine.mixin.item.accessories.umbrella.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import terramine.common.item.equipment.UmbrellaItem;
import terramine.extensions.EntityRenderStateExtensions;

@Mixin(PlayerModel.class)
public class PlayerModelMixin {

    @WrapOperation(
            method = "getArmPose(Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;Lnet/minecraft/world/entity/HumanoidArm;)Lnet/minecraft/client/model/HumanoidModel$ArmPose;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/player/PlayerRenderer;getArmPose(Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;Lnet/minecraft/world/entity/HumanoidArm;)Lnet/minecraft/client/model/HumanoidModel$ArmPose;")
    )
    private HumanoidModel.ArmPose renderUmbrella(PlayerRenderState playerRenderState, HumanoidArm humanoidArm, Operation<HumanoidModel.ArmPose> original) {
        if (((EntityRenderStateExtensions)playerRenderState).terrariaCraft$getLivingEntity() instanceof Player player) {
            boolean mainHandHeldUp = UmbrellaItem.getHeldStatusForHand(player, InteractionHand.MAIN_HAND) == UmbrellaItem.HeldStatus.HELD_UP;
            boolean offHandHeldUp = UmbrellaItem.getHeldStatusForHand(player, InteractionHand.OFF_HAND) == UmbrellaItem.HeldStatus.HELD_UP;
            boolean isRightHanded = playerRenderState.mainArm == HumanoidArm.RIGHT;

            boolean isRightArm = humanoidArm == HumanoidArm.RIGHT;
            boolean umbrellaMatchesRight = (mainHandHeldUp && isRightHanded) || (offHandHeldUp && !isRightHanded);
            boolean umbrellaMatchesLeft = (mainHandHeldUp && !isRightHanded) || (offHandHeldUp && isRightHanded);

            if ((isRightArm && umbrellaMatchesRight) || (!isRightArm && umbrellaMatchesLeft)) {
                return HumanoidModel.ArmPose.THROW_SPEAR;
            }
        }

        return original.call(playerRenderState, humanoidArm);
    }
}
