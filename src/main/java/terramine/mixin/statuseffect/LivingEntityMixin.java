package terramine.mixin.statuseffect;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.TerraMine;
import terramine.common.item.accessories.AccessoryTerrariaItem;
import terramine.common.misc.TerrariaInventory;
import terramine.extensions.PlayerStorages;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// todo: equipping multiple of an item no longer causes the modifier to apply more then once, but now unequipping one of the duplicates causes the modifier to be removed even if one of the accessory is still equipped
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	@Unique
	private final Map<String, ItemStack> lastEquippedAccessory = new HashMap<>();

	@Shadow
	public abstract AttributeMap getAttributes();

	public LivingEntityMixin(EntityType<?> type, Level world) {
		super(type, world);
	}

	@Inject(at = @At("TAIL"), method = "tick")
	private void tick(CallbackInfo info) {
		LivingEntity entity = (LivingEntity) (Object) this;
		if (entity instanceof Player player) {
			TerrariaInventory inventory = ((PlayerStorages)player).getTerrariaInventory();
			for (int i = 0; i < 7; i++) {
				ItemStack oldStack = getOldStack(i);
				ItemStack newStack = inventory.getItem(i);
				ItemStack copy = newStack.copy();
				String key = TerraMine.MOD_ID + "/accessorySlots";
				UUID uuid = UUID.nameUUIDFromBytes(key.getBytes());
				if (!ItemStack.isSame(newStack, oldStack)) {
					if (!this.level.isClientSide) {
						if (!oldStack.isEmpty()) {
							Multimap<Attribute, AttributeModifier> map = getOldStackAccessory(i).getModifiers(getOldStack(i), player, uuid);
							Multimap<String, AttributeModifier> slotMap = HashMultimap.create();

							for (Attribute attr : map.keySet()) {
								slotMap.putAll("accessorySlot/" + i, map.get(attr));
							}

							this.getAttributes().removeAttributeModifiers(map);
						}

						if (!newStack.isEmpty()) {
							if (inventory.getItem(i).getItem() instanceof AccessoryTerrariaItem item) {
								Multimap<Attribute, AttributeModifier> map = item.getModifiers(inventory.getItem(i), player, uuid);

								Multimap<String, AttributeModifier> slotMap = HashMultimap.create();

								for (Attribute attr : map.keySet()) {
									slotMap.putAll("accessorySlot/" + i, map.get(attr));
								}

								this.getAttributes().addTransientAttributeModifiers(map);
							}
						}
					}

					if (!newStack.sameItem(oldStack)) {
						if (oldStack.getItem() instanceof AccessoryTerrariaItem oldItem) {
							oldItem.onUnequip(oldStack, player);
						}
						if (newStack.getItem() instanceof AccessoryTerrariaItem newItem) {
							newItem.onEquip(newStack, player);
						}
					}
				}

				lastEquippedAccessory.put("accessorySlot/" + i, copy);
			}
		}
	}

	@Unique
	private AccessoryTerrariaItem getOldStackAccessory(int slotNumber) {
		if (getOldStack(slotNumber).getItem() instanceof AccessoryTerrariaItem accessory) {
			return accessory;
		}
		return null;
	}

	@Unique
	private ItemStack getOldStack(int slotNumber) {
		return lastEquippedAccessory.getOrDefault("accessorySlot/" + slotNumber, ItemStack.EMPTY);
	}
}
