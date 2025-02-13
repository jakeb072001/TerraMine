package terramine.mixin.world.entity;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terramine.common.init.ModAttributes;
import terramine.common.init.ModItems;
import terramine.common.item.accessories.ShieldAccessoryLikeItem;
import terramine.common.item.armor.vanity.VanityArmor;
import terramine.common.misc.AccessoriesHelper;
import terramine.extensions.ItemExtensions;
import terramine.extensions.PlayerStorages;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow public abstract ItemStack getItemInHand(InteractionHand interactionHand);

    @Shadow public abstract boolean isInvulnerableTo(ServerLevel serverLevel, DamageSource damageSource);

    @Shadow public ItemStack useItem;

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "createLivingAttributes", at = @At("RETURN"))
    private static void addAttributes(final CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        cir.getReturnValue()
                .add(ModAttributes.RANGER_ATTACK_DAMAGE)
                .add(ModAttributes.MAGIC_ATTACK_DAMAGE)
                .add(ModAttributes.MAGIC_ATTACK_SPEED);
    }

    @WrapMethod(method = "updatingUsingItem")
    public void fixCustomOffhandShield(Operation<ItemStack> original) {
        if ((((LivingEntity) (Object) this)) instanceof Player player && (useItem.getItem() instanceof ShieldItem || useItem.getItem() instanceof ShieldAccessoryLikeItem)) {
            if (((PlayerStorages)player).getTerrariaInventory().getItem(21) != ItemStack.EMPTY) {
                return;
            }
        }

        original.call();
    }

    @Inject(method = "isInWall", at = @At("HEAD"), cancellable = true)
    private void preventSuffocation(CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof Player player && (player instanceof PlayerStorages tracking && tracking.isPhasing())) {
            cir.setReturnValue(false);
        }
    }

    @Inject(at = @At("HEAD"), method = "swing(Lnet/minecraft/world/InteractionHand;)V", cancellable = true)
    public void swing(InteractionHand hand, CallbackInfo info) {
        ItemStack stack = this.getItemInHand(hand);
        if (!stack.isEmpty() && ((ItemExtensions) stack.getItem()).onEntitySwing(stack, (((LivingEntity) (Object)this)))) info.cancel();
    }

    @Inject(at = @At("TAIL"), method = "hurtServer")
    public void fireGauntlet(ServerLevel serverLevel, DamageSource damageSource, float f, CallbackInfoReturnable<Boolean> cir) {
        if (damageSource.getEntity() instanceof Player player && !isInvulnerableTo(serverLevel, damageSources().onFire())) {
            if (AccessoriesHelper.isEquipped(ModItems.FIRE_GAUNTLET, player) || AccessoriesHelper.isEquipped(ModItems.MAGMA_STONE, player) || AccessoriesHelper.isEquipped(ModItems.MAGMA_SKULL, player)
                    || AccessoriesHelper.isEquipped(ModItems.MOLTEN_SKULL_ROSE, player)) {
                this.setRemainingFireTicks(80);
            }
        }
    }

    @WrapOperation(
            method = "doHurtEquipment",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;hurtAndBreak(ILnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;)V")
    )
    public void doHurtEquipment(ItemStack instance, int i, LivingEntity livingEntity, EquipmentSlot equipmentSlot, Operation<Void> original) {
        if (instance.getItem() instanceof VanityArmor) {
            return;
        }

        original.call(instance, i, livingEntity, equipmentSlot);
    }
}
