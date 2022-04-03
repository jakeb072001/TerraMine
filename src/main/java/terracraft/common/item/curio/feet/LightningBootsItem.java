package terracraft.common.item.curio.feet;

import dev.emi.stepheightentityattribute.StepHeightEntityAttributeMain;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import terracraft.TerraCraft;
import terracraft.common.init.ModItems;
import terracraft.common.init.ModSoundEvents;
import terracraft.common.item.curio.TrinketTerrariaItem;
import terracraft.common.trinkets.TrinketsHelper;

import java.util.Random;
import java.util.UUID;

public class LightningBootsItem extends TrinketTerrariaItem {

	public static final AttributeModifier SPEED_BOOST_MODIFIER = new AttributeModifier(UUID.fromString("ac7ab816-2b08-46b6-879d-e5dea34ff305"),
			"terracraft:lightning_boots_movement_speed", 0.28, AttributeModifier.Operation.MULTIPLY_TOTAL);
	public static final AttributeModifier SPEED_BOOST_MODIFIER_WALK = new AttributeModifier(UUID.fromString("ac7ab816-2b08-46b6-879d-e5dea34ff306"),
			"terracraft:lightning_boots_movement_speed_walk", 0.08, AttributeModifier.Operation.MULTIPLY_TOTAL);
	public static final AttributeModifier STEP_HEIGHT_MODIFIER = new AttributeModifier(UUID.fromString("7e97cede-a343-411f-b465-14cdf6df3666"),
			"terracraft:lightning_boots_step_height", .5, AttributeModifier.Operation.ADDITION);
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

	@Override
	public void curioTick(LivingEntity player, ItemStack stack) {
		if (TrinketsHelper.isEquipped(ModItems.LIGHTNING_BOOTS, player) && !TrinketsHelper.isEquipped(ModItems.FROSTSPARK_BOOTS, player) && !TrinketsHelper.isEquipped(ModItems.TERRASPARK_BOOTS, player)) {
			Options settings = Minecraft.getInstance().options;
			Minecraft mc = Minecraft.getInstance();
			if (player.isSprinting() && player.isOnGround() && !player.isCrouching()) {
				float random = (RANDOM.nextFloat() - 0.5F) * 0.1F;
				mc.particleEngine.createParticle(ParticleTypes.POOF, player.getX(), player.getY() + 0.2F, player.getZ(), random, -0.2D, random);
			}
			if (player.isOnGround()) {
				if (timer >= 10) {
					((Player) player).getCooldowns().addCooldown(ModItems.LIGHTNING_BOOTS, 30);
				}
				if (timer > 0) {
					timer = 0;
					soundTimer = 0;
				}
			}
			if (settings != null && timer < rocketTime && player instanceof Player user && !user.getCooldowns().isOnCooldown(ModItems.LIGHTNING_BOOTS)) {
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
							player.level.playSound((Player) player, player.blockPosition(), ModSoundEvents.SPECTRE_BOOTS, SoundSource.PLAYERS, 5f, 1f);
							soundTimer = 0;
						}
						Vec3 vLeft = new Vec3(-0.15, -1.5, 0).xRot(0).yRot(mc.player.yBodyRot * -0.017453292F);
						Vec3 vRight = new Vec3(0.15, -1.5, 0).xRot(0).yRot(mc.player.yBodyRot * -0.017453292F);
						Vec3 playerPos = mc.player.getPosition(0).add(0, 1.5, 0);
						Vec3 v = playerPos.add(vLeft);
						mc.particleEngine.createParticle(TerraCraft.BLUE_POOF, v.x, v.y, v.z, random, -0.2D, random);
						mc.particleEngine.createParticle(ParticleTypes.POOF, v.x, v.y, v.z, random, -0.2D, random);
						v = playerPos.add(vRight);
						mc.particleEngine.createParticle(TerraCraft.BLUE_POOF, v.x, v.y, v.z, random, -0.2D, random);
						mc.particleEngine.createParticle(ParticleTypes.POOF, v.x, v.y, v.z, random, -0.2D, random);
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
			TerraCraft.LOGGER.debug("Entity {} missing entity attribute(s)", this);
			return;
		}

		addModifier(movementSpeed, SPEED_BOOST_MODIFIER_WALK);
	}

	@Override
	public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
		AttributeInstance movementSpeed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
		AttributeInstance stepHeight = entity.getAttribute(StepHeightEntityAttributeMain.STEP_HEIGHT);

		if (movementSpeed == null || stepHeight == null) {
			TerraCraft.LOGGER.debug("Entity {} missing entity attribute(s)", this);
			return;
		}

		removeModifier(movementSpeed, SPEED_BOOST_MODIFIER);
		removeModifier(movementSpeed, SPEED_BOOST_MODIFIER_WALK);
		removeModifier(stepHeight, STEP_HEIGHT_MODIFIER);
	}
}

