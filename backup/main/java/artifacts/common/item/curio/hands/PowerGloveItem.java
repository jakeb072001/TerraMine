package terracraft.common.item.curio.hands;

import terracraft.Artifacts;
import terracraft.common.item.curio.TrinketArtifactItem;
import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.UUID;

public class PowerGloveItem extends TrinketArtifactItem {

	int timer = 0;

	@Override
	protected Multimap<Attribute, AttributeModifier> applyModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
		Multimap<Attribute, AttributeModifier> result = super.applyModifiers(stack, slot, entity, uuid);
		AttributeModifier modifier = new AttributeModifier(uuid,
				Artifacts.id("power_glove_attack_speed").toString(),
				0.12, AttributeModifier.Operation.MULTIPLY_TOTAL);
		AttributeModifier modifier2 = new AttributeModifier(uuid,
				Artifacts.id("power_glove_attack_range").toString(),
				3, AttributeModifier.Operation.ADDITION);
		AttributeModifier modifier3 = new AttributeModifier(uuid,
				Artifacts.id("power_glove_range").toString(),
				0.5, AttributeModifier.Operation.ADDITION);
		result.put(Attributes.ATTACK_SPEED, modifier);
		result.put(ReachEntityAttributes.ATTACK_RANGE, modifier2);
		result.put(ReachEntityAttributes.REACH, modifier3);
		return result;
	}

	@Override
	public void curioTick(LivingEntity player, ItemStack stack) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.options.keyAttack.isDown())
			if (mc.player != null && mc.player.getAttackStrengthScale(0) >= 1) {
				if (mc.hitResult != null && mc.hitResult.getType() == HitResult.Type.ENTITY && !(mc.hitResult instanceof BlockHitResult)) {
					Entity entity = ((EntityHitResult) mc.hitResult).getEntity();
					if (entity.isAlive() && entity.isAttackable()) {
						timer += 1;
						if (timer >= 2) {
							mc.gameMode.attack(mc.player, entity);
							timer = 0;
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
