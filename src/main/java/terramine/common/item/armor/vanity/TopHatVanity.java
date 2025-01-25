package terramine.common.item.armor.vanity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import terramine.common.init.ModModelLayers;

// todo: custom models don't work on server
public class TopHatVanity extends VanityArmor {
    public TopHatVanity(ArmorMaterial armorMaterial, ArmorType type, Properties properties) {
        super(armorMaterial, type, properties);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public HumanoidModel<PlayerRenderState> getCustomArmorModel() {
        return new HumanoidModel<>(bakeLayer(ModModelLayers.TOP_HAT));
    }

    @Override
    public String getCustomArmorLocation() {
        return "top_hat";
    }
}
