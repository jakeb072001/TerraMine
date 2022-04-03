package terracraft.common.item.curio.belt;

import terracraft.Artifacts;
import terracraft.common.item.curio.TrinketArtifactItem;
import terracraft.extensions.LivingEntityExtensions;
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

public class BundleOfBalloonsItem extends TrinketArtifactItem {

	public static final ResourceLocation C2S_QUADRUPLE_JUMPED_ID = Artifacts.id("c2s_quadruple_jumped");

	public BundleOfBalloonsItem() {
		ServerPlayNetworking.registerGlobalReceiver(C2S_QUADRUPLE_JUMPED_ID, BundleOfBalloonsItem::handleDoubleJumpPacket);
	}

	private static void handleDoubleJumpPacket(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
		server.execute(() -> {
			((LivingEntityExtensions) player).artifacts$doubleJump();

			// Spawn particles
			for (int i = 0; i < 20; ++i) {
				double motionX = player.getRandom().nextGaussian() * 0.02;
				double motionY = player.getRandom().nextGaussian() * 0.02 + 0.20;
				double motionZ = player.getRandom().nextGaussian() * 0.02;
				player.getLevel().sendParticles(ParticleTypes.POOF, player.getX(), player.getY(), player.getZ(), 1, motionX, motionY, motionZ, 0.15);
			}
		});
	}

	@Override
	protected SoundInfo getEquipSoundInfo() {
		return new SoundInfo(SoundEvents.BOTTLE_FILL_DRAGONBREATH);
	}

    @Override
	public MobEffectInstance getPermanentEffect() {
		return new MobEffectInstance(MobEffects.JUMP, 20, 1, true, false);
	}
}
