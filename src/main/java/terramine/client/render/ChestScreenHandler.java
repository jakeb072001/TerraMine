package terramine.client.render;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;

public class ChestScreenHandler extends SyncedGuiDescription {
    Container inventory;

    public ChestScreenHandler(int size, MenuType<?> type, int syncId, Inventory playerInventory, ContainerLevelAccess ctx) {
        super(type, syncId, playerInventory, getBlockInventory(ctx, size), null);
        inventory = blockInventory;
        int rows = 4;
        int length = 10;

        WPlainPanel root = new WPlainPanel();
        setRootPanel(root);

        WItemSlot itemSlot;
        int counter = 0;
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < length; i++) {
                itemSlot = WItemSlot.of(blockInventory, counter);
                root.add(itemSlot, (18 * i), 12 + (18 * j));
                counter++;
            }
        }

        // Sets the correct GUI Size
        root.setInsets(Insets.ROOT_PANEL);

        int height = 15;
        height += 18 * (size / length);
        int width = 9;

        root.add(this.createPlayerInventoryPanel(), width, height);
        root.validate(this);
    }

    public Container getContainer() {
        return this.inventory;
    }
}
