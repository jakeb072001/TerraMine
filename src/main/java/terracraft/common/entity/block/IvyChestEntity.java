package terracraft.common.entity.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import terracraft.common.init.ModBlockEntityType;
import terracraft.common.init.ModScreenHandlerType;

public class IvyChestEntity extends ChestEntity {
    public IvyChestEntity(BlockPos pos, BlockState state) {
        super("ivy_chest", ModScreenHandlerType.IVY_CHEST, ModBlockEntityType.IVY_CHEST, pos, state);
    }
}
