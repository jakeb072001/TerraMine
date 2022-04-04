package terracraft.common.item.magic;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import terracraft.common.entity.MagicMissileEntity;
import terracraft.common.init.ModEntities;
import terracraft.common.init.ModSoundEvents;
import terracraft.common.item.MagicTerrariaItem;

public class FlamelashItem extends MagicTerrariaItem {

    public FlamelashItem() {
        this.setVars(7, 21);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        Player player = (Player)entity;
        if (canUse(player)) {
            MagicMissileEntity flameMissile = ModEntities.MAGIC_MISSILE.create(world);
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
                world.playSound(null, player.blockPosition(), ModSoundEvents.FLAMELASH_SHOOT, SoundSource.PLAYERS, 0.50f, 1f);
            }
        }
        return super.finishUsingItem(stack, world, entity);
    }
}