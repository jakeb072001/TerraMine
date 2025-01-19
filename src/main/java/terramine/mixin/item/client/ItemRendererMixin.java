package terramine.mixin.item.client;

import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import terramine.TerraMine;
import terramine.common.init.ModItems;

// Priority is higher so that we can inject into canvas' renderItem overwrite
// TODO: rewrite this using FabricBakedModel if/when RenderContext gets the transform mode
@Mixin(value = ItemRenderer.class, priority = 1500)
public abstract class ItemRendererMixin {

	@Shadow @Final private ItemModelShaper itemModelShaper;
	@Unique
	private static final ResourceLocation UMBRELLA_ICON_MODEL = TerraMine.id("umbrella");
	@Unique
	private static final ResourceLocation MAGIC_MISSILE_ICON_MODEL = TerraMine.id("magic_missile");
	@Unique
	private static final ResourceLocation FLAMELASH_ICON_MODEL = TerraMine.id("flamelash");
	@Unique
	private static final ResourceLocation RAINBOW_ROD_ICON_MODEL = TerraMine.id("rainbow_rod");
	@Unique
	private static final ResourceLocation SPACE_GUN_ICON_MODEL = TerraMine.id("space_gun");
	@Unique
	private static final ResourceLocation GRENADE_ICON_MODEL = TerraMine.id("grenade");
	@Unique
	private static final ResourceLocation STICKY_GRENADE_ICON_MODEL = TerraMine.id("sticky_grenade");
	@Unique
	private static final ResourceLocation BOUNCY_GRENADE_ICON_MODEL = TerraMine.id("bouncy_grenade");
	@Unique
	private static final ResourceLocation BOMB_ICON_MODEL = TerraMine.id("bomb");
	@Unique
	private static final ResourceLocation STICKY_BOMB_ICON_MODEL = TerraMine.id("sticky_bomb");
	@Unique
	private static final ResourceLocation BOUNCY_BOMB_ICON_MODEL = TerraMine.id("bouncy_bomb");
	@Unique
	private static final ResourceLocation DYNAMITE_ICON_MODEL = TerraMine.id("dynamite");
	@Unique
	private static final ResourceLocation STICKY_DYNAMITE_ICON_MODEL = TerraMine.id("sticky_dynamite");
	@Unique
	private static final ResourceLocation BOUNCY_DYNAMITE_ICON_MODEL = TerraMine.id("bouncy_dynamite");

	@ModifyVariable(method = "render", argsOnly = true, at = @At(value = "HEAD"))
	private BakedModel setCustomInventoryModels(BakedModel model, ItemStack stack, ItemDisplayContext itemDisplayContext) {
		boolean shouldUseIcon = itemDisplayContext == ItemDisplayContext.GUI ||
								itemDisplayContext == ItemDisplayContext.GROUND ||
								itemDisplayContext == ItemDisplayContext.FIXED;

		if (shouldUseIcon) {
			if (stack.getItem() == ModItems.UMBRELLA) {
				return this.itemModelShaper.getItemModel(UMBRELLA_ICON_MODEL);
			} else if (stack.getItem() == ModItems.MAGIC_MISSILE_ITEM) {
				return this.itemModelShaper.getItemModel(MAGIC_MISSILE_ICON_MODEL);
			} else if (stack.getItem() == ModItems.FLAMELASH_ITEM) {
				return this.itemModelShaper.getItemModel(FLAMELASH_ICON_MODEL);
			} else if (stack.getItem() == ModItems.RAINBOW_ROD_ITEM) {
				return this.itemModelShaper.getItemModel(RAINBOW_ROD_ICON_MODEL);
			} else if (stack.getItem() == ModItems.SPACE_GUN) {
				return this.itemModelShaper.getItemModel(SPACE_GUN_ICON_MODEL);
			} else if (stack.getItem() == ModItems.GRENADE) {
				return this.itemModelShaper.getItemModel(GRENADE_ICON_MODEL);
			} else if (stack.getItem() == ModItems.STICKY_GRENADE) {
				return this.itemModelShaper.getItemModel(STICKY_GRENADE_ICON_MODEL);
			} else if (stack.getItem() == ModItems.BOUNCY_GRENADE) {
				return this.itemModelShaper.getItemModel(BOUNCY_GRENADE_ICON_MODEL);
			} else if (stack.getItem() == ModItems.BOMB) {
				return this.itemModelShaper.getItemModel(BOMB_ICON_MODEL);
			} else if (stack.getItem() == ModItems.STICKY_BOMB) {
				return this.itemModelShaper.getItemModel(STICKY_BOMB_ICON_MODEL);
			} else if (stack.getItem() == ModItems.BOUNCY_BOMB) {
				return this.itemModelShaper.getItemModel(BOUNCY_BOMB_ICON_MODEL);
			} else if (stack.getItem() == ModItems.DYNAMITE) {
				return this.itemModelShaper.getItemModel(DYNAMITE_ICON_MODEL);
			} else if (stack.getItem() == ModItems.STICKY_DYNAMITE) {
				return this.itemModelShaper.getItemModel(STICKY_DYNAMITE_ICON_MODEL);
			} else if (stack.getItem() == ModItems.BOUNCY_DYNAMITE) {
				return this.itemModelShaper.getItemModel(BOUNCY_DYNAMITE_ICON_MODEL);
			}
		}

		return model;
	}
}
