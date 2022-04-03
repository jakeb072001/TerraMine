package terracraft.common.init;

import terracraft.Artifacts;
import terracraft.common.loot.ConfigurableRandomChance;
import net.minecraft.core.Registry;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class ModLootConditions {

    public static final LootItemConditionType CONFIGURABLE_ARTIFACT_CHANCE = new LootItemConditionType(new ConfigurableRandomChance.Serializer());

    public static void register() {
        Registry.register(Registry.LOOT_CONDITION_TYPE, Artifacts.id("configurable_random_chance"), CONFIGURABLE_ARTIFACT_CHANCE);
    }
}
