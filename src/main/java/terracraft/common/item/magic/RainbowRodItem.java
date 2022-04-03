package terracraft.common.item.magic;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import terracraft.common.entity.MagicMissileEntity;
import terracraft.common.init.ModComponents;
import terracraft.common.init.ModEntities;
import terracraft.common.init.ModItems;
import terracraft.common.init.ModSoundEvents;
import terracraft.common.item.TerrariaItem;

public class RainbowRodItem extends TerrariaItem {

    int manaUsed = 21;

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        Player player = (Player)entity;
        if (!player.getCooldowns().isOnCooldown(ModItems.RAINBOW_ROD_ITEM)) {
            ModComponents.MANA_HANDLER.get(player).isInUse();
            ModComponents.MANA_HANDLER.get(player).addCurrentMana(-manaUsed);
            MagicMissileEntity rainbowMissile = ModEntities.MAGIC_MISSILE.create(world);
            if (rainbowMissile != null) {
                rainbowMissile.setPos(player.position().x(), player.position().y() + 2, player.position().z());
                rainbowMissile.setOwner(player);
                rainbowMissile.setCooldownItem(this);
                rainbowMissile.setParticleType(2);
                rainbowMissile.setSpeed(0.8f);
                rainbowMissile.setDamage(5.0f);
                rainbowMissile.liquidCollision(true, true);
                world.addFreshEntity(rainbowMissile);
                world.playSound(null, player.blockPosition(), ModSoundEvents.RAINBOW_ROD_SHOOT, SoundSource.PLAYERS, 0.50f, 1f);
            }
        }
        return super.finishUsingItem(stack, world, entity);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemstack = user.getItemInHand(hand);
        if (!(ModComponents.MANA_HANDLER.get(user).getCurrentMana() < manaUsed) || user.isCreative()) {
            user.startUsingItem(hand);
            return InteractionResultHolder.consume(itemstack);
        }
        return InteractionResultHolder.fail(itemstack);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 6;
    }
}
