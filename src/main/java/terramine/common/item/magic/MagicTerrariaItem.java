package terramine.common.item.magic;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import terramine.common.init.ModComponents;
import terramine.common.item.TerrariaItem;

public class MagicTerrariaItem extends TerrariaItem {
    public int useDuration, manaCost;

    public MagicTerrariaItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant(), false);
    }

    public void setVars(int useDuration, int manaCost) {
        this.useDuration = useDuration;
        this.manaCost = manaCost;
    }

    public boolean canUse(Player player) {
        if (!player.getCooldowns().isOnCooldown(this)) {
            ModComponents.MANA_HANDLER.get(player).isInUse();
            if (!isFree(player)) {
                ModComponents.MANA_HANDLER.get(player).addCurrentMana(-manaCost);
            }
            return true;
        }
        return false;
    }

    public boolean isFree(Player player) {
        return false;
    }

    @Override
    public ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level world, @NotNull LivingEntity entity) {
        return super.finishUsingItem(stack, world, entity);
    }

    @Override
    public UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level world, Player user, @NotNull InteractionHand hand) {
        ItemStack itemstack = user.getItemInHand(hand);
        if (!(ModComponents.MANA_HANDLER.get(user).getCurrentMana() < manaCost) || user.isCreative()) {
            user.startUsingItem(hand);
            return InteractionResultHolder.consume(itemstack);
        }
        return InteractionResultHolder.fail(itemstack);
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return useDuration;
    }
}
