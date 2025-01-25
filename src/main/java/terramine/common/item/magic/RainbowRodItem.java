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

public class RainbowRodItem extends MagicTerrariaItem {

    public RainbowRodItem(ResourceKey<Item> key) {
        super(key);
        this.setVars(6, 21);
    }

    @Override
    public ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level world, @NotNull LivingEntity entity) {
        Player player = (Player)entity;
        if (canUse(player)) {
            MagicMissileHelper rainbowMissile = ModEntities.RAINBOW_MISSILE.create(world, EntitySpawnReason.DISPENSER);
            if (rainbowMissile != null) {
                rainbowMissile.setCooldownItem(this);
                rainbowMissile.setPos(player.position().x(), player.position().y() + 2, player.position().z());
                rainbowMissile.setOwner(player);
                rainbowMissile.setSpeed(1.5f);
                rainbowMissile.setDamage(9.0f);
                rainbowMissile.liquidCollision(true, true);
                world.addFreshEntity(rainbowMissile);
                world.playSound(null, player.blockPosition(), ModSoundEvents.RAINBOW_ROD_SHOOT, SoundSource.PLAYERS, 0.50f, 1f);
            }
        }
        return super.finishUsingItem(stack, world, entity);
    }
}