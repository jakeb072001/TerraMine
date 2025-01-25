package terramine.mixin.client.render;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.trim.ArmorTrim;
import net.minecraft.world.item.equipment.trim.TrimPattern;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terramine.TerraMine;
import terramine.common.item.armor.TerrariaArmor;
import terramine.common.item.armor.TerrariaEquipmentModels;
import terramine.common.item.armor.vanity.FamiliarVanity;
import terramine.common.item.armor.vanity.VanityArmor;
import terramine.common.item.dye.BasicDye;
import terramine.extensions.EntityRenderStateExtensions;
import terramine.extensions.PlayerStorages;

import java.util.Iterator;
import java.util.List;

@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin<S extends HumanoidRenderState, M extends HumanoidModel<S>, A extends HumanoidModel<S>> extends RenderLayer<S, M> {
	@Unique
	private Player player;

	public HumanoidArmorLayerMixin(RenderLayerParent<S, M> renderLayerParent) {
		super(renderLayerParent);
    }

	@Inject(at = @At("RETURN"), method = "getArmorModel")
	private void getPlayer(S humanoidRenderState, EquipmentSlot equipmentSlot, CallbackInfoReturnable<A> cir) {
		if (((EntityRenderStateExtensions) humanoidRenderState).terrariaCraft$getLivingEntity() instanceof Player playerEnt) {
			this.player = playerEnt;
		}
	}

	@ModifyVariable(method = "renderArmorPiece", at = @At("HEAD"), ordinal = 0, argsOnly = true)
	private ItemStack vanityArmor(ItemStack itemStack, PoseStack poseStack, MultiBufferSource multiBufferSource, ItemStack itemStack2, EquipmentSlot equipmentSlot, int i, A humanoidModel) {
		if (player instanceof Player player) {
			ItemStack vanityItem = ((PlayerStorages)player).getTerrariaInventory().getItem(equipmentSlot.getIndex() + 23);
			if (vanityItem != ItemStack.EMPTY) {
				if (vanityItem.getItem() == Items.ELYTRA) {
					return itemStack;
				}
				return vanityItem;
			}
		}
		return itemStack;
	}

	// todo: look more into FancyDyes for special shaders, once working for armor implement into the accessory renderers
	// also maybe mixin to EquipmentLayerRenderer instead, would work with more things and ElytraLayerMixin is just a copy paste of this because it also uses EquipmentLayerRenderer
	@WrapOperation(
			method = "renderArmorPiece",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/EquipmentLayerRenderer;renderLayers(Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/client/model/Model;Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V")
	)
	private void armorDyeVanity(EquipmentLayerRenderer instance, EquipmentClientInfo.LayerType layerType, ResourceKey<EquipmentAsset> resourceKey, Model model, ItemStack itemStack, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, Operation<Void> original) {
		if (!(itemStack.getItem() instanceof FamiliarVanity) && player != null) {
			if (itemStack.getItem() instanceof TerrariaArmor terrariaArmor && terrariaArmor.getCustomArmorModel() != null) {
				A customModel = (A) terrariaArmor.getCustomArmorModel();
				((A) model).copyPropertiesTo(customModel);
				model = customModel;
			}
			int equipmentSlotIndex = player.getEquipmentSlotForItem(itemStack).getIndex();
			if (((PlayerStorages) player).getTerrariaInventory().getItem(equipmentSlotIndex + 27).getItem() instanceof BasicDye dye) {
				List<EquipmentClientInfo.Layer> list = instance.equipmentAssets.get(getArmorLocation(itemStack.getItem(), resourceKey)).getLayers(layerType);
				if (!list.isEmpty()) {
					int j = itemStack.is(ItemTags.DYEABLE) ? DyedItemColor.getOrDefault(itemStack, 0) : 0;
					boolean bl = itemStack.hasFoil();
					Iterator var12 = list.iterator();

					while(true) {
						EquipmentClientInfo.Layer layer;
						int k;
						do {
							if (!var12.hasNext()) {
								ArmorTrim armorTrim = itemStack.get(DataComponents.TRIM);
								if (armorTrim != null) {
									TextureAtlasSprite textureAtlasSprite = instance.trimSpriteLookup.apply(TrimSpriteKeyInvoker.create(armorTrim, layerType, resourceKey));
									VertexConsumer vertexConsumer2 = textureAtlasSprite.wrap(multiBufferSource.getBuffer(Sheets.armorTrimsSheet(((TrimPattern)armorTrim.pattern().value()).decal())));
									model.renderToBuffer(poseStack, vertexConsumer2, i, OverlayTexture.NO_OVERLAY, dye.getColourInt());
								}

								return;
							}

							layer = (EquipmentClientInfo.Layer)var12.next();
							k = instance.getColorForLayer(layer, j);
						} while(k == 0);

						ResourceLocation resourceLocation2 = instance.layerTextureLookup.apply(LayerTextureKeyInvoker.create(layerType, layer));
						VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(multiBufferSource, RenderType.armorCutoutNoCull(resourceLocation2), bl);
						model.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, dye.getColourInt());
						bl = false;
					}
				}

				return;
			}
		}

		original.call(instance, layerType, resourceKey, model, itemStack, poseStack, multiBufferSource, i);
	}

	@Unique
	private ResourceKey<EquipmentAsset> getArmorLocation(Item item, ResourceKey<EquipmentAsset> originalResource) {
		if (item instanceof VanityArmor vanityArmor) {
			// todo: not sure if working, can't test till custom models added
			if (vanityArmor.getCustomArmorLocation() != null) {
				return ResourceKey.create(TerrariaEquipmentModels.ROOT_ID, ResourceLocation.fromNamespaceAndPath(TerraMine.MOD_ID, "textures/models/vanity/" + vanityArmor.getCustomArmorLocation() + ".png"));
			}
		}

		return originalResource;
	}
}