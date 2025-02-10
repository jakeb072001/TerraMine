package terramine.common.components;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.entity.C2SSelfMessagingComponent;
import terramine.common.init.ModComponents;
import terramine.common.init.ModItems;
import terramine.common.misc.AccessoriesHelper;
import terramine.common.utility.equipmentchecks.ArmorSetCheck;

@SuppressWarnings("UnstableApiUsage")
public class LavaImmunityComponent implements C2SSelfMessagingComponent, AutoSyncedComponent {
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

        if (provider.isInLava() && !provider.hasEffect(MobEffects.FIRE_RESISTANCE) && !ArmorSetCheck.isSetEquipped(provider, "molten")) {
            if (immunityTimer > 0 && getEquippedAccessories(provider)) {
                --immunityTimer;
            }
        } else {
            if (immunityTimer < maxImmunityTimer) {
                ++immunityTimer;
            }
        }
    }

    @Override
    public void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
    }

    @Override
    public void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
    }

    @Override
    public boolean shouldSyncWith(ServerPlayer player) {
        return player == provider;
    }

    @Override
    public void writeSyncPacket(RegistryFriendlyByteBuf buf, ServerPlayer recipient) {
        buf.writeInt(this.getLavaImmunityTimer());
    }

    @Override
    public void applySyncPacket(RegistryFriendlyByteBuf buf) {
        this.setLavaImmunityTimer(buf.readInt());
    }

    private boolean getEquippedAccessories(Player player) {
        return AccessoriesHelper.isEquipped(ModItems.TERRASPARK_BOOTS, player) || AccessoriesHelper.isEquipped(ModItems.LAVA_WADERS, player) ||
                AccessoriesHelper.isEquipped(ModItems.MOLTEN_CHARM, player) || AccessoriesHelper.isEquipped(ModItems.LAVA_CHARM, player);
    }

    @Override
    public void handleC2SMessage(RegistryFriendlyByteBuf buf) {
    }
}
