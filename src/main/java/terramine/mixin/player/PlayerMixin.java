package terramine.mixin.player;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.TerraMine;
import terramine.common.entity.projectiles.FallingStarEntity;
import terramine.common.init.ModComponents;
import terramine.common.init.ModEntities;
import terramine.extensions.ItemExtensions;
import terramine.extensions.PlayerStorages;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements PlayerStorages {

	@Unique
	SimpleContainer terrariaInventory = new SimpleContainer(30);

	@Unique
	SimpleContainer piggyBankInventory = new SimpleContainer(40);

	@Unique
	SimpleContainer safeInventory = new SimpleContainer(40);

	public PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
		super(entityType, level);
	}
	private final RandomSource rand = RandomSource.create();

	@Inject(method = "tick", at = @At("TAIL"))
	private void manaTickRegen(CallbackInfo ci) {
		ModComponents.MANA_HANDLER.get(this).update();
		ModComponents.LAVA_IMMUNITY.get(this).update();
		if (!TerraMine.CONFIG.general.disableFallingStars) {
			if (level != null && level.dimensionType().bedWorks() && !level.isDay()) { // handles spawning stars randomly during the night, not the best way to do it most likely, but it works for now.
				if (rand.nextInt(16800) <= TerraMine.CONFIG.general.fallingStarRarity) {
					FallingStarEntity star = ModEntities.FALLING_STAR.create(level);
					if (star != null && blockPosition().getY() >= 50) {
						star.setPos(blockPosition().getX() + rand.nextInt(12), blockPosition().getY() + 30, blockPosition().getZ() + rand.nextInt(12));
						level.addFreshEntity(star);
					}
				}
			}
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
	public SimpleContainer getTerrariaInventory() {
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
	public void setTerrariaInventory(SimpleContainer terrariaInventory) {
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

	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	public void writePlayerChests(CompoundTag tag, CallbackInfo ci) {
		tag.put("terrariaItems", getTags(terrariaInventory));
		tag.put("piggyBankItems", getTags(piggyBankInventory));
		tag.put("safeItems", getTags(safeInventory));
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	public void readPlayerChests(CompoundTag tag, CallbackInfo ci) {
		if (tag.contains("terrariaItems", 9)) {
			readTags(tag.getList("terrariaItems", 10), terrariaInventory);
		}
		if (tag.contains("piggyBankItems", 9)) {
			readTags(tag.getList("piggyBankItems", 10), piggyBankInventory);
		}
		if (tag.contains("safeItems", 9)) {
			readTags(tag.getList("safeItems", 10), safeInventory);
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
