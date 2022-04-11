package terracraft.common.entity.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import terracraft.common.init.ModBlockEntityType;
import terracraft.common.init.ModScreenHandlerType;

public class WaterChestEntity extends ChestEntity {
    public WaterChestEntity(BlockPos pos, BlockState state) {
        super("water_chest", ModScreenHandlerType.WATER_CHEST, ModBlockEntityType.WATER_CHEST, pos, state);
    }
}
