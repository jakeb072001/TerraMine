package terramine.common.item.accessories.hands;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import terramine.TerraMine;
import terramine.common.item.accessories.AccessoryTerrariaItem;
import terramine.extensions.LivingEntityExtensions;

public class CloudInABalloonItem extends AccessoryTerrariaItem {

	public static final ResourceLocation C2S_DOUBLE_JUMPED_ID = TerraMine.id("c2s_double_jumped");

	public CloudInABalloonItem() {
		ServerPlayNetworking.registerGlobalReceiver(C2S_DOUBLE_JUMPED_ID, CloudInABalloonItem::handleDoubleJumpPacket);
	}

	private static void handleDoubleJumpPacket(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
		server.execute(() -> {
			((LivingEntityExtensions) player).terramine$doubleJump();

			// Spawn particles
			for (int i = 0; i < 20; ++i) {
				double motionX = player.getRandom().nextGaussian() * 0.02;
				double motionY = player.getRandom().nextGaussian() * 0.02 + 0.20;
				double motionZ = player.getRandom().nextGaussian() * 0.02;
				player.serverLevel().sendParticles(ParticleTypes.POOF, player.getX(), player.getY(), player.getZ(), 1, motionX, motionY, motionZ, 0.15);
			}
		});
	}

	@Override
	public SoundInfo getEquipSoundInfo() {
		return new SoundInfo(SoundEvents.BOTTLE_FILL_DRAGONBREATH);
	}

	@Override
	public void onEquip(ItemStack stack, Player player) {
		player.addEffect(new MobEffectInstance(MobEffects.JUMP, -1, 1, true, false));
	}

	@Override
	public void onUnequip(ItemStack stack, Player player) {
		player.removeEffect(MobEffects.JUMP);
	}
}
