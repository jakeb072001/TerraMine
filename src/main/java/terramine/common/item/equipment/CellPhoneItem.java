package terramine.common.item.equipment;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import terramine.common.init.ModSoundEvents;
import terramine.common.item.accessories.TrinketTerrariaItem;

import java.util.Optional;

public class CellPhoneItem extends TrinketTerrariaItem {

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
		Player player = (Player)entity;
		if (!level.isClientSide) {
			ServerPlayer serverPlayer = (ServerPlayer)player;
			ServerLevel serverLevel = serverPlayer.server.getLevel(serverPlayer.getRespawnDimension());
			if (serverLevel != null) {
				BlockPos spawnpoint = serverPlayer.getRespawnPosition();
				if (spawnpoint != null) {
					Optional<Vec3> optionalSpawnVec = Player.findRespawnPositionAndUseSpawnBlock(serverLevel, spawnpoint, serverPlayer.getRespawnAngle(), false, false);

					//Player Spawn
					BlockPos finalSpawnpoint = spawnpoint;
					optionalSpawnVec.ifPresentOrElse(spawnVec -> {
						serverPlayer.teleportTo(serverLevel, spawnVec.x(), spawnVec.y(), spawnVec.z(), serverPlayer.getRespawnAngle(), 0.5F);
						serverLevel.playSound(null, finalSpawnpoint, ModSoundEvents.MAGIC_MIRROR_USE, SoundSource.PLAYERS, 0.4f, 1f);
					}, () -> {
						worldSpawn(serverPlayer, serverLevel);
					});
				} else {
					worldSpawn(serverPlayer, serverLevel);
				}
			} else {
				((ServerPlayer) player).sendSystemMessage(Component.translatable("magic_mirror.fail"), ChatType.SYSTEM);
				level.playSound(null, player.blockPosition(), net.minecraft.sounds.SoundEvents.SHULKER_BULLET_HURT, SoundSource.BLOCKS, 1f, 1f);
			}
		}
		if (player != null) {
			player.getCooldowns().addCooldown(this, 20);
			player.awardStat(Stats.ITEM_USED.get(this));
		}
		return super.finishUsingItem(stack, level, entity);
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.BOW;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(@NotNull Level world, Player user, @NotNull InteractionHand hand) {
		ItemStack itemstack = user.getItemInHand(hand);
		user.startUsingItem(hand);
		return InteractionResultHolder.consume(itemstack);
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 28;
	}

	public void worldSpawn(ServerPlayer serverPlayer, ServerLevel serverWorld) {
		BlockPos spawnpoint = serverWorld.getSharedSpawnPos();
		serverPlayer.teleportTo(serverWorld, spawnpoint.getX(), spawnpoint.getY(), spawnpoint.getZ(), serverPlayer.getRespawnAngle(), 0.5F);
		while (!serverWorld.isEmptyBlock(serverPlayer.blockPosition())) {
			serverPlayer.teleportTo(serverPlayer.getX(), serverPlayer.getY() + 1.0D, serverPlayer.getZ());
		}
		serverWorld.playSound(null, spawnpoint, ModSoundEvents.MAGIC_MIRROR_USE, SoundSource.PLAYERS, 0.4f, 1f);
	}
}
