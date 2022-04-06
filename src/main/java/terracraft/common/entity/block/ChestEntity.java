package terracraft.common.entity.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import terracraft.TerraCraft;
import terracraft.client.render.ChestScreenHandler;

public class ChestEntity extends ChestBlockEntity {
    String name;
    MenuType<ChestScreenHandler> menu;

    public ChestEntity(String name, MenuType<ChestScreenHandler> menu, BlockEntityType type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.name = name;
        this.menu = menu;
        this.setItems(NonNullList.withSize(40, ItemStack.EMPTY));
    }

    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new ChestScreenHandler(40, menu, i, inventory, ContainerLevelAccess.create(level, getBlockPos()));
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("entity." + TerraCraft.MOD_ID + "." + name);
    }

    @Override
    public int getContainerSize() {
        return 40;
    }
}
