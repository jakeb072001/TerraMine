package terramine.common.item.accessories.necklace;

import be.florens.expandability.api.EventResult;
import be.florens.expandability.api.fabric.PlayerSwimCallback;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import terramine.TerraMine;
import terramine.common.init.ModComponents;
import terramine.common.init.ModItems;
import terramine.common.init.ModMobEffects;
import terramine.common.item.accessories.AccessoryTerrariaItem;
import terramine.common.misc.AccessoriesHelper;

import java.util.UUID;

public class CelestialShell extends AccessoryTerrariaItem {

    private boolean isNight;
    private final boolean shell, wolf, sun, moon;
    private int timer;

    public CelestialShell(boolean shell, boolean wolf, boolean sun, boolean moon, ResourceKey<Item> key) {
        super(key);
        if (shell) {
            PlayerSwimCallback.EVENT.register(CelestialShell::onPlayerSwim);
        }
        this.shell = shell;
        this.wolf = wolf;
        this.sun = sun;
        this.moon = moon;
    }

    @Override
    protected Multimap<Holder<Attribute>, AttributeModifier> applyModifiers(ItemStack stack, LivingEntity entity, UUID uuid) {
        Multimap<Holder<Attribute>, AttributeModifier> result = super.applyModifiers(stack, entity, uuid);
        if (shell) {
            AttributeModifier modifier = new AttributeModifier(TerraMine.id("water_mining_speed"),
                    1, AttributeModifier.Operation.ADD_VALUE);
            result.put(Attributes.SUBMERGED_MINING_SPEED, modifier);
        }
        return result;
    }

    @Override
    protected void curioTick(Player player, ItemStack stack) {
        boolean inWater = player.isInWater();

        if (!player.level().isClientSide()) {
            isNight = player.level().isNight();
        }

        if (player.tickCount % 15 == 0) {
            if (shell && inWater) {
                player.addEffect(new MobEffectInstance(ModMobEffects.MERFOLK, -1, 0, true, false));
            } else {
                player.removeEffect(ModMobEffects.MERFOLK);
            }
            if (wolf && isNight && !inWater) {
                player.addEffect(new MobEffectInstance(ModMobEffects.WEREWOLF, -1, 0, true, false));
            } else {
                player.removeEffect(ModMobEffects.WEREWOLF);
            }
        }

        if (((wolf || moon) && isNight) || (sun && !isNight)) {
            timer += 1;
            if (timer >= 50) {
                player.heal(0.25f);
                timer = 0;
            }
        }
    }

    private static EventResult onPlayerSwim(Player player) {
        if (AccessoriesHelper.isEquipped(ModItems.NEPTUNE_SHELL, player) || AccessoriesHelper.isEquipped(ModItems.MOON_SHELL, player)
                || AccessoriesHelper.isEquipped(ModItems.CELESTIAL_SHELL, player)) {
            return ModComponents.SWIM_ABILITIES.maybeGet(player)
                    .filter(swimAbilityComponent -> swimAbilityComponent.isSinking() && !swimAbilityComponent.isSwimming())
                    .map(swimAbilities -> EventResult.FAIL)
                    .orElse(EventResult.PASS);
        }
        return EventResult.PASS;
    }

    @Override
    public void onEquip(ItemStack stack, Player player) {
        if (player instanceof ServerPlayer && shell) {
            ModComponents.SWIM_ABILITIES.maybeGet(player).ifPresent(comp -> {
                comp.setSinking(true);
                ModComponents.SWIM_ABILITIES.sync(player);
            });
        }
    }

    @Override
    public void onUnequip(ItemStack stack, Player player) {
        if (player instanceof ServerPlayer && shell) {
            ModComponents.SWIM_ABILITIES.maybeGet(player).ifPresent(comp -> {
                comp.setSinking(false);
                ModComponents.SWIM_ABILITIES.sync(player);
            });
        }
        player.removeEffect(ModMobEffects.MERFOLK);
        player.removeEffect(ModMobEffects.WEREWOLF);
    }
}
