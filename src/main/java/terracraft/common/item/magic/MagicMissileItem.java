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

public class MagicMissileItem extends TerrariaItem {

    int manaUsed = 14;

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        Player player = (Player)entity;
        if (!player.getCooldowns().isOnCooldown(ModItems.MAGIC_MISSILE_ITEM)) {
            ModComponents.MANA_HANDLER.get(player).isInUse();
            ModComponents.MANA_HANDLER.get(player).addCurrentMana(-manaUsed);
            MagicMissileEntity missile = ModEntities.MAGIC_MISSILE.create(world);
            if (missile != null) {
                missile.setPos(player.position().x(), player.position().y() + 2, player.position().z());
                missile.setOwner(player);
                missile.setCooldownItem(this);
                missile.setParticleType(0);
                missile.setSpeed(0.8f);
                missile.setDamage(2.7f);
                missile.liquidCollision(true, false);
                world.addFreshEntity(missile);
                world.playSound(null, player.blockPosition(), ModSoundEvents.MAGIC_MISSILE_SHOOT, SoundSource.PLAYERS, 0.50f, 1f);
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
        return 5;
    }
}
