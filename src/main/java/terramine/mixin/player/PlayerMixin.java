package terramine.mixin.player;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.common.init.ModComponents;
import terramine.common.init.ModMobEffects;
import terramine.common.item.accessories.AccessoryTerrariaItem;
import terramine.common.misc.TerrariaInventory;
import terramine.common.network.ServerPacketHandler;
import terramine.extensions.ItemExtensions;
import terramine.extensions.PlayerStorages;

import java.util.HashMap;
import java.util.Map;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements PlayerStorages {

	@Shadow public abstract boolean isCreative();

	@Shadow public abstract boolean isSpectator();

	@Shadow public abstract boolean isLocalPlayer();

	@Unique
	TerrariaInventory terrariaInventory = new TerrariaInventory(35);

	@Unique
	SimpleContainer piggyBankInventory = new SimpleContainer(40);

	@Unique
	SimpleContainer safeInventory = new SimpleContainer(40);

	@Unique
	public Map<Integer, Boolean> slotVisibility = new HashMap<>();

	public PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
		super(entityType, level);
	}

	@Inject(method = "tick", at = @At("TAIL"))
	private void onTick(CallbackInfo ci) {
		ModComponents.MANA_HANDLER.get(this).update();
		ModComponents.LAVA_IMMUNITY.get(this).update();

		if (!isCreative() && !isSpectator()) {
			for (int i = 0; i < 7; i++) {
				if (terrariaInventory.getItem(i).getItem() instanceof AccessoryTerrariaItem accessoryItem) {
					accessoryItem.tick(terrariaInventory.getItem(i), (Player) (Object) this);
				}
			}
		} else {
			removeEffect(ModMobEffects.WEREWOLF);
			removeEffect(ModMobEffects.MERFOLK);
		}
	}

	@Inject(at = @At("HEAD"), method = "blockUsingShield")
	public void blockUsingShield(LivingEntity attacker, CallbackInfo info) {
		super.blockUsingShield(attacker);
		if (((ItemExtensions) attacker.getMainHandItem().getItem()).canDisableShield(attacker.getMainHandItem(), this.getUseItem(), this, attacker)) {
			(((Player) (Object)this)).disableShield(true);
		}
	}

	@Override
	public TerrariaInventory getTerrariaInventory() {
		return this.terrariaInventory;
	}

	@Override
	public SimpleContainer getPiggyBankInventory() {
		return this.piggyBankInventory;
	}

	@Override
	public SimpleContainer getSafeInventory() {
		return this.safeInventory;
	}

	@Override
	public boolean getSlotVisibility(int slot) {
		return slotVisibility.getOrDefault(slot, true);
	}

	@Override
	public void setTerrariaInventory(TerrariaInventory terrariaInventory) {
		this.terrariaInventory = terrariaInventory;
	}

	@Override
	public void setPiggyBankInventory(SimpleContainer piggyBankInventory) {
		this.piggyBankInventory = piggyBankInventory;
	}

	@Override
	public void setSafeInventory(SimpleContainer safeInventory) {
		this.safeInventory = safeInventory;
	}

	@Override
	public void setSlotVisibility(int slot, boolean visible) {
		slotVisibility.put(slot, visible);
		if (!this.isLocalPlayer()) {
			FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
			buf.writeInt(slot);
			buf.writeBoolean(visible);
			buf.writeUUID(getUUID());
			if (getServer() != null) {
				for (ServerPlayer otherPlayer : getServer().getLevel(level().dimension()).players()) {
					ServerPlayNetworking.send(otherPlayer, ServerPacketHandler.UPDATE_ACCESSORY_VISIBILITY_PACKET_ID, buf);
				}
			}
		}
	}

	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	public void writePlayerInventories(CompoundTag tag, CallbackInfo ci) {
		tag.put("terrariaItems", getTags(terrariaInventory));
		tag.put("piggyBankItems", getTags(piggyBankInventory));
		tag.put("safeItems", getTags(safeInventory));
		for (int i = 0; i < 7; i++) {
			tag.putBoolean("slotVisibility/" + i, getSlotVisibility(i));
		}
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	public void readPlayerInventories(CompoundTag tag, CallbackInfo ci) {
		if (tag.contains("terrariaItems", 9)) {
			readTags(tag.getList("terrariaItems", 10), terrariaInventory);
		}
		if (tag.contains("piggyBankItems", 9)) {
			readTags(tag.getList("piggyBankItems", 10), piggyBankInventory);
		}
		if (tag.contains("safeItems", 9)) {
			readTags(tag.getList("safeItems", 10), safeInventory);
		}
		for (int i = 0; i < 7; i++) {
			if (tag.contains("slotVisibility/" + i)) {
				setSlotVisibility(i, tag.getBoolean("slotVisibility/" + i));
			}
		}
	}

	@Unique
	public void readTags(ListTag tags, SimpleContainer simpleInventory) {
		int j;
		for (j = 0; j < simpleInventory.getContainerSize(); ++j) {
			simpleInventory.setItem(j, ItemStack.EMPTY);
		}

		for (j = 0; j < tags.size(); ++j) {
			CompoundTag compoundTag = tags.getCompound(j);
			int k = compoundTag.getByte("Slot") & 255;
			if (k >= 0 && k < simpleInventory.getContainerSize()) {
				simpleInventory.setItem(k, ItemStack.of(compoundTag));
			}
		}
	}

	@Unique
	public ListTag getTags(SimpleContainer simpleInventory) {
		ListTag listTag = new ListTag();

		for (int i = 0; i < simpleInventory.getContainerSize(); ++i) {
			ItemStack itemStack = simpleInventory.getItem(i);
			if (!itemStack.isEmpty()) {
				CompoundTag compoundTag = new CompoundTag();
				compoundTag.putByte("Slot", (byte) i);
				itemStack.save(compoundTag);
				listTag.add(compoundTag);
			}
		}

		return listTag;
	}
}
