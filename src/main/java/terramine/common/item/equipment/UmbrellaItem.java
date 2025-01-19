package terramine.common.item.equipment;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import terramine.client.render.HeldItemModels;
import terramine.common.init.ModItems;
import terramine.common.item.TerrariaItem;

public class UmbrellaItem extends TerrariaItem {

	public UmbrellaItem(ResourceKey<Item> key) {
		super(new Properties().setId(key).stacksTo(1).rarity(Rarity.RARE).fireResistant().overrideModel(HeldItemModels.UMBRELLA_HELD_MODEL), false);
	}

	public static HeldStatus getHeldStatusForHand(LivingEntity entity, InteractionHand hand) {
		if (entity.getItemInHand(hand).getItem() != ModItems.UMBRELLA) {
			return HeldStatus.NONE;
		}

		if (entity.isUsingItem() && entity.getUsedItemHand() == hand && !entity.getUseItem().isEmpty()
				&& entity.getUseItem().getUseAnimation() == ItemUseAnimation.BLOCK) {
			return HeldStatus.BLOCKING;
		}

		return HeldStatus.HELD_UP;
	}

	public static HeldStatus getHeldStatusForHand(ItemStack itemStack, boolean isUsingItem, InteractionHand usedItemHand, InteractionHand hand) {
		if (itemStack.getItem() != ModItems.UMBRELLA) {
			return HeldStatus.NONE;
		}

		if (isUsingItem && usedItemHand == hand && !itemStack.isEmpty()
				&& itemStack.getUseAnimation() == ItemUseAnimation.BLOCK) {
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
	public @NotNull ItemUseAnimation getUseAnimation(@NotNull ItemStack stack) {
		return ItemUseAnimation.BLOCK;
	}

	@Override
	public int getUseDuration(ItemStack itemStack, LivingEntity livingEntity) {
		return 72000;
	}

	@Override
	public InteractionResult use(@NotNull Level world, Player player, @NotNull InteractionHand hand) {
		player.startUsingItem(hand);
		return InteractionResult.CONSUME;
	}

	public enum HeldStatus {
		NONE,
		HELD_UP,
		BLOCKING
	}
}
