package terracraft.common.init;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BlockEntityType;
import terracraft.TerraCraft;
import terracraft.common.entity.block.*;

public class ModBlockEntityType {
    public static final BlockEntityType<GoldChestEntity> GOLD_CHEST = FabricBlockEntityTypeBuilder.create(GoldChestEntity::new, ModBlocks.GOLD_CHEST, ModBlocks.TRAPPED_GOLD_CHEST).build(null);
    public static final BlockEntityType<FrozenChestEntity> FROZEN_CHEST = FabricBlockEntityTypeBuilder.create(FrozenChestEntity::new, ModBlocks.FROZEN_CHEST, ModBlocks.TRAPPED_FROZEN_CHEST).build(null);
    public static final BlockEntityType<IvyChestEntity> IVY_CHEST = FabricBlockEntityTypeBuilder.create(IvyChestEntity::new, ModBlocks.IVY_CHEST, ModBlocks.TRAPPED_IVY_CHEST).build(null);
    public static final BlockEntityType<SandstoneChestEntity> SANDSTONE_CHEST = FabricBlockEntityTypeBuilder.create(SandstoneChestEntity::new, ModBlocks.SANDSTONE_CHEST, ModBlocks.TRAPPED_SANDSTONE_CHEST).build(null);
    public static final BlockEntityType<WaterChestEntity> WATER_CHEST = FabricBlockEntityTypeBuilder.create(WaterChestEntity::new, ModBlocks.WATER_CHEST).build(null);
    public static final BlockEntityType<SkywareChestEntity> SKYWARE_CHEST = FabricBlockEntityTypeBuilder.create(SkywareChestEntity::new, ModBlocks.SKYWARE_CHEST).build(null);
    public static final BlockEntityType<ShadowChestEntity> SHADOW_CHEST = FabricBlockEntityTypeBuilder.create(ShadowChestEntity::new, ModBlocks.SHADOW_CHEST).build(null);

    public static void register() {
        Registry.register(Registry.BLOCK_ENTITY_TYPE, TerraCraft.id("gold_chest"), GOLD_CHEST);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, TerraCraft.id("frozen_chest"), FROZEN_CHEST);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, TerraCraft.id("ivy_chest"), IVY_CHEST);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, TerraCraft.id("sandstone_chest"), SANDSTONE_CHEST);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, TerraCraft.id("water_chest"), WATER_CHEST);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, TerraCraft.id("skyware_chest"), SKYWARE_CHEST);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, TerraCraft.id("shadow_chest"), SHADOW_CHEST);
    }
}
