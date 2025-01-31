package terramine.common.network.packet;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import terramine.common.network.ServerPacketHandler;
import terramine.common.network.types.LongNetworkType;

public class BoneMealPacket {
    public static void send(BlockPos pos) {
        ClientPlayNetworking.send(new LongNetworkType(pos.asLong(), ServerPacketHandler.BONE_MEAL_PACKET_ID));
    }

    public static void receive(LongNetworkType type, ServerPlayNetworking.Context context) {
        BlockPos pos = BlockPos.of(type.savedLong());
        context.player().server.execute(() -> {
            ServerPlayer player = context.player();
                BlockState state = player.level().getBlockState(pos);
                if (state.getBlock() instanceof BonemealableBlock fertilizable) {
                    if (fertilizable.isBonemealSuccess(player.level(), player.getRandom(), pos, state)) {
                        fertilizable.performBonemeal(player.server.getLevel(player.level().dimension()), player.getRandom(), pos, state);
                        player.level().levelEvent(2005, pos, 0);
                        player.level().playSound(null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1, 1);
                    }
                }
        });
    }
}
