package terramine.common.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

import static terramine.TerraMine.id;

public class ModItemGroups {
    public static final CreativeModeTab ITEM_GROUP_EQUIPMENT = registerCreativeTab("terramine_equipment", FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.DEMONITE_TOOLS.SWORD))
            .title(Component.translatable("itemGroup.terramine.terramine_equipment"))
            .build());
    public static final CreativeModeTab ITEM_GROUP_ARMOR = registerCreativeTab("terramine_armor", FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.SHADOW_ARMOR.HELMET))
            .title(Component.translatable("itemGroup.terramine.terramine_armor"))
            .build());
    public static final CreativeModeTab ITEM_GROUP_ACCESSORIES = registerCreativeTab("terramine_accessories", FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.TERRASPARK_BOOTS))
            .title(Component.translatable("itemGroup.terramine.terramine_accessories"))
            .build());
    public static final CreativeModeTab ITEM_GROUP_BLOCKS = registerCreativeTab("terramine_blocks", FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModBlocks.RAW_DEMONITE_BLOCK.ITEM))
            .title(Component.translatable("itemGroup.terramine.terramine_blocks"))
            .build());
    public static final CreativeModeTab ITEM_GROUP_THROWABLES = registerCreativeTab("terramine_throwables", FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.DYNAMITE))
            .title(Component.translatable("itemGroup.terramine.terramine_throwables"))
            .build());
    public static final CreativeModeTab ITEM_GROUP_STUFF = registerCreativeTab("terramine_stuff", FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.RAW_DEMONITE))
            .title(Component.translatable("itemGroup.terramine.terramine_stuff"))
            .build());
    public static final CreativeModeTab ITEM_GROUP_DYES = registerCreativeTab("terramine_dyes", FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.BLUE_DYE))
            .title(Component.translatable("itemGroup.terramine.terramine_dyes"))
            .build());

    public static void registerItemGroups() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SPAWN_EGGS).register(entries -> {
            for (Item item : ModItems.SPAWN_EGGS) {
                entries.accept(item);
            }
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            entries.accept(ModItems.SHIMMER_BUCKET);
        });

        ItemGroupEvents.modifyEntriesEvent(createKey("terramine_equipment")).register(id("first_phase"), entries -> {
            entries.accept(ModItems.UMBRELLA);
            entries.accept(ModItems.MAGIC_MIRROR);
            entries.accept(ModItems.CLENTAMINATOR);
            for (Item item : ModItems.TOOLS) {
                entries.accept(item);
            }
            for (Item item : ModItems.WEAPONS) {
                entries.accept(item);
            }
            for (Item item : ModItems.BIG_WEAPONS) {
                entries.accept(item);
            }
            for (Item item : ModItems.MAGIC_WEAPONS) {
                entries.accept(item);
            }
        });

        ItemGroupEvents.modifyEntriesEvent(createKey("terramine_armor")).register(id("second_phase"), entries -> {
            for (Item item : ModItems.ARMORS) {
                entries.accept(item);
            }
        });

        ItemGroupEvents.modifyEntriesEvent(createKey("terramine_accessories")).register(id("third_phase"), entries -> {
            for (Item item : ModItems.SHIELDS) {
                entries.accept(item);
            }
            for (Item item : ModItems.ACCESSORIES) {
                entries.accept(item);
            }
        });

        ItemGroupEvents.modifyEntriesEvent(createKey("terramine_blocks")).register(id("fourth_phase"), entries -> {
            for (Item item : ModBlocks.BLOCK_ITEMS) {
                entries.accept(item);
            }
        });

        ItemGroupEvents.modifyEntriesEvent(createKey("terramine_throwables")).register(id("fifth_phase"), entries -> {
            for (Item item : ModItems.THROWABLES) {
                entries.accept(item);
            }
            for (Item item : ModItems.ARROWS) {
                entries.accept(item);
            }
        });

        ItemGroupEvents.modifyEntriesEvent(createKey("terramine_stuff")).register(id("sixth_phase"), entries -> {
            for (Item item : ModItems.MISC) {
                if (!item.equals(ModItems.FAKE_FALLEN_STAR)) {
                    entries.accept(item);
                }
            }
            for (Item item : ModBlocks.BLOCK_PLANTS) {
                entries.accept(item);
            }
        });

        ItemGroupEvents.modifyEntriesEvent(createKey("terramine_dyes")).register(id("seventh_phase"), entries -> {
            for (Item item : ModItems.DYES) {
                entries.accept(item);
            }
        });

        List<String> phases = List.of("first_phase", "second_phase", "third_phase", "fourth_phase", "fifth_phase", "sixth_phase", "seventh_phase");

        for (int i = 0; i < phases.size() - 1; i++) {
            ItemGroupEvents.MODIFY_ENTRIES_ALL.addPhaseOrdering(id(phases.get(i)), id(phases.get(i + 1)));
        }
    }

    private static CreativeModeTab registerCreativeTab(String string, CreativeModeTab tab) {
        return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, id(string), tab);
    }

    private static ResourceKey<CreativeModeTab> createKey(String string) {
        return ResourceKey.create(Registries.CREATIVE_MODE_TAB, id(string));
    }
}
