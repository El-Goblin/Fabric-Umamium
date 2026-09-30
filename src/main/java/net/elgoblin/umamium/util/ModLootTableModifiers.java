package net.elgoblin.umamium.util;

import net.elgoblin.umamium.Umamium;
import net.elgoblin.umamium.item.ModItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntries;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Map;

public class ModLootTableModifiers {
    public static void modifyLootTables() {

        LootTableEvents.MODIFY.register(
                Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "modify_loot_tables"),
                (key, tableBuilder, source, registries) -> {

                    LootPool.Builder startChest = LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .when(LootItemRandomChanceCondition.randomChance(1.0F))
                            .add(LootItem.lootTableItem(ModItems.CHAOS_ORB))
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(32.0F, 32.0F)));

                    LootPool.Builder village = LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .when(LootItemRandomChanceCondition.randomChance(0.5F))
                            .add(LootItem.lootTableItem(ModItems.CHAOS_ORB))
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)));

                    LootPool.Builder dungeon = LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .when(LootItemRandomChanceCondition.randomChance(0.8F))
                            .add(LootItem.lootTableItem(ModItems.CHAOS_ORB))
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)));

                    LootPool.Builder stronghold = LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .when(LootItemRandomChanceCondition.randomChance(1F))
                            .add(LootItem.lootTableItem(ModItems.CHAOS_ORB))
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(16.0F, 64.0F)));

                    LootPool.Builder desertTemple = LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .when(LootItemRandomChanceCondition.randomChance(0.5F))
                            .add(LootItem.lootTableItem(ModItems.CHAOS_ORB))
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 8.0F)));

                    LootPool.Builder jungleTemple = LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .when(LootItemRandomChanceCondition.randomChance(0.75F))
                            .add(LootItem.lootTableItem(ModItems.CHAOS_ORB))
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 8.0F)));

                    Map<Integer, Integer> iglooDistribution = Map.of(
                            0, 495,
                            1, 495,
                            64, 10);
                    LootPool.Builder igloo = LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .when(LootItemRandomChanceCondition.randomChance(1F));
                    addWeightedCounts(igloo, ModItems.CHAOS_ORB, iglooDistribution);

                    LootPool.Builder mansion = LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .when(LootItemRandomChanceCondition.randomChance(0.75F))
                            .add(LootItem.lootTableItem(ModItems.CHAOS_ORB))
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(16.0F, 32.0F)));

                    LootPool.Builder shipwreck = LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .when(LootItemRandomChanceCondition.randomChance(0.5F))
                            .add(LootItem.lootTableItem(ModItems.CHAOS_ORB))
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F)));

                    LootPool.Builder shipwreckTreasure = LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(2.0F))
                            .when(LootItemRandomChanceCondition.randomChance(0.5F))
                            .add(LootItem.lootTableItem(ModItems.CHAOS_ORB))
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F)));

                    LootPool.Builder buriedTreasure = LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .when(LootItemRandomChanceCondition.randomChance(1F))
                            .add(LootItem.lootTableItem(ModItems.CHAOS_ORB))
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(8.0F, 32.0F)));

                    LootPool.Builder netherBridge = LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .when(LootItemRandomChanceCondition.randomChance(0.33F))
                            .add(LootItem.lootTableItem(ModItems.CHAOS_ORB))
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(6.0F, 12.0F)));

                    LootPool.Builder bastion = LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(5.0F))
                            .when(LootItemRandomChanceCondition.randomChance(0.33F))
                            .add(LootItem.lootTableItem(ModItems.CHAOS_ORB))
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 16.0F)));

                    LootPool.Builder ancientCity = LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(2.0F))
                            .when(LootItemRandomChanceCondition.randomChance(0.25F))
                            .add(LootItem.lootTableItem(ModItems.CHAOS_ORB))
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 8.0F)));

                    LootPool.Builder endCity = LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(4.0F))
                            .when(LootItemRandomChanceCondition.randomChance(0.25F))
                            .add(LootItem.lootTableItem(ModItems.CHAOS_ORB))
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(8.0F, 16.0F)));

                    LootPool.Builder trialChambers = LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .when(LootItemRandomChanceCondition.randomChance(0.1F))
                            .add(LootItem.lootTableItem(ModItems.CHAOS_ORB))
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)));

                    LootPool.Builder trialChamberPot = LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .when(LootItemRandomChanceCondition.randomChance(0.1F))
                            .add(LootItem.lootTableItem(ModItems.CHAOS_ORB))
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F)));

                    LootPool.Builder sniffer = LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .when(LootItemRandomChanceCondition.randomChance(1F));
                    addWeightedCounts(sniffer, ModItems.CHAOS_ORB, iglooDistribution);

                    // Intentionally adding the possibility of getting both a junk and a chaos orb
                    LootPool.Builder fishingJunk = LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .when(LootItemRandomChanceCondition.randomChance(0.05F))
                            .add(LootItem.lootTableItem(ModItems.CHAOS_ORB))
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F)));

                    LootPool.Builder fishingFish = LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .when(LootItemRandomChanceCondition.randomChance(0.1F))
                            .add(LootItem.lootTableItem(ModItems.CHAOS_ORB))
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F)));

                    LootPool.Builder fishingTreasure = LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .when(LootItemRandomChanceCondition.randomChance(0.1F))
                            .add(LootItem.lootTableItem(ModItems.CHAOS_ORB))
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F)));

                    LootPool.Builder pandaSneeze = LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .when(LootItemRandomChanceCondition.randomChance(0.01F))
                            .add(LootItem.lootTableItem(ModItems.CHAOS_ORB))
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F)));


                    LootPool.Builder jungleLeaves = LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .when(LootItemRandomChanceCondition.randomChance(0.025641F))
                            .add(LootItem.lootTableItem(Items.JUNGLE_SAPLING));

                    // JUNGLE LEAVES

                    if (key.identifier().equals(Identifier.withDefaultNamespace("blocks/jungle_leaves"))) {
                        tableBuilder.withPool(jungleLeaves);
                    }

                    // START CHEST

                    if (BuiltInLootTables.SPAWN_BONUS_CHEST.equals(key)) {
                        tableBuilder.withPool(startChest);
                    }

                    // VILLAGES

                    if (BuiltInLootTables.VILLAGE_ARMORER.equals(key)) {
                        tableBuilder.pool(village.build());
                    }
                    if (BuiltInLootTables.VILLAGE_BUTCHER.equals(key)) {
                        tableBuilder.pool(village.build());
                    }
                    if (BuiltInLootTables.VILLAGE_CARTOGRAPHER.equals(key)) {
                        tableBuilder.pool(village.build());
                    }
                    if (BuiltInLootTables.VILLAGE_FISHER.equals(key)) {
                        tableBuilder.pool(village.build());
                    }
                    if (BuiltInLootTables.VILLAGE_FLETCHER.equals(key)) {
                        tableBuilder.pool(village.build());
                    }
                    if (BuiltInLootTables.VILLAGE_DESERT_HOUSE.equals(key)) {
                        tableBuilder.pool(village.build());
                    }
                    if (BuiltInLootTables.VILLAGE_MASON.equals(key)) {
                        tableBuilder.pool(village.build());
                    }
                    if (BuiltInLootTables.VILLAGE_PLAINS_HOUSE.equals(key)) {
                        tableBuilder.pool(village.build());
                    }
                    if (BuiltInLootTables.VILLAGE_SAVANNA_HOUSE.equals(key)) {
                        tableBuilder.pool(village.build());
                    }
                    if (BuiltInLootTables.VILLAGE_SHEPHERD.equals(key)) {
                        tableBuilder.pool(village.build());
                    }
                    if (BuiltInLootTables.VILLAGE_SNOWY_HOUSE.equals(key)) {
                        tableBuilder.pool(village.build());
                    }
                    if (BuiltInLootTables.VILLAGE_TAIGA_HOUSE.equals(key)) {
                        tableBuilder.pool(village.build());
                    }
                    if (BuiltInLootTables.VILLAGE_TANNERY.equals(key)) {
                        tableBuilder.pool(village.build());
                    }
                    if (BuiltInLootTables.VILLAGE_TEMPLE.equals(key)) {
                        tableBuilder.pool(village.build());
                    }
                    if (BuiltInLootTables.VILLAGE_TOOLSMITH.equals(key)) {
                        tableBuilder.pool(village.build());
                    }
                    if (BuiltInLootTables.VILLAGE_WEAPONSMITH.equals(key)) {
                        tableBuilder.pool(village.build());
                    }
                    if (BuiltInLootTables.VILLAGE_WEAPONSMITH.equals(key)) {
                        tableBuilder.pool(village.build());
                    }

                    // DUNGEON

                    if (BuiltInLootTables.SIMPLE_DUNGEON.equals(key)) {
                        tableBuilder.pool(dungeon.build());
                    }

                    if (BuiltInLootTables.ABANDONED_MINESHAFT.equals(key)) {
                        tableBuilder.pool(dungeon.build());
                    }

                    // STRONGHOLD

                    if (BuiltInLootTables.STRONGHOLD_LIBRARY.equals(key)) {
                        tableBuilder.pool(stronghold.build());
                    }
                    if (BuiltInLootTables.STRONGHOLD_CROSSING.equals(key)) {
                        tableBuilder.pool(stronghold.build());
                    }
                    if (BuiltInLootTables.STRONGHOLD_CORRIDOR.equals(key)) {
                        tableBuilder.pool(stronghold.build());
                    }

                    // DESERT TEMPLE

                    if (BuiltInLootTables.DESERT_PYRAMID.equals(key)) {
                        tableBuilder.pool(desertTemple.build());
                    }
                    if (BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY.equals(key)) {
                        tableBuilder.pool(igloo.build());
                    }
                    if (BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY.equals(key)) {
                        tableBuilder.pool(igloo.build());
                    }

                    // JUNGLE TEMPLE

                    if (BuiltInLootTables.JUNGLE_TEMPLE.equals(key)) {
                        tableBuilder.pool(jungleTemple.build());
                    }
                    if (BuiltInLootTables.JUNGLE_TEMPLE_DISPENSER.equals(key)) {
                        tableBuilder.pool(fishingTreasure.build());
                    }

                    // IGLOO

                    if (BuiltInLootTables.IGLOO_CHEST.equals(key)) {
                        tableBuilder.pool(igloo.build());
                    }

                    // MANSION

                    if (BuiltInLootTables.WOODLAND_MANSION.equals(key)) {
                        tableBuilder.pool(mansion.build());
                    }

                    // SHIPWRECK, OCEAN RUINS, RUINED PORTALS

                    if (BuiltInLootTables.SHIPWRECK_MAP.equals(key)) {
                        tableBuilder.pool(shipwreck.build());
                    }
                    if (BuiltInLootTables.SHIPWRECK_SUPPLY.equals(key)) {
                        tableBuilder.pool(shipwreck.build());
                    }
                    if (BuiltInLootTables.SHIPWRECK_TREASURE.equals(key)) {
                        tableBuilder.pool(shipwreckTreasure.build());
                    }
                    if (BuiltInLootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY.equals(key)) {
                        tableBuilder.pool(shipwreck.build());
                    }
                    if (BuiltInLootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY.equals(key)) {
                        tableBuilder.pool(shipwreck.build());
                    }
                    if (BuiltInLootTables.RUINED_PORTAL.equals(key)) {
                        tableBuilder.pool(shipwreck.build());
                    }

                    // BURIED TREASURE

                    if (BuiltInLootTables.BURIED_TREASURE.equals(key)) {
                        tableBuilder.pool(buriedTreasure.build());
                    }

                    // NETHER BRIDGE

                    if (BuiltInLootTables.NETHER_BRIDGE.equals(key)) {
                        tableBuilder.pool(netherBridge.build());
                    }

                    // BASTION

                    if (BuiltInLootTables.BASTION_BRIDGE.equals(key)) {
                        tableBuilder.pool(bastion.build());
                    }
                    if (BuiltInLootTables.BASTION_OTHER.equals(key)) {
                        tableBuilder.pool(bastion.build());
                    }
                    if (BuiltInLootTables.BASTION_TREASURE.equals(key)) {
                        tableBuilder.pool(bastion.build());
                    }
                    if (BuiltInLootTables.BASTION_HOGLIN_STABLE.equals(key)) {
                        tableBuilder.pool(bastion.build());
                    }

                    // ANCIENT CITY

                    if (BuiltInLootTables.ANCIENT_CITY.equals(key)) {
                        tableBuilder.pool(ancientCity.build());
                    }
                    if (BuiltInLootTables.ANCIENT_CITY_ICE_BOX.equals(key)) {
                        tableBuilder.pool(ancientCity.build());
                    }

                    // TRIAL CHAMBERS

                    if (BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_COMMON.equals(key)) {
                        tableBuilder.pool(igloo.build());
                    }
                    if (BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE.equals(key)) {
                        tableBuilder.pool(igloo.build());
                    }

                    if (BuiltInLootTables.TRIAL_CHAMBERS_REWARD.equals(key)) {
                        tableBuilder.pool(trialChambers.build());
                    }
                    if (BuiltInLootTables.TRIAL_CHAMBERS_REWARD_COMMON.equals(key)) {
                        tableBuilder.pool(trialChambers.build());
                    }
                    if (BuiltInLootTables.TRIAL_CHAMBERS_REWARD_RARE.equals(key)) {
                        tableBuilder.pool(trialChambers.build());
                    }
                    if (BuiltInLootTables.TRIAL_CHAMBERS_REWARD_UNIQUE.equals(key)) {
                        tableBuilder.pool(trialChambers.build());
                    }

                    if (BuiltInLootTables.TRIAL_CHAMBERS_REWARD_OMINOUS.equals(key)) {
                        tableBuilder.pool(trialChambers.build());
                    }
                    if (BuiltInLootTables.TRIAL_CHAMBERS_REWARD_OMINOUS_COMMON.equals(key)) {
                        tableBuilder.pool(trialChambers.build());
                    }
                    if (BuiltInLootTables.TRIAL_CHAMBERS_REWARD_OMINOUS_RARE.equals(key)) {
                        tableBuilder.pool(trialChambers.build());
                    }
                    if (BuiltInLootTables.TRIAL_CHAMBERS_REWARD_OMINOUS_UNIQUE.equals(key)) {
                        tableBuilder.pool(trialChambers.build());
                    }


                    if (BuiltInLootTables.TRIAL_CHAMBERS_CHAMBER_DISPENSER.equals(key)) {
                        tableBuilder.pool(trialChamberPot.build());
                    }
                    if (BuiltInLootTables.TRIAL_CHAMBERS_CORRIDOR.equals(key)) {
                        tableBuilder.pool(trialChamberPot.build());
                    }
                    if (BuiltInLootTables.TRIAL_CHAMBERS_CORRIDOR_DISPENSER.equals(key)) {
                        tableBuilder.pool(trialChamberPot.build());
                    }
                    if (BuiltInLootTables.TRIAL_CHAMBERS_CORRIDOR_POT.equals(key)) {
                        tableBuilder.pool(trialChamberPot.build());
                    }
                    if (BuiltInLootTables.TRIAL_CHAMBERS_ENTRANCE.equals(key)) {
                        tableBuilder.pool(trialChamberPot.build());
                    }
                    if (BuiltInLootTables.TRIAL_CHAMBERS_INTERSECTION.equals(key)) {
                        tableBuilder.pool(trialChamberPot.build());
                    }
                    if (BuiltInLootTables.TRIAL_CHAMBERS_INTERSECTION_BARREL.equals(key)) {
                        tableBuilder.pool(trialChamberPot.build());
                    }
                    if (BuiltInLootTables.TRIAL_CHAMBERS_SUPPLY.equals(key)) {
                        tableBuilder.pool(trialChamberPot.build());
                    }
                    if (BuiltInLootTables.TRIAL_CHAMBERS_WATER_DISPENSER.equals(key)) {
                        tableBuilder.pool(trialChamberPot.build());
                    }

                    // END CITY

                    if (BuiltInLootTables.END_CITY_TREASURE.equals(key)) {
                        tableBuilder.pool(endCity.build());
                    }

                    // FISHING

                    if (BuiltInLootTables.FISHING_FISH.equals(key)) {
                        tableBuilder.pool(fishingFish.build());
                    }
                    if (BuiltInLootTables.FISHING_JUNK.equals(key)) {
                        tableBuilder.pool(fishingJunk.build());
                    }
                    if (BuiltInLootTables.FISHING_TREASURE.equals(key)) {
                        tableBuilder.pool(fishingTreasure.build());
                    }

                    // GIFTS

                    if (BuiltInLootTables.BABY_VILLAGER_GIFT.equals(key)) {
                        tableBuilder.pool(sniffer.build());
                    }
                    if (BuiltInLootTables.CAT_MORNING_GIFT.equals(key)) {
                        tableBuilder.pool(igloo.build());
                    }
                    if (BuiltInLootTables.SNIFFER_DIGGING.equals(key)) {
                        tableBuilder.pool(sniffer.build());
                    }

                    // OTHERS

                    if (BuiltInLootTables.PILLAGER_OUTPOST.equals(key)) {
                        tableBuilder.pool(bastion.build());
                    }
                    if (BuiltInLootTables.PANDA_SNEEZE.equals(key)) {
                        tableBuilder.pool(pandaSneeze.build());
                    }
                }
        );
    }

    private static LootPool.Builder addWeightedCounts(LootPool.Builder builder, Item loot, Map<Integer, Integer> ocurrences) {
        ocurrences.forEach((count, weight) -> {
            if (weight > 0) {
                builder.add(LootItem.lootTableItem(loot)
                        .setWeight(weight)
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(count)))
                );
            }
        });
        return builder;
    }
}