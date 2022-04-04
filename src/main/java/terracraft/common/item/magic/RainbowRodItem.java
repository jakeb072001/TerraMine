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

public class RainbowRodItem extends MagicTerrariaItem {

    public RainbowRodItem() {
        this.setVars(6, 21);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        Player player = (Player)entity;
        if (canUse(player)) {
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
}