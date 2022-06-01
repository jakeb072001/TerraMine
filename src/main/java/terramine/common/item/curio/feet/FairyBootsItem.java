package terramine.common.item.curio.feet;

import dev.emi.stepheightentityattribute.StepHeightEntityAttributeMain;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.phys.Vec3;
import terramine.TerraMine;
import terramine.common.init.ModItems;
import terramine.common.init.ModSoundEvents;
import terramine.common.item.curio.TrinketTerrariaItem;
import terramine.common.network.packet.BoneMealPacket;
import terramine.common.trinkets.TrinketsHelper;
import terramine.common.utility.RocketBootHelper;

import java.util.Random;
import java.util.UUID;

public class FairyBootsItem extends TrinketTerrariaItem {

	public static final AttributeModifier SPEED_BOOST_MODIFIER = new AttributeModifier(UUID.fromString("ac7ab816-2b08-46b6-879d-e5dea34ff305"),
			"terramine:spectre_boots_movement_speed", 0.2, AttributeModifier.Operation.MULTIPLY_TOTAL);
	public static final AttributeModifier STEP_HEIGHT_MODIFIER = new AttributeModifier(UUID.fromString("7e97cede-a343-411f-b465-14cdf6df3666"),
			"terramine:spectre_boots_step_height", .5, AttributeModifier.Operation.ADDITION);
	private static final Random RANDOM = new Random();
	public RocketBootHelper rocketHelper = new RocketBootHelper();
	public double speed = 0.08D;

	public FairyBootsItem() {
		rocketHelper.setSoundSettings(ModSoundEvents.SPECTRE_BOOTS, 1f, 1f);
		rocketHelper.setParticleSettings(TerraMine.BLUE_POOF, ParticleTypes.POOF);
	}

	@Override
	public void curioTick(LivingEntity player, ItemStack stack) {
		if (player != null && player.isSprinting()) {
			Level level = Minecraft.getInstance().level;
			BlockPos blockPos = player.getOnPos();
			BlockPos blockCropPos = player.getOnPos().offset(0,1.3,0);
			if (level.getBlockState(blockPos).getBlock() instanceof BonemealableBlock) {
				BoneMealPacket.send(blockPos);
			}
			if (level.getBlockState(blockCropPos).getBlock() instanceof BonemealableBlock) {
				BoneMealPacket.send(blockCropPos);
			}
		}
		if (TrinketsHelper.isEquipped(ModItems.FAIRY_BOOTS, player) && !TrinketsHelper.isEquipped(ModItems.SPECTRE_BOOTS, player) && !TrinketsHelper.isEquipped(ModItems.LIGHTNING_BOOTS, player) && !TrinketsHelper.isEquipped(ModItems.FROSTSPARK_BOOTS, player)  && !TrinketsHelper.isEquipped(ModItems.TERRASPARK_BOOTS, player)) {
			if (player.isSprinting() && player.isOnGround() && !player.isCrouching()) {
				float random = (RANDOM.nextFloat() - 0.5F) * 0.1F;
				if (player instanceof Player user && !user.isLocalPlayer()) {
					((ServerPlayer) user).getLevel().sendParticles(ParticleTypes.POOF, player.getX(), player.getY() + 0.2F, player.getZ(), 1, 0, 0, 0, random);
				}
			}
		}
		rocketHelper.rocketFly(speed, 3, player);
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
	public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
		AttributeInstance movementSpeed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
		AttributeInstance stepHeight = entity.getAttribute(StepHeightEntityAttributeMain.STEP_HEIGHT);

		if (movementSpeed == null || stepHeight == null) {
			TerraMine.LOGGER.debug("Entity {} missing entity attribute(s)", this);
			return;
		}

		removeModifier(movementSpeed, SPEED_BOOST_MODIFIER);
		removeModifier(stepHeight, STEP_HEIGHT_MODIFIER);
	}
}

