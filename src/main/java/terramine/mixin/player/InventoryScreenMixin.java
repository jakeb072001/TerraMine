package terramine.mixin.player;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
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

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractContainerScreen<InventoryMenu> implements RecipeUpdateListener {
    @Unique
    private static final ResourceLocation BUTTON_TEX = TerraMine.id("textures/gui/terraria_slots_button.png");

    @Shadow
    private boolean buttonClicked;

    public InventoryScreenMixin(InventoryMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
    }

    @Inject(method = "init", at = @At("TAIL"))
    protected void onInit(CallbackInfo ci) {
        if (!this.minecraft.gameMode.hasInfiniteItems()) {
            this.addRenderableWidget(new ToggleImageButton(this.leftPos + 66, this.height / 2 - 14, 8, 8, 0, 0, 8, 0, 0, false, BUTTON_TEX, 8, 16, (buttonWidget) -> {
                //this.minecraft.setScreen(new TerrariaInventoryHandler(this.minecraft.player));
                ClientPlayNetworking.send(new LongNetworkType(0L, ServerPacketHandler.OPEN_INVENTORY_PACKET_ID));
                this.buttonClicked = true;
            }));
        }
    }
}
