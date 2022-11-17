package terramine.mixin.item.accessories.neptuneshell;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.common.init.ModItems;
import terramine.common.item.accessories.TrinketTerrariaItem;
import terramine.common.trinkets.TrinketsHelper;

import java.util.UUID;

@Mixin(Player.class)
public abstract class PlayerMixin  extends LivingEntity {

    @Unique
    private static final AttributeModifier charmMeleeModifier = new AttributeModifier(UUID.fromString("f1d1c64e-4a5c-4d56-86c6-d6e6affc8c97"),
            "moon_charm_melee",0.051, AttributeModifier.Operation.MULTIPLY_TOTAL);
    @Unique
    private static final AttributeModifier charmMovementSpeedModifier = new AttributeModifier(UUID.fromString("f8fa8653-da98-4e1b-a88b-b2f33abb5d18"),
            "moon_charm_movement_speed",0.05, AttributeModifier.Operation.MULTIPLY_TOTAL);
    @Unique
    private static final AttributeModifier charmDefenseModifier = new AttributeModifier(UUID.fromString("71d7a7da-fca0-4bc1-8fae-b9f622a5b29d"),
            "moon_charm_defense",3, AttributeModifier.Operation.ADDITION);
    @Unique
    private static final AttributeModifier celestialMeleeModifier = new AttributeModifier(UUID.fromString("0c835c4a-64b3-4a3e-a913-c6a517e7a8be"),
            "celestial_stone_melee",0.1, AttributeModifier.Operation.MULTIPLY_TOTAL);
    @Unique
    private static final AttributeModifier celestialDefenseModifier = new AttributeModifier(UUID.fromString("dcf873c8-0cf6-451f-aa52-2e7572ec3b18"),
            "celestial_stone_defense",4, AttributeModifier.Operation.ADDITION);

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "tick")
    private void tick(CallbackInfo info) {
        if(TrinketsHelper.isEquipped(ModItems.NEPTUNE_SHELL, this) || TrinketsHelper.isEquipped(ModItems.MOON_SHELL, this)
                || TrinketsHelper.isEquipped(ModItems.CELESTIAL_SHELL, this)) {
            if(this.getAirSupply() < this.getMaxAirSupply()){
                this.setAirSupply(this.increaseAirSupply(this.getAirSupply()));
            }
        }

        if (!this.level.isClientSide()) {
            AttributeInstance attackDamage = this.getAttribute(Attributes.ATTACK_DAMAGE);
            AttributeInstance attackSpeed = this.getAttribute(Attributes.ATTACK_SPEED);
            AttributeInstance movementSpeed = this.getAttribute(Attributes.MOVEMENT_SPEED);
            AttributeInstance armorAdd = this.getAttribute(Attributes.ARMOR);

            boolean isNight = this.level.isNight();

            if (attackDamage != null && attackSpeed != null && movementSpeed != null && armorAdd != null) {
                if ((TrinketsHelper.isEquipped(ModItems.MOON_STONE, this) && isNight) || (TrinketsHelper.isEquipped(ModItems.SUN_STONE, this) && !isNight)
                        || TrinketsHelper.isEquipped(ModItems.CELESTIAL_STONE, this) || TrinketsHelper.isEquipped(ModItems.CELESTIAL_SHELL, this)) {
                    TrinketTerrariaItem.addModifier(attackDamage, celestialMeleeModifier);
                    TrinketTerrariaItem.addModifier(attackSpeed, celestialMeleeModifier);
                    TrinketTerrariaItem.addModifier(armorAdd, celestialDefenseModifier);
                } else {
                    TrinketTerrariaItem.removeModifier(attackDamage, celestialMeleeModifier);
                    TrinketTerrariaItem.removeModifier(attackSpeed, celestialMeleeModifier);
                    TrinketTerrariaItem.removeModifier(armorAdd, celestialDefenseModifier);
                }
                if ((TrinketsHelper.isEquipped(ModItems.MOON_CHARM, this) || TrinketsHelper.isEquipped(ModItems.MOON_SHELL, this)
                        || TrinketsHelper.isEquipped(ModItems.CELESTIAL_SHELL, this)) && isNight) {
                    TrinketTerrariaItem.addModifier(attackDamage, charmMeleeModifier);
                    TrinketTerrariaItem.addModifier(attackSpeed, charmMeleeModifier);
                    TrinketTerrariaItem.addModifier(movementSpeed, charmMovementSpeedModifier);
                    TrinketTerrariaItem.addModifier(armorAdd, charmDefenseModifier);
                } else {
                    TrinketTerrariaItem.removeModifier(attackDamage, charmMeleeModifier);
                    TrinketTerrariaItem.removeModifier(attackSpeed, charmMeleeModifier);
                    TrinketTerrariaItem.removeModifier(movementSpeed, charmMovementSpeedModifier);
                    TrinketTerrariaItem.removeModifier(armorAdd, charmDefenseModifier);
                }
            }
        }
    }
}
