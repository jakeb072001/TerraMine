package terramine.common.compat;

import mcp.mobius.waila.api.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import terramine.common.block.RedStoneDeepslateBlock;
import terramine.common.block.RedStoneStoneBlock;
import terramine.common.block.chests.BaseChest;
import terramine.common.init.ModBlocks;

// todo: somehow display mimic as a chest
// todo: fix, doesn't work no more
public class WailaCompat implements IWailaClientPlugin {
    @Override
    public void register(IClientRegistrar registrar) {
        registrar.override(new ChestOverride(), BaseChest.class);
        registrar.override(new RedStoneStoneOverride(), RedStoneStoneBlock.class);
        registrar.override(new RedStoneDeepslateOverride(), RedStoneDeepslateBlock.class);
    }

    protected static class ChestOverride implements IBlockComponentProvider {
        @Override
        public BlockState getOverride(IBlockAccessor accessor, IPluginConfig config) {
            if (accessor.getBlock().equals(ModBlocks.TRAPPED_GOLD_CHEST.BLOCK)) {
                return ModBlocks.GOLD_CHEST.BLOCK.defaultBlockState();
            } else if (accessor.getBlock().equals(ModBlocks.TRAPPED_FROZEN_CHEST.BLOCK)) {
                return ModBlocks.FROZEN_CHEST.BLOCK.defaultBlockState();
            } else if (accessor.getBlock().equals(ModBlocks.TRAPPED_IVY_CHEST.BLOCK)) {
                return ModBlocks.IVY_CHEST.BLOCK.defaultBlockState();
            } else if (accessor.getBlock().equals(ModBlocks.TRAPPED_SANDSTONE_CHEST.BLOCK)) {
                return ModBlocks.SANDSTONE_CHEST.BLOCK.defaultBlockState();
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
