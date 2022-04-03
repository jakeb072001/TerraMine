package terracraft.common.item.curio.feet;

import terracraft.common.init.Components;
import terracraft.common.init.Items;
import terracraft.common.item.curio.TrinketArtifactItem;
import terracraft.common.trinkets.TrinketsHelper;
import be.florens.expandability.api.fabric.LivingFluidCollisionCallback;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.FluidState;

public class ObsidianWaterWalkingBootsItem extends TrinketArtifactItem {

	public ObsidianWaterWalkingBootsItem() {
		//noinspection UnstableApiUsage
        LivingFluidCollisionCallback.EVENT.register(ObsidianWaterWalkingBootsItem::onFluidCollision);
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
		if (TrinketsHelper.isEquipped(Items.OBSIDIAN_WATER_WALKING_BOOTS, entity) && !entity.isCrouching()) {
			if (fluidState.is(FluidTags.LAVA)) {
				return false;
			}
			return true;
		}

		return false;
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
