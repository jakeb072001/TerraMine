package terramine.mixin.world.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import terramine.extensions.ItemExtensions;

@Mixin(Item.class)
public abstract class ItemMixin implements ItemExtensions {
    @Override
    @Unique
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
        return shield.getItem() instanceof AxeItem;
    }

    @Override
    @Unique
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        return false;
    }
}
