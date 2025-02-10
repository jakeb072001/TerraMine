package terramine.mixin.player;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
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
public abstract class InventoryScreenMixin extends AbstractRecipeBookScreen<InventoryMenu> {
    @Unique
    private static final ResourceLocation BUTTON_TEX = TerraMine.id("textures/gui/terraria_slots_button.png");
    @Unique
    private Button button_holder;

    @Shadow
    private boolean buttonClicked;

    public InventoryScreenMixin(InventoryMenu recipeBookMenu, RecipeBookComponent<?> recipeBookComponent, Inventory inventory, Component component) {
        super(recipeBookMenu, recipeBookComponent, inventory, component);
    }

    @Inject(method = "init", at = @At("TAIL"))
    protected void onInit(CallbackInfo ci) {
        if (!this.minecraft.gameMode.hasInfiniteItems()) {
            ScreenPosition screenPosition = new ScreenPosition(this.leftPos + 66, this.height / 2 - 14);
            button_holder = new ToggleImageButton(screenPosition.x(), screenPosition.y(), 8, 8, 0, 0, 8, 0, 0, false, BUTTON_TEX, 8, 16, (buttonWidget) -> {
                ClientPlayNetworking.send(new LongNetworkType(0L, ServerPacketHandler.OPEN_INVENTORY_PACKET_ID));
                this.buttonClicked = true;
            });
            this.addRenderableWidget(button_holder);
        }
    }

    @Inject(method = "onRecipeBookButtonClick", at = @At("TAIL"))
    protected void updatePosition(CallbackInfo ci) {
        if (!this.minecraft.gameMode.hasInfiniteItems() && button_holder != null) {
            ScreenPosition screenPosition = new ScreenPosition(this.leftPos + 66, this.height / 2 - 14);
            button_holder.setPosition(screenPosition.x(), screenPosition.y());
        }
    }
}
