package terracraft.common.entity.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import terracraft.common.init.ModBlockEntityType;
import terracraft.common.init.ModScreenHandlerType;

public class GoldChestEntity extends ChestEntity {
    public GoldChestEntity(BlockPos pos, BlockState state) {
        super("gold_chest", ModScreenHandlerType.GOLD_CHEST, ModBlockEntityType.GOLD_CHEST, pos, state);
    }
}
