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
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.TerraMine;
import terramine.common.network.ServerPacketHandler;

@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeModeInventoryScreenMixin extends EffectRenderingInventoryScreen<CreativeModeInventoryScreen.ItemPickerMenu> {
    @Shadow public abstract int getSelectedTab();

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
                ClientPlayNetworking.send(ServerPacketHandler.OPEN_INVENTORY_PACKET_ID, new FriendlyByteBuf(Unpooled.buffer()));
            }));
        }

        terrariaButton.visible = false;
    }

    // todo: both below methods together looks best but probably not the cleanest way, maybe improve later, not too important
    @Inject(method = "selectTab", at = @At("HEAD"))
    private void onSelectTab(CreativeModeTab creativeModeTab, CallbackInfo ci) {
        if (terrariaButton != null) {
            terrariaButton.visible = creativeModeTab == CreativeModeTab.TAB_INVENTORY;
        }
    }

    @Inject(method = "containerTick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        if (terrariaButton != null) {
            terrariaButton.visible = getSelectedTab() == CreativeModeTab.TAB_INVENTORY.getId();
        }
    }
}
