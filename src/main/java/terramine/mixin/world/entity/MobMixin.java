package terramine.mixin.world.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.common.item.accessories.ShieldAccessoryItem;
import terramine.extensions.ItemExtensions;

@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity {
    protected MobMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(at = @At("HEAD"), method = "maybeDisableShield")
    public void maybeDisableShield(Player player, ItemStack itemStack, ItemStack itemStack2, CallbackInfo info) {
        if (!itemStack.isEmpty() && !itemStack2.isEmpty() && ((ItemExtensions) itemStack.getItem()).canDisableShield(itemStack, itemStack2, player, this) && (itemStack2.getItem() instanceof ShieldItem || itemStack2.getItem() instanceof ShieldAccessoryItem)) {
            float f = 0.25F + (float) EnchantmentHelper.getBlockEfficiency(this) * 0.05F;
            if (this.random.nextFloat() < f) {
                player.getCooldowns().addCooldown(itemStack2.getItem(), 100);
                this.level.broadcastEntityEvent(player, (byte) 30);
            }
        }
    }
}
