package terramine.mixin.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terramine.common.item.armor.TerrariaArmor;
import terramine.extensions.PlayerStorages;

import java.util.Locale;
import java.util.Map;

@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {
	@Shadow
	@Final
	private static Map<String, ResourceLocation> ARMOR_LOCATION_CACHE;

	public HumanoidArmorLayerMixin(RenderLayerParent<T, M> renderLayerParent) {
		super(renderLayerParent);
	}

	// todo: no clue if other players see correctly, check into this
	@ModifyVariable(method = "renderArmorPiece", at = @At("STORE"), ordinal = 0)
	private ItemStack vanityArmor(ItemStack itemStack, PoseStack poseStack, MultiBufferSource multiBufferSource, T livingEntity, EquipmentSlot equipmentSlot, int i, A humanoidModel) {
		if (livingEntity instanceof Player player) {
			if (((PlayerStorages)player).getTerrariaInventory().getItem(equipmentSlot.getIndex() + 23) != ItemStack.EMPTY) {
				return ((PlayerStorages)player).getTerrariaInventory().getItem(equipmentSlot.getIndex() + 23);
			}
		}
		return itemStack;
	}

	// todo: remove later, 1.19.2 fabric api has this already, this if for 1.19 and 1.18
	@Inject(method = "getArmorLocation", at = @At(value = "HEAD"), cancellable = true)
	private void getArmorTexture(ArmorItem item, boolean secondLayer, String overlay, CallbackInfoReturnable<ResourceLocation> cir) {
		if (item instanceof TerrariaArmor) {
			final String name = item.getMaterial().getName();
			final int separator = name.indexOf(ResourceLocation.NAMESPACE_SEPARATOR);

			if (separator != -1) {
				final String namespace = name.substring(0, separator);
				final String path = name.substring(separator + 1);
				final String texture = String.format(Locale.ROOT, "%s:textures/models/armor/%s_layer_%d%s.png", namespace, path, secondLayer ? 2 : 1, overlay == null ? "" : "_" + overlay);

				cir.setReturnValue(ARMOR_LOCATION_CACHE.computeIfAbsent(texture, ResourceLocation::new));
			}
		}
	}
}
