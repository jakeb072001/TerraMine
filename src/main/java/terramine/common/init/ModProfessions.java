package terramine.common.init;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.villager.VillagerProfessionBuilder;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import terramine.TerraMine;

import java.util.List;

public class ModProfessions {
    public static final PoiType GOBLIN_TINKERER_POI = register("goblin_tinkerer", 1, 1, ModBlocks.TINKERER_TABLE);
    public static final VillagerProfession GOBLIN_TINKERER = register("goblin_tinkerer", GOBLIN_TINKERER_POI, ModSoundEvents.FART); // change sound to something else later, using for placeholder right now

    public static void fillTradeData() {
        //EmeraldForItems: i = getEmerald, j = maxUses, k = villagerXp
        //ItemsForEmeralds: i = costEmerald, j = numberOfItems, k = maxUses, l = villagerXp, f = priceMultiplier

        // GOBLIN_TINKERER TRADES
        TradeOfferHelper.registerVillagerOffers(GOBLIN_TINKERER, 1, factories -> factories.addAll(List.of(
                new VillagerTrades.ItemsForEmeralds(ModItems.ROCKET_BOOTS, 10, 1, 1, 10),
                new VillagerTrades.EmeraldForItems(Items.DISPENSER, 5, 15, 2)
        )));
        TradeOfferHelper.registerVillagerOffers(GOBLIN_TINKERER, 2, factories -> factories.addAll(List.of(
                new VillagerTrades.ItemsForEmeralds(ModItems.STOPWATCH, 5, 1, 1, 10),
                new VillagerTrades.EmeraldForItems(Items.STICKY_PISTON, 7, 20, 4)
        )));
        TradeOfferHelper.registerVillagerOffers(GOBLIN_TINKERER, 3, factories -> factories.addAll(List.of(
                new VillagerTrades.ItemsForEmeralds(ModItems.WEATHER_RADIO, 7, 1, 1, 15),
                new VillagerTrades.ItemsForEmeralds(ModItems.SEXTANT, 10, 1, 1, 15)
        )));
        TradeOfferHelper.registerVillagerOffers(GOBLIN_TINKERER, 4, factories -> factories.addAll(List.of(
                new VillagerTrades.EmeraldForItems(Items.OBSERVER, 10, 15, 15),
                new VillagerTrades.ItemsForEmeralds(ModItems.EXTENDO_GRIP, 20, 1, 1, 15)
        )));
        TradeOfferHelper.registerVillagerOffers(GOBLIN_TINKERER, 5, factories -> factories.addAll(List.of(
                new VillagerTrades.ItemsForEmeralds(ModItems.TOOLBELT, 20, 1, 1, 30),
                new VillagerTrades.ItemsForEmeralds(ModItems.TOOLBOX, 20, 1, 1, 30)
        )));
    }

    private static PoiType register(String name, int tickCount, int searchDistance, Block block) {
        return PointOfInterestHelper.register(TerraMine.id(name), tickCount, searchDistance, block);
    }

    private static VillagerProfession register(String name, PoiType poi, SoundEvent sound) {
        var key = Registry.POINT_OF_INTEREST_TYPE.getResourceKey(poi).orElseThrow();
        return Registry.register(Registry.VILLAGER_PROFESSION, TerraMine.id(name), VillagerProfessionBuilder.create().id(TerraMine.id(name)).workstation(holder -> holder.is(key)).jobSite(holder -> holder.is(key)).workSound(sound).build());
    }
}
