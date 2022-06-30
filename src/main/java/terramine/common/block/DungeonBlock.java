package terramine.common.block;

import net.fabricmc.fabric.api.mininglevel.v1.MiningLevelManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class DungeonBlock extends Block {
    public DungeonBlock(Properties properties) {
        super(properties);
    }

    @Override
    @Deprecated
    public float getDestroyProgress(@NotNull BlockState state, @NotNull Player player, @NotNull BlockGetter getter, @NotNull BlockPos pos) {
        ItemStack cei = player.getMainHandItem();

        if (pos.getY() >= 60) { // todo: need to find a way to make block not indestructible when placed by player (to avoid trolling)
            return super.getDestroyProgress(state, player, getter, pos);
        } else if (cei.getItem() instanceof DiggerItem test && test.getTier().getLevel() >= MiningLevelManager.getRequiredMiningLevel(state)) {
            return super.getDestroyProgress(state, player, getter, pos);
        }

        return 0;
    }
}
