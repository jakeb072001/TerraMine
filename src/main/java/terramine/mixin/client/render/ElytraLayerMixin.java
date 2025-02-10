package terramine.mixin.client.render;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.layers.WingsLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.trim.ArmorTrim;
import net.minecraft.world.item.equipment.trim.TrimPattern;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import terramine.common.item.armor.vanity.FamiliarVanity;
import terramine.common.item.dye.BasicDye;
import terramine.extensions.EntityRenderStateExtensions;
import terramine.extensions.PlayerStorages;

import java.util.Iterator;
import java.util.List;

// todo: if using shaders and dye is applied to armour or elytra, then they just wont render
@Mixin(WingsLayer.class)
public abstract class ElytraLayerMixin<S extends HumanoidRenderState, M extends EntityModel<S>> extends RenderLayer<S, M> {
    @Unique
    private BasicDye dyeItem;

    public ElytraLayerMixin(RenderLayerParent<S, M> renderLayerParent) {
        super(renderLayerParent);
    }

    @ModifyVariable(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/HumanoidRenderState;FF)V", at = @At("STORE"), ordinal = 0)
    private ItemStack hideElytra(ItemStack itemStack, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, S humanoidRenderState, float f, float g) {
        if (((EntityRenderStateExtensions)humanoidRenderState).terrariaCraft$getLivingEntity() instanceof Player player) {
            if ((((PlayerStorages) player).getTerrariaInventory().getItem(25).getItem() instanceof FamiliarVanity)) {
                return ItemStack.EMPTY;
            }
            if (((PlayerStorages) player).getTerrariaInventory().getItem(29).getItem() instanceof BasicDye dye) {
                this.dyeItem = dye;
            } else {
                this.dyeItem = null;
            }
            if ((((PlayerStorages) player).getTerrariaInventory().getItem(25).getItem().components().has(DataComponents.GLIDER))) {
                return ((PlayerStorages) player).getTerrariaInventory().getItem(25);
            }
        }

        return itemStack;
    }

    @WrapOperation(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/HumanoidRenderState;FF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/EquipmentLayerRenderer;renderLayers(Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/client/model/Model;Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/resources/ResourceLocation;)V")
    )
    private void elytraDye(EquipmentLayerRenderer instance, EquipmentClientInfo.LayerType layerType, ResourceKey<EquipmentAsset> resourceKey, Model model, ItemStack itemStack, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, ResourceLocation resourceLocation, Operation<Void> original) {
        if (dyeItem != null) {
            List<EquipmentClientInfo.Layer> list = instance.equipmentAssets.get(resourceKey).getLayers(layerType);
            if (!list.isEmpty()) {
                int j = itemStack.is(ItemTags.DYEABLE) ? DyedItemColor.getOrDefault(itemStack, 0) : 0;
                boolean bl = itemStack.hasFoil();
                Iterator<EquipmentClientInfo.Layer> var12 = list.iterator();

                while(true) {
                    EquipmentClientInfo.Layer layer;
                    int k;
                    do {
                        if (!var12.hasNext()) {
                            ArmorTrim armorTrim = itemStack.get(DataComponents.TRIM);
                            if (armorTrim != null) {
                                TextureAtlasSprite textureAtlasSprite = instance.trimSpriteLookup.apply(TrimSpriteKeyInvoker.create(armorTrim, layerType, resourceKey));
                                VertexConsumer vertexConsumer2 = textureAtlasSprite.wrap(multiBufferSource.getBuffer(Sheets.armorTrimsSheet(((TrimPattern)armorTrim.pattern().value()).decal())));
                                model.renderToBuffer(poseStack, vertexConsumer2, i, OverlayTexture.NO_OVERLAY, dyeItem.getColourInt());
                            }

                            return;
                        }

                        layer = (EquipmentClientInfo.Layer)var12.next();
                        k = EquipmentLayerRenderer.getColorForLayer(layer, j);
                    } while(k == 0);

                    ResourceLocation resourceLocation2 = instance.layerTextureLookup.apply(LayerTextureKeyInvoker.create(layerType, layer));
                    VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(multiBufferSource, RenderType.armorCutoutNoCull(resourceLocation2), bl);
                    model.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, dyeItem.getColourInt());
                    bl = false;
                }
            }

            return;
        }
        original.call(instance, layerType, resourceKey, model, itemStack, poseStack, multiBufferSource, i, resourceLocation);
    }
}