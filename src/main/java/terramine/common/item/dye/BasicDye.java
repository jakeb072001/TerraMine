package terramine.common.item.dye;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import terramine.common.item.TerrariaItem;

import java.util.List;

public class BasicDye extends TerrariaItem {
    public int colour;

    public BasicDye(int colour, ResourceKey<Item> key) {
        super(new Properties().setId(key).stacksTo(16).rarity(Rarity.UNCOMMON).fireResistant(), false);
        this.colour = colour;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
    }

    public int getColourInt() {
        return colour;
    }
}
