package terracraft.mixin.item.lightningboots;

import dev.emi.stepheightentityattribute.StepHeightEntityAttributeMain;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terracraft.TerraCraft;
import terracraft.common.init.ModItems;
import terracraft.common.item.curio.TrinketTerrariaItem;
import terracraft.common.item.curio.feet.LightningBootsItem;
import terracraft.common.trinkets.TrinketsHelper;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	@Inject(method = "setSprinting", at = @At("TAIL"))
	private void onSetSprinting(boolean sprinting, CallbackInfo info) {
		LivingEntity self = (LivingEntity) (Object) this;

		if (!TrinketsHelper.isEquipped(ModItems.LIGHTNING_BOOTS, self)) {
			return;
		}

		AttributeInstance movementSpeed = self.getAttribute(Attributes.MOVEMENT_SPEED);
		AttributeInstance stepHeight = self.getAttribute(StepHeightEntityAttributeMain.STEP_HEIGHT);

		if (movementSpeed == null || stepHeight == null) {
			TerraCraft.LOGGER.debug("Entity {} missing entity attribute(s)", this);
			return;
		}

		if (sprinting) {
			TrinketTerrariaItem.addModifier(movementSpeed, LightningBootsItem.SPEED_BOOST_MODIFIER);
			TrinketTerrariaItem.addModifier(stepHeight, LightningBootsItem.STEP_HEIGHT_MODIFIER);
		} else {
			TrinketTerrariaItem.removeModifier(movementSpeed, LightningBootsItem.SPEED_BOOST_MODIFIER);
			TrinketTerrariaItem.removeModifier(stepHeight, LightningBootsItem.STEP_HEIGHT_MODIFIER);
		}
	}
}
