package terramine.common.entity.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import terramine.common.init.ModBlockEntityType;
import terramine.common.init.ModScreenHandlerType;

public class SandstoneChestEntity extends ChestEntity {
    public SandstoneChestEntity(BlockPos pos, BlockState state) {
        super("sandstone_chest", ModScreenHandlerType.SANDSTONE_CHEST, ModBlockEntityType.SANDSTONE_CHEST, pos, state);
    }
}
