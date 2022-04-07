package terracraft.common.entity.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import terracraft.TerraCraft;
import terracraft.client.render.ChestScreenHandler;

import javax.annotation.Nullable;

public class ChestEntity extends ChestBlockEntity {
    String name;
    MenuType<ChestScreenHandler> menu;
    static int viewerCount = 0; // this being static makes all my chests open, but if non-static doesn't change value at all
    private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter(){

        @Override
        protected void onOpen(Level level, BlockPos blockPos, BlockState blockState) {
            playSound(level, blockPos, blockState, SoundEvents.CHEST_OPEN);
        }

        @Override
        protected void onClose(Level level, BlockPos blockPos, BlockState blockState) {
            playSound(level, blockPos, blockState, SoundEvents.CHEST_CLOSE);
        }

        @Override
        protected void openerCountChanged(Level level, BlockPos blockPos, BlockState blockState, int i, int j) {
            signalOpenCount(level, blockPos, blockState, i, j);
        }

        @Override
        protected boolean isOwnContainer(Player player) {
            if (player.containerMenu instanceof ChestMenu) {
                Container container = ((ChestMenu)player.containerMenu).getContainer();
                return container == ChestEntity.this || container instanceof CompoundContainer && ((CompoundContainer)container).contains(ChestEntity.this);
            }
            return false;
        }
    };


    public ChestEntity(String name, MenuType<ChestScreenHandler> menu, BlockEntityType type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.name = name;
        this.menu = menu;
        this.setItems(NonNullList.withSize(40, ItemStack.EMPTY));
    }

    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new ChestScreenHandler(40, menu, i, inventory, ContainerLevelAccess.create(level, getBlockPos()));
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("entity." + TerraCraft.MOD_ID + "." + name);
    }

    float animationAngle;
    float lastAnimationAngle;

    public void clientTick() {
        if (level != null && level.isClientSide) {
            int viewerCount = this.countViewers();
            lastAnimationAngle = animationAngle;
            if (viewerCount == 0 && animationAngle > 0.0F || viewerCount > 0 && animationAngle < 1.0F) {
                if (viewerCount > 0) animationAngle += 0.1F;
                else animationAngle -= 0.1F;
                animationAngle = Mth.clamp(animationAngle, 0, 1);
            }
        }
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public int countViewers() {
        return viewerCount;
    }

    @Override
    public void startOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            openersCounter.incrementOpeners(player, getLevel(), getBlockPos(), getBlockState());
            ++viewerCount;
        }
    }

    @Override
    public void stopOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            openersCounter.decrementOpeners(player, getLevel(), getBlockPos(), getBlockState());
            --viewerCount;
        }
    }

    @Override
    public float getOpenNess(float f) {
        return Mth.lerp(f, lastAnimationAngle, animationAngle);
    }

    private void playSound(Level level, BlockPos blockPos, BlockState blockState, SoundEvent soundEvent) {
        ChestType chestType = blockState.getValue(ChestBlock.TYPE);
        if (chestType == ChestType.LEFT) {
            return;
        }
        double d = (double)blockPos.getX() + 0.5;
        double e = (double)blockPos.getY() + 0.5;
        double f = (double)blockPos.getZ() + 0.5;
        if (chestType == ChestType.RIGHT) {
            Direction direction = ChestBlock.getConnectedDirection(blockState);
            d += (double)direction.getStepX() * 0.5;
            f += (double)direction.getStepZ() * 0.5;
        }
        level.playSound(null, d, e, f, soundEvent, SoundSource.BLOCKS, 0.5f, level.random.nextFloat() * 0.1f + 0.9f);
    }

    @Override
    public int getContainerSize() {
        return 40;
    }
}
