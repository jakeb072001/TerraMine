package terramine.client.render;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class ChestBlockScreenHandler extends CottonInventoryScreen<ChestScreenHandler> {
    public ChestBlockScreenHandler(ChestScreenHandler description, Player player, Component title) {
        super(description, player, title);
    }
}
