package terramine.common.item;

import net.minecraft.world.item.Item;

public abstract class CraftingItemBase extends Item {

	public CraftingItemBase(Properties properties) {
		super(properties);
	}

	public CraftingItemBase() {
		this(new Properties());
	}
}
