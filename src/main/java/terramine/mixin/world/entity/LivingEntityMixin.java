package terramine.mixin.world.entity;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terramine.common.init.ModAttributes;
import terramine.extensions.ItemExtensions;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow public abstract ItemStack getItemInHand(InteractionHand interactionHand);

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

    @Inject(at = @At("HEAD"), method = "swing(Lnet/minecraft/world/InteractionHand;)V", cancellable = true)
    public void swing(InteractionHand hand, CallbackInfo info) {
        ItemStack stack = this.getItemInHand(hand);
        if (!stack.isEmpty() && ((ItemExtensions) stack.getItem()).onEntitySwing(stack, (((LivingEntity) (Object)this)))) info.cancel();
    }
}
