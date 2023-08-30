package terramine.common.misc;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import terramine.common.item.accessories.AccessoryTerrariaItem;
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
		for (int i = 0; i < 7; i++) {
			if (inventory.getItem(i).getItem() == itemStack.getItem()) {
				return true;
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
		for (int i = 0; i < 7; i++) {
			if (inventory.getItem(i) != ItemStack.EMPTY) {
				list.add(inventory.getItem(i));
			}
		}
		return list;
	}

	public static boolean areEffectsEnabled(ItemStack stack, Player player) {
		return AccessoryTerrariaItem.getTerraMineStatus(stack)
				.map(AccessoryTerrariaItem.terramineStatus::hasEffects)
				.orElse(false) && !player.isCreative() && !player.isSpectator();
	}

	public static boolean areCosmeticsEnabled(ItemStack stack) {
		return AccessoryTerrariaItem.getTerraMineStatus(stack)
				.map(AccessoryTerrariaItem.terramineStatus::hasCosmetics)
				.orElse(false);
	}

	//public static List<ItemStack> getAllEquippedForSlot(Player player, String groupId, String slotId) {
	//	return getAllEquippedForSlot(player, groupId, slotId, false);
	//}

	//public static List<ItemStack> getAllEquippedForSlot(Player player, String groupId, String slotId, boolean ignoreEffectsDisabled) {
	//	return TrinketsApi.getTrinketComponent(player)
	//			.map(TrinketComponent::getInventory)
	//			.flatMap(invByGroup -> Optional.ofNullable(invByGroup.get(groupId)))
	//			.flatMap(invBySlot -> Optional.ofNullable(invBySlot.get(slotId)))
	//			.stream()
	//			.flatMap(inv -> IntStream.range(0, inv.getContainerSize()).mapToObj(inv::getItem))
	//			.filter(stack -> stack.getItem() instanceof TrinketTerrariaItem && (areEffectsEnabled(stack, player) || ignoreEffectsDisabled))
	//			.collect(Collectors.toList());
	//}
}
