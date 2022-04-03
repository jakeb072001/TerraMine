package terracraft.mixin.event;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terracraft.common.init.ModComponents;

@Mixin(LivingEntity.class)
public abstract class EntityMixin extends Entity {

    public EntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    public abstract float getHealth();

    @Inject(method = "setHealth", at = @At("TAIL"))
    private void setLastDamage(float f, CallbackInfo info) {
        Minecraft mc = Minecraft.getInstance();
        float damage = this.getHealth() - f;
        if (damage > 0 && mc.player != null) {
            ModComponents.DPS_METER_DAMAGE.get(mc.player).setDamageTaken(damage);
        }
    }
}
