package terracraft.mixin.item.metaldetector.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terracraft.TerraCraft;
import terracraft.common.init.ModBlocks;
import terracraft.common.init.ModItems;
import terracraft.common.trinkets.TrinketsHelper;

import java.util.List;
import java.util.stream.Stream;

@Mixin(Gui.class)
public abstract class GuiMixin {

	@Shadow private int screenHeight;
	@Shadow private int screenWidth;
	@Unique private int timer = 0;
	@Unique TranslatableComponent detectedText = new TranslatableComponent(TerraCraft.MOD_ID + ".ui.oreDetected");
	@Unique TranslatableComponent noDetectedText = new TranslatableComponent(TerraCraft.MOD_ID + ".ui.oreNotDetected");
	@Unique private String lastOre;

	@Shadow protected abstract Player getCameraPlayer();
	@Shadow public abstract Font getFont();

	@Inject(method = "renderPlayerHealth", require = 0, at = @At(value = "TAIL"))
	private void renderGuiClock(PoseStack matrices, CallbackInfo ci) {
		Player player = this.getCameraPlayer();

		if (player == null || !getEquippedTrinkets(player)) {
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
	private boolean getEquippedTrinkets(Player player) {
		boolean equipped = false;

		if (TrinketsHelper.isInInventory(ModItems.METAL_DETECTOR, player) || TrinketsHelper.isInInventory(ModItems.GOBLIN_TECH, player) || TrinketsHelper.isInInventory(ModItems.PDA, player)
				|| TrinketsHelper.isInInventory(ModItems.CELL_PHONE, player)) {
			equipped = true;
		}

		return equipped;
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
				if (block.getBlock().equals(Blocks.ANCIENT_DEBRIS)) {
					ancientDebris = true;
				} else if (block.getBlock().equals(Blocks.EMERALD_ORE) || block.getBlock().equals(Blocks.DEEPSLATE_EMERALD_ORE)) {
					emeraldOre = true;
				} else if (block.getBlock().equals(Blocks.DIAMOND_ORE) || block.getBlock().equals(Blocks.DEEPSLATE_DIAMOND_ORE)) {
					diamondOre = true;
				} else if (block.getBlock().equals(Blocks.GOLD_ORE) || block.getBlock().equals(Blocks.DEEPSLATE_GOLD_ORE) || block.getBlock().equals(Blocks.NETHER_GOLD_ORE)) {
					goldOre = true;
				} else if (block.getBlock().equals(ModBlocks.DEMONITE_ORE) || block.getBlock().equals(ModBlocks.DEEPSLATE_DEMONITE_ORE)) {
					demoniteOre = true;
				} else if (block.getBlock().equals(Blocks.LAPIS_ORE) || block.getBlock().equals(Blocks.DEEPSLATE_LAPIS_ORE)) {
					lapisOre = true;
				} else if (block.getBlock().equals(Blocks.REDSTONE_ORE) || block.getBlock().equals(Blocks.DEEPSLATE_REDSTONE_ORE)) {
					redstoneOre = true;
				} else if (block.getBlock().equals(Blocks.IRON_ORE) || block.getBlock().equals(Blocks.DEEPSLATE_IRON_ORE)) {
					ironOre = true;
				} else if (block.getBlock().equals(Blocks.COPPER_ORE) || block.getBlock().equals(Blocks.DEEPSLATE_COPPER_ORE)) {
					copperOre = true;
				} else if (block.getBlock().equals(Blocks.COAL_ORE) || block.getBlock().equals(Blocks.DEEPSLATE_COAL_ORE)) {
					coalOre = true;
				} else if (block.getBlock().equals(Blocks.NETHER_QUARTZ_ORE)) {
					netherQuartz = true;
				}
			}
		}
		if (ancientDebris) {
			sb.append(Blocks.ANCIENT_DEBRIS.getName().getString()).append(" ").append(detectedText.getString());
		} else if (emeraldOre) {
			sb.append(Blocks.EMERALD_ORE.getName().getString()).append(" ").append(detectedText.getString());
		} else if (diamondOre) {
			sb.append(Blocks.DIAMOND_ORE.getName().getString()).append(" ").append(detectedText.getString());
		} else if (goldOre) {
			sb.append(Blocks.GOLD_ORE.getName().getString()).append(" ").append(detectedText.getString());
		} else if (demoniteOre) {
			sb.append(ModBlocks.DEMONITE_ORE.getName().getString()).append(" ").append(detectedText.getString());
		} else if (lapisOre) {
			sb.append(Blocks.LAPIS_ORE.getName().getString()).append(" ").append(detectedText.getString());
		} else if (redstoneOre) {
			sb.append(Blocks.REDSTONE_ORE.getName().getString()).append(" ").append(detectedText.getString());
		} else if (ironOre) {
			sb.append(Blocks.IRON_ORE.getName().getString()).append(" ").append(detectedText.getString());
		} else if (copperOre) {
			sb.append(Blocks.COPPER_ORE.getName().getString()).append(" ").append(detectedText.getString());
		} else if (coalOre) {
			sb.append(Blocks.COAL_ORE.getName().getString()).append(" ").append(detectedText.getString());
		} else if (netherQuartz) {
			sb.append(Blocks.NETHER_QUARTZ_ORE.getName().getString()).append(" ").append(detectedText.getString());
		} else {
			sb.append(noDetectedText.getString());
		}
		return sb.toString();
	}
}
