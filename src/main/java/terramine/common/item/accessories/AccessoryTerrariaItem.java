package terramine.common.item.accessories;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Wearable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import terramine.TerraMine;
import terramine.common.events.PlayHurtSoundCallback;
import terramine.common.item.TerrariaItem;
import terramine.common.misc.AccessoriesHelper;
import terramine.common.misc.TerrariaInventory;
import terramine.extensions.PlayerStorages;
import terramine.extensions.accessories.Accessories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AccessoryTerrariaItem extends TerrariaItem implements Accessories {
	public boolean effectEnabled = true;

	public AccessoryTerrariaItem() {
		initialise();
	}

	public AccessoryTerrariaItem(Properties settings) {
		super(settings, false);
		initialise();
	}

	private void initialise() {
		PlayHurtSoundCallback.EVENT.register(this::playExtraHurtSound);
	}

	@Override
	public boolean overrideOtherStackedOnMe(@NotNull ItemStack slotStack, @NotNull ItemStack holdingStack, @NotNull Slot slot, @NotNull ClickAction clickAction, @NotNull Player player, @NotNull SlotAccess slotAccess) {
		// Toggle accessory status when right-clicked in inventory without a stack
		if (clickAction == ClickAction.SECONDARY && holdingStack.isEmpty()) {
			CompoundTag tag = slotStack.getOrCreateTagElement("terramine");
			tag.putByte("Status", (byte) terramineStatus.nextIndex(tag.getByte("Status")));
			slotStack.addTagElement("terramine", tag);
			effectEnabled = terramineStatus.values()[tag.getByte("Status")].hasEffects();
			return true;
		}
		return false;
	}

	public static boolean equipItem(Player player, ItemStack stack) {
		TerrariaInventory inventory = ((PlayerStorages)player).getTerrariaInventory();
			for (int i = 0; i < 7; i++) {
				if (inventory.getItem(i).isEmpty()) {
					ItemStack newStack = stack.copy();
					inventory.setItem(i, newStack);
					SoundEvent soundEvent = stack.getItem() instanceof Wearable ? stack.getItem().getEquipSound() : null;
					if (!stack.isEmpty() && soundEvent != null) {
						player.gameEvent(GameEvent.EQUIP);
						player.playSound(soundEvent, 1.0F, 1.0F);
					}
					stack.setCount(0);
					return true;
				}
			}
		return false;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(@NotNull Level level, Player user, @NotNull InteractionHand hand) {
		ItemStack stack = user.getItemInHand(hand);
		if (equipItem(user, stack)) {
			// Play right click equip sound
			SoundInfo sound = this.getEquipSoundInfo();
			user.playSound(sound.soundEvent(), sound.volume(), sound.pitch());

			return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
		}

		return super.use(level, user, hand);
	}

	@Override
	public void tick(ItemStack stack, Player player) {
		if (AccessoriesHelper.areEffectsEnabled(stack, player)) {
			curioTick(player, stack);
		}
	}

	protected void curioTick(Player player, ItemStack stack) {
	}

	public boolean isGlove() {
		return false;
	}

	public boolean isBothHands() {
		return false;
	}

	@Override
	public final Multimap<Attribute, AttributeModifier> getModifiers(ItemStack stack, Player player, UUID uuid) {
		Multimap<Attribute, AttributeModifier> modifiers = Accessories.super.getModifiers(stack, player, uuid);
		if (AccessoriesHelper.areEffectsEnabled(stack, player)) {
			Multimap<Attribute, AttributeModifier> accessoryModifiers = this.applyModifiers(stack, player, uuid);
			modifiers.putAll(accessoryModifiers);
		}
		return modifiers;
	}

	protected Multimap<Attribute, AttributeModifier> applyModifiers(ItemStack stack, LivingEntity entity, UUID uuid) {
		return HashMultimap.create();
	}

	@Override
	public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags) {
		super.appendHoverText(stack, world, tooltip, flags);
		getEffectsEnabledLanguageKey(stack).ifPresent(key -> {
			MutableComponent enabled = Component.translatable(key).withStyle(ChatFormatting.GOLD);
			Component toggletooltip = Component.translatable(TerraMine.MOD_ID + ".status.toggletooltip").withStyle(ChatFormatting.GRAY);
			tooltip.add(enabled.append(" ").append(toggletooltip));
		});
	}

	/**
	 * @return The {@link SoundInfo} to play when the accessory is right-click equipped
	 */
	protected SoundInfo getEquipSoundInfo() {
		return new SoundInfo(SoundEvents.ARMOR_EQUIP_GENERIC);
	}

	/**
	 * @return An extra {@link SoundEvent} to play when an entity wearing this accessory is hurt
	 */
	protected SoundEvent getExtraHurtSound() {
		return null;
	}

	/**
	 * Used to give an Accessory a permanent status effect while wearing it.
	 * The StatusEffectInstance is applied every 15 ticks so a duration greater than that is required.
	 *
	 * @return The {@link MobEffectInstance} to be applied while wearing this accessory
	 */
	public MobEffectInstance getPermanentEffect() {
		return null;
	}

	private void playExtraHurtSound(Player player, float volume, float pitch) { // keeping for now, may use for moon charm and Neptune's shell
		SoundEvent hurtSound = getExtraHurtSound();

		if (hurtSound != null && AccessoriesHelper.isEquipped(this, player, true)) {
			player.playSound(hurtSound, volume, pitch);
		}
	}

	public static void addModifier(AttributeInstance instance, AttributeModifier modifier) {
		if (!instance.hasModifier(modifier)) {
			instance.addTransientModifier(modifier);
		}
	}

	public static void removeModifier(AttributeInstance instance, AttributeModifier modifier) {
		if (instance.hasModifier(modifier)) {
			instance.removeModifier(modifier);
		}
	}

	private static Optional<String> getEffectsEnabledLanguageKey(ItemStack stack) {
		return getTerraMineStatus(stack).map(status -> switch (status) {
			case ALL_ENABLED -> "terramine.status.allenabled";
			case COSMETIC_ONLY -> "terramine.status.cosmeticonly";
			case EFFECTS_ONLY -> "terramine.status.effectsonly";
		});
	}

	public static Optional<terramineStatus> getTerraMineStatus(ItemStack stack) {
		if (!(stack.getItem() instanceof AccessoryTerrariaItem)) {
			return Optional.empty();
		}

		CompoundTag tag = stack.getTagElement("terramine");
		if (tag == null || !tag.contains("Status", 1)) {
			return Optional.of(terramineStatus.ALL_ENABLED);
		}

		return Optional.ofNullable(terramineStatus.values()[tag.getByte("Status")]);
	}

	public enum terramineStatus {
		ALL_ENABLED(true, true),
		COSMETIC_ONLY(false, true),
		EFFECTS_ONLY(true, false);

		private final boolean hasEffects;
		private final boolean hasCosmetics;

		terramineStatus(boolean hasEffects, boolean hasCosmetics) {
			this.hasEffects = hasEffects;
			this.hasCosmetics = hasCosmetics;
		}

		public boolean hasEffects() {
			return hasEffects;
		}

		public boolean hasCosmetics() {
			return hasCosmetics;
		}

		public static int nextIndex(int index) {
			return index >= values().length - 1 ? 0 : index + 1;
		}
	}

	protected record SoundInfo(SoundEvent soundEvent, float volume, float pitch) {

		// Changes access modifier to public
		@SuppressWarnings({"RedundantRecordConstructor", "RedundantSuppression"})
		public SoundInfo {}

		public SoundInfo(SoundEvent soundEvent) {
			this(soundEvent, 1f, 1f);
		}
	}
}
