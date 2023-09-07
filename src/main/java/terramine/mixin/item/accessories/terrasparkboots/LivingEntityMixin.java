package terramine.mixin.item.accessories.terrasparkboots;

import dev.emi.stepheightentityattribute.StepHeightEntityAttributeMain;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.TerraMine;
import terramine.common.init.ModBlocks;
import terramine.common.init.ModItems;
import terramine.common.item.accessories.AccessoryTerrariaItem;
import terramine.common.misc.AccessoriesHelper;

import java.util.UUID;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	@Unique
	private static final AttributeModifier SPEED_BOOST_MODIFIER = new AttributeModifier(UUID.fromString("ac7ab816-2b08-46b6-879d-e5dea34ff305"),
			"speed_boost", 0.28, AttributeModifier.Operation.MULTIPLY_TOTAL);
	@Unique
	private static final AttributeModifier STEP_HEIGHT_MODIFIER = new AttributeModifier(UUID.fromString("7e97cede-a343-411f-b465-14cdf6df3666"),
			"step_height", 0.5, AttributeModifier.Operation.ADDITION);
	@Unique
	private static final AttributeModifier ICE_SPEED_BOOST_MODIFIER = new AttributeModifier(UUID.fromString("c5051561-0943-402e-9a36-b3cff979cbdc"),
			"ice_speed_boost", 0.25, AttributeModifier.Operation.MULTIPLY_TOTAL);

	public LivingEntityMixin(EntityType<?> type, Level world) {
		super(type, world);
	}

	@Inject(method = "setSprinting", at = @At("TAIL"))
	private void onSetSprinting(boolean sprinting, CallbackInfo info) {
		LivingEntity self = (LivingEntity) (Object) this;

		if (!runningShoesEquipped(self)) {
			return;
		}

		AttributeInstance movementSpeed = self.getAttribute(Attributes.MOVEMENT_SPEED);
		AttributeInstance stepHeight = self.getAttribute(StepHeightEntityAttributeMain.STEP_HEIGHT);

		if (movementSpeed == null || stepHeight == null) {
			TerraMine.LOGGER.debug("Entity {} missing entity attribute(s)", this);
			return;
		}

		if (sprinting) {
			AccessoryTerrariaItem.addModifier(movementSpeed, SPEED_BOOST_MODIFIER);
			AccessoryTerrariaItem.addModifier(stepHeight, STEP_HEIGHT_MODIFIER);
		} else {
			AccessoryTerrariaItem.removeModifier(movementSpeed, SPEED_BOOST_MODIFIER);
			AccessoryTerrariaItem.removeModifier(stepHeight, STEP_HEIGHT_MODIFIER);
		}
	}

	@ModifyVariable(method = "travel", at = @At("STORE"), ordinal = 0)
	private float noIceSlip(float t, Vec3 vec3) {
		LivingEntity self = (LivingEntity) (Object) this;
		AttributeInstance movementSpeed = self.getAttribute(Attributes.MOVEMENT_SPEED);

		if (movementSpeed == null) {
			TerraMine.LOGGER.debug("Entity {} missing entity attribute(s)", this);
			return t;
		}

		if (iceSkatesEquipped(self)) {
			if (isIceBlock()) {
				AccessoryTerrariaItem.addModifier(movementSpeed, ICE_SPEED_BOOST_MODIFIER);
				t = 0.6F; // default friction on regular blocks
			} else {
				AccessoryTerrariaItem.removeModifier(movementSpeed, ICE_SPEED_BOOST_MODIFIER);
			}
		} else {
			AccessoryTerrariaItem.removeModifier(movementSpeed, ICE_SPEED_BOOST_MODIFIER);
		}
		return t;
	}

	private boolean runningShoesEquipped(LivingEntity self) {
		return AccessoriesHelper.isEquipped(ModItems.HERMES_BOOTS, self) || AccessoriesHelper.isEquipped(ModItems.SPECTRE_BOOTS, self)
				|| AccessoriesHelper.isEquipped(ModItems.FAIRY_BOOTS, self) || AccessoriesHelper.isEquipped(ModItems.LIGHTNING_BOOTS, self)
				|| AccessoriesHelper.isEquipped(ModItems.FROSTSPARK_BOOTS, self) || AccessoriesHelper.isEquipped(ModItems.TERRASPARK_BOOTS, self);
	}

	private boolean iceSkatesEquipped(LivingEntity self) {
		return AccessoriesHelper.isEquipped(ModItems.ICE_SKATES, self) || AccessoriesHelper.isEquipped(ModItems.FROSTSPARK_BOOTS, self)
				|| AccessoriesHelper.isEquipped(ModItems.TERRASPARK_BOOTS, self);
	}

	private boolean isIceBlock() {
		BlockPos blockPos = this.getBlockPosBelowThatAffectsMyMovement();
		Block block = this.level.getBlockState(blockPos).getBlock();
		return block.equals(Blocks.ICE) || block.equals(Blocks.BLUE_ICE) || block.equals(Blocks.FROSTED_ICE) || block.equals(Blocks.PACKED_ICE) ||
				block.equals(ModBlocks.CORRUPTED_ICE) || block.equals(ModBlocks.CORRUPTED_BLUE_ICE) || block.equals(ModBlocks.CORRUPTED_PACKED_ICE) ||
				block.equals(ModBlocks.CRIMSON_ICE) || block.equals(ModBlocks.CRIMSON_BLUE_ICE) || block.equals(ModBlocks.CRIMSON_PACKED_ICE) ||
				block.equals(ModBlocks.FROZEN_CHEST) || block.equals(ModBlocks.TRAPPED_FROZEN_CHEST);
	}
}
