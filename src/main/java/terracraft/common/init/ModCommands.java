package terracraft.common.init;

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
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules;

public class ModCommands {
    public static GameRules.Key<GameRules.IntegerValue> MANA_REGEN_SPEED;
    public static GameRules.Key<GameRules.BooleanValue> MANA_INFINITE;
    public static LiteralCommandNode<CommandSourceStack> SETMANA;
    public static LiteralCommandNode<CommandSourceStack> GETMANA;

    public static void registerRules() {
        MANA_REGEN_SPEED = GameRuleRegistry.register("manaRegenSpeed", GameRules.Category.PLAYER, GameRuleFactory.createIntRule(3, 0));
        MANA_INFINITE = GameRuleRegistry.register("manaInfinite", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(false));
    }

    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        SETMANA = dispatcher.register(literal("setmaxmana")
                .requires(cs -> cs.hasPermission(3))
                .then(argument("player", EntityArgument.player())
                        .then(argument("int", IntegerArgumentType.integer(0, 10))
                            .executes(context -> setMaxMana(context, EntityArgument.getPlayer(context, "player"), IntegerArgumentType.getInteger(context, "int")))
                        )
                )
        );

        GETMANA = dispatcher.register(literal("getmaxmana")
                .requires(cs -> cs.hasPermission(2))
                .then(argument("player", EntityArgument.player())
                        .executes(context -> getMaxMana(context, EntityArgument.getPlayer(context, "player")))
                )
        );
    }

    private static int setMaxMana(CommandContext<CommandSourceStack> context, ServerPlayer player, int value) {
        int i = 0;
        if (player != null) {
            i++;
            ModComponents.MANA_HANDLER.get(player).setMaxMana(value * 20);
            context.getSource().sendSuccess(new TranslatableComponent("commands.setMaxMana.pass", player.getDisplayName(), value), false);
        } else {
            i--;
            context.getSource().sendFailure(new TranslatableComponent("commands.setMaxMana.fail", value));
        }
        return i;
    }
    private static int getMaxMana(CommandContext<CommandSourceStack> context, ServerPlayer player) {
        int i = 0;
        if (player != null) {
            i++;
            if (ModComponents.MANA_HANDLER.get(player).getMaxMana() == 0) {
                context.getSource().sendSuccess(new TranslatableComponent("commands.getMaxMana.noMana", player.getDisplayName()), false);
            } else {
                context.getSource().sendSuccess(new TranslatableComponent("commands.getMaxMana.pass", player.getDisplayName(), ModComponents.MANA_HANDLER.get(player).getMaxMana() / 20), false);
            }
        } else {
            i--;
            context.getSource().sendFailure(new TranslatableComponent("commands.getMaxMana.fail"));
        }
        return i;
    }
}
