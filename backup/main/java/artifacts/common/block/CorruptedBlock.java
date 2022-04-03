package terracraft.common.block;

import terracraft.common.utility.CorruptionHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class CorruptedBlock extends CorruptionHelper {
    public CorruptedBlock(Properties properties) {
        super(properties);
    }
}
