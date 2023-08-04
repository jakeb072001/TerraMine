package terramine.common.entity.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import terramine.client.render.gui.ChestScreenCreator;
import terramine.common.init.ModBlockEntityType;
import terramine.common.init.ModScreenHandlerType;
import terramine.common.utility.PlayerInventories;

public class SafeEntity extends ChestEntity {
    public SafeEntity(BlockPos pos, BlockState state) {
        super("safe", ModScreenHandlerType.SAFE, ModBlockEntityType.SAFE, pos, state);
    }

    @Override
    public void startOpen(@NotNull Player player) {
    }

    @Override
    public void stopOpen(@NotNull Player player) {
    }

    @Override
    public AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory, @NotNull Player player) {
        PlayerInventories inv = new PlayerInventories(player);
        inv.setActiveBlockEntity(this);
        return new ChestScreenCreator(40, menu, i, inventory, inv);
    }
}
