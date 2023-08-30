package terramine.mixin.item.accessories.metaldetector.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.TerraMine;
import terramine.common.init.ModBlocks;
import terramine.common.init.ModItems;
import terramine.common.misc.AccessoriesHelper;

import java.util.List;
import java.util.stream.Stream;

@Mixin(Gui.class)
public abstract class GuiMixin {

	@Shadow private int screenHeight;
	@Shadow private int screenWidth;
	@Unique private int timer = 0;
	@Unique MutableComponent detectedText = Component.translatable(TerraMine.MOD_ID + ".ui.oreDetected");
	@Unique MutableComponent noDetectedText = Component.translatable(TerraMine.MOD_ID + ".ui.oreNotDetected");
	@Unique private String lastOre;

	@Shadow protected abstract Player getCameraPlayer();
	@Shadow public abstract Font getFont();

	@Inject(method = "renderPlayerHealth", require = 0, at = @At(value = "TAIL"))
	private void renderGuiMetalDetector(PoseStack matrices, CallbackInfo ci) {
		Player player = this.getCameraPlayer();

		if (player == null || !getEquippedAccessories(player)) {
			return;
		}
		if (lastOre == null) {
			lastOre = noDetectedText.getString();
		}

		timer += 1;
		if (timer >= 500) {
			lastOre = getNearRareOre();
			timer = 0;
		}


		int left = this.screenWidth - 22 - this.getFont().width(lastOre);
		int top = this.screenHeight - 63;

		matrices.pushPose();
		Gui.drawString(matrices, Minecraft.getInstance().font, lastOre, left, top, 0xffffff);
	}

	@Unique
	private boolean getEquippedAccessories(Player player) {
		return AccessoriesHelper.isInInventory(ModItems.METAL_DETECTOR, player) || AccessoriesHelper.isInInventory(ModItems.GOBLIN_TECH, player) || AccessoriesHelper.isInInventory(ModItems.PDA, player)
				|| AccessoriesHelper.isInInventory(ModItems.CELL_PHONE, player);
	}

	@Unique
	private String getNearRareOre() {
		Minecraft mc = Minecraft.getInstance();
		StringBuilder sb = new StringBuilder();
		boolean ancientDebris = false;
		boolean emeraldOre = false;
		boolean diamondOre = false;
		boolean goldOre = false;
		boolean demoniteOre = false;
		boolean crimtaneOre = false;
		boolean lapisOre = false;
		boolean redstoneOre = false;
		boolean ironOre = false;
		boolean copperOre = false;
		boolean coalOre = false;
		boolean netherQuartz = false;
		if (mc.player != null && mc.level != null) {
			AABB detectionBounds = new AABB(
					mc.player.position().x() - 7.5f,
					mc.player.position().y() - 7.5f,
					mc.player.position().z() - 7.5f,
					mc.player.position().x() + 7.5f,
					mc.player.position().y() + 7.5f,
					mc.player.position().z() + 7.5f
			);
			Stream<BlockState> blocks = mc.level.getBlockStates(detectionBounds);
			List<BlockState> blocksList = blocks.toList();
			for (BlockState block : blocksList) {
				if (checkBlocks(block, Blocks.ANCIENT_DEBRIS)) {
					ancientDebris = true;
				} else if (checkBlocks(block, Blocks.EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE)) {
					emeraldOre = true;
				} else if (checkBlocks(block, Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE)) {
					diamondOre = true;
				} else if (checkBlocks(block, Blocks.GOLD_ORE, Blocks.DEEPSLATE_GOLD_ORE, Blocks.NETHER_GOLD_ORE)) {
					goldOre = true;
				} else if (checkBlocks(block, ModBlocks.DEMONITE_ORE, ModBlocks.DEEPSLATE_DEMONITE_ORE)) {
					demoniteOre = true;
				} else if (checkBlocks(block, ModBlocks.CRIMTANE_ORE, ModBlocks.DEEPSLATE_CRIMTANE_ORE)) {
					crimtaneOre = true;
				} else if (checkBlocks(block, Blocks.LAPIS_ORE, Blocks.DEEPSLATE_LAPIS_ORE)) {
					lapisOre = true;
				} else if (checkBlocks(block, Blocks.REDSTONE_ORE, Blocks.DEEPSLATE_REDSTONE_ORE)) {
					redstoneOre = true;
				} else if (checkBlocks(block, Blocks.IRON_ORE, Blocks.DEEPSLATE_IRON_ORE)) {
					ironOre = true;
				} else if (checkBlocks(block, Blocks.COPPER_ORE, Blocks.DEEPSLATE_COPPER_ORE)) {
					copperOre = true;
				} else if (checkBlocks(block, Blocks.COAL_ORE, Blocks.DEEPSLATE_COAL_ORE)) {
					coalOre = true;
				} else if (checkBlocks(block, Blocks.NETHER_QUARTZ_ORE)) {
					netherQuartz = true;
				}
			}
		}
		if (ancientDebris) {
			createText(Blocks.ANCIENT_DEBRIS, sb);
		} else if (emeraldOre) {
			createText(Blocks.EMERALD_ORE, sb);
		} else if (diamondOre) {
			createText(Blocks.DIAMOND_ORE, sb);
		} else if (goldOre) {
			createText(Blocks.GOLD_ORE, sb);
		} else if (demoniteOre) {
			createText(ModBlocks.DEMONITE_ORE, sb);
		} else if (crimtaneOre) {
			createText(ModBlocks.CRIMTANE_ORE, sb);
		} else if (lapisOre) {
			createText(Blocks.LAPIS_ORE, sb);
		} else if (redstoneOre) {
			createText(Blocks.REDSTONE_ORE, sb);
		} else if (ironOre) {
			createText(Blocks.IRON_ORE, sb);
		} else if (copperOre) {
			createText(Blocks.COPPER_ORE, sb);
		} else if (coalOre) {
			createText(Blocks.COAL_ORE, sb);
		} else if (netherQuartz) {
			createText(Blocks.NETHER_QUARTZ_ORE, sb);
		} else {
			sb.append(noDetectedText.getString());
		}
		return sb.toString();
	}

	@Unique
	private void createText(Block block, StringBuilder sb) {
		sb.append(block.getName().getString()).append(" ").append(detectedText.getString());
	}

	@Unique
	private boolean checkBlocks(BlockState state, Block block) {
		return state.getBlock().equals(block);
	}

	@Unique
	private boolean checkBlocks(BlockState state, Block block1, Block block2) {
		return state.getBlock().equals(block1) || state.getBlock().equals(block2);
	}

	@Unique
	private boolean checkBlocks(BlockState state, Block block1, Block block2, Block block3) {
		return state.getBlock().equals(block1) || state.getBlock().equals(block2) || state.getBlock().equals(block3);
	}
}
