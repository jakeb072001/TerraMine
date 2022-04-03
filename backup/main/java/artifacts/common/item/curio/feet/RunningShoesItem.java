package terracraft.common.item.curio.feet;

import terracraft.Artifacts;
import terracraft.common.item.curio.TrinketArtifactItem;
import dev.emi.stepheightentityattribute.StepHeightEntityAttributeMain;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.Random;
import java.util.UUID;

public class RunningShoesItem extends TrinketArtifactItem {

	public static final AttributeModifier SPEED_BOOST_MODIFIER = new AttributeModifier(UUID.fromString("ac7ab816-2b08-46b6-879d-e5dea34ff305"),
			"artifacts:running_shoes_movement_speed", 0.2, AttributeModifier.Operation.MULTIPLY_TOTAL);
	public static final AttributeModifier STEP_HEIGHT_MODIFIER = new AttributeModifier(UUID.fromString("7e97cede-a343-411f-b465-14cdf6df3666"),
			"artifacts:running_shoes_step_height", .5, AttributeModifier.Operation.ADDITION);
	private static final Random RANDOM = new Random();
	@Override
	public void curioTick(LivingEntity player, ItemStack stack) {
		if (player.isSprinting() && player.isOnGround() && !player.isCrouching())
		{
			Minecraft mc = Minecraft.getInstance();
			float random = (RANDOM.nextFloat() - 0.5F) * 0.1F;
			mc.particleEngine.createParticle(ParticleTypes.POOF, player.getX(), player.getY() + 0.2F, player.getZ(), random, -0.2D, random);
		}
	}

    @Override
	public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
		AttributeInstance movementSpeed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
		AttributeInstance stepHeight = entity.getAttribute(StepHeightEntityAttributeMain.STEP_HEIGHT);

		if (movementSpeed == null || stepHeight == null) {
			Artifacts.LOGGER.debug("Entity {} missing entity attribute(s)", this);
			return;
		}

		removeModifier(movementSpeed, SPEED_BOOST_MODIFIER);
		removeModifier(stepHeight, STEP_HEIGHT_MODIFIER);
	}
}
