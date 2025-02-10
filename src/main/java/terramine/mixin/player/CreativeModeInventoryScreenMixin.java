package terramine.mixin.player;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
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
import terramine.client.render.gui.ToggleImageButton;
import terramine.common.network.ServerPacketHandler;
import terramine.common.network.types.LongNetworkType;

@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeModeInventoryScreenMixin extends AbstractContainerScreen<CreativeModeInventoryScreen.ItemPickerMenu> {
    @Shadow private static CreativeModeTab selectedTab;
    @Unique
    private static final ResourceLocation BUTTON_TEX = TerraMine.id("textures/gui/terraria_slots_button.png");

    @Unique
    private static ToggleImageButton terrariaButton;

    public CreativeModeInventoryScreenMixin(CreativeModeInventoryScreen.ItemPickerMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
    }

    @Inject(method = "init", at = @At("TAIL"))
    protected void onInit(CallbackInfo ci) {
        if (this.minecraft.gameMode.hasInfiniteItems()) {
            ScreenPosition screenPosition = new ScreenPosition(this.leftPos + 96, this.height / 2 - 28);
            this.addRenderableWidget(terrariaButton = new ToggleImageButton(screenPosition.x(), screenPosition.y(), 8, 8, 0, 0, 8, 0, 0, false, BUTTON_TEX, 8, 16, (buttonWidget) -> {
                ClientPlayNetworking.send(new LongNetworkType(0L, ServerPacketHandler.OPEN_INVENTORY_PACKET_ID));
            }));
        }

        terrariaButton.visible = false;
    }

    // todo: both below methods together looks best but probably not the cleanest way, maybe improve later, not too important
    @Inject(method = "selectTab", at = @At("HEAD"))
    private void onSelectTab(CreativeModeTab creativeModeTab, CallbackInfo ci) {
        if (terrariaButton != null) {
            terrariaButton.visible = creativeModeTab.getType() == CreativeModeTab.Type.INVENTORY;
        }
    }

    @Inject(method = "containerTick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        if (terrariaButton != null) {
            terrariaButton.visible = selectedTab.getType().equals(CreativeModeTab.Type.INVENTORY);
        }
    }
}
