package terracraft.extensions;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface AnimalExtensions {

	boolean terracraft$isBreedingItemWithCooldown(ItemStack stack, Player player);
}
