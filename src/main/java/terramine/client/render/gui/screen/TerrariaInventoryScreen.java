package terramine.client.render.gui.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Axis;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import terramine.TerraMine;
import terramine.client.render.gui.ToggleImageButton;
import terramine.client.render.gui.menu.TerrariaInventoryContainerMenu;
import terramine.common.init.ModComponents;
import terramine.common.network.ServerPacketHandler;

import java.util.List;

// todo: sometimes when clicking a slot the item isn't picked up or placed down
// todo: also items aren't thrown when clicking outside screen, hasClickedOutside is working correctly so not sure why this is happening
@Environment(EnvType.CLIENT)
public class TerrariaInventoryScreen extends EffectRenderingInventoryScreen<TerrariaInventoryContainerMenu> {
    private static final ResourceLocation BUTTON_TEX = TerraMine.id("textures/gui/terraria_slots_button.png");
    private static final ResourceLocation ROTATE_LEFT_TEX = TerraMine.id("textures/gui/terraria_rotate_left_button.png");
    private static final ResourceLocation ROTATE_RIGHT_TEX = TerraMine.id("textures/gui/terraria_rotate_right_button.png");
    private static final ResourceLocation EYE_TEX = TerraMine.id("textures/gui/visibility.png");
    private static final ResourceLocation TERRARIA_CONTAINER_5 = TerraMine.id("textures/gui/container/terraria_slots_5.png");
    private static final ResourceLocation TERRARIA_CONTAINER_6 = TerraMine.id("textures/gui/container/terraria_slots_6.png");
    private static final ResourceLocation TERRARIA_CONTAINER_7 = TerraMine.id("textures/gui/container/terraria_slots_7.png");
    private float xMouse;
    private float yMouse;
    private final int imageWidth = 176;
    private final int imageHeight = 218;
    private int rotation;
    private boolean buttonClicked;

    public TerrariaInventoryScreen(Player player) {
        super(new TerrariaInventoryContainerMenu(player), player.getInventory(), Component.empty());
    }

    protected void init() {
        super.init();
        rotation = 0;
        //this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        this.addRenderableWidget(new ImageButton(this.leftPos + 63, this.height / 2 - 100, 8, 8, 0, 0, 8, ROTATE_RIGHT_TEX, 8, 16, (buttonWidget) -> {
            rotation -= 40;
        }));
        this.addRenderableWidget(new ImageButton(this.leftPos + 104, this.height / 2 - 100, 8, 8, 0, 0, 8, ROTATE_LEFT_TEX, 8, 16, (buttonWidget) -> {
            rotation += 40;
        }));
        if (this.minecraft.gameMode.hasInfiniteItems()) {
            this.addRenderableWidget(new ImageButton(this.leftPos + 105, this.height / 2 - 40, 8, 8, 0, 0, 8, BUTTON_TEX, 8, 16, (buttonWidget) -> {
                this.minecraft.setScreen(new CreativeModeInventoryScreen(this.minecraft.player, this.minecraft.player.connection.enabledFeatures(), this.minecraft.options.operatorItemsTab().get()));
                this.buttonClicked = true;
            }));
        } else {
            this.addRenderableWidget(new ImageButton(this.leftPos + 105, this.height / 2 - 40, 8, 8, 0, 0, 8, BUTTON_TEX, 8, 16, (buttonWidget) -> {
                this.minecraft.setScreen(new InventoryScreen(minecraft.player));
                this.buttonClicked = true;
            }));
        }

        for (int i = 0; i < 5 + ModComponents.ACCESSORY_SLOTS_ADDER.get(this.minecraft.player).get(); i++) {
            int finalI = i;
            this.addRenderableWidget(new ToggleImageButton(this.leftPos + 130, this.height / 2 - 105 + (18 * i), 8, 8, 0, 0, 8, 8, i, EYE_TEX, 16, 16, (buttonWidget) -> {
                FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
                buf.writeInt(finalI);
                buf.writeBoolean(!ModComponents.ACCESSORY_VISIBILITY.get(this.minecraft.player).getSlotVisibility(finalI));
                ClientPlayNetworking.send(ServerPacketHandler.UPDATE_ACCESSORY_VISIBILITY_PACKET_ID, buf);
            }));
        }
    }

    protected void renderLabels(@NotNull GuiGraphics guiGraphics, int i, int j) {
    }

    public void render(@NotNull GuiGraphics guiGraphics, int i, int j, float f) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, i, j, f);
        this.renderTooltip(guiGraphics, i, j);
        this.xMouse = (float)i;
        this.yMouse = (float)j;
    }

    protected void renderBg(@NotNull GuiGraphics guiGraphics, float f, int i, int j) {
        int k = (this.width - this.imageWidth) / 2;
        int l = (this.height - this.imageHeight) / 2;
        if (ModComponents.ACCESSORY_SLOTS_ADDER.get(this.minecraft.player).get() == 1) {
            guiGraphics.blit(TERRARIA_CONTAINER_6, k, l, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        } else if (ModComponents.ACCESSORY_SLOTS_ADDER.get(this.minecraft.player).get() == 2) {
            guiGraphics.blit(TERRARIA_CONTAINER_7, k, l, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        } else {
            guiGraphics.blit(TERRARIA_CONTAINER_5, k, l, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        }
        //guiGraphics.blit(guiGraphics, k, l, this.getBlitOffset(), 0f, 0f, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        renderEntityInInventoryFollowsMouse(guiGraphics, k + 88, l + 71, 30, (k + 51f) - this.xMouse, l + 75f - 50f - this.yMouse, this.minecraft.player);
    }

    public void renderEntityInInventoryFollowsMouse(GuiGraphics guiGraphics, int i, int j, int k, float f, float g, LivingEntity livingEntity) {
        float h = (float)Math.atan(f / 40.0F);
        float l = (float)Math.atan(g / 40.0F);
        Quaternionf quaternionf = (new Quaternionf()).rotateZ(3.1415927F);
        Quaternionf quaternionf2 = (new Quaternionf()).rotateX(l * 20.0F * 0.017453292F);
        quaternionf.mul(quaternionf2);
        float m = livingEntity.yBodyRot;
        float n = livingEntity.getYRot();
        float o = livingEntity.getXRot();
        float p = livingEntity.yHeadRotO;
        float q = livingEntity.yHeadRot;
        livingEntity.yBodyRot = 180.0F + h * 20.0F;
        livingEntity.setYRot(180.0F + h * 40.0F);
        livingEntity.setXRot(-l * 20.0F);
        livingEntity.yHeadRot = livingEntity.getYRot();
        livingEntity.yHeadRotO = livingEntity.getYRot();
        renderEntityInInventory(guiGraphics, i, j, k, quaternionf, quaternionf2, livingEntity);
        livingEntity.yBodyRot = m;
        livingEntity.setYRot(n);
        livingEntity.setXRot(o);
        livingEntity.yHeadRotO = p;
        livingEntity.yHeadRot = q;
    }

    public void renderEntityInInventory(GuiGraphics guiGraphics, int i, int j, int k, Quaternionf quaternionf, @Nullable Quaternionf quaternionf2, LivingEntity livingEntity) {
        Quaternionf quaternion = Axis.YP.rotationDegrees(rotation);
        quaternionf.mul(quaternion);
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(i, j, 50.0);
        guiGraphics.pose().mulPoseMatrix((new Matrix4f()).scaling((float)k, (float)k, (float)(-k)));
        guiGraphics.pose().mulPose(quaternionf);
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        if (quaternionf2 != null) {
            quaternionf2.conjugate();
            entityRenderDispatcher.overrideCameraOrientation(quaternionf2);
        }

        entityRenderDispatcher.setRenderShadow(false);
        RenderSystem.runAsFancy(() -> {
            entityRenderDispatcher.render(livingEntity, 0.0, 0.0, 0.0, 0.0F, 1.0F, guiGraphics.pose(), guiGraphics.bufferSource(), 15728880);
        });
        guiGraphics.flush();
        entityRenderDispatcher.setRenderShadow(true);
        guiGraphics.pose().popPose();
        Lighting.setupFor3DItems();
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

    protected boolean hasClickedOutside(double d, double e, int i, int j, int k) {
        return d < (double)i || e < (double)j - 26 || d >= (double)(i + this.imageWidth) || e >= (double)(j + this.imageHeight - 26);
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
