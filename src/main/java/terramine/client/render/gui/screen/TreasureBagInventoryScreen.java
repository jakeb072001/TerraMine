package terramine.client.render.gui.screen;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import terramine.TerraMine;
import terramine.client.render.gui.menu.TreasureBagInventoryContainerMenu;

import java.util.List;

// todo: sometimes when clicking a slot the item isn't picked up or placed down
@Environment(EnvType.CLIENT)
public class TreasureBagInventoryScreen extends EffectRenderingInventoryScreen<TreasureBagInventoryContainerMenu> {
    private static final ResourceLocation CONTAINER = TerraMine.id("textures/gui/container/treasure_bag.png");
    private final int imageWidth = 176;
    private final int imageHeight = 152;
    private boolean buttonClicked;

    public TreasureBagInventoryScreen(Player player) {
        super(new TreasureBagInventoryContainerMenu(player), player.getInventory(), Component.empty());
    }

    protected void init() {
        super.init();
    }

    protected void renderLabels(@NotNull GuiGraphics guiGraphics, int i, int j) {
        // todo: add treasure bag name (which boss it's from)
    }

    public void render(@NotNull GuiGraphics guiGraphics, int i, int j, float f) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, i, j, f);
        this.renderTooltip(guiGraphics, i, j);
    }

    protected void renderBg(@NotNull GuiGraphics guiGraphics, float f, int i, int j) {
        int k = (this.width - this.imageWidth) / 2;
        int l = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(CONTAINER, k, l, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
    }

    protected boolean isHovering(int i, int j, int k, int l, double d, double e) {
        return super.isHovering(i, j, k, l, d, e);
    }

    public boolean mouseClicked(double d, double e, int i) {
        return super.mouseClicked(d, e, i);
    }

    public boolean mouseReleased(double d, double e, int i) {
        if (this.buttonClicked) {
            this.buttonClicked = false;
            return true;
        } else {
            return super.mouseReleased(d, e, i);
        }
    }

    // todo: fix, items don't throw out of inventory
    protected boolean hasClickedOutside(double d, double e, int i, int j, int k) {
        return d < (double)i || e < (double)j - 26 || d >= (double)(i + this.imageWidth) || e >= (double)(j + this.imageHeight);
    }

    protected void slotClicked(Slot slot, int i, int j, @NotNull ClickType clickType) {
        if (slot != null && this.minecraft != null) {
            i = slot.index;

            Player player = this.minecraft.player;
            int k = this.menu.containerId;
            if (player != null) {
                AbstractContainerMenu abstractContainerMenu = this.getMenu();
                if (k != abstractContainerMenu.containerId) {
                    TerraMine.LOGGER.warn("Ignoring click in mismatching container. Click in {}, player has {}.", k, abstractContainerMenu.containerId);
                } else {
                    NonNullList<Slot> slots = abstractContainerMenu.slots;
                    int l = slots.size();
                    List<ItemStack> list = Lists.newArrayListWithCapacity(l);

                    for (Slot slot2 : slots) {
                        list.add(slot2.getItem().copy());
                    }

                    abstractContainerMenu.clicked(i, j, clickType, player);
                    Int2ObjectMap<ItemStack> int2ObjectMap = new Int2ObjectOpenHashMap<>();

                    for (int m = 0; m < l; ++m) {
                        ItemStack itemStack = list.get(m);
                        ItemStack itemStack2 = slots.get(m).getItem();
                        if (!ItemStack.matches(itemStack, itemStack2)) {
                            int2ObjectMap.put(m, itemStack2.copy());
                        }
                    }

                    this.minecraft.getConnection().send(new ServerboundContainerClickPacket(k, abstractContainerMenu.getStateId(), i, j, clickType, abstractContainerMenu.getCarried().copy(), int2ObjectMap));
                }
            }
        }
    }

    public void removed() {
        super.removed();
        //this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }
}
