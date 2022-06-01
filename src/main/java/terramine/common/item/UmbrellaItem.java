package terramine.common.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import terramine.common.init.ModItems;

public class UmbrellaItem extends TerrariaItem {

	public UmbrellaItem() {
		DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
	}

    /* TODO: wait for fapi/lib
    @Override
    public boolean isShield(ItemStack stack, LivingEntity entity) {
        return true;
    }*/

	public static HeldStatus getHeldStatusForHand(LivingEntity entity, InteractionHand hand) {
		if (entity.getItemInHand(hand).getItem() != ModItems.UMBRELLA) {
			return HeldStatus.NONE;
		}

		if (entity.isUsingItem() && entity.getUsedItemHand() == hand && !entity.getUseItem().isEmpty()
				&& entity.getUseItem().getUseAnimation() == UseAnim.BLOCK) {
			return HeldStatus.BLOCKING;
		}

		return HeldStatus.HELD_UP;
	}

	public static boolean isHeldUpInEitherHand(LivingEntity entity) {
		for (InteractionHand hand : InteractionHand.values()) {
			if (getHeldStatusForHand(entity, hand) == HeldStatus.HELD_UP) {
				return true;
			}
		}

		return false;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.BLOCK;
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 72000;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		player.startUsingItem(hand);
		return InteractionResultHolder.consume(itemstack);
	}

	public enum HeldStatus {
		NONE,
		HELD_UP,
		BLOCKING
	}
}
