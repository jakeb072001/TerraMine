package terramine.common.item.equipment;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ToolMaterial;
import terramine.datagen.ModTags;

public record TerrariaToolMaterials() {
    public static final ToolMaterial DEMONITE;
    public static final ToolMaterial CRIMTANE;
    public static final ToolMaterial METEOR;
    public static final ToolMaterial MOLTEN;

    static {
        DEMONITE = new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL,500, 7.5f, 2.5f, 17, ModTags.REPAIRS_SHADOW_ARMOR);
        CRIMTANE = new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL,500, 7.0f, 3.0f, 17, ModTags.REPAIRS_CRIMSON_ARMOR);
        METEOR = new ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL,1800, 9f, 4.0f, 17, ModTags.REPAIRS_METEOR_ARMOR);
        MOLTEN = new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL,1600, 9.5f, 5.0f, 20, ModTags.REPAIRS_MOLTEN_ARMOR);
    }
}
