package terramine.mixin.accessors;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemStack.class)
public interface ItemStackAccessor {

	// Get around ItemStack.getHolder returning null if the stack is empty
	// TODO: this is horrible, should add our own field instead
	@Accessor
	Entity getEntityRepresentation();
}
