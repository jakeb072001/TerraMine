package terracraft.common.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import terracraft.common.init.ModComponents;

public class MagicTerrariaItem extends TerrariaItem {
    public int useDuration;
    public int manaCost;

    public void setVars(int useDuration, int manaCost) {
        this.useDuration = useDuration;
        this.manaCost = manaCost;
    }

    public boolean canUse(Player player) {
        if (!player.getCooldowns().isOnCooldown(this)) {
            ModComponents.MANA_HANDLER.get(player).isInUse();
            ModComponents.MANA_HANDLER.get(player).addCurrentMana(-manaCost);
            return true;
        }
        return false;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        return super.finishUsingItem(stack, world, entity);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemstack = user.getItemInHand(hand);
        if (!(ModComponents.MANA_HANDLER.get(user).getCurrentMana() < manaCost) || user.isCreative()) {
            user.startUsingItem(hand);
            return InteractionResultHolder.consume(itemstack);
        }
        return InteractionResultHolder.fail(itemstack);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return useDuration;
    }
}
