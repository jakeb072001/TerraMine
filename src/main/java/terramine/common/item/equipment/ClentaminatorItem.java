package terramine.common.item.equipment;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import terramine.common.init.ModBlocks;
import terramine.common.init.ModComponents;
import terramine.common.init.ModItems;
import terramine.common.init.ModSoundEvents;
import terramine.common.item.TerrariaItem;
import terramine.common.utility.Utilities;

// todo: add other fuel types, do for hardmode update
public class ClentaminatorItem extends TerrariaItem {
	private static final int MAX_POINTS = 10;
	private static final double SPACING = 1;
	private int tickCounter = 0;

	public ClentaminatorItem(ResourceKey<Item> key) {
		super(new Properties().setId(key).stacksTo(1).rarity(Rarity.EPIC), false);
	}

	@Override
	public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
		tickCounter = 0;

		if (entity instanceof Player player) {
			player.awardStat(Stats.ITEM_USED.get(this));
			player.getCooldowns().addCooldown(this.getDefaultInstance(), 10);
			consumeFuel(player);
		}

		return super.finishUsingItem(stack, level, entity);
	}

	// todo: add custom use animation (also use this animation for space gun), third person is bow but first person needs to look better
	@Override
	public @NotNull ItemUseAnimation getUseAnimation(ItemStack stack) {
		return ItemUseAnimation.BOW;
	}

	@Override
	public @NotNull InteractionResult use(Level level, Player player, InteractionHand hand) {
		if (hasFuel(player)) {
			player.startUsingItem(hand);
			if (level instanceof ServerLevel serverLevel) {
				cleansAction(serverLevel, player);
			}

			return InteractionResult.SUCCESS;
		}

		return InteractionResult.FAIL;
	}

	@Override
	public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int slot) {
		if (!level.isClientSide && entity instanceof Player player && hasFuel(player)) {
			tickCounter++;

			if (tickCounter % 10 == 0) {
				cleansAction((ServerLevel) level, player);
			}
		}
	}

	@Override
	public boolean releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeCharged) {
		tickCounter = 0;
		if (entity instanceof Player player) {
			player.getCooldowns().addCooldown(this.getDefaultInstance(), 10);
			consumeFuel(player);
		}

		return true;
	}

	@Override
	public int getUseDuration(ItemStack itemStack, LivingEntity livingEntity) {
		return 65;
	}

	private void cleansAction(ServerLevel level, Player player) {
		// todo: make look like it's coming out of the gun
		Vec3 playerPos = player.position().add(0, 1.5, 0);
		Vec3 playerDirection = player.getLookAngle();

		for (int i = 0; i < MAX_POINTS; i++) {
			Vec3 offsetPos = playerPos.add(playerDirection.scale(i * SPACING));

			spawnParticles(level, offsetPos);

			int radius = 2;
			for (int dx = -radius; dx <= radius; dx++) {
				for (int dy = -radius; dy <= radius; dy++) {
					for (int dz = -radius; dz <= radius; dz++) {
						BlockPos blockPos = new BlockPos((int) offsetPos.x() + dx, (int) offsetPos.y() + dy, (int) offsetPos.z() + dz);
						level.setBlockAndUpdate(blockPos, getCleanBlock(level, blockPos));
						Utilities.cleanBiome(level, blockPos);
						level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSoundEvents.CLENTAMINATOR_USE, SoundSource.PLAYERS, 0.05f, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F));
					}
				}
			}
		}
	}

	private void spawnParticles(ServerLevel level, Vec3 pos) {
		DustParticleOptions dustEffect = new DustParticleOptions(65280, 5.0F);
		level.sendParticles(dustEffect, pos.x, pos.y, pos.z, 10, 0.1, 0.1, 0.1, 0.05);
	}

	private BlockState getCleanBlock(ServerLevel serverLevel, BlockPos blockPos) {
		if (ModComponents.EVIL_TYPE.get(serverLevel.getLevelData()).get()) {
			if (ModBlocks.crimsonSpreadBlocks.containsKey(serverLevel.getBlockState(blockPos).getBlock())) {
				return ModBlocks.crimsonSpreadBlocks.get(serverLevel.getBlockState(blockPos).getBlock()).defaultBlockState();
			}
		} else {
			if (ModBlocks.corruptionSpreadBlocks.containsKey(serverLevel.getBlockState(blockPos).getBlock())) {
				return ModBlocks.corruptionSpreadBlocks.get(serverLevel.getBlockState(blockPos).getBlock()).defaultBlockState();
			}
		}

		return serverLevel.getBlockState(blockPos);
	}

	private boolean hasFuel(Player player) {
		if (player.hasInfiniteMaterials()) {
			return true;
		}

		return player.getInventory().contains(ModItems.GREEN_SOLUTION.getDefaultInstance());
	}

	private void consumeFuel(Player player) {
		if (!player.hasInfiniteMaterials()) {
			for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
				ItemStack slotStack = player.getInventory().getItem(i);
				if (slotStack.is(ModItems.GREEN_SOLUTION)) {
					slotStack.shrink(1);
					if (slotStack.isEmpty()) {
						player.getInventory().setItem(i, ItemStack.EMPTY);
					}
					break;
				}
			}
		}
	}
}
