package terracraft.common.init;

import net.minecraft.core.Registry;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import terracraft.TerraCraft;
import terracraft.common.loot.ConfigurableRandomChance;

public class ModLootConditions {

    public static final LootItemConditionType CONFIGURABLE_ACCESSORY_CHANCE = new LootItemConditionType(new ConfigurableRandomChance.Serializer());

    public static void register() {
        Registry.register(Registry.LOOT_CONDITION_TYPE, TerraCraft.id("configurable_random_chance"), CONFIGURABLE_ACCESSORY_CHANCE);
    }
}
