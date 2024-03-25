package terramine.common.entity.projectiles.arrows;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

// todo: make arrows work and look like they do in Terraria
// todo: maybe make a TerrariaArrow class for easily making future arrows?
public class UnholyArrow extends AbstractArrow {

    protected UnholyArrow(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected ItemStack getPickupItem() {
        return null;
    }
}
