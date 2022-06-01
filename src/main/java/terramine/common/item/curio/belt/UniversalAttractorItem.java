package terramine.common.item.curio.belt;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import terramine.common.init.ModComponents;
import terramine.common.item.curio.TrinketTerrariaItem;

import java.util.List;

public class UniversalAttractorItem extends TrinketTerrariaItem {

    @Override
	// Magnet logic from Botania, see https://github.com/Vazkii/Botania
	protected void curioTick(LivingEntity livingEntity, ItemStack stack) {
		Vec3 playerPos = livingEntity.position().add(0, 0.75, 0);
		AABB itemRange = new AABB(playerPos, playerPos).inflate(5);
		List<ItemEntity> items = livingEntity.level.getEntitiesOfClass(ItemEntity.class, itemRange);

		int pulled = 0;
		for (ItemEntity item : items) {
			boolean attractable = ModComponents.DROPPED_ITEM_ENTITY.maybeGet(item)
					.map(dropped -> !dropped.get() || item.getAge() > 100)
					.orElse(false);
			if (attractable && item.isAlive() && !item.hasPickUpDelay()) {
				if (pulled++ > 200) {
					break;
				}

				Vec3 motion = playerPos.subtract(item.position().add(0, item.getBbHeight() / 2, 0));
				if (Math.sqrt(motion.x * motion.x + motion.y * motion.y + motion.z * motion.z) > 1) {
					motion = motion.normalize();
				}
				item.setDeltaMovement(motion.scale(0.6));
			}
		}
	}
}
