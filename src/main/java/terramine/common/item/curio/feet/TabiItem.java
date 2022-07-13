package terramine.common.item.curio.feet;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import terramine.common.init.ModItems;
import terramine.common.item.curio.TrinketTerrariaItem;
import terramine.common.network.ServerPacketHandler;
import terramine.common.trinkets.TrinketsHelper;
import terramine.common.utility.InputHandler;

public class TabiItem extends TrinketTerrariaItem {

	public boolean upPressed;
	public boolean downPressed;
	public boolean leftPressed;
	public boolean rightPressed;
	public boolean upKeyUnpressed;
	public boolean downKeyUnpressed;
	public boolean leftKeyUnpressed;
	public boolean rightKeyUnpressed;
	public int timer;

	@Environment(EnvType.CLIENT)
    @Override
	protected void curioTick(LivingEntity livingEntity, ItemStack stack) {
		if (livingEntity instanceof LocalPlayer player && !TrinketsHelper.isEquipped(ModItems.MASTER_NINJA_GEAR, player)) {
			if (timer++ >= 6) {
				if (upPressed) {
					upPressed = false;
					upKeyUnpressed = false;
					timer = 0;
				}
				if (downPressed) {
					downPressed = false;
					downKeyUnpressed = false;
					timer = 0;
				}
				if (leftPressed) {
					leftPressed = false;
					leftKeyUnpressed = false;
					timer = 0;
				}
				if (rightPressed) {
					rightPressed = false;
					rightKeyUnpressed = false;
					timer = 0;
				}
			}
			//up
			if (InputHandler.isHoldingForwards(player)) {
				if (InputHandler.isHoldingForwards(player) && upPressed && upKeyUnpressed && !player.getCooldowns().isOnCooldown(ModItems.TABI) && !player.isInWaterOrBubble()) {
					sendDash();
					player.moveRelative(1, new Vec3(0, 0, 10));
					upPressed = false;
					upKeyUnpressed = false;
				} else {
					upPressed = true;
				}
			} else if (upPressed) {
				upKeyUnpressed = true;
			}
			//down
			if (InputHandler.isHoldingBackwards(player)) {
				if (InputHandler.isHoldingBackwards(player) && downPressed && downKeyUnpressed && !player.getCooldowns().isOnCooldown(ModItems.TABI) && !player.isInWaterOrBubble()) {
					sendDash();
					player.moveRelative(1, new Vec3(0, 0, -10));
					downPressed = false;
					downKeyUnpressed = false;
				} else {
					downPressed = true;
				}
			} else if (downPressed) {
				downKeyUnpressed = true;
			}
			//left
			if (InputHandler.isHoldingLeft(player)) {
				if (InputHandler.isHoldingLeft(player) && leftPressed && leftKeyUnpressed && !player.getCooldowns().isOnCooldown(ModItems.TABI) && !player.isInWaterOrBubble()) {
					sendDash();
					player.moveRelative(1, new Vec3(10, 0, 0));
					leftPressed = false;
					leftKeyUnpressed = false;
				} else {
					leftPressed = true;
				}
			} else if (leftPressed) {
				leftKeyUnpressed = true;
			}
			//right
			if (InputHandler.isHoldingRight(player)) {
				if (InputHandler.isHoldingRight(player) && rightPressed && rightKeyUnpressed && !player.getCooldowns().isOnCooldown(ModItems.TABI) && !player.isInWaterOrBubble()) {
					sendDash();
					player.moveRelative(1, new Vec3(-10, 0, 0));
					rightPressed = false;
					rightKeyUnpressed = false;
				} else {
					rightPressed = true;
				}
			} else if (rightPressed) {
				rightKeyUnpressed = true;
			}
		}
	}

	@Environment(EnvType.CLIENT)
	private static void sendDash() {
		FriendlyByteBuf buf = PacketByteBufs.create();
		buf.writeBoolean(false);
		ClientPlayNetworking.send(ServerPacketHandler.DASH_PACKET_ID, buf);
	}
}
