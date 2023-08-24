package terramine.mixin.player;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.TerraMine;
import terramine.common.network.ServerPacketHandler;

@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeModeInventoryScreenMixin extends EffectRenderingInventoryScreen<CreativeModeInventoryScreen.ItemPickerMenu> {
    @Unique
    private static final ResourceLocation BUTTON_TEX = TerraMine.id("textures/gui/terraria_slots_button.png");

    @Unique
    private static ImageButton terrariaButton;

    public CreativeModeInventoryScreenMixin(CreativeModeInventoryScreen.ItemPickerMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
    }

    @Inject(method = "init", at = @At("TAIL"))
    protected void onInit(CallbackInfo ci) {
        if (this.minecraft.gameMode.hasInfiniteItems()) {
            this.addRenderableWidget(terrariaButton = new ImageButton(this.leftPos + 96, this.height / 2 - 28, 8, 8, 0, 0, 8, BUTTON_TEX, 8, 16, (buttonWidget) -> {
                ClientPlayNetworking.send(ServerPacketHandler.SETUP_INVENTORY_PACKET_ID, new FriendlyByteBuf(Unpooled.buffer()));
            }));
        }
    }

    // todo: if opening another screen like the rei settings and then returning this isn't called and terrariaButton.visible is set to true, fix plz
    // todo: maybe use containerTick again, I don't remember what issue that had
    @Inject(method = "selectTab", at = @At("HEAD"))
    private void onSelectTab(CreativeModeTab creativeModeTab, CallbackInfo ci) {
        if (terrariaButton != null) {
            terrariaButton.visible = creativeModeTab == CreativeModeTab.TAB_INVENTORY;
        }
    }
}
