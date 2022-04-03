package terracraft.mixin.item.terrasparkboots;

import terracraft.Artifacts;
import terracraft.common.init.Items;
import terracraft.common.init.ModBlocks;
import terracraft.common.item.curio.TrinketArtifactItem;
import terracraft.common.item.curio.feet.TerrasparkBootsItem;
import terracraft.common.trinkets.TrinketsHelper;
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

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	public LivingEntityMixin(EntityType<?> type, Level world) {
		super(type, world);
	}

	@Inject(method = "setSprinting", at = @At("TAIL"))
	private void onSetSprinting(boolean sprinting, CallbackInfo info) {
		LivingEntity self = (LivingEntity) (Object) this;

		if (!TrinketsHelper.isEquipped(Items.TERRASPARK_BOOTS, self)) {
			return;
		}

		AttributeInstance movementSpeed = self.getAttribute(Attributes.MOVEMENT_SPEED);
		AttributeInstance stepHeight = self.getAttribute(StepHeightEntityAttributeMain.STEP_HEIGHT);

		if (movementSpeed == null || stepHeight == null) {
			Artifacts.LOGGER.debug("Entity {} missing entity attribute(s)", this);
			return;
		}

		if (sprinting) {
			TrinketArtifactItem.addModifier(movementSpeed, TerrasparkBootsItem.SPEED_BOOST_MODIFIER);
			TrinketArtifactItem.addModifier(stepHeight, TerrasparkBootsItem.STEP_HEIGHT_MODIFIER);
		} else {
			TrinketArtifactItem.removeModifier(movementSpeed, TerrasparkBootsItem.SPEED_BOOST_MODIFIER);
			TrinketArtifactItem.removeModifier(stepHeight, TerrasparkBootsItem.STEP_HEIGHT_MODIFIER);
		}
	}

	@ModifyVariable(method = "travel", at = @At("STORE"), ordinal = 0)
	private float noIceSlip(float t, Vec3 vec3) {
		if (TrinketsHelper.isEquipped(Items.TERRASPARK_BOOTS, (LivingEntity) (Object) this)) {
			BlockPos blockPos = this.getBlockPosBelowThatAffectsMyMovement();
			Block block = this.level.getBlockState(blockPos).getBlock();
			if (block.equals(Blocks.ICE) || block.equals(Blocks.BLUE_ICE) || block.equals(Blocks.FROSTED_ICE) || block.equals(Blocks.PACKED_ICE) ||
					block.equals(ModBlocks.CORRUPTED_ICE) || block.equals(ModBlocks.CORRUPTED_BLUE_ICE) || block.equals(ModBlocks.CORRUPTED_PACKED_ICE)) {
				t = 0.6F;
			}
		}
		return t;
	}
}
