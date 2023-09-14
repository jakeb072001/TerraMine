package terramine.common.item.armor.vanity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import terramine.TerraMine;
import terramine.client.render.accessory.model.HeadModel;
import terramine.common.init.ModModelLayers;

// todo: custom models don't work on server
public class TopHatVanity extends VanityArmor {
    public TopHatVanity(String armorType, ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorType, armorMaterial, type, properties);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public HumanoidModel<LivingEntity> getCustomArmorModel() {
        return new HeadModel(bakeLayer(ModModelLayers.TOP_HAT));
    }

    @Override
    public String getCustomArmorLocation() {
        return TerraMine.MOD_ID + ":textures/models/vanity/top_hat.png";
    }
}
