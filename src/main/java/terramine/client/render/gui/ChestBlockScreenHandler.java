package terramine.client.render.gui;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class ChestBlockScreenHandler extends CottonInventoryScreen<ChestScreenCreator> {
    public ChestBlockScreenHandler(ChestScreenCreator description, Player player, Component title) {
        super(description, player, title);
    }
}
