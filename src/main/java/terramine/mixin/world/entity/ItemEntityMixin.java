package terramine.mixin.world.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.common.init.ModItems;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    public ItemEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow public abstract ItemStack getItem();
    @Shadow public abstract void setItem(ItemStack itemStack);

    @Inject(method = "tick", at = @At("TAIL"))
    public void daytimeVanish(CallbackInfo ci) {
        if (this.getItem().is(ModItems.FAKE_FALLEN_STAR)) {
            if (this.level.isDay() && !this.level.isClientSide) {
                this.discard();
            }
        }
    }

    @Inject(method = "playerTouch", at = @At("HEAD"))
    public void preventVanish(Player player, CallbackInfo ci) {
        if (this.getItem().is(ModItems.FAKE_FALLEN_STAR)) {
            this.setItem(ModItems.FALLEN_STAR.getDefaultInstance());
        }
    }
}
