package terramine.common.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import terramine.common.init.ModItems;

import java.util.function.Supplier;

public enum TerrariaArmorMaterials implements ArmorMaterial {

    SHADOW("shadow", 20, new int[]{3, 5, 7, 2}, 9, SoundEvents.ARMOR_EQUIP_IRON, 1.0F, 0.0F, Ingredient.of(ModItems.DEMONITE_INGOT)),
    CRIMSON("crimson", 20, new int[]{3, 5, 7, 2}, 9, SoundEvents.ARMOR_EQUIP_IRON, 1.0F, 0.0F, Ingredient.of(ModItems.CRIMTANE_INGOT));

    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
    private final String name;
    private final int durabilityMultiplier;
    private final int[] slotProtections;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final Ingredient repairIngredient;

    TerrariaArmorMaterials(String string2, int j, int[] is, int k, SoundEvent soundEvent, float f, float g, Ingredient supplier) {
        this.name = string2;
        this.durabilityMultiplier = j;
        this.slotProtections = is;
        this.enchantmentValue = k;
        this.sound = soundEvent;
        this.toughness = f;
        this.knockbackResistance = g;
        this.repairIngredient = supplier;
    }

    @Override
    public int getDurabilityForSlot(EquipmentSlot equipmentSlot) {
        return HEALTH_PER_SLOT[equipmentSlot.getIndex()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlot equipmentSlot) {
        return this.slotProtections[equipmentSlot.getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.sound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
