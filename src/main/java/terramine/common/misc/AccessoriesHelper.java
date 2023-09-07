package terramine.common.misc;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import terramine.extensions.PlayerStorages;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods for Accessories
 */
public final class AccessoriesHelper {

	public static boolean isEquipped(Item item, LivingEntity entity) {
		if (entity instanceof Player player) {
			return isEquipped(item, player, false);
		}
		return false;
	}

	public static boolean isEquipped(Item item, Player player) {
		return isEquipped(item, player, false);
	}

	public static boolean isEquipped(ItemStack itemStack, Player player) {
		return isEquipped(itemStack, player, false);
	}

	public static boolean isEquipped(Item item, Player player, boolean ignoreEffectsDisabled) {
		return isEquipped(item.getDefaultInstance(), player, ignoreEffectsDisabled);
	}

	public static boolean isEquipped(ItemStack itemStack, Player player, boolean ignoreEffectsDisabled) {
		TerrariaInventory inventory = ((PlayerStorages)player).getTerrariaInventory();
		if (!player.isCreative()) {
			for (int i = 0; i < 7; i++) {
				if (inventory.getItem(i).getItem() == itemStack.getItem()) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isInInventory(Item item, Player player) {
		return isInInventory(item.getDefaultInstance(), player);
	}

	public static boolean isInInventory(ItemStack itemStack, Player player) {
		return player.getInventory().contains(itemStack) || isEquipped(itemStack, player, false);
	}

	public static List<ItemStack> getAllEquipped(Player player) {
		return getAllEquipped(player, false);
	}

	public static List<ItemStack> getAllEquipped(Player player, boolean ignoreEffectsDisabled) {
		TerrariaInventory inventory = ((PlayerStorages)player).getTerrariaInventory();
		List<ItemStack> list = new ArrayList<>();
		if (!player.isCreative()) {
			for (int i = 0; i < 7; i++) {
				if (inventory.getItem(i) != ItemStack.EMPTY) {
					list.add(inventory.getItem(i));
				}
			}
		}
		return list;
	}
}
