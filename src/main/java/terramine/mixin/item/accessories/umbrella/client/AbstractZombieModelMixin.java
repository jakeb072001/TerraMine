package terramine.mixin.item.accessories.umbrella.client;

import net.minecraft.client.model.AbstractZombieModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.common.item.equipment.UmbrellaItem;
import terramine.extensions.EntityRenderStateExtensions;

@Mixin(AbstractZombieModel.class)
public class AbstractZombieModelMixin<S extends ZombieRenderState> extends HumanoidModel<S> {

    public AbstractZombieModelMixin(ModelPart modelPart) {
        super(modelPart);
    }

    @Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/ZombieRenderState;)V", at = @At("TAIL"))
    private void afterAnimateZombieArms(S zombieRenderState, CallbackInfo ci) {
        if (((EntityRenderStateExtensions)zombieRenderState).terrariaCraft$getLivingEntity() instanceof LivingEntity livingEntity) {
            boolean mainHandHeldUp = UmbrellaItem.getHeldStatusForHand(livingEntity, InteractionHand.MAIN_HAND) == UmbrellaItem.HeldStatus.HELD_UP;
            boolean offHandHeldUp = UmbrellaItem.getHeldStatusForHand(livingEntity, InteractionHand.OFF_HAND) == UmbrellaItem.HeldStatus.HELD_UP;
            boolean rightHanded = zombieRenderState.mainArm == HumanoidArm.RIGHT;

            if ((mainHandHeldUp && rightHanded) || (offHandHeldUp && !rightHanded)) {
                this.rightArm.xRot = this.rightArm.xRot * 0.5F - 2.45F;
                this.rightArm.yRot = 0.0F;
            }

            if ((mainHandHeldUp && !rightHanded) || (offHandHeldUp && rightHanded)) {
                this.leftArm.xRot = this.leftArm.xRot * 0.5F - 2.45F;
                this.leftArm.yRot = 0.0F;
            }
        }
    }
}
