package terracraft.common.item.curio.feet;

import terracraft.Artifacts;
import terracraft.common.events.LivingEntityHurtCallback;
import terracraft.common.init.Components;
import terracraft.common.init.Items;
import terracraft.common.init.SoundEvents;
import terracraft.common.item.curio.TrinketArtifactItem;
import terracraft.common.trinkets.TrinketsHelper;
import be.florens.expandability.api.fabric.LivingFluidCollisionCallback;
import dev.emi.stepheightentityattribute.StepHeightEntityAttributeMain;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

import java.util.Random;
import java.util.UUID;

public class TerrasparkBootsItem extends TrinketArtifactItem {

	public static final AttributeModifier SPEED_BOOST_MODIFIER = new AttributeModifier(UUID.fromString("ac7ab816-2b08-46b6-879d-e5dea34ff305"),
			"artifacts:terraspark_boots_movement_speed", 0.28, AttributeModifier.Operation.MULTIPLY_TOTAL);
	public static final AttributeModifier SPEED_BOOST_MODIFIER_WALK = new AttributeModifier(UUID.fromString("ac7ab816-2b08-46b6-879d-e5dea34ff306"),
			"artifacts:terraspark_boots_movement_speed_walk", 0.08, AttributeModifier.Operation.MULTIPLY_TOTAL);
	public static final AttributeModifier STEP_HEIGHT_MODIFIER = new AttributeModifier(UUID.fromString("7e97cede-a343-411f-b465-14cdf6df3666"),
			"artifacts:terraspark_boots_step_height", .5, AttributeModifier.Operation.ADDITION);
	private static final Random RANDOM = new Random();
	public double speedVert = 0.08D;
	public double accelVert = 0.08D;
	public double speedSide = 0.03D;
	public double speedHover = 0.05D;
	public double speedHoverSlow = 0.03D;
	public double sprintSpeed = 1.0D;
	public int timer;
	public int soundTimer;
	public int rocketTime = 40;

	public TerrasparkBootsItem() {
		//noinspection UnstableApiUsage
		LivingFluidCollisionCallback.EVENT.register(TerrasparkBootsItem::onFluidCollision);
		LivingEntityHurtCallback.EVENT.register(TerrasparkBootsItem::onLivingHurt);
	}

	@Override
	public void curioTick(LivingEntity player, ItemStack stack) {
		if (TrinketsHelper.isEquipped(Items.TERRASPARK_BOOTS, player)) {
			Options settings = Minecraft.getInstance().options;
			Minecraft mc = Minecraft.getInstance();
			if (player.isSprinting() && player.isOnGround() && !player.isCrouching()) {
				float random = (RANDOM.nextFloat() - 0.5F) * 0.1F;
				mc.particleEngine.createParticle(ParticleTypes.POOF, player.getX(), player.getY() + 0.2F, player.getZ(), random, -0.2D, random);
			}
			if (player.isOnGround()) {
				if (timer > 0) {
					timer = 0;
					soundTimer = 0;
				}
			}
			if (settings != null && timer < rocketTime) {
				if (settings.keyJump.isDown()) {
					double hoverSpeed = settings.keyShift.isDown() ? this.speedHover : this.speedHoverSlow;
					double currentAccel = this.accelVert * (player.getDeltaMovement().y() < 0.3D ? 2.5D : 1.0D);
					double currentSpeedVertical = this.speedVert * (player.isUnderWater() ? 0.4D : 1.0D);
					float random = (RANDOM.nextFloat() - 0.5F) * 0.1F;
					timer += 1;
					soundTimer += 1;

					double motionY = player.getDeltaMovement().y();
					if (settings.keyJump.isDown()) {
						this.fly(player, Math.min(motionY + currentAccel, currentSpeedVertical));
						if (soundTimer >= 3) {
							player.level.playSound((Player) player, player.blockPosition(), SoundEvents.SPECTRE_BOOTS, SoundSource.PLAYERS, 5f, 1f);
							soundTimer = 0;
						}
						Vec3 vLeft = new Vec3(-0.15, -1.5, 0).xRot(0).yRot(mc.player.yBodyRot * -0.017453292F);
						Vec3 vRight = new Vec3(0.15, -1.5, 0).xRot(0).yRot(mc.player.yBodyRot * -0.017453292F);
						Vec3 playerPos = mc.player.getPosition(0).add(0, 1.5, 0);
						Vec3 v = playerPos.add(vLeft);
						mc.particleEngine.createParticle(Artifacts.GREEN_SPARK, v.x, v.y, v.z, random, -0.2D, random);
						v = playerPos.add(vRight);
						mc.particleEngine.createParticle(Artifacts.GREEN_SPARK, v.x, v.y, v.z, random, -0.2D, random);
					} else {
						//this.fly(player, Math.min(motionY + currentAccel, -hoverSpeed));
					}

					float speedSideways = (float) (player.isCrouching() ? this.speedSide * 0.5F : this.speedSide);
					float speedForward = (float) (player.isSprinting() ? speedSideways * this.sprintSpeed : speedSideways);

					if (settings.keyUp.isDown()) {
						player.moveRelative(1, new Vec3(0, 0, speedForward));
					}

					if (settings.keyDown.isDown()) {
						player.moveRelative(1, new Vec3(0, 0, -speedSideways * 0.8F));
					}

					if (settings.keyLeft.isDown()) {
						player.moveRelative(1, new Vec3(speedSideways, 0, 0));
					}

					if (settings.keyRight.isDown()) {
						player.moveRelative(1, new Vec3(-speedSideways, 0, 0));
					}

					if (!player.level.isClientSide()) {
						player.fallDistance = 0.0F;

						//if (player instanceof Player) {
						//	((ServerPlayerConnection) ((Player) player)).setFloatingTicks(0);
						//}
					}
				}
			}
		}
	}
	private void fly(LivingEntity player, double y) {
		Vec3 motion = player.getDeltaMovement();
		if (motion.y() >= 0)
		{
			player.moveRelative(1, new Vec3(0, y, 0));
		} else {
			player.moveRelative(1, new Vec3(0, -y + 1, 0));
		}
		//player.moveRelative(1, new Vec3(motion.x(), y, motion.z()));
	}

	@Override
	public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
		AttributeInstance movementSpeed = entity.getAttribute(Attributes.MOVEMENT_SPEED);

		if (movementSpeed == null) {
			Artifacts.LOGGER.debug("Entity {} missing entity attribute(s)", this);
			return;
		}

		addModifier(movementSpeed, SPEED_BOOST_MODIFIER_WALK);
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
		removeModifier(movementSpeed, SPEED_BOOST_MODIFIER_WALK);
		removeModifier(stepHeight, STEP_HEIGHT_MODIFIER);
	}

	@Override
	public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
		Components.SWIM_ABILITIES.maybeGet(entity).ifPresent(swimAbilities -> {
			if (entity.isInWater()) {
				swimAbilities.setWet(true);
			} else if (entity.isOnGround() || (entity instanceof Player player && player.getAbilities().flying)) {
				swimAbilities.setWet(false);
			}
		});

		super.tick(stack, slot, entity);
	}

	private static boolean onFluidCollision(LivingEntity entity, FluidState fluidState) {
		if (TrinketsHelper.isEquipped(Items.TERRASPARK_BOOTS, entity) && !entity.isCrouching()) {
			if (fluidState.is(FluidTags.LAVA) && !entity.fireImmune() && !EnchantmentHelper.hasFrostWalker(entity)) {
				//entity.hurt(DamageSource.HOT_FLOOR, 1);
			}
			return true;
		}

		return false;
	}

	private static void onLivingHurt(LivingEntity user, DamageSource source, float amount) {
		if (!user.level.isClientSide && amount >= 1 && user instanceof Player player && TrinketsHelper.isEquipped(Items.TERRASPARK_BOOTS, user)) {
			if (user.isInLava() && !player.getCooldowns().isOnCooldown(Items.TERRASPARK_BOOTS)) {
				user.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 140, 0, false, true));
				((Player) user).getCooldowns().addCooldown(Items.TERRASPARK_BOOTS, 450);
			}
		}
	}

	public static boolean canSprintOnWater(LivingEntity entity) {
		return Components.SWIM_ABILITIES.maybeGet(entity)
				.map(swimAbilities -> entity.isSprinting()
						&& entity.fallDistance < 6
						&& !entity.isUsingItem()
						&& !entity.isCrouching()
						&& !swimAbilities.isWet()
						&& !swimAbilities.isSwimming())
				.orElse(false);
	}
}

