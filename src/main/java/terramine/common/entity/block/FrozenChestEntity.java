package terramine.common.entity.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import terramine.common.init.ModBlockEntityType;
import terramine.common.init.ModScreenHandlerType;

public class FrozenChestEntity extends ChestEntity {
    public FrozenChestEntity(BlockPos pos, BlockState state) {
        super("frozen_chest", ModScreenHandlerType.FROZEN_CHEST, ModBlockEntityType.FROZEN_CHEST, pos, state);
    }
}
