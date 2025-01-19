package terramine.common.item.equipment.tools;

import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ToolMaterial;

public class TerrariaPickaxeItem extends PickaxeItem {
    private final boolean canBreakDungeon;

    public TerrariaPickaxeItem(ToolMaterial toolMaterial, float f, float g, Properties properties) {
        super(toolMaterial, f, g, properties);
        this.canBreakDungeon = false;
    }

    public TerrariaPickaxeItem(ToolMaterial toolMaterial, float f, float g, boolean canBreakDungeon, Properties properties) {
        super(toolMaterial, f, g, properties);
        this.canBreakDungeon = canBreakDungeon;
    }

    public boolean canBreakDungeon() {
        return canBreakDungeon;
    }
}
