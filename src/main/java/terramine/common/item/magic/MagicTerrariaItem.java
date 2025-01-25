package terramine.common.item.magic;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import terramine.common.init.ModComponents;
import terramine.common.item.TerrariaItem;

public class MagicTerrariaItem extends TerrariaItem {
    public int useDuration, manaCost;

    public MagicTerrariaItem(ResourceKey<Item> key) {
        super(new Properties().setId(key).stacksTo(1).rarity(Rarity.RARE).fireResistant(), false);
    }

    public void setVars(int useDuration, int manaCost) {
        this.useDuration = useDuration;
        this.manaCost = manaCost;
    }

    public boolean canUse(Player player) {
        if (!player.getCooldowns().isOnCooldown(this.getDefaultInstance())) {
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
    public @NotNull ItemUseAnimation getUseAnimation(@NotNull ItemStack stack) {
        return ItemUseAnimation.BOW;
    }

    @Override
    public InteractionResult use(@NotNull Level world, Player user, @NotNull InteractionHand hand) {
        if (!(ModComponents.MANA_HANDLER.get(user).getCurrentMana() < manaCost) || user.isCreative()) {
            user.startUsingItem(hand);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity livingEntity) {
        return useDuration;
    }
}
