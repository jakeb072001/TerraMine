package terramine.common.item.accessories.belt;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import terramine.common.init.ModItems;
import terramine.common.item.accessories.AccessoryTerrariaItem;
import terramine.common.network.ServerPacketHandler;
import terramine.common.utility.InputHandler;

// todo: make work without a packet, curioTick runs on both client and server so it should work fine, do same for Tabi
public class MasterNinjaGearItem extends AccessoryTerrariaItem {

	public boolean upPressed, downPressed, leftPressed, rightPressed;
	public boolean upKeyUnpressed, downKeyUnpressed, leftKeyUnpressed, rightKeyUnpressed;
	public int timer;

    @Override
	protected void curioTick(Player player, ItemStack stack) {
		if (player.level().isClientSide) {
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
				if (InputHandler.isHoldingForwards(player) && upPressed && upKeyUnpressed && !player.getCooldowns().isOnCooldown(ModItems.MASTER_NINJA_GEAR) && !player.isInWaterOrBubble()) {
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
				if (InputHandler.isHoldingBackwards(player) && downPressed && downKeyUnpressed && !player.getCooldowns().isOnCooldown(ModItems.MASTER_NINJA_GEAR) && !player.isInWaterOrBubble()) {
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
				if (InputHandler.isHoldingLeft(player) && leftPressed && leftKeyUnpressed && !player.getCooldowns().isOnCooldown(ModItems.MASTER_NINJA_GEAR) && !player.isInWaterOrBubble()) {
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
				if (InputHandler.isHoldingRight(player) && rightPressed && rightKeyUnpressed && !player.getCooldowns().isOnCooldown(ModItems.MASTER_NINJA_GEAR) && !player.isInWaterOrBubble()) {
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
		buf.writeBoolean(true);
		ClientPlayNetworking.send(ServerPacketHandler.DASH_PACKET_ID, buf);
	}
}
