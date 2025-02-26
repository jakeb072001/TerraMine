package terramine.common.item.misc;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import terramine.TerraMine;
import terramine.common.init.ModComponents;
import terramine.common.init.ModSoundEvents;
import terramine.common.item.TerrariaItemConfigurable;

public class DemonHeartItem extends TerrariaItemConfigurable {
    public DemonHeartItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level world, @NotNull LivingEntity entity) {
        Player player = (Player)entity;
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)player, stack);
        }

        if (ModComponents.ACCESSORY_SLOTS_ADDER.get(player).get() < 2 && !ModComponents.ACCESSORY_DEMON_HEART_CHECK.get(player).get() && !TerraMine.CONFIG.general.disableDemonHeart) {
            ModComponents.ACCESSORY_SLOTS_ADDER.get(player).add(1);
            ModComponents.ACCESSORY_SLOTS_ADDER.sync(player);
            ModComponents.ACCESSORY_DEMON_HEART_CHECK.get(player).set(true);
            ModComponents.ACCESSORY_DEMON_HEART_CHECK.sync(player);
            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }
        player.level().playSound(null, player.blockPosition(), ModSoundEvents.MANA_CRYSTAL_USE, SoundSource.PLAYERS, 0.50f, 1f);
        return super.finishUsingItem(stack, world, entity);
    }

    @Override
    public @NotNull ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.BOW;
    }

    @Override
    public @NotNull InteractionResult use(Level world, Player user, InteractionHand hand) {
        user.startUsingItem(hand);
        return InteractionResult.CONSUME;
    }

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity livingEntity) {
        return 10;
    }
}
