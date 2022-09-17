package terramine.common.item.curio.feet;

import dev.emi.trinkets.api.SlotReference;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import terramine.TerraMine;
import terramine.common.init.ModParticles;
import terramine.common.init.ModSoundEvents;
import terramine.common.item.curio.TrinketTerrariaItem;
import terramine.common.utility.RocketBootHelper;

import java.util.UUID;

public class LightningBootsItem extends TrinketTerrariaItem {

	public static final AttributeModifier SPEED_BOOST_MODIFIER_WALK = new AttributeModifier(UUID.fromString("0bb62526-1cf9-4d7d-be51-ada9c7648422"),
			"lightning_boots_movement_speed_walk", 0.08, AttributeModifier.Operation.MULTIPLY_TOTAL);
	public RocketBootHelper rocketHelper = new RocketBootHelper();
	public double speed = 0.4D;

	public LightningBootsItem() {
		rocketHelper.setSoundSettings(ModSoundEvents.SPECTRE_BOOTS, 1f, 1f);
		rocketHelper.setParticleSettings(ModParticles.BLUE_POOF, ParticleTypes.POOF);
	}

	@Override
	public void curioTick(LivingEntity player, ItemStack stack) {
		rocketHelper.rocketFly(speed, 4, player);
	}

	@Override
	public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
		AttributeInstance movementSpeed = entity.getAttribute(Attributes.MOVEMENT_SPEED);

		if (movementSpeed == null) {
			TerraMine.LOGGER.debug("Entity {} missing entity attribute(s)", this);
			return;
		}

		addModifier(movementSpeed, SPEED_BOOST_MODIFIER_WALK);
	}

	@Override
	public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
		AttributeInstance movementSpeed = entity.getAttribute(Attributes.MOVEMENT_SPEED);

		if (movementSpeed == null) {
			TerraMine.LOGGER.debug("Entity {} missing entity attribute(s)", this);
			return;
		}
		removeModifier(movementSpeed, SPEED_BOOST_MODIFIER_WALK);
	}
}

