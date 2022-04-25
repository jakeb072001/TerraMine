package terracraft.common.entity.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import terracraft.common.init.ModBlockEntityType;
import terracraft.common.init.ModScreenHandlerType;

public class SandstoneChestEntity extends ChestEntity {
    public SandstoneChestEntity(BlockPos pos, BlockState state) {
        super("sandstone_chest", ModScreenHandlerType.SANDSTONE_CHEST, ModBlockEntityType.SANDSTONE_CHEST, pos, state);
    }
}
