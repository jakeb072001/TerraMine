package terramine.common.item.armor.vanity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import terramine.common.item.armor.TerrariaArmor;

import java.util.List;

public class VanityArmor extends TerrariaArmor {
    public VanityArmor(String armorType, ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorType, armorMaterial, type, properties);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags) {
    }
}
