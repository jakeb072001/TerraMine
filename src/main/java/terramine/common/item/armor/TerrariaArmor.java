package terramine.common.item.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import terramine.TerraMine;
import terramine.common.utility.equipmentchecks.ArmorSetCheck;

import java.util.List;
import java.util.UUID;

// todo: custom models: https://github.com/Luke100000/ImmersiveArmors/blob/1.19.2/common/src/main/java/immersive_armors/mixin/MixinArmorFeatureRenderer.java ?
// todo: trail effect, like in terraria when the full set is equipped, both these todos go for all armor
public class TerrariaArmor extends ArmorItem {
    private final String armorType;
    protected final int defense;
    protected final float toughness;
    protected Multimap<Attribute, AttributeModifier> attributeModifiers;
    protected static final UUID[] ARMOR_MODIFIER_UUID_PER_SLOT = new UUID[]{
            UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"),
            UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"),
            UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"),
            UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")
    };

    public TerrariaArmor(String armorType, ArmorMaterial armorMaterial, EquipmentSlot equipmentSlot, Properties properties) {
        super(armorMaterial, equipmentSlot, properties);

        this.armorType = armorType;
        this.defense = armorMaterial.getDefenseForSlot(equipmentSlot);
        this.toughness = armorMaterial.getToughness();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        UUID uUID = ARMOR_MODIFIER_UUID_PER_SLOT[equipmentSlot.getIndex()];
        builder.put(Attributes.ARMOR, new AttributeModifier(uUID, "Armor modifier", this.defense, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uUID, "Armor toughness", this.toughness, AttributeModifier.Operation.ADDITION));
        attributeModifiers = builder.build();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot equipmentSlot) {
        return equipmentSlot == this.slot ? this.attributeModifiers : super.getDefaultAttributeModifiers(equipmentSlot);
    }

    public static void addModifier(AttributeInstance instance, AttributeModifier modifier) {
        if (!instance.hasModifier(modifier)) {
            instance.addTransientModifier(modifier);
        }
    }

    public static void removeModifier(AttributeInstance instance, AttributeModifier modifier) {
        if (instance.hasModifier(modifier)) {
            instance.removeModifier(modifier);
        }
    }

    public String getArmorType() {
        return armorType;
    }

    @Environment(EnvType.CLIENT)
    public HumanoidModel<LivingEntity> getCustomArmorModel() {
        return null;
    }

    public String getCustomArmorLocation() {
        return null;
    }

    @Environment(EnvType.CLIENT)
    public static ModelPart bakeLayer(ModelLayerLocation layerLocation) {
        return Minecraft.getInstance().getEntityModels().bakeLayer(layerLocation);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull Entity entity, int i, boolean bl) {
        super.inventoryTick(itemStack, level, entity, i, bl);

        boolean isEquipped;
        if (entity instanceof LivingEntity livingEntity) {
            ItemStack equippedStack = livingEntity.getItemBySlot(getSlot());
            if (equippedStack == itemStack) {
                isEquipped = ArmorSetCheck.isSetEquipped(livingEntity, this.getArmorType());

                if (isEquipped) {
                    if (itemStack.getItem() instanceof TerrariaArmor armor && armor.getSlot() == EquipmentSlot.HEAD) { // do this so the set bonus only happens once and not per armor (4 times the buff)
                        setBonusEffect(livingEntity, level);
                    }
                } else {
                    removeBonusEffect(livingEntity, level);
                }
            }
        }
    }

    public void setBonusEffect(LivingEntity livingEntity, Level level) {
    }

    public void removeBonusEffect(LivingEntity livingEntity, Level level) {
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags) {
        if (TerraMine.CONFIG.general.showTooltips) {
            appendTooltipDescription(tooltip, this.getDescriptionId() + ".tooltip");

            // Checks if the player is wearing a full set of one type of armor, then display the set bonus
            boolean isEquipped = false;
            if (Minecraft.getInstance().player != null) {
                isEquipped = ArmorSetCheck.isSetEquipped(Minecraft.getInstance().player, this.getArmorType());
            }
            if (isEquipped) {
                appendTooltipDescription(tooltip, "item." + TerraMine.MOD_ID + "." + armorType + ".setbonus");
            }
        }
    }

    public String[] getREITooltip() {
        return Language.getInstance().getOrDefault(this.getDescriptionId() + ".tooltip").replace("%%", "%").split("\n");
    }

    public String[] getREISetBonusTooltip() {
        return Language.getInstance().getOrDefault("item." + TerraMine.MOD_ID + "." + armorType + ".setbonus").replace("%%", "%").split("\n");
    }

    protected void appendTooltipDescription(List<Component> tooltip, String translKey) {
        String[] lines = Language.getInstance().getOrDefault(translKey).split("\n");

        for (String line : lines) {
            tooltip.add(Component.literal(line).withStyle(ChatFormatting.GRAY));
        }
    }
}
