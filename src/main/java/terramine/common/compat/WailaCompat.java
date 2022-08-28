package terramine.common.compat;

import mcp.mobius.waila.api.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import terramine.common.block.RedStoneDeepslateBlock;
import terramine.common.block.RedStoneStoneBlock;
import terramine.common.block.chests.BaseChest;
import terramine.common.init.ModBlocks;

// todo: somehow display mimic as a chest
public class WailaCompat implements IWailaPlugin {
    @Override
    public void register(IRegistrar registrar) {
        registrar.addOverride(new ChestOverride(), BaseChest.class);
        registrar.addOverride(new RedStoneStoneOverride(), RedStoneStoneBlock.class);
        registrar.addOverride(new RedStoneDeepslateOverride(), RedStoneDeepslateBlock.class);
    }

    protected static class ChestOverride implements IBlockComponentProvider {
        @Override
        public BlockState getOverride(IBlockAccessor accessor, IPluginConfig config) {
            if (accessor.getBlock().equals(ModBlocks.TRAPPED_GOLD_CHEST)) {
                return ModBlocks.GOLD_CHEST.defaultBlockState();
            } else if (accessor.getBlock().equals(ModBlocks.TRAPPED_FROZEN_CHEST)) {
                return ModBlocks.FROZEN_CHEST.defaultBlockState();
            } else if (accessor.getBlock().equals(ModBlocks.TRAPPED_IVY_CHEST)) {
                return ModBlocks.IVY_CHEST.defaultBlockState();
            } else if (accessor.getBlock().equals(ModBlocks.TRAPPED_SANDSTONE_CHEST)) {
                return ModBlocks.SANDSTONE_CHEST.defaultBlockState();
            } else {
                return accessor.getBlockState();
            }
        }
    }

    protected static class RedStoneStoneOverride implements IBlockComponentProvider {
        @Override
        public BlockState getOverride(IBlockAccessor accessor, IPluginConfig config) {
            return Blocks.STONE.defaultBlockState();
        }
    }

    protected static class RedStoneDeepslateOverride implements IBlockComponentProvider {
        @Override
        public BlockState getOverride(IBlockAccessor accessor, IPluginConfig config) {
            return Blocks.DEEPSLATE.defaultBlockState();
        }
    }
}
