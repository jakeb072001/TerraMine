package terracraft.common.entity.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import terracraft.common.init.ModBlockEntityType;
import terracraft.common.init.ModScreenHandlerType;

public class SkywareChestEntity extends ChestEntity {
    public SkywareChestEntity(BlockPos pos, BlockState state) {
        super("skyware_chest", ModScreenHandlerType.SKYWARE_CHEST, ModBlockEntityType.SKYWARE_CHEST, pos, state);
    }
}
