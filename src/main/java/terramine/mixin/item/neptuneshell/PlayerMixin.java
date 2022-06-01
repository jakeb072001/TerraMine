package terramine.mixin.item.neptuneshell;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.common.init.ModItems;
import terramine.common.trinkets.TrinketsHelper;

@Mixin(Player.class)
public abstract class PlayerMixin  extends LivingEntity {

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "tick")
    private void tick(CallbackInfo info) {
        if(TrinketsHelper.isEquipped(ModItems.NEPTUNE_SHELL, this)) {
            if(this.getAirSupply() < this.getMaxAirSupply()){
                this.setAirSupply(this.increaseAirSupply(this.getAirSupply()));
            }
        }
    }
}
