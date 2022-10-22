package terramine.common.item;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import terramine.TerraMine;
import terramine.common.init.ModSoundEvents;

import java.util.Optional;
import java.util.UUID;

public class MagicMirrorItem extends TerrariaItem {

	public MagicMirrorItem() {
		super(new Properties().stacksTo(1).tab(TerraMine.ITEM_GROUP_EQUIPMENT).rarity(Rarity.RARE).fireResistant(), false);
	}

	@Override
	public ItemStack finishUsingItem(@NotNull ItemStack stack, Level level, @NotNull LivingEntity entity) {
		Player player = (Player)entity;
		if (!level.isClientSide) {
			ServerPlayer serverPlayer = (ServerPlayer)player;
			ServerLevel serverLevel = serverPlayer.server.getLevel(serverPlayer.getRespawnDimension());
			if (serverLevel != null) {
				BlockPos spawnpoint = serverPlayer.getRespawnPosition();
				if (spawnpoint != null) {
					Optional<Vec3> optionalSpawnVec = Player.findRespawnPositionAndUseSpawnBlock(serverLevel, spawnpoint, serverPlayer.getRespawnAngle(), false, false);

					//Player Spawn
					optionalSpawnVec.ifPresentOrElse(spawnVec -> {
						serverPlayer.teleportTo(serverLevel, spawnVec.x(), spawnVec.y(), spawnVec.z(), serverPlayer.getRespawnAngle(), 0.5F);
						serverLevel.playSound(null, spawnpoint, ModSoundEvents.MAGIC_MIRROR_USE, SoundSource.PLAYERS, 0.4f, 1f);
					}, () -> {
						worldSpawn(serverPlayer, serverLevel);
					});
				} else {
					worldSpawn(serverPlayer, serverLevel);
				}
			} else {
				((ServerPlayer) player).sendMessage(new TranslatableComponent("magic_mirror.fail"), ChatType.SYSTEM, UUID.randomUUID());
				level.playSound(null, player.blockPosition(), net.minecraft.sounds.SoundEvents.SHULKER_BULLET_HURT, SoundSource.BLOCKS, 1f, 1f);
			}
		}
		player.getCooldowns().addCooldown(this, 20);
		player.awardStat(Stats.ITEM_USED.get(this));
		return super.finishUsingItem(stack, level, entity);
	}

	@Override
	public UseAnim getUseAnimation(@NotNull ItemStack stack) {
		return UseAnim.BOW;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(@NotNull Level world, Player user, @NotNull InteractionHand hand) {
		ItemStack itemstack = user.getItemInHand(hand);
		user.startUsingItem(hand);
		return InteractionResultHolder.consume(itemstack);
	}

	@Override
	public int getUseDuration(@NotNull ItemStack stack) {
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
