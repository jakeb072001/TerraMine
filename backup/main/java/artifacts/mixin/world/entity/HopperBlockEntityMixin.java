package terracraft.mixin.world.entity;

import terracraft.common.init.Items;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HopperBlockEntity.class)
public class HopperBlockEntityMixin {

    @Inject(method = "addItem", at = @At("HEAD"))
    private static void preventVanish(Container container, ItemEntity itemEntity, CallbackInfoReturnable ci) {
        if (itemEntity.getItem().is(Items.FAKE_FALLEN_STAR)) {
            itemEntity.setItem(Items.FALLEN_STAR.getDefaultInstance());
        }
    }
}
