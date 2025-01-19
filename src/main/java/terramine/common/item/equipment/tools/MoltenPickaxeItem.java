package terramine.common.item.equipment.tools;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ToolMaterial;
import org.jetbrains.annotations.NotNull;

public class MoltenPickaxeItem extends TerrariaPickaxeItem {
    public MoltenPickaxeItem(ToolMaterial tier, float f, float g, Properties properties) {
        super(tier, f, g, true, properties);
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack itemStack, @NotNull LivingEntity livingEntity, @NotNull LivingEntity livingEntity2) {
        if (livingEntity.getRandom().nextFloat() <= 0.10) {
            livingEntity.setRemainingFireTicks(6);
        }
        return super.hurtEnemy(itemStack, livingEntity, livingEntity2);
    }
}
