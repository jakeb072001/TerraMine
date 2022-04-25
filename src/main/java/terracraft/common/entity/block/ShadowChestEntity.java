package terracraft.common.entity.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import terracraft.common.init.ModBlockEntityType;
import terracraft.common.init.ModScreenHandlerType;

public class ShadowChestEntity extends ChestEntity {
    public ShadowChestEntity(BlockPos pos, BlockState state) {
        super("shadow_chest", ModScreenHandlerType.SHADOW_CHEST, ModBlockEntityType.SHADOW_CHEST, pos, state);
    }
}
