package terramine.common.item.magic;

import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import terramine.common.init.ModEntities;
import terramine.common.init.ModSoundEvents;
import terramine.common.utility.MagicMissileHelper;

public class FlamelashItem extends MagicTerrariaItem {

    public FlamelashItem(ResourceKey<Item> key) {
        super(key);
        this.setVars(7, 21);
    }

    @Override
    public ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level world, @NotNull LivingEntity entity) {
        Player player = (Player)entity;
        if (canUse(player)) {
            MagicMissileHelper flameMissile = ModEntities.FLAMELASH_MISSILE.create(world, EntitySpawnReason.DISPENSER);
            if (flameMissile != null) {
                flameMissile.setCooldownItem(this);
                flameMissile.setPos(player.position().x(), player.position().y() + 2, player.position().z());
                flameMissile.setOwner(player);
                flameMissile.setSpeed(1.5f);
                flameMissile.setDamage(6.5f);
                flameMissile.liquidCollision(false, false);
                flameMissile.canIgnite(true);
                flameMissile.limitedTime(true);
                world.addFreshEntity(flameMissile);
                world.playSound(null, player.blockPosition(), ModSoundEvents.FLAMELASH_SHOOT, SoundSource.PLAYERS, 0.50f, 1f);
            }
        }
        return super.finishUsingItem(stack, world, entity);
    }
}