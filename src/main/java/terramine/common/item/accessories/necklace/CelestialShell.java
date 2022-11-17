package terramine.common.item.accessories.necklace;

import be.florens.expandability.api.fabric.PlayerSwimCallback;
import dev.emi.trinkets.api.SlotReference;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import terramine.common.init.ModComponents;
import terramine.common.init.ModItems;
import terramine.common.init.ModMobEffects;
import terramine.common.item.accessories.TrinketTerrariaItem;
import terramine.common.trinkets.TrinketsHelper;

public class CelestialShell extends TrinketTerrariaItem {

    private boolean inWater, isNight;
    private final boolean shell, wolf, sun, moon;
    private int timer;

    public CelestialShell(boolean shell, boolean wolf, boolean sun, boolean moon) {
        if (shell) {
            PlayerSwimCallback.EVENT.register(CelestialShell::onPlayerSwim);
        }
        this.shell = shell;
        this.wolf = wolf;
        this.sun = sun;
        this.moon = moon;
    }

    @Override
    public MobEffectInstance getPermanentEffect() {
        if (shell && inWater) {
            return new MobEffectInstance(ModMobEffects.MERFOLK, 20, 0, true, false);
        }
        if (wolf && isNight && !inWater) {
            return new MobEffectInstance(ModMobEffects.WEREWOLF, 20, 0, true, false);
        }
        return null;
    }

    @Override
    protected void curioTick(LivingEntity livingEntity, ItemStack stack) {
        inWater = livingEntity.isInWater();

        if (!livingEntity.level.isClientSide()) {
            isNight = livingEntity.level.isNight();
        }

        if (((wolf || moon) && isNight) || (sun && !isNight)) {
            timer += 1;
            if (timer >= 50) {
                livingEntity.heal(0.25f);
                timer = 0;
            }
        }
    }

    private static TriState onPlayerSwim(Player player) {
        if (TrinketsHelper.isEquipped(ModItems.NEPTUNE_SHELL, player) || TrinketsHelper.isEquipped(ModItems.MOON_SHELL, player)
                || TrinketsHelper.isEquipped(ModItems.CELESTIAL_SHELL, player)) {
            return ModComponents.SWIM_ABILITIES.maybeGet(player)
                    .filter(swimAbilityComponent -> swimAbilityComponent.isSinking() && !swimAbilityComponent.isSwimming())
                    .map(swimAbilities -> TriState.FALSE)
                    .orElse(TriState.DEFAULT);
        }
        return TriState.DEFAULT;
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (entity instanceof ServerPlayer && TrinketsHelper.areEffectsEnabled(stack, entity) && shell) {
            ModComponents.SWIM_ABILITIES.maybeGet(entity).ifPresent(comp -> {
                comp.setSinking(true);
                ModComponents.SWIM_ABILITIES.sync(entity);
            });
        }
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (entity instanceof ServerPlayer && shell) {
            ModComponents.SWIM_ABILITIES.maybeGet(entity).ifPresent(comp -> {
                comp.setSinking(false);
                ModComponents.SWIM_ABILITIES.sync(entity);
            });
        }
    }
}
