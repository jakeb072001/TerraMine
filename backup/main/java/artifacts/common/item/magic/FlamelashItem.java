package terracraft.common.item.magic;

import terracraft.common.entity.MagicMissileEntity;
import terracraft.common.init.Components;
import terracraft.common.init.Entities;
import terracraft.common.init.Items;
import terracraft.common.init.SoundEvents;
import terracraft.common.item.ArtifactItem;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class FlamelashItem extends ArtifactItem {

    int manaUsed = 21;

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        Player player = (Player)entity;
        if (!player.getCooldowns().isOnCooldown(Items.FLAMELASH_ITEM)) {
            Components.MANA_HANDLER.get(player).isInUse();
            Components.MANA_HANDLER.get(player).addCurrentMana(-manaUsed);
            MagicMissileEntity flameMissile = Entities.MAGIC_MISSILE.create(world);
            if (flameMissile != null) {
                flameMissile.setPos(player.position().x(), player.position().y() + 2, player.position().z());
                flameMissile.setOwner(player);
                flameMissile.setCooldownItem(this);
                flameMissile.setParticleType(1);
                flameMissile.setSpeed(0.8f);
                flameMissile.setDamage(3.2f);
                flameMissile.liquidCollision(false, false);
                flameMissile.canIgnite(true);
                flameMissile.limitedTime(true);
                world.addFreshEntity(flameMissile);
                world.playSound(null, player.blockPosition(), SoundEvents.FLAMELASH_SHOOT, SoundSource.PLAYERS, 0.50f, 1f);
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
        if (!(Components.MANA_HANDLER.get(user).getCurrentMana() < manaUsed) || user.isCreative()) {
            user.startUsingItem(hand);
            return InteractionResultHolder.consume(itemstack);
        }
        return InteractionResultHolder.fail(itemstack);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 7;
    }
}
