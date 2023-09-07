package terramine.common.item.accessories;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import terramine.common.init.ModItems;
import terramine.common.init.ModSoundEvents;

public class WhoopeeCushionItem extends AccessoryTerrariaItem {

	@Override
	public InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand interactionHand) {
		if (!player.getCooldowns().isOnCooldown(ModItems.WHOOPEE_CUSHION)) {
			player.getCooldowns().addCooldown(ModItems.WHOOPEE_CUSHION, 10);
			level.playSound(null, player.blockPosition(), ModSoundEvents.FART, SoundSource.PLAYERS, 1f, 1f);
		}

		return InteractionResultHolder.success(player.getItemInHand(interactionHand));
	}

	@Override
	public UseAnim getUseAnimation(@NotNull ItemStack stack) {
		return UseAnim.BLOCK;
	}

	@Override
	public SoundInfo getEquipSoundInfo() {
		return new SoundInfo(ModSoundEvents.FART);
	}
}
