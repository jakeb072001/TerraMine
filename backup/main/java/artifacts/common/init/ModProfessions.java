package terracraft.common.init;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Items;

public class ModProfessions {
    public static final PoiType GOBLIN_TINKERER_POI = register("goblin_tinkerer", 1, 1, ModBlocks.TINKERER_TABLE);
    public static final VillagerProfession GOBLIN_TINKERER = register("goblin_tinkerer", GOBLIN_TINKERER_POI, SoundEvents.DEMON_EYE_HURT);

    public static void fillTradeData() {
        //EmeraldForItems: i = getEmerald, j = maxUses, k = villagerXp
        //ItemsForEmeralds: i = costEmerald, j = numberOfItems, k = maxUses, l = villagerXp, f = priceMultiplier

        // GOBLIN_TINKERER TRADES
        VillagerTrades.ItemListing[] oceanographerLevel1 = new VillagerTrades.ItemListing[]{
                new VillagerTrades.ItemsForEmeralds(terracraft.common.init.Items.ROCKET_BOOTS,10, 1,1,10),
                new VillagerTrades.EmeraldForItems(Items.DISPENSER, 5,15, 2)
        };
        VillagerTrades.ItemListing[] oceanographerLevel2 = new VillagerTrades.ItemListing[]{
                new VillagerTrades.ItemsForEmeralds(terracraft.common.init.Items.STOPWATCH,5, 1,1,10),
                new VillagerTrades.EmeraldForItems(Items.STICKY_PISTON, 7,20, 4)
        };
        VillagerTrades.ItemListing[] oceanographerLevel3 = new VillagerTrades.ItemListing[]{
                new VillagerTrades.ItemsForEmeralds(terracraft.common.init.Items.WEATHER_RADIO,7, 1,1,15),
                new VillagerTrades.ItemsForEmeralds(terracraft.common.init.Items.SEXTANT,10, 1,1,15)
        };
        VillagerTrades.ItemListing[] oceanographerLevel4 = new VillagerTrades.ItemListing[]{
                new VillagerTrades.EmeraldForItems(Items.OBSERVER, 10,15, 15),
                new VillagerTrades.ItemsForEmeralds(terracraft.common.init.Items.EXTENDO_GRIP,20, 1,1,15)
        };
        VillagerTrades.ItemListing[] oceanographerLevel5 = new VillagerTrades.ItemListing[]{
                new VillagerTrades.ItemsForEmeralds(terracraft.common.init.Items.TOOLBELT,20, 1,1,30),
                new VillagerTrades.ItemsForEmeralds(terracraft.common.init.Items.TOOLBOX,20, 1,1,30)
        };
        VillagerTrades.TRADES.put(GOBLIN_TINKERER,toIntMap(ImmutableMap.of(1,oceanographerLevel1,2,oceanographerLevel2,3,oceanographerLevel3,4,oceanographerLevel4,5,oceanographerLevel5)));

    }

    private static PoiType register(String name, int tickCount, int searchDistance, Block block) {
        return PoiType.register(name, PoiType.getBlockStates(block), tickCount, searchDistance);
    }
    private static VillagerProfession register(String name, PoiType poi, SoundEvent sound) {
        return VillagerProfession.register(name, poi, sound);
    }

    private static Int2ObjectMap<VillagerTrades.ItemListing[]> toIntMap(ImmutableMap<Integer, VillagerTrades.ItemListing[]> trades) {
        return new Int2ObjectOpenHashMap<>(trades);
    }
}
