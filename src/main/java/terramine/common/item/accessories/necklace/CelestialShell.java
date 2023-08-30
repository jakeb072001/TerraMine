package terramine.common.item.accessories.necklace;

import be.florens.expandability.api.fabric.PlayerSwimCallback;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import terramine.common.init.ModComponents;
import terramine.common.init.ModItems;
import terramine.common.init.ModMobEffects;
import terramine.common.item.accessories.AccessoryTerrariaItem;
import terramine.common.misc.AccessoriesHelper;

public class CelestialShell extends AccessoryTerrariaItem {

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
    protected void curioTick(Player player, ItemStack stack) {
        inWater = player.isInWater();

        if (!player.level.isClientSide()) {
            isNight = player.level.isNight();
        }

        if (((wolf || moon) && isNight) || (sun && !isNight)) {
            timer += 1;
            if (timer >= 50) {
                player.heal(0.25f);
                timer = 0;
            }
        }
    }

    private static TriState onPlayerSwim(Player player) {
        if (AccessoriesHelper.isEquipped(ModItems.NEPTUNE_SHELL, player) || AccessoriesHelper.isEquipped(ModItems.MOON_SHELL, player)
                || AccessoriesHelper.isEquipped(ModItems.CELESTIAL_SHELL, player)) {
            return ModComponents.SWIM_ABILITIES.maybeGet(player)
                    .filter(swimAbilityComponent -> swimAbilityComponent.isSinking() && !swimAbilityComponent.isSwimming())
                    .map(swimAbilities -> TriState.FALSE)
                    .orElse(TriState.DEFAULT);
        }
        return TriState.DEFAULT;
    }

    @Override
    public void onEquip(ItemStack stack, Player player) {
        if (player instanceof ServerPlayer && AccessoriesHelper.areEffectsEnabled(stack, player) && shell) {
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
    }
}
