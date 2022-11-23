package terramine.common.item.magic;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import terramine.common.entity.projectiles.LaserEntity;
import terramine.common.init.ModComponents;
import terramine.common.init.ModEntities;
import terramine.common.init.ModSoundEvents;
import terramine.common.item.MagicTerrariaItem;

public class SpaceGunItem extends MagicTerrariaItem {

    public SpaceGunItem() {
        this.setVars(2, 6);
    }

    @Override
    public ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level world, @NotNull LivingEntity entity) {
        Player player = (Player)entity;
        if (canUse(player)) {
            LaserEntity laser = ModEntities.LASER.create(world);
            if (laser != null) {
                laser.setOwner(player);
                laser.setGun(this);
                laser.setPos(player.position().x(), player.position().y() + 1.5f, player.position().z());
                laser.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 1f, 4f);
                world.addFreshEntity(laser);
                world.playSound(null, player.blockPosition(), ModSoundEvents.SPACE_GUN_SHOOT, SoundSource.PLAYERS, 0.50f, 1f);
            }
        }
        return super.finishUsingItem(stack, world, entity);
    }

    @Override
    public boolean isFree(Player player) {
        return ModComponents.SPACE_GUN_FREE.get(player).get();
    }
}