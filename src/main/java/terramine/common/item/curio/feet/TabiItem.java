package terramine.common.item.curio.feet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import terramine.common.init.ModItems;
import terramine.common.item.curio.TrinketTerrariaItem;

public class TabiItem extends TrinketTerrariaItem {

	// todo: make work everytime, right now the booleans for checking doubled pressed will only set on either server or client which causes it to sometimes not work
	public boolean upPressed;
	public boolean downPressed;
	public boolean leftPressed;
	public boolean rightPressed;
	public boolean upKeyUnpressed;
	public boolean downKeyUnpressed;
	public boolean leftKeyUnpressed;
	public boolean rightKeyUnpressed;
	public int timer;

    @Override
	protected void curioTick(LivingEntity livingEntity, ItemStack stack) {
		Options options = Minecraft.getInstance().options;

		if (livingEntity instanceof Player player) {
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
			if (options.keyUp.isDown()) {
				if (options.keyUp.isDown() && upPressed && upKeyUnpressed && !player.getCooldowns().isOnCooldown(ModItems.TABI) && !player.isInWaterOrBubble()) {
					playParticle(player);
					playSound(player);
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
			if (options.keyDown.isDown()) {
				if (options.keyDown.isDown() && downPressed && downKeyUnpressed && !player.getCooldowns().isOnCooldown(ModItems.TABI) && !player.isInWaterOrBubble()) {
					playParticle(player);
					playSound(player);
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
			if (options.keyLeft.isDown()) {
				if (options.keyLeft.isDown() && leftPressed && leftKeyUnpressed && !player.getCooldowns().isOnCooldown(ModItems.TABI) && !player.isInWaterOrBubble()) {
					playParticle(player);
					playSound(player);
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
			if (options.keyRight.isDown()) {
				if (options.keyRight.isDown() && rightPressed && rightKeyUnpressed && !player.getCooldowns().isOnCooldown(ModItems.TABI) && !player.isInWaterOrBubble()) {
					playParticle(player);
					playSound(player);
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

	private void playParticle(Player player) {
		for (int i = 0; i < 20; ++i) {
			double d0 = player.level.random.nextGaussian() * 0.02D;
			double d1 = player.level.random.nextGaussian() * 0.02D;
			double d2 = player.level.random.nextGaussian() * 0.02D;
			float random = (player.getRandom().nextFloat() - 0.5F) * 0.1F;
			if (!player.isLocalPlayer()) {
				((ServerPlayer) player).getLevel().sendParticles(ParticleTypes.POOF, player.getX() + (double) (player.level.random.nextFloat() * player.getBbWidth() * 2.0F) - (double) player.getBbWidth() - d0 * 10.0D, player.getY() + (double) (player.level.random.nextFloat() * player.getBbHeight()) - d1 * 10.0D, player.getZ() + (double) (player.level.random.nextFloat() * player.getBbWidth() * 2.0F) - (double) player.getBbWidth() - d2 * 10.0D, 1, 0, -0.2D, 0, random);
			}
		}
		player.getCooldowns().addCooldown(ModItems.TABI, 20);
	}

	private void playSound(Player player) {
		player.playSound(SoundEvents.PHANTOM_FLAP, 1.0F, 2.0F);
	}
}
