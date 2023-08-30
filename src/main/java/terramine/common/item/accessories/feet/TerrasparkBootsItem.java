package terramine.common.item.accessories.feet;

import be.florens.expandability.api.fabric.LivingFluidCollisionCallback;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.FluidState;
import terramine.TerraMine;
import terramine.common.init.ModComponents;
import terramine.common.init.ModItems;
import terramine.common.init.ModParticles;
import terramine.common.init.ModSoundEvents;
import terramine.common.item.accessories.AccessoryTerrariaItem;
import terramine.common.misc.AccessoriesHelper;
import terramine.common.utility.RocketBootHelper;

import java.util.UUID;

public class TerrasparkBootsItem extends AccessoryTerrariaItem {

	public static final AttributeModifier SPEED_BOOST_MODIFIER_WALK = new AttributeModifier(UUID.fromString("0bb62526-1cf9-4d7d-be51-ada9c7648422"),
			"terraspark_boots_movement_speed_walk", 0.08, AttributeModifier.Operation.MULTIPLY_TOTAL);
	public RocketBootHelper rocketHelper = new RocketBootHelper();
	public double speed = 0.4D;

	public TerrasparkBootsItem() {
		//noinspection UnstableApiUsage
		LivingFluidCollisionCallback.EVENT.register(TerrasparkBootsItem::onFluidCollision);
		rocketHelper.setSoundSettings(ModSoundEvents.SPECTRE_BOOTS, 1f, 1f);
		rocketHelper.setParticleSettings(ModParticles.GREEN_SPARK);
	}

	@Override
	public void curioTick(Player player, ItemStack stack) {
		rocketHelper.rocketFly(speed, 6, player);
	}

	@Override
	public void onEquip(ItemStack stack, Player player) {
		AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);

		if (movementSpeed == null) {
			TerraMine.LOGGER.debug("Entity {} missing entity attribute(s)", this);
			return;
		}

		addModifier(movementSpeed, SPEED_BOOST_MODIFIER_WALK);
	}

	@Override
	public void onUnequip(ItemStack stack, Player player) {
		AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);

		if (movementSpeed == null) {
			TerraMine.LOGGER.debug("Entity {} missing entity attribute(s)", this);
			return;
		}
		removeModifier(movementSpeed, SPEED_BOOST_MODIFIER_WALK);
	}

	@Override
	public void tick(ItemStack stack, Player player) {
		ModComponents.SWIM_ABILITIES.maybeGet(player).ifPresent(swimAbilities -> {
			if (player.isInWater()) {
				swimAbilities.setWet(true);
			} else if (player.isOnGround() || player.getAbilities().flying) {
				swimAbilities.setWet(false);
			}
		});

		super.tick(stack, player);
	}

	private static boolean onFluidCollision(LivingEntity entity, FluidState fluidState) {
		if (entity instanceof Player player) {
			return AccessoriesHelper.isEquipped(ModItems.TERRASPARK_BOOTS, player) && !player.isCrouching();
		}
		return false;
	}

	public static boolean canSprintOnWater(LivingEntity entity) { // determines when the water walking sound should play
		return ModComponents.SWIM_ABILITIES.maybeGet(entity)
				.map(swimAbilities -> entity.isSprinting()
						&& !entity.isUsingItem()
						&& !entity.isCrouching()
						&& !swimAbilities.isWet()
						&& !swimAbilities.isSwimming())
				.orElse(false);
	}
}

