package terramine.common.item.armor.vanity;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import terramine.TerraMine;
import terramine.client.render.accessory.model.HeadModel;
import terramine.common.init.ModModelLayers;

public class TopHatVanity extends VanityArmor {
    public TopHatVanity(String armorType, ArmorMaterial armorMaterial, EquipmentSlot equipmentSlot, Properties properties) {
        super(armorType, armorMaterial, equipmentSlot, properties);
    }

    @Override
    public HumanoidModel<LivingEntity> getCustomArmorModel() {
        return new HeadModel(bakeLayer(ModModelLayers.TOP_HAT));
    }

    @Override
    public String getCustomArmorLocation() {
        return TerraMine.MOD_ID + ":textures/models/vanity/top_hat.png";
    }
}
