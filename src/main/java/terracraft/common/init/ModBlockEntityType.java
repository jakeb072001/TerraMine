package terracraft.common.init;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BlockEntityType;
import terracraft.TerraCraft;
import terracraft.common.entity.block.GoldChestEntity;

public class ModBlockEntityType {
    public static final BlockEntityType<GoldChestEntity> GOLD_CHEST = FabricBlockEntityTypeBuilder.create(GoldChestEntity::new, ModBlocks.GOLD_CHEST).build(null);

    public static void register() {
        Registry.register(Registry.BLOCK_ENTITY_TYPE, TerraCraft.id("gold_chest"), GOLD_CHEST);
    }
}
