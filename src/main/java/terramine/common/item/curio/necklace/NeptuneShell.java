package terramine.common.item.curio.necklace;

import be.florens.expandability.api.fabric.PlayerSwimCallback;
import dev.emi.trinkets.api.SlotReference;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import terramine.common.init.ModComponents;
import terramine.common.init.ModMobEffects;
import terramine.common.item.curio.TrinketTerrariaItem;
import terramine.common.trinkets.TrinketsHelper;

public class NeptuneShell extends TrinketTerrariaItem {

    private boolean inWater;

    public NeptuneShell() {
        PlayerSwimCallback.EVENT.register(NeptuneShell::onPlayerSwim);
    }

    @Override
    public MobEffectInstance getPermanentEffect() {
        if (inWater) {
            return new MobEffectInstance(ModMobEffects.MERFOLK, 20, 0, true, false);
        }
        return null;
    }

    @Override
    protected void curioTick(LivingEntity livingEntity, ItemStack stack) {
        inWater = livingEntity.isInWater();
    }

    private static TriState onPlayerSwim(Player player) {
        return ModComponents.SWIM_ABILITIES.maybeGet(player)
                .filter(swimAbilityComponent -> swimAbilityComponent.isSinking() && !swimAbilityComponent.isSwimming())
                .map(swimAbilities -> TriState.FALSE)
                .orElse(TriState.DEFAULT);
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (entity instanceof ServerPlayer && TrinketsHelper.areEffectsEnabled(stack)) {
            ModComponents.SWIM_ABILITIES.maybeGet(entity).ifPresent(comp -> {
                comp.setSinking(true);
                ModComponents.SWIM_ABILITIES.sync(entity);
            });
        }
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (entity instanceof ServerPlayer) {
            ModComponents.SWIM_ABILITIES.maybeGet(entity).ifPresent(comp -> {
                comp.setSinking(false);
                ModComponents.SWIM_ABILITIES.sync(entity);
            });
        }
    }
}
