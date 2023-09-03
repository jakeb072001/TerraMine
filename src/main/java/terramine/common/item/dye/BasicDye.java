package terramine.common.item.dye;

import com.mojang.math.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import terramine.TerraMine;
import terramine.common.item.TerrariaItem;
import terramine.common.utility.Utilities;

import java.util.List;

public class BasicDye extends TerrariaItem {
    public Vector3f colour;

    public BasicDye(int colour) {
        super(new Properties().stacksTo(16).tab(TerraMine.ITEM_GROUP_DYES).rarity(Rarity.UNCOMMON).fireResistant(), false);
        this.colour = Utilities.colorFromInt(colour);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags) {
    }

    public Vector3f getColour() {
        return colour;
    }
}
