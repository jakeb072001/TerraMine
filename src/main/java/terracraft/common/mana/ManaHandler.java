package terracraft.common.mana;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import terracraft.common.init.ModComponents;
import terracraft.common.init.ModSoundEvents;
import terracraft.common.init.ModStatistics;

@SuppressWarnings("UnstableApiUsage")
public class ManaHandler implements PlayerComponent<Component>, AutoSyncedComponent {
    private int maxMana = 0;
    private int currentMana = 0;
    private double manaRegenDelay = 0;
    private int manaBonus = 0;
    private int manaDelayBonus = 1;
    private final Player provider;

    public ManaHandler(Player provider) {
        this.provider = provider;
    }

    public void update() {
        ModComponents.MANA_HANDLER.sync(provider);

        if (currentMana > maxMana) {
            currentMana = maxMana;
        }

        if (currentMana == maxMana && manaRegenDelay <= 0) {
            manaRegenDelay = (0.7 * ((1 - ((double)currentMana / maxMana)) * 240 + 45)) / manaDelayBonus;
            provider.level.playSound(null, provider.blockPosition(), ModSoundEvents.MANA_FULL, SoundSource.PLAYERS, 1f, 1f);
        } else if (currentMana != maxMana) {
            manaRegenDelay -= 10;
        }
        if (manaRegenDelay <= 0 && currentMana != maxMana) {
            addCurrentMana((int) ((((double)maxMana / 7) + 1 + ((double)maxMana / 2) + manaBonus) * (((double)currentMana / maxMana) * 0.8 + 0.2) * 1.15) / 20);
        }
    }

    public void updateMoving() { // not working right now, don't know if it's possible or not.
        /*
        if (manaRegenDelay <= 0 && currentMana != maxMana) {
            addCurrentMana((int) ((((double)maxMana / 7) + 1 + manaBonus) * (((double)currentMana / maxMana) * 0.8 + 0.2) * 1.15) / 20);
        }
        */
    }

    public void isInUse() {
        this.manaRegenDelay = ((0.7 * ((1 - ((double)currentMana / maxMana)) * 240 + 45)) / manaDelayBonus) * 1.5;
    }

    public void setManaBonus(int manaBonus, boolean delayBonus) {
        this.manaBonus = manaBonus;
        if (delayBonus) {
            manaDelayBonus = 3;
        } else {
            manaDelayBonus = 1;
        }
    }

    public int getCurrentMana() {
        return this.currentMana;
    }

    public int getMaxMana() {
        return this.maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public void setCurrentMana(int currentMana) {
        this.currentMana = currentMana;
    }

    public void addCurrentMana(int currentMana) {
        if (provider != null && !provider.isCreative()) {
            if (currentMana > 0) {
                this.currentMana = Math.min(currentMana + this.currentMana, maxMana);
                provider.awardStat(Stats.CUSTOM.get(ModStatistics.MANA_USED), Math.max(currentMana, 0));
            } else if (currentMana < 0) {
                this.currentMana = Math.max(currentMana + this.currentMana, 0);
            }
        }
    }

    public void addMaxMana(int maxMana) {
        this.maxMana = Math.min(maxMana + this.maxMana, 400);
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        if (tag.contains("maxMana")) {
            setMaxMana(tag.getInt("maxMana"));
        } else {
            setMaxMana(20);
        }
        if (tag.contains("currentMana")) {
            setCurrentMana(tag.getInt("currentMana"));
        } else {
            setCurrentMana(20);
        }
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.putInt("maxMana", this.getMaxMana());
        tag.putInt("currentMana", this.getCurrentMana());
    }

    @Override
    public boolean shouldSyncWith(ServerPlayer player) {
        return player == provider;
    }

    @Override
    public void writeSyncPacket(FriendlyByteBuf buf, ServerPlayer recipient) {
        buf.writeInt(this.getMaxMana());
        buf.writeInt(this.getCurrentMana());
    }

    @Override
    public void applySyncPacket(FriendlyByteBuf buf) {
        this.setMaxMana(buf.readInt());
        this.setCurrentMana(buf.readInt());
    }
}
