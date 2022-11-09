package terramine.mixin.item.umbrella.client;

import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import terramine.TerraMine;
import terramine.client.render.HeldItemModels;
import terramine.common.init.ModItems;

// Priority is higher so we can inject into canvas' renderItem overwrite
// TODO: rewrite this using FabricBakedModel if/when RenderContext gets the transform mode
// todo: make work for throwables and magic weapons, maybe somehow use json like forge does?
@Mixin(value = ItemRenderer.class, priority = 1500)
public abstract class ItemRendererMixin {

	@Shadow @Final private ItemModelShaper itemModelShaper;
	@Unique
	private static final ModelResourceLocation UMBRELLA_ICON_MODEL = new ModelResourceLocation(TerraMine.id("umbrella"), "inventory");
	@Unique
	private static final ModelResourceLocation GRENADE_ICON_MODEL = new ModelResourceLocation(TerraMine.id("grenade"), "inventory");
	@Unique
	private static final ModelResourceLocation STICKY_GRENADE_ICON_MODEL = new ModelResourceLocation(TerraMine.id("sticky_grenade"), "inventory");
	@Unique
	private static final ModelResourceLocation BOUNCY_GRENADE_ICON_MODEL = new ModelResourceLocation(TerraMine.id("bouncy_grenade"), "inventory");
	@Unique
	private static final ModelResourceLocation BOMB_ICON_MODEL = new ModelResourceLocation(TerraMine.id("bomb"), "inventory");
	@Unique
	private static final ModelResourceLocation STICKY_BOMB_ICON_MODEL = new ModelResourceLocation(TerraMine.id("sticky_bomb"), "inventory");
	@Unique
	private static final ModelResourceLocation BOUNCY_BOMB_ICON_MODEL = new ModelResourceLocation(TerraMine.id("bouncy_bomb"), "inventory");
	@Unique
	private static final ModelResourceLocation DYNAMITE_ICON_MODEL = new ModelResourceLocation(TerraMine.id("dynamite"), "inventory");
	@Unique
	private static final ModelResourceLocation STICKY_DYNAMITE_ICON_MODEL = new ModelResourceLocation(TerraMine.id("sticky_dynamite"), "inventory");
	@Unique
	private static final ModelResourceLocation BOUNCY_DYNAMITE_ICON_MODEL = new ModelResourceLocation(TerraMine.id("bouncy_dynamite"), "inventory");

	@ModifyVariable(method = "getModel", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/renderer/ItemModelShaper;getItemModel(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/client/resources/model/BakedModel;"))
	private BakedModel setUmbrellaHeldModel(BakedModel model, ItemStack stack) {
		if (stack.getItem() == ModItems.UMBRELLA) {
			return this.itemModelShaper.getModelManager().getModel(HeldItemModels.UMBRELLA_HELD_MODEL);
		}
		if (stack.getItem() == ModItems.GRENADE) {
			return this.itemModelShaper.getModelManager().getModel(HeldItemModels.GRENADE_HELD_MODEL);
		}
		if (stack.getItem() == ModItems.STICKY_GRENADE) {
			return this.itemModelShaper.getModelManager().getModel(HeldItemModels.STICKY_GRENADE_HELD_MODEL);
		}
		if (stack.getItem() == ModItems.BOUNCY_GRENADE) {
			return this.itemModelShaper.getModelManager().getModel(HeldItemModels.BOUNCY_GRENADE_HELD_MODEL);
		}
		if (stack.getItem() == ModItems.BOMB) {
			return this.itemModelShaper.getModelManager().getModel(HeldItemModels.BOMB_HELD_MODEL);
		}
		if (stack.getItem() == ModItems.STICKY_BOMB) {
			return this.itemModelShaper.getModelManager().getModel(HeldItemModels.STICKY_BOMB_HELD_MODEL);
		}
		if (stack.getItem() == ModItems.BOUNCY_BOMB) {
			return this.itemModelShaper.getModelManager().getModel(HeldItemModels.BOUNCY_BOMB_HELD_MODEL);
		}
		if (stack.getItem() == ModItems.DYNAMITE) {
			return this.itemModelShaper.getModelManager().getModel(HeldItemModels.DYNAMITE_HELD_MODEL);
		}
		if (stack.getItem() == ModItems.STICKY_DYNAMITE) {
			return this.itemModelShaper.getModelManager().getModel(HeldItemModels.STICKY_DYNAMITE_HELD_MODEL);
		}
		if (stack.getItem() == ModItems.BOUNCY_DYNAMITE) {
			return this.itemModelShaper.getModelManager().getModel(HeldItemModels.BOUNCY_DYNAMITE_HELD_MODEL);
		}

		return model;
	}

	@ModifyVariable(method = "render", argsOnly = true, at = @At(value = "HEAD"))
	private BakedModel setUmbrellaIconModel(BakedModel model, ItemStack stack, ItemTransforms.TransformType renderMode) {
		boolean shouldUseIcon = renderMode == ItemTransforms.TransformType.GUI ||
								renderMode == ItemTransforms.TransformType.GROUND ||
								renderMode == ItemTransforms.TransformType.FIXED;

		if (shouldUseIcon) {
			if (stack.getItem() == ModItems.UMBRELLA) {
				return this.itemModelShaper.getModelManager().getModel(UMBRELLA_ICON_MODEL);
			} else if (stack.getItem() == ModItems.GRENADE) {
				return this.itemModelShaper.getModelManager().getModel(GRENADE_ICON_MODEL);
			} else if (stack.getItem() == ModItems.STICKY_GRENADE) {
				return this.itemModelShaper.getModelManager().getModel(STICKY_GRENADE_ICON_MODEL);
			} else if (stack.getItem() == ModItems.BOUNCY_GRENADE) {
				return this.itemModelShaper.getModelManager().getModel(BOUNCY_GRENADE_ICON_MODEL);
			} else if (stack.getItem() == ModItems.BOMB) {
				return this.itemModelShaper.getModelManager().getModel(BOMB_ICON_MODEL);
			} else if (stack.getItem() == ModItems.STICKY_BOMB) {
				return this.itemModelShaper.getModelManager().getModel(STICKY_BOMB_ICON_MODEL);
			} else if (stack.getItem() == ModItems.BOUNCY_BOMB) {
				return this.itemModelShaper.getModelManager().getModel(BOUNCY_BOMB_ICON_MODEL);
			} else if (stack.getItem() == ModItems.DYNAMITE) {
				return this.itemModelShaper.getModelManager().getModel(DYNAMITE_ICON_MODEL);
			} else if (stack.getItem() == ModItems.STICKY_DYNAMITE) {
				return this.itemModelShaper.getModelManager().getModel(STICKY_DYNAMITE_ICON_MODEL);
			} else if (stack.getItem() == ModItems.BOUNCY_DYNAMITE) {
				return this.itemModelShaper.getModelManager().getModel(BOUNCY_DYNAMITE_ICON_MODEL);
			}
		}

		return model;
	}
}
