package terramine.client.render.gui.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
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
        super(new TerrariaInventoryContainerMenu(player), player.getInventory(), TextComponent.EMPTY);
        this.passEvents = true;
    }

    protected void init() {
        super.init();
        rotation = 0;
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        this.addRenderableWidget(new ImageButton(this.leftPos + 63, this.height / 2 - 100, 8, 8, 0, 0, 8, ROTATE_RIGHT_TEX, 8, 16, (buttonWidget) -> {
            rotation -= 40;
        }));
        this.addRenderableWidget(new ImageButton(this.leftPos + 104, this.height / 2 - 100, 8, 8, 0, 0, 8, ROTATE_LEFT_TEX, 8, 16, (buttonWidget) -> {
            rotation += 40;
        }));
        if (this.minecraft.gameMode.hasInfiniteItems()) {
            this.addRenderableWidget(new ImageButton(this.leftPos + 105, this.height / 2 - 40, 8, 8, 0, 0, 8, BUTTON_TEX, 8, 16, (buttonWidget) -> {
                this.minecraft.setScreen(new CreativeModeInventoryScreen(this.minecraft.player));
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

    protected void renderLabels(@NotNull PoseStack poseStack, int i, int j) {
        this.font.draw(poseStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);
    }

    public void render(@NotNull PoseStack poseStack, int i, int j, float f) {
        this.renderBackground(poseStack);
        super.render(poseStack, i, j, f);
        this.renderTooltip(poseStack, i, j);
        this.xMouse = (float)i;
        this.yMouse = (float)j;
    }

    protected void renderBg(@NotNull PoseStack poseStack, float f, int i, int j) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        if (ModComponents.ACCESSORY_SLOTS_ADDER.get(this.minecraft.player).get() == 1) {
            RenderSystem.setShaderTexture(0, TERRARIA_CONTAINER_6);
        } else if (ModComponents.ACCESSORY_SLOTS_ADDER.get(this.minecraft.player).get() == 2) {
            RenderSystem.setShaderTexture(0, TERRARIA_CONTAINER_7);
        } else {
            RenderSystem.setShaderTexture(0, TERRARIA_CONTAINER_5);
        }
        int k = (this.width - this.imageWidth) / 2;
        int l = (this.height - this.imageHeight) / 2;
        blit(poseStack, k, l, this.getBlitOffset(), 0f, 0f, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        renderEntityInInventory(k + 88, l + 71, 30, (k + 51f) - this.xMouse, l + 75f - 50f - this.yMouse, this.minecraft.player);
    }

    public void renderEntityInInventory(int i, int j, int k, float f, float g, LivingEntity livingEntity) {
        float h = (float)Math.atan(f / 40.0F);
        float l = (float)Math.atan(g / 40.0F);
        PoseStack poseStack = RenderSystem.getModelViewStack();
        poseStack.pushPose();
        poseStack.translate(i, j, 1050.0);
        poseStack.scale(1.0F, 1.0F, -1.0F);
        RenderSystem.applyModelViewMatrix();
        PoseStack poseStack2 = new PoseStack();
        poseStack2.translate(0.0, 0.0, 1000.0);
        poseStack2.scale((float)k, (float)k, (float)k);
        Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0F);
        Quaternion quaternion2 = Vector3f.XP.rotationDegrees(l * 20.0F);
        Quaternion quaternion3 = Vector3f.YP.rotationDegrees(rotation);
        quaternion.mul(quaternion2);
        quaternion.mul(quaternion3);
        poseStack2.mulPose(quaternion);
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
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        quaternion2.conj();
        entityRenderDispatcher.overrideCameraOrientation(quaternion2);
        entityRenderDispatcher.setRenderShadow(false);
        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> {
            entityRenderDispatcher.render(livingEntity, 0.0, 0.0, 0.0, 0.0F, 1.0F, poseStack2, bufferSource, 15728880);
        });
        bufferSource.endBatch();
        entityRenderDispatcher.setRenderShadow(true);
        livingEntity.yBodyRot = m;
        livingEntity.setYRot(n);
        livingEntity.setXRot(o);
        livingEntity.yHeadRotO = p;
        livingEntity.yHeadRot = q;
        poseStack.popPose();
        RenderSystem.applyModelViewMatrix();
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
        this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }
}
