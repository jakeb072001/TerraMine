package terramine.common.item.armor.vanity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import terramine.common.item.armor.TerrariaArmor;

import java.util.List;

public class VanityArmor extends TerrariaArmor {
    public VanityArmor(ArmorMaterial holder, ArmorType type, Properties properties) {
        super("vanity", holder, type, properties);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
    }

    public String getCustomArmorLocation() {
        return "empty";
    }
}
