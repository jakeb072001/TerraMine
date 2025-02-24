package terramine.common.item.equipment;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ToolMaterial;
import terramine.datagen.ModTags;

public record TerrariaToolMaterials() {
    public static final ToolMaterial COPPER;
    public static final ToolMaterial TIN;
    public static final ToolMaterial LEAD;
    public static final ToolMaterial SILVER;
    public static final ToolMaterial TUNGSTEN;
    public static final ToolMaterial PLATINUM;
    public static final ToolMaterial DEMONITE;
    public static final ToolMaterial CRIMTANE;
    public static final ToolMaterial METEOR;
    public static final ToolMaterial MOLTEN;

    static {
        COPPER = new ToolMaterial(BlockTags.INCORRECT_FOR_STONE_TOOL,170, 4.5F, 1.0F, 10, ModTags.REPAIRS_COPPER_ARMOR);
        TIN = new ToolMaterial(BlockTags.INCORRECT_FOR_STONE_TOOL,170, 4.5F, 1.0F, 10, ModTags.REPAIRS_TIN_ARMOR);
        LEAD = new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL,250, 6F, 2.0F, 14, ModTags.REPAIRS_LEAD_ARMOR);
        SILVER = new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL,370, 7f, 2.5f, 16, ModTags.REPAIRS_SILVER_ARMOR);
        TUNGSTEN = new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL,370, 7f, 2.5f, 16, ModTags.REPAIRS_TUNGSTEN_ARMOR);
        PLATINUM = new ToolMaterial(BlockTags.INCORRECT_FOR_GOLD_TOOL,64, 12.0F, 0.0F, 22, ModTags.REPAIRS_PLATINUM_ARMOR);
        DEMONITE = new ToolMaterial(ModTags.INCORRECT_FOR_EVIL_TOOL,500, 8f, 3f, 18, ModTags.REPAIRS_SHADOW_ARMOR);
        CRIMTANE = new ToolMaterial(ModTags.INCORRECT_FOR_EVIL_TOOL,500, 7.5f, 3.5f, 18, ModTags.REPAIRS_CRIMSON_ARMOR);
        METEOR = new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL,1800, 9f, 4.0f, 18, ModTags.REPAIRS_METEOR_ARMOR);
        MOLTEN = new ToolMaterial(ModTags.INCORRECT_FOR_MOLTEN_TOOL,1600, 9.5f, 5.0f, 20, ModTags.REPAIRS_MOLTEN_ARMOR);
    }
}
