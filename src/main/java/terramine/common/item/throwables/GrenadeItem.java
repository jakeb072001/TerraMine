package terramine.common.item.throwables;

import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import terramine.common.entity.throwables.GrenadeEntity;
import terramine.common.init.ModEntities;
import terramine.common.init.ModSoundEvents;
import terramine.common.item.TerrariaItemConfigurable;

public class GrenadeItem extends TerrariaItemConfigurable {

    private final boolean sticky;
    private final boolean bouncy;

    public GrenadeItem(Properties properties, boolean sticky, boolean bouncy) {
        super(properties);
        this.sticky = sticky;
        this.bouncy = bouncy;
    }

    @Override
    public ItemStack finishUsingItem(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull LivingEntity entity) {
        Player player = (Player)entity;
        level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSoundEvents.THROW, SoundSource.NEUTRAL, 0.5f, 1f / (level.getRandom().nextFloat() * 0.4f + 0.8f));
        if (!level.isClientSide) {
            GrenadeEntity grenade = ModEntities.GRENADE.create(level);
            if (grenade != null) {
                grenade.setPos(player.position().x(), player.position().y() + 1, player.position().z());
                grenade.setSticky(sticky);
                grenade.setBouncy(bouncy);
                grenade.setOwner(player);
                grenade.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 0.5f, 0.1f);
                level.addFreshEntity(grenade);
            }
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
        }
        return super.finishUsingItem(itemStack, level, entity);
    }

    @Override
    public UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level world, Player user, @NotNull InteractionHand hand) {
        ItemStack itemstack = user.getItemInHand(hand);
        user.startUsingItem(hand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 5;
    }
}