package terramine.common.item.projectiles.throwables;

import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import terramine.common.entity.projectiles.throwables.DynamiteEntity;
import terramine.common.init.ModEntities;
import terramine.common.init.ModSoundEvents;
import terramine.common.item.TerrariaItemConfigurable;

public class DynamiteItem extends TerrariaItemConfigurable {

    private final boolean sticky;
    private final boolean bouncy;

    public DynamiteItem(Properties properties, boolean sticky, boolean bouncy) {
        super(properties);
        this.sticky = sticky;
        this.bouncy = bouncy;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity) {
        Player player = (Player)entity;
        level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSoundEvents.THROW, SoundSource.NEUTRAL, 0.5f, 1f / (level.getRandom().nextFloat() * 0.4f + 0.8f));
        if (!level.isClientSide) {
            DynamiteEntity dynamite = ModEntities.DYNAMITE.create(level, EntitySpawnReason.SPAWN_ITEM_USE);
            if (dynamite != null) {
                dynamite.setPos(player.position().x(), player.position().y() + 1, player.position().z());
                dynamite.setSticky(sticky);
                dynamite.setBouncy(bouncy);
                dynamite.setOwner(player);
                dynamite.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 0.5f, 0.1f);
                level.addFreshEntity(dynamite);
            }
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
        }
        return super.finishUsingItem(itemStack, level, entity);
    }

    @Override
    public @NotNull ItemUseAnimation getUseAnimation(@NotNull ItemStack stack) {
        return ItemUseAnimation.BOW;
    }

    @Override
    public @NotNull InteractionResult use(Level world, Player user, InteractionHand hand) {
        user.startUsingItem(hand);
        return InteractionResult.CONSUME;
    }

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity livingEntity) {
        return 5;
    }
}