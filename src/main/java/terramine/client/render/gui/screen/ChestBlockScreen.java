package terramine.client.render.gui.screen;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import terramine.client.render.gui.menu.ChestBlockContainerMenu;

public class ChestBlockScreen extends CottonInventoryScreen<ChestBlockContainerMenu> {
    public ChestBlockScreen(ChestBlockContainerMenu description, Player player, Component title) {
        super(description, player, title);
    }
}
