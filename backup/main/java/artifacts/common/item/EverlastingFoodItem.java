package terracraft.common.item;

import terracraft.Artifacts;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EverlastingFoodItem extends ArtifactItem {

	public EverlastingFoodItem(FoodProperties foodComponent) {
		super(new Properties().food(foodComponent));
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
		if (!world.isClientSide && entity instanceof Player player) {
			player.getCooldowns().addCooldown(this, Artifacts.CONFIG.general.everlastingFoodCooldown);
		}

		// Stack decrement is cancelled in LivingEntity.eatFood() mixin
		return super.finishUsingItem(stack, world, entity);
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 24;
	}
}
