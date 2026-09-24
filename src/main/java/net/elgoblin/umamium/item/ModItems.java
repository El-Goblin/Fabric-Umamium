package net.elgoblin.umamium.item;

import net.elgoblin.umamium.Umamium;
import net.elgoblin.umamium.component.ModDataComponentTypes;
import net.elgoblin.umamium.item.custom.*;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Unit;
import net.minecraft.world.item.*;

import java.util.function.Function;

public class ModItems {

    public static final Item MOSS = registerItem("moss", MossItem::new);

    public static final Item MAGIC_MIRROR = registerItem("magic_mirror", MagicMirrorItem::new);
    public static final Item MEMORY_MIRROR = registerItem("memory_mirror", properties -> new MemoryMirrorItem(properties.rarity(Rarity.UNCOMMON)));
    public static final Item CHAOS_MIRROR = registerItem("chaos_mirror", properties -> new ChaosMirrorItem(properties.rarity(Rarity.UNCOMMON)));

    public static final Item LA_LECHONA = registerItem("la_lechona", properties ->
            new Item(properties.stacksTo(1).craftRemainder(Items.BUCKET)
                    .component(DataComponents.CONSUMABLE, ModDataComponentTypes.LA_LECHONA)
                    .usingConvertsTo(Items.BUCKET)));

    public static final Item CHAOS_ORB = registerItem("chaos_orb", ChaosOrbItem::new);

    public static final Item FLASH = registerItem("flash", properties ->
            new FlashItem(properties.stacksTo(1).rarity(Rarity.EPIC)));

    public static final Item NETHERITE_NUGGET = registerItem("netherite_nugget", Item::new);


    public static final Item DIMENSIONAL_POCKET = registerItem("dimensional_pocket", properties ->
            new DimensionalPocketItem(properties.stacksTo(1).rarity(Rarity.EPIC)));

    public static final Item LEGENDARY_ROCKET = registerItem("legendary_rocket", properties ->
            new LegendaryRocketItem(properties.stacksTo(1).fireResistant().component(DataComponents.UNBREAKABLE, Unit.INSTANCE).rarity(Rarity.EPIC)));

    public static final Item LEGENDARY_SWORD = registerItem("legendary_sword", properties ->
            new LegendaryNonRightClickToolItem(properties.sword(ModToolMaterials.LEGENDARY, 9.0F, -2.0F).fireResistant().rarity(Rarity.EPIC).component(DataComponents.UNBREAKABLE, Unit.INSTANCE)));
    public static final Item LEGENDARY_SHOVEL = registerItem("legendary_shovel", properties ->
            new LegendaryShovelItem(ModToolMaterials.LEGENDARY, 1.0F, -2.8F, properties.fireResistant().rarity(Rarity.EPIC).component(DataComponents.UNBREAKABLE, Unit.INSTANCE)));
    public static final Item LEGENDARY_AXE = registerItem("legendary_axe", properties ->
            new LegendaryAxeItem(ModToolMaterials.LEGENDARY, 5.0F, -2.6F, properties.fireResistant().rarity(Rarity.EPIC).component(DataComponents.UNBREAKABLE, Unit.INSTANCE)));
    public static final Item LEGENDARY_PICKAXE = registerItem("legendary_pickaxe", properties ->
            new LegendaryNonRightClickToolItem(properties.pickaxe(ModToolMaterials.LEGENDARY, 1.0F, -2.8F).fireResistant().rarity(Rarity.EPIC).component(DataComponents.UNBREAKABLE, Unit.INSTANCE)));
    public static final Item LEGENDARY_HOE = registerItem("legendary_hoe", properties ->
            new LegendaryHoeItem(ModToolMaterials.LEGENDARY, 1.0F, -2.8F, properties.fireResistant().rarity(Rarity.EPIC).component(DataComponents.UNBREAKABLE, Unit.INSTANCE)));
    public static final Item LEGENDARY_SPEAR = registerItem("legendary_spear", properties ->
            new LegendaryNonRightClickToolItem(properties.spear(ModToolMaterials.LEGENDARY,
                    1.25F,
                    1.5F,
                    0.25F,
                    2F,
                    6F,
                    5F,
                    5.1F,
                    7F,
                    4.6F).fireResistant().rarity(Rarity.EPIC).component(DataComponents.UNBREAKABLE, Unit.INSTANCE)));

    public static final Item LEGENDARY_LONGSWORD = registerItem("legendary_longsword", properties ->
            new LegendaryLongswordItem(properties.fireResistant().rarity(Rarity.EPIC).component(DataComponents.UNBREAKABLE, Unit.INSTANCE),
                    ModToolMaterials.LEGENDARY, 11.0F, -2.4F, 1, 5));
    public static final Item WOODEN_LONGSWORD = registerItem("wooden_longsword", properties ->
            new LongswordItem(properties,
                    ToolMaterial.WOOD, 5, -2.4F, 1, 2));
    public static final Item STONE_LONGSWORD = registerItem("stone_longsword", properties ->
            new LongswordItem(properties,
                    ToolMaterial.STONE, 5, -2.4F, 1, 2));
    public static final Item COPPER_LONGSWORD = registerItem("copper_longsword", properties ->
            new LongswordItem(properties,
                    ToolMaterial.COPPER, 5, -2.4F, 1, 2));
    public static final Item IRON_LONGSWORD = registerItem("iron_longsword", properties ->
            new LongswordItem(properties,
                    ToolMaterial.IRON, 5, -2.4F, 1, 2));
    public static final Item GOLDEN_LONGSWORD = registerItem("golden_longsword", properties ->
            new LongswordItem(properties,
                    ToolMaterial.GOLD, 5, -2.4F, 1, 2));
    public static final Item DIAMOND_LONGSWORD = registerItem("diamond_longsword", properties ->
            new LongswordItem(properties,
                    ToolMaterial.DIAMOND, 5, -2.4F, 1, 2));
    public static final Item NETHERITE_LONGSWORD = registerItem("netherite_longsword", properties ->
            new LongswordItem(properties.fireResistant(),
                    ToolMaterial.NETHERITE, 5, -2.4F, 1, 2));
    public static final Item FIENDBLADE_LONGSWORD = registerItem("fiendblade_longsword", properties ->
            new FiendbladLongswordItem(properties.fireResistant().rarity(Rarity.RARE),
                    ToolMaterial.NETHERITE, 5, -2.4F, 1, 2));
    public static final Item FIRE_DRAGONSWORD_LONGSWORD = registerItem("fire_dragonsword_longsword", properties ->
            new LongswordItem(properties.fireResistant(),
                    ToolMaterial.NETHERITE, 5, -2.4F, 1, 2));
    public static final Item FLAMEBERGE_LONGSWORD = registerItem("flameberge_longsword", properties ->
            new LongswordItem(properties,
                    ToolMaterial.IRON, 5, -2.4F, 1, 2));



    public static ResourceKey<Item> getResourceKey(Item item) {
        return BuiltInRegistries.ITEM.getResourceKey(item).get();
    }

    private static Item registerItem(String name, Function<Item.Properties, Item> function) {
        return Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(Umamium.MOD_ID, name),
        function.apply(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Umamium.MOD_ID, name)))));
    }

    public static void registerModItems() {
        Umamium.LOGGER.info("Registering Mod Items for " + Umamium.MOD_ID);

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(output -> {
            output.accept(MOSS);
        });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(output -> {
            output.accept(FLASH);
            output.accept(LA_LECHONA);
            output.accept(LEGENDARY_ROCKET);
            output.accept(LEGENDARY_PICKAXE);
            output.accept(LEGENDARY_SHOVEL);
            output.accept(LEGENDARY_AXE);
            output.accept(LEGENDARY_HOE);
            output.accept(MAGIC_MIRROR);
            output.accept(MEMORY_MIRROR);
            output.accept(CHAOS_MIRROR);
            output.accept(DIMENSIONAL_POCKET);
            output.accept(CHAOS_ORB);
        });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(output -> {
            output.accept(FLASH);
            output.accept(LEGENDARY_SWORD);
            output.accept(LEGENDARY_LONGSWORD);
            output.accept(LEGENDARY_SPEAR);
            output.accept(LEGENDARY_AXE);
            output.accept(WOODEN_LONGSWORD);
            output.accept(STONE_LONGSWORD);
            output.accept(COPPER_LONGSWORD);
            output.accept(IRON_LONGSWORD);
            output.accept(GOLDEN_LONGSWORD);
            output.accept(DIAMOND_LONGSWORD);
            output.accept(NETHERITE_LONGSWORD);
            output.accept(FLAMEBERGE_LONGSWORD);
            output.accept(FIRE_DRAGONSWORD_LONGSWORD);
            output.accept(FIENDBLADE_LONGSWORD);
            output.accept(LEGENDARY_ROCKET);
            output.accept(CHAOS_ORB);
        });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(output -> {
            output.accept(NETHERITE_NUGGET);
        });
    }
}
