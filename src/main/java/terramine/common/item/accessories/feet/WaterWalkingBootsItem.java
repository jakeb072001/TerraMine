package terramine.common.item.accessories.feet;

import be.florens.expandability.api.fabric.LivingFluidCollisionCallback;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.FluidState;
import terramine.common.init.ModComponents;
import terramine.common.init.ModItems;
import terramine.common.item.accessories.AccessoryTerrariaItem;
import terramine.common.misc.AccessoriesHelper;

public class WaterWalkingBootsItem extends AccessoryTerrariaItem {

	public WaterWalkingBootsItem() {
		//noinspection UnstableApiUsage
        LivingFluidCollisionCallback.EVENT.register(WaterWalkingBootsItem::onFluidCollision);
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
		if (AccessoriesHelper.isEquipped(ModItems.WATER_WALKING_BOOTS, entity) && !entity.isCrouching()) {
			return !fluidState.is(FluidTags.LAVA);
		}

		return false;
	}
}
