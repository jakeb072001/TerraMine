package terramine.mixin.item.terrasparkboots;

import dev.emi.stepheightentityattribute.StepHeightEntityAttributeMain;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.TerraMine;
import terramine.common.init.ModBlocks;
import terramine.common.init.ModItems;
import terramine.common.item.curio.TrinketTerrariaItem;
import terramine.common.item.curio.feet.TerrasparkBootsItem;
import terramine.common.trinkets.TrinketsHelper;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	public LivingEntityMixin(EntityType<?> type, Level world) {
		super(type, world);
	}

	@Inject(method = "setSprinting", at = @At("TAIL"))
	private void onSetSprinting(boolean sprinting, CallbackInfo info) {
		LivingEntity self = (LivingEntity) (Object) this;

		if (!TrinketsHelper.isEquipped(ModItems.TERRASPARK_BOOTS, self)) {
			return;
		}

		AttributeInstance movementSpeed = self.getAttribute(Attributes.MOVEMENT_SPEED);
		AttributeInstance stepHeight = self.getAttribute(StepHeightEntityAttributeMain.STEP_HEIGHT);

		if (movementSpeed == null || stepHeight == null) {
			TerraMine.LOGGER.debug("Entity {} missing entity attribute(s)", this);
			return;
		}

		if (sprinting) {
			TrinketTerrariaItem.addModifier(movementSpeed, TerrasparkBootsItem.SPEED_BOOST_MODIFIER);
			TrinketTerrariaItem.addModifier(stepHeight, TerrasparkBootsItem.STEP_HEIGHT_MODIFIER);
		} else {
			TrinketTerrariaItem.removeModifier(movementSpeed, TerrasparkBootsItem.SPEED_BOOST_MODIFIER);
			TrinketTerrariaItem.removeModifier(stepHeight, TerrasparkBootsItem.STEP_HEIGHT_MODIFIER);
		}
	}

	@ModifyVariable(method = "travel", at = @At("STORE"), ordinal = 0)
	private float noIceSlip(float t, Vec3 vec3) {
		if (TrinketsHelper.isEquipped(ModItems.TERRASPARK_BOOTS, (LivingEntity) (Object) this)) {
			BlockPos blockPos = this.getBlockPosBelowThatAffectsMyMovement();
			Block block = this.level.getBlockState(blockPos).getBlock();
			if (block.equals(Blocks.ICE) || block.equals(Blocks.BLUE_ICE) || block.equals(Blocks.FROSTED_ICE) || block.equals(Blocks.PACKED_ICE) ||
					block.equals(ModBlocks.CORRUPTED_ICE) || block.equals(ModBlocks.CORRUPTED_BLUE_ICE) || block.equals(ModBlocks.CORRUPTED_PACKED_ICE) || block.equals(ModBlocks.FROZEN_CHEST)) {
				t = 0.6F;
			}
		}
		return t;
	}
}
