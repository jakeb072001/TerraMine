package terracraft.common.item.curio.feet;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import terracraft.common.init.ModItems;
import terracraft.common.item.curio.TrinketTerrariaItem;

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

    @Override
	protected void curioTick(LivingEntity livingEntity, ItemStack stack) {
		Minecraft mc = Minecraft.getInstance();

		if (mc.player == null || mc.level == null)
			return;

		Player player = mc.player;

		if (timer++ >= 6)
		{
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
		if (mc.options.keyUp.isDown()) {
			if (mc.options.keyUp.isDown() && upPressed && upKeyUnpressed && !player.getCooldowns().isOnCooldown(ModItems.TABI) && !player.isInWaterOrBubble()) {
				playParticle((Player) livingEntity, mc);
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
		if (mc.options.keyDown.isDown()) {
			if (mc.options.keyDown.isDown() && downPressed && downKeyUnpressed && !player.getCooldowns().isOnCooldown(ModItems.TABI) && !player.isInWaterOrBubble()) {
				playParticle((Player) livingEntity, mc);
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
		if (mc.options.keyLeft.isDown()) {
			if (mc.options.keyLeft.isDown() && leftPressed && leftKeyUnpressed && !player.getCooldowns().isOnCooldown(ModItems.TABI) && !player.isInWaterOrBubble()) {
				playParticle((Player) livingEntity, mc);
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
		if (mc.options.keyRight.isDown()) {
			if (mc.options.keyRight.isDown() && rightPressed && rightKeyUnpressed && !player.getCooldowns().isOnCooldown(ModItems.TABI) && !player.isInWaterOrBubble()) {
				playParticle((Player) livingEntity, mc);
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

	private void playParticle(Player player, Minecraft mc) {
		for (int i = 0; i < 20; ++i) {
			double d0 = mc.level.random.nextGaussian() * 0.02D;
			double d1 = mc.level.random.nextGaussian() * 0.02D;
			double d2 = mc.level.random.nextGaussian() * 0.02D;
			float random = (player.getRandom().nextFloat() - 0.5F) * 0.1F;
			if (!player.isLocalPlayer()) {
				((ServerPlayer) player).getLevel().sendParticles(ParticleTypes.POOF, player.getX() + (double) (player.level.random.nextFloat() * player.getBbWidth() * 2.0F) - (double) player.getBbWidth() - d0 * 10.0D, player.getY() + (double) (player.level.random.nextFloat() * player.getBbHeight()) - d1 * 10.0D, player.getZ() + (double) (player.level.random.nextFloat() * player.getBbWidth() * 2.0F) - (double) player.getBbWidth() - d2 * 10.0D, 1, 0, -0.2D, 0, random);
			}
		}
		mc.player.playSound(SoundEvents.PHANTOM_FLAP, 1.0F, 2.0F);
		player.getCooldowns().addCooldown(ModItems.MASTER_NINJA_GEAR, 20);
	}
}
