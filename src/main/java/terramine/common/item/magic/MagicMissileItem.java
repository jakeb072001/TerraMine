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

public class MagicMissileItem extends MagicTerrariaItem {

    public MagicMissileItem(ResourceKey<Item> key) {
        super(key);
        this.setVars(5, 14);
    }

    @Override
    public ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level world, @NotNull LivingEntity entity) {
        Player player = (Player)entity;
        if (canUse(player)) {
            MagicMissileHelper missile = ModEntities.MAGIC_MISSILE.create(world, EntitySpawnReason.DISPENSER);
            if (missile != null) {
                missile.setCooldownItem(this);
                missile.setPos(player.position().x(), player.position().y() + 2, player.position().z());
                missile.setOwner(player);
                missile.setSpeed(1.5f);
                missile.setDamage(6.0f);
                missile.liquidCollision(true, false);
                world.addFreshEntity(missile);
                world.playSound(null, player.blockPosition(), ModSoundEvents.MAGIC_MISSILE_SHOOT, SoundSource.PLAYERS, 0.50f, 1f);
            }
        }
        return super.finishUsingItem(stack, world, entity);
    }
}