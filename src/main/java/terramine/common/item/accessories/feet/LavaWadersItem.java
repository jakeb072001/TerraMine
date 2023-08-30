package terramine.common.item.accessories.feet;

import be.florens.expandability.api.fabric.LivingFluidCollisionCallback;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.FluidState;
import terramine.common.init.ModComponents;
import terramine.common.init.ModItems;
import terramine.common.item.accessories.AccessoryTerrariaItem;
import terramine.common.misc.AccessoriesHelper;

public class LavaWadersItem extends AccessoryTerrariaItem {

	public LavaWadersItem() {
		//noinspection UnstableApiUsage
        LivingFluidCollisionCallback.EVENT.register(LavaWadersItem::onFluidCollision);
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
			return AccessoriesHelper.isEquipped(ModItems.LAVA_WADERS, player) && !entity.isCrouching();
		}
		return false;
	}
}
