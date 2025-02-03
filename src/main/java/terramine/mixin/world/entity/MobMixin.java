package terramine.mixin.world.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.common.item.accessories.ShieldAccessoryLikeItem;
import terramine.extensions.ItemExtensions;

@Mixin(Player.class)
public abstract class MobMixin extends LivingEntity {
    protected MobMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(at = @At("HEAD"), method = "disableShield")
    public void maybeDisableShield(CallbackInfo ci) {
        Player player = (Player) (Object) this;
        ItemStack itemStack = player.getOffhandItem();
        ItemStack itemStack2 = player.getMainHandItem();
        if (!itemStack.isEmpty() && !itemStack2.isEmpty() && ((ItemExtensions) itemStack.getItem()).canDisableShield(itemStack, itemStack2, player, this) && (itemStack2.getItem() instanceof ShieldItem || itemStack2.getItem() instanceof ShieldAccessoryLikeItem)) {
            player.getCooldowns().addCooldown(itemStack2, 100);
            this.stopUsingItem();
            this.level().broadcastEntityEvent(player, (byte) 30);
        }
    }
}
