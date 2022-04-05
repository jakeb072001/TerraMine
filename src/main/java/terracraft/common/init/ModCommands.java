package terracraft.common.init;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.level.GameRules;

public class ModCommands {
    public static GameRules.Key<GameRules.IntegerValue> MANA_REGEN_SPEED;

    public static void registerAll() {
        MANA_REGEN_SPEED = GameRuleRegistry.register("manaRegenSpeed", GameRules.Category.PLAYER, GameRuleFactory.createIntRule(2, 0));
    }
}
