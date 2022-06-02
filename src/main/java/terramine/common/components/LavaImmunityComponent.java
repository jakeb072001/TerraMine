package terramine.common.components;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import terramine.common.init.ModComponents;
import terramine.common.init.ModItems;
import terramine.common.trinkets.TrinketsHelper;

@SuppressWarnings("UnstableApiUsage")
public class LavaImmunityComponent implements PlayerComponent<Component>, AutoSyncedComponent {
    private final Player provider;
    private int immunityTimer = 140;

    public LavaImmunityComponent(Player provider) {
        this.provider = provider;
    }

    public void setLavaImmunityTimer(int immunityTimer) {
        this.immunityTimer = immunityTimer;
    }

    public int getLavaImmunityTimer() {
        return immunityTimer;
    }

    public void update() {
        ModComponents.LAVA_IMMUNITY.sync(provider);
        int maxImmunityTimer = 140;

        if (provider.isInLava() && !provider.hasEffect(MobEffects.FIRE_RESISTANCE)) {
            if (immunityTimer > 0 && getEquippedTrinkets(provider)) {
                --immunityTimer;
            }
        } else {
            if (immunityTimer < maxImmunityTimer) {
                ++immunityTimer;
            }
        }
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
    }

    @Override
    public boolean shouldSyncWith(ServerPlayer player) {
        return player == provider;
    }

    @Override
    public void writeSyncPacket(FriendlyByteBuf buf, ServerPlayer recipient) {
        buf.writeInt(this.getLavaImmunityTimer());
    }

    @Override
    public void applySyncPacket(FriendlyByteBuf buf) {
        this.setLavaImmunityTimer(buf.readInt());
    }

    private boolean getEquippedTrinkets(Player player) {
        boolean equipped = false;

        if (TrinketsHelper.isEquipped(ModItems.TERRASPARK_BOOTS, player) || TrinketsHelper.isEquipped(ModItems.LAVA_WADERS, player) ||
                TrinketsHelper.isEquipped(ModItems.MOLTEN_CHARM, player) || TrinketsHelper.isEquipped(ModItems.LAVA_CHARM, player)) {
            equipped = true;
        }

        return equipped;
    }
}
