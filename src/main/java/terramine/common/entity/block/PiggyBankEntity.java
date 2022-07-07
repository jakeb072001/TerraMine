package terramine.common.entity.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import terramine.client.render.ChestScreenHandler;
import terramine.common.init.ModBlockEntityType;
import terramine.common.init.ModScreenHandlerType;
import terramine.common.utility.PlayerInventories;

public class PiggyBankEntity extends ChestEntity {
    public PiggyBankEntity(BlockPos pos, BlockState state) {
        super("piggy_bank", ModScreenHandlerType.PIGGY_BANK, ModBlockEntityType.PIGGY_BANK, pos, state);
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
        return new ChestScreenHandler(40, menu, i, inventory, inv);
    }
}
