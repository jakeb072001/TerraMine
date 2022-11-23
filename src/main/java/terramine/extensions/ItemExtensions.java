package terramine.extensions;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface ItemExtensions {

	/**
	 * @param stack The item that the entity is swinging
	 * @param entity The entity that is swinging
	 * @return if true, stops from swinging
	 */
	boolean onEntitySwing(ItemStack stack, LivingEntity entity);

	/**
	 * @param stack The item that the entity is swinging
	 * @param shield The shield item that is being hit
	 * @param entity The entity that is being attacked
	 * @param attacker The entity that is attacking
	 * @return if true, item can disable shields
	 */
	boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker);
}
