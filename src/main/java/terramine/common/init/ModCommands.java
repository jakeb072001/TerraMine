package terramine.common.init;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.commands.CommandSourceStack;
import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules;

public class ModCommands {
    public static GameRules.Key<GameRules.IntegerValue> MANA_REGEN_SPEED;
    public static GameRules.Key<GameRules.BooleanValue> MANA_INFINITE;
    public static LiteralCommandNode<CommandSourceStack> GETSETMANA;

    public static void registerRules() {
        MANA_REGEN_SPEED = GameRuleRegistry.register("manaRegenSpeed", GameRules.Category.PLAYER, GameRuleFactory.createIntRule(3, 0));
        MANA_INFINITE = GameRuleRegistry.register("manaInfinite", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(false));
    }

    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        GETSETMANA = dispatcher.register(literal("mana")
                .requires(cs -> cs.hasPermission(0))
                .then(literal("get")
                        .then(argument("player", EntityArgument.player())
                                .executes(context -> getMaxMana(context, EntityArgument.getPlayer(context, "player")))
                        )
                )
                .then(literal("set")
                        .requires(cs -> cs.hasPermission(2))
                        .then(argument("player", EntityArgument.player())
                                .then(argument("int", IntegerArgumentType.integer(0, 10))
                                        .executes(context -> setMaxMana(context, EntityArgument.getPlayer(context, "player"), IntegerArgumentType.getInteger(context, "int")))
                                )
                        )
                )
        );
    }

    private static int setMaxMana(CommandContext<CommandSourceStack> context, ServerPlayer player, int value) {
        int i = 0;
        if (player != null) {
            i++;
            ModComponents.MANA_HANDLER.get(player).setMaxMana(value * 20);
            context.getSource().sendSuccess(Component.translatable("commands.setMaxMana.pass", player.getDisplayName(), value), false);
        } else {
            i--;
            context.getSource().sendFailure(Component.translatable("commands.setMaxMana.fail", value));
        }
        return i;
    }
    private static int getMaxMana(CommandContext<CommandSourceStack> context, ServerPlayer player) {
        int i = 0;
        if (player != null) {
            i++;
            if (ModComponents.MANA_HANDLER.get(player).getMaxMana() == 0) {
                context.getSource().sendSuccess(Component.translatable("commands.getMaxMana.noMana", player.getDisplayName()), false);
            } else {
                context.getSource().sendSuccess(Component.translatable("commands.getMaxMana.pass", player.getDisplayName(), ModComponents.MANA_HANDLER.get(player).getMaxMana() / 20), false);
            }
        } else {
            i--;
            context.getSource().sendFailure(Component.translatable("commands.getMaxMana.fail"));
        }
        return i;
    }
}
