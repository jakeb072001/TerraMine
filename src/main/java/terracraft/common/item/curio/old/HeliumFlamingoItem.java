package terracraft.common.item.curio.old;

import be.florens.expandability.api.fabric.PlayerSwimCallback;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.client.Minecraft;
import net.minecraft.locale.Language;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.player.Player;
import terracraft.TerraCraft;
import terracraft.common.components.SwimAbilityComponent;
import terracraft.common.init.ModComponents;
import terracraft.common.init.ModSoundEvents;
import terracraft.common.item.curio.TrinketTerrariaItem;

import java.util.Collections;
import java.util.List;


public class HeliumFlamingoItem extends TrinketTerrariaItem {

	public static final ResourceLocation C2S_AIR_SWIMMING_ID = TerraCraft.id("c2s_air_swimming");

	// TODO: config
	public static final int MAX_FLIGHT_TIME = 150;
	public static final int RECHARGE_TIME = 300;

	public HeliumFlamingoItem() {
		PlayerSwimCallback.EVENT.register(HeliumFlamingoItem::onPlayerSwim);
		ServerPlayNetworking.registerGlobalReceiver(C2S_AIR_SWIMMING_ID, HeliumFlamingoItem::handleAirSwimmingPacket);
	}

	private static TriState onPlayerSwim(Player player) {
		return ModComponents.SWIM_ABILITIES.maybeGet(player)
				.filter(SwimAbilityComponent::isSwimming)
				.map(swimAbilities -> TriState.TRUE)
				.orElse(TriState.DEFAULT);
	}

	private static void handleAirSwimmingPacket(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender packetSender) {
		boolean shouldSwim = buf.readBoolean();
		server.execute(() -> ModComponents.SWIM_ABILITIES.maybeGet(player)
				.ifPresent(swimAbilities -> swimAbilities.setSwimming(shouldSwim)));
	}

	@Override
	protected SoundInfo getEquipSoundInfo() {
		return new SoundInfo(ModSoundEvents.POP, 1f, 0.7f);
	}

	@Override
	protected List<String> getTooltipDescriptionArguments() {
		String translationKey = Minecraft.getInstance().options.keySprint.saveString();
		return Collections.singletonList(Language.getInstance().getOrDefault(translationKey));
	}
}
