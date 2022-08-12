package terramine.common.item.armor;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import terramine.TerraMine;
import terramine.common.trinkets.TrinketsHelper;

import java.util.List;
import java.util.UUID;

public class TerrariaArmor extends ArmorItem implements Trinket {
    String armorType;

    public TerrariaArmor(String armorType, ArmorMaterial armorMaterial, EquipmentSlot equipmentSlot, Properties properties) {
        super(armorMaterial, equipmentSlot, properties);
        this.armorType = armorType;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags) {
        if (TerraMine.CONFIG.general.showTooltips) {
            appendTooltipDescription(tooltip, this.getDescriptionId() + ".tooltip");

            // Checks if the player is wearing a full set of one type of armor, then display the set bonus
            boolean isEquipped = false;
            if (Minecraft.getInstance().player != null) {
                for (ItemStack item : Minecraft.getInstance().player.getArmorSlots()) {
                    if (item.getItem() instanceof TerrariaArmor armorItem) {
                        isEquipped = armorItem.getMaterial() == this.getMaterial();
                        if (!isEquipped) {
                            break;
                        }
                    } else {
                        isEquipped = false;
                        break;
                    }
                }
            }
            if (isEquipped) {
                appendTooltipDescription(tooltip, "item." + TerraMine.MOD_ID + "." + armorType + ".setbonus");
            }
        }
    }

    public String[] getREITooltip() {
        return Language.getInstance().getOrDefault(this.getDescriptionId() + ".tooltip").replace("%%", "%").split("\n");
    }

    protected void appendTooltipDescription(List<Component> tooltip, String translKey) {
        String[] lines = Language.getInstance().getOrDefault(translKey).split("\n");

        for (String line : lines) {
            tooltip.add(Component.literal(line).withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public final Multimap<Attribute, AttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
        Multimap<Attribute, AttributeModifier> modifiers = Trinket.super.getModifiers(stack, slot, entity, uuid);
        if (TrinketsHelper.areEffectsEnabled(stack, entity) && entity instanceof Player) {
            Multimap<Attribute, AttributeModifier> accessoryModifiers = this.applyModifiers(stack, slot, entity, uuid);
            modifiers.putAll(accessoryModifiers);
        }
        return modifiers;
    }

    protected Multimap<Attribute, AttributeModifier> applyModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
        return HashMultimap.create();
    }
}
