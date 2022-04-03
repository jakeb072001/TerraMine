package terracraft.mixin.item.lightningboots;

import terracraft.Artifacts;
import terracraft.common.init.Items;
import terracraft.common.item.curio.TrinketArtifactItem;
import terracraft.common.item.curio.feet.LightningBootsItem;
import terracraft.common.trinkets.TrinketsHelper;
import dev.emi.stepheightentityattribute.StepHeightEntityAttributeMain;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	@Inject(method = "setSprinting", at = @At("TAIL"))
	private void onSetSprinting(boolean sprinting, CallbackInfo info) {
		LivingEntity self = (LivingEntity) (Object) this;

		if (!TrinketsHelper.isEquipped(Items.LIGHTNING_BOOTS, self)) {
			return;
		}

		AttributeInstance movementSpeed = self.getAttribute(Attributes.MOVEMENT_SPEED);
		AttributeInstance stepHeight = self.getAttribute(StepHeightEntityAttributeMain.STEP_HEIGHT);

		if (movementSpeed == null || stepHeight == null) {
			Artifacts.LOGGER.debug("Entity {} missing entity attribute(s)", this);
			return;
		}

		if (sprinting) {
			TrinketArtifactItem.addModifier(movementSpeed, LightningBootsItem.SPEED_BOOST_MODIFIER);
			TrinketArtifactItem.addModifier(stepHeight, LightningBootsItem.STEP_HEIGHT_MODIFIER);
		} else {
			TrinketArtifactItem.removeModifier(movementSpeed, LightningBootsItem.SPEED_BOOST_MODIFIER);
			TrinketArtifactItem.removeModifier(stepHeight, LightningBootsItem.STEP_HEIGHT_MODIFIER);
		}
	}
}
