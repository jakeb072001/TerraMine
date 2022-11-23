package terramine.common.item.accessories.hands;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import terramine.TerraMine;
import terramine.common.item.accessories.TrinketTerrariaItem;
import terramine.common.utility.InputHandler;

import java.util.UUID;

public class FeralClawsItem extends TrinketTerrariaItem {

	int timer = 0;

    @Override
	protected Multimap<Attribute, AttributeModifier> applyModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
		Multimap<Attribute, AttributeModifier> result = super.applyModifiers(stack, slot, entity, uuid);
		AttributeModifier modifier = new AttributeModifier(uuid,
				TerraMine.id("feral_claws_attack_speed").toString(),
				0.12, AttributeModifier.Operation.MULTIPLY_TOTAL);
		result.put(Attributes.ATTACK_SPEED, modifier);
		return result;
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void curioTick(LivingEntity livingEntity, ItemStack stack) {
		Minecraft mc = Minecraft.getInstance();
		if (livingEntity instanceof Player player && InputHandler.isHoldingAttack(player)) {
			if (player.getAttackStrengthScale(0) >= 1) {
				if (mc.hitResult != null && mc.hitResult.getType() == HitResult.Type.ENTITY && !(mc.hitResult instanceof BlockHitResult)) {
					Entity entity = ((EntityHitResult) mc.hitResult).getEntity();
					if (entity.isAlive() && entity.isAttackable()) {
						timer++;
						if (timer >= 50 && mc.gameMode != null) {
							mc.gameMode.attack(player, entity);
							timer = 0;
						}
					}
				}
			}
		}
	}

	@Override
	protected SoundInfo getEquipSoundInfo() {
		return new SoundInfo(SoundEvents.ARMOR_EQUIP_NETHERITE);
	}
}
