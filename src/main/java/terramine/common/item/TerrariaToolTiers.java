package terramine.common.item;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import terramine.common.init.ModItems;

public enum TerrariaToolTiers implements Tier {

    DEMONITE(3, 500, 7.5f, 2.5f, 17, Ingredient.of(ModItems.DEMONITE_INGOT)),
    CRIMTANE(3, 500, 7.0f, 3.0f, 17, Ingredient.of(ModItems.CRIMTANE_INGOT)),
    MOLTEN(4, 1600, 7.5f, 5.0f, 20, Ingredient.of(ModItems.HELLSTONE_INGOT));

    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final Ingredient repairIngredient;

    TerrariaToolTiers(int j, int k, float f, float g, int l, Ingredient supplier) {
        this.level = j;
        this.uses = k;
        this.speed = f;
        this.damage = g;
        this.enchantmentValue = l;
        this.repairIngredient = supplier;
    }

    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.damage;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient;
    }
}
