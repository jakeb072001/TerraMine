package terramine.mixin.world.entity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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

    @Shadow public ItemStack getItem()  {
        return (ItemStack)this.getEntityData().get(DATA_ITEM);
    }
    @Shadow public void setItem(ItemStack itemStack)  {
        this.getEntityData().set(DATA_ITEM, itemStack);
    }
    @Shadow private static EntityDataAccessor<ItemStack> DATA_ITEM;

    static {
        DATA_ITEM = SynchedEntityData.defineId(ItemEntity.class, EntityDataSerializers.ITEM_STACK);
    }

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
