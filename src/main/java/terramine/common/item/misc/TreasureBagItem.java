package terramine.common.item.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import terramine.client.render.gui.menu.TreasureBagInventoryContainerMenu;
import terramine.common.item.TerrariaItemConfigurable;
import terramine.common.misc.TreasureBagInventory;

import java.util.List;

public class TreasureBagItem extends TerrariaItemConfigurable {
    public TreasureBagInventory treasureBagInventory;
    protected ResourceLocation lootTable;

    public TreasureBagItem(Properties properties, ResourceLocation lootTable) {
        super(properties);
        this.lootTable = lootTable;
    }

    @Override
    public UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.BLOCK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        TreasureBagInventory treasureBagInventory = new TreasureBagInventory(itemStack, player, lootTable);
        this.treasureBagInventory = treasureBagInventory;
        player.startUsingItem(hand);

        if (player instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)player, itemStack);
        }

        if (!level.isClientSide) {
            TreasureBagInventoryContainerMenu menu = new TreasureBagInventoryContainerMenu(player, treasureBagInventory);
            player.openMenu(new SimpleMenuProvider((id, inventory, player2) -> menu, Component.empty()));
        }

        return InteractionResultHolder.consume(itemStack);
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 1;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags) {
    }
}
