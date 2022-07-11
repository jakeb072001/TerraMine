package terramine.common.item.curio.feet;

import dev.emi.stepheightentityattribute.StepHeightEntityAttributeMain;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import terramine.TerraMine;
import terramine.common.init.ModItems;
import terramine.common.init.ModSoundEvents;
import terramine.common.item.curio.TrinketTerrariaItem;
import terramine.common.trinkets.TrinketsHelper;
import terramine.common.utility.RocketBootHelper;

import java.util.UUID;

public class FrostsparkBootsItem extends TrinketTerrariaItem {

	public static final AttributeModifier SPEED_BOOST_MODIFIER = new AttributeModifier(UUID.fromString("ac7ab816-2b08-46b6-879d-e5dea34ff305"),
			"terramine:frostspark_boots_movement_speed", 0.28, AttributeModifier.Operation.MULTIPLY_TOTAL);
	public static final AttributeModifier SPEED_BOOST_MODIFIER_WALK = new AttributeModifier(UUID.fromString("ac7ab816-2b08-46b6-879d-e5dea34ff306"),
			"terramine:frostspark_boots_movement_speed_walk", 0.08, AttributeModifier.Operation.MULTIPLY_TOTAL);
	public static final AttributeModifier STEP_HEIGHT_MODIFIER = new AttributeModifier(UUID.fromString("7e97cede-a343-411f-b465-14cdf6df3666"),
			"terramine:frostspark_boots_step_height", .5, AttributeModifier.Operation.ADDITION);
	private static final RandomSource RANDOM = RandomSource.create();
	public RocketBootHelper rocketHelper = new RocketBootHelper();
	public double speed = 0.4D;

	public FrostsparkBootsItem() {
		rocketHelper.setSoundSettings(ModSoundEvents.SPECTRE_BOOTS, 1f, 1f);
		rocketHelper.setParticleSettings(ParticleTypes.POOF);
	}

	@Override
	public void curioTick(LivingEntity player, ItemStack stack) {
		if (TrinketsHelper.isEquipped(ModItems.FROSTSPARK_BOOTS, player) && !TrinketsHelper.isEquipped(ModItems.TERRASPARK_BOOTS, player)) {
			if (player.isSprinting() && player.isOnGround() && !player.isCrouching()) {
				float random = (RANDOM.nextFloat() - 0.5F) * 0.1F;
				if (player instanceof Player user && !user.isLocalPlayer()) {
					((ServerPlayer) user).getLevel().sendParticles(ParticleTypes.POOF, player.getX(), player.getY() + 0.2F, player.getZ(), 1, 0, 0, 0, random);
				}
			}
		}
		rocketHelper.rocketFly(speed, 5, player);
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
		AttributeInstance stepHeight = entity.getAttribute(StepHeightEntityAttributeMain.STEP_HEIGHT);

		if (movementSpeed == null || stepHeight == null) {
			TerraMine.LOGGER.debug("Entity {} missing entity attribute(s)", this);
			return;
		}

		removeModifier(movementSpeed, SPEED_BOOST_MODIFIER);
		removeModifier(movementSpeed, SPEED_BOOST_MODIFIER_WALK);
		removeModifier(stepHeight, STEP_HEIGHT_MODIFIER);
	}
}

