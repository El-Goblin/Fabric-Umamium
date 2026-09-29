package net.elgoblin.umamium.datagen;

import net.elgoblin.umamium.block.ModBlocks;
import net.elgoblin.umamium.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.advancements.predicates.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.concurrent.CompletableFuture;

public class ModBlockLootTableProvider extends FabricBlockLootSubProvider {
    public ModBlockLootTableProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(packOutput, registriesFuture);
    }

    @Override
    public void generate() {
        generateLootTablesForSet(ModBlocks.GOLD_SET, false);
        generateLootTablesForSet(ModBlocks.GOLD_BRICKS_SET, true);
        generateLootTablesForSet(ModBlocks.CALCIFIED_GOLD_SET, true);
        generateLootTablesForSet(ModBlocks.POLISHED_GOLD_SET, true);
        generateLootTablesForDropExperienceSet(ModBlocks.GOLD_ORE_SET, Items.RAW_GOLD, 1, 1);
        generateLootTablesForDropExperienceSet(ModBlocks.DEEPSLATE_GOLD_ORE_SET, Items.RAW_GOLD, 1, 1);
        generateLootTablesForDropExperienceSet(ModBlocks.NETHER_GOLD_ORE_SET, Items.GOLD_NUGGET, 2, 6);

        generateLootTablesForSet(ModBlocks.DIAMOND_SET, false);
        generateLootTablesForSet(ModBlocks.DIAMOND_BRICKS_SET, true);
        generateLootTablesForSet(ModBlocks.CALCIFIED_DIAMOND_SET, true);
        generateLootTablesForSet(ModBlocks.POLISHED_DIAMOND_SET, true);
        generateLootTablesForDropExperienceSet(ModBlocks.DIAMOND_ORE_SET, Items.DIAMOND, 1, 1);
        generateLootTablesForDropExperienceSet(ModBlocks.DEEPSLATE_DIAMOND_ORE_SET, Items.DIAMOND, 1, 1);

        generateLootTablesForSet(ModBlocks.IRON_SET, false);
        generateLootTablesForSet(ModBlocks.IRON_BRICKS_SET, true);
        generateLootTablesForSet(ModBlocks.CALCIFIED_IRON_SET, true);
        generateLootTablesForSet(ModBlocks.POLISHED_IRON_SET, true);
        generateLootTablesForDropExperienceSet(ModBlocks.IRON_ORE_SET, Items.RAW_IRON, 1, 1);
        generateLootTablesForDropExperienceSet(ModBlocks.DEEPSLATE_IRON_ORE_SET, Items.RAW_IRON, 1, 1);

        generateLootTablesForSet(ModBlocks.EMERALD_SET, false);
        generateLootTablesForSet(ModBlocks.EMERALD_BRICKS_SET, true);
        generateLootTablesForSet(ModBlocks.CALCIFIED_EMERALD_SET, true);
        generateLootTablesForSet(ModBlocks.POLISHED_EMERALD_SET, true);
        generateLootTablesForSet(ModBlocks.CHISELED_EMERALD_SET, false);
        generateLootTablesForDropExperienceSet(ModBlocks.EMERALD_ORE_SET, Items.EMERALD, 1, 1);
        generateLootTablesForDropExperienceSet(ModBlocks.DEEPSLATE_EMERALD_ORE_SET, Items.EMERALD, 1, 1);

        generateLootTablesForSet(ModBlocks.AMETHYST_SET, false);
        generateLootTablesForSet(ModBlocks.AMETHYST_BRICKS_SET, true);
        generateLootTablesForSet(ModBlocks.CALCIFIED_AMETHYST_SET, true);
        generateLootTablesForSet(ModBlocks.POLISHED_AMETHYST_SET, true);

        generateLootTablesForSet(ModBlocks.LAPIS_SET, false);
        generateLootTablesForSet(ModBlocks.LAPIS_BRICKS_SET, true);
        generateLootTablesForSet(ModBlocks.CALCIFIED_LAPIS_SET, true);
        generateLootTablesForSet(ModBlocks.POLISHED_LAPIS_SET, true);
        generateLootTablesForDropExperienceSet(ModBlocks.LAPIS_ORE_SET, Items.LAPIS_LAZULI, 4, 9);
        generateLootTablesForDropExperienceSet(ModBlocks.DEEPSLATE_LAPIS_ORE_SET, Items.LAPIS_LAZULI, 4, 9);

        generateLootTablesForSet(ModBlocks.COAL_SET, false);
        generateLootTablesForSet(ModBlocks.COAL_BRICKS_SET, true);
        generateLootTablesForSet(ModBlocks.CALCIFIED_COAL_SET, true);
        generateLootTablesForSet(ModBlocks.POLISHED_COAL_SET, true);
        generateLootTablesForDropExperienceSet(ModBlocks.COAL_ORE_SET, Items.COAL, 1, 1);
        generateLootTablesForDropExperienceSet(ModBlocks.DEEPSLATE_COAL_ORE_SET, Items.COAL, 1, 1);

        generateLootTablesForSet(ModBlocks.NETHERITE_SET, false);
        generateLootTablesForSet(ModBlocks.NETHERITE_BRICKS_SET, true);
        generateLootTablesForSet(ModBlocks.CALCIFIED_NETHERITE_SET, true);
        generateLootTablesForSet(ModBlocks.POLISHED_NETHERITE_SET, true);

        generateLootTablesForSet(ModBlocks.REDSTONE_SET, false);
        generateLootTablesForSet(ModBlocks.REDSTONE_BRICKS_SET, true);
        generateLootTablesForSet(ModBlocks.CALCIFIED_REDSTONE_SET, true);
        generateLootTablesForSet(ModBlocks.POLISHED_REDSTONE_SET, true);
        generateLootTablesForDropExperienceSet(ModBlocks.REDSTONE_ORE_SET, Items.REDSTONE, 4, 5);
        generateLootTablesForDropExperienceSet(ModBlocks.DEEPSLATE_REDSTONE_ORE_SET, Items.REDSTONE, 4, 5);

        generateLootTablesForDropExperienceSet(ModBlocks.COPPER_ORE_SET, Items.RAW_COPPER, 2, 5);
        generateLootTablesForDropExperienceSet(ModBlocks.DEEPSLATE_COPPER_ORE_SET, Items.RAW_COPPER, 2, 5);

        generateLootTablesForSet(ModBlocks.OBSIDIAN_SET, false);
        generateLootTablesForSet(ModBlocks.FLINT_SET, true);
        generateLootTablesForSet(ModBlocks.ICE_SET, false);
        generateLootTablesForSet(ModBlocks.PACKED_ICE_SET, false);
        generateLootTablesForSet(ModBlocks.BLUE_ICE_SET, false);
        generateLootTablesForSet(ModBlocks.CALCITE_SET, false);
        generateLootTablesForSet(ModBlocks.SCULK_SET, false);

        dropSelf(ModBlocks.DARK_PRISMARINE_WALL);
        dropSelf(ModBlocks.PRISMARINE_BRICK_WALL);
        dropSelf(ModBlocks.POLISHED_ANDESITE_WALL);
        dropSelf(ModBlocks.POLISHED_GRANITE_WALL);
        dropSelf(ModBlocks.POLISHED_DIORITE_WALL);
        dropSelf(ModBlocks.STONE_WALL);
        dropSelf(ModBlocks.PURPUR_WALL);
        dropSelf(ModBlocks.SMOOTH_QUARTZ_WALL);
        dropSelf(ModBlocks.NETHER_BRICK_FENCE_GATE);
        dropSelf(ModBlocks.PROTECTOR_BLOCK);
        add(Blocks.SPAWNER, multipleDrops(ModItems.CHAOS_ORB, 10, 10));
    }

    private void generateLootTablesForSet(ModBlocks.BlockSet blockSet, boolean includeBase) {
        if (includeBase) {dropSelf(blockSet.base());}
        dropSelf(blockSet.stairs());
        add(blockSet.slab(), this::createSlabItemTable);
        dropSelf(blockSet.fence());
        dropSelf(blockSet.fenceGate());
        dropSelf(blockSet.wall());
        if (blockSet.door() != null) {add(blockSet.door(), this::createDoorTable);}
        if (blockSet.trapdoor() != null) {dropSelf(blockSet.trapdoor());}
    }

    private void generateLootTablesForDropExperienceSet(ModBlocks.BlockSet blockSet, Item item, int minDrops, int maxDrops) {
        add(blockSet.stairs(), multipleOreDrops(blockSet.stairs(), item, minDrops, maxDrops, 0.75f));
        add(blockSet.slab(), createOreSlabItemTable(blockSet.slab(), item, minDrops, maxDrops, 0.5f));
        add(blockSet.fence(), multipleOreDrops(blockSet.fence(), item, minDrops, maxDrops, 0.25f));
        add(blockSet.fenceGate(), multipleOreDrops(blockSet.fenceGate(), item, minDrops, maxDrops, 0.25f));
        add(blockSet.wall(), multipleOreDrops(blockSet.wall(), item, minDrops, maxDrops, 0.5f));
    }

    public LootTable.Builder multipleDrops(Item item, int minDrops, int maxDrops) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(
                                        UniformGenerator.between(minDrops, maxDrops)
                                )))
                );
    }

    public LootTable.Builder multipleOreDrops(final Block block, Item item, float minDrops, float maxDrops, float probability) {
        HolderLookup.RegistryLookup<Enchantment> enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

        return this.createSilkTouchDispatchTable(block, this.applyExplosionDecay(
                block, LootItem.lootTableItem(item)
                        .when(LootItemRandomChanceCondition.randomChance(probability))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))));
    }

    public LootTable.Builder createOreSlabItemTable(final Block slab, Item item, float minDrops, float maxDrops, float singleSlabProbability) {
        HolderLookup.RegistryLookup<Enchantment> enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        Holder<Enchantment> fortune = enchantments.getOrThrow(Enchantments.FORTUNE);

        LootItemBlockStatePropertyCondition.Builder isDoubleSlab = LootItemBlockStatePropertyCondition.hasBlockStateProperties(slab)
                .setProperties(StatePropertiesPredicate.Builder.properties()
                        .hasProperty(SlabBlock.TYPE, SlabType.DOUBLE));

        LootPoolEntryContainer.Builder<?> silkTouchDrop = this.applyExplosionDecay(
                slab,
                LootItem.lootTableItem(slab)
                        .when(this.hasSilkTouch())
                        .apply(
                                SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
                                        .when(isDoubleSlab)
                        )
        );

        LootPoolEntryContainer.Builder<?> singleSlabDrop = this.applyExplosionDecay(
                slab,
                LootItem.lootTableItem(item)
                        .when(this.hasSilkTouch().invert())
                        .when(isDoubleSlab.invert())
                        .when(LootItemRandomChanceCondition.randomChance(singleSlabProbability))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(fortune))
        );

        LootPoolEntryContainer.Builder<?> doubleSlabNothing = EmptyLootItem.emptyItem()
                .setWeight(25)
                .when(this.hasSilkTouch().invert())
                .when(isDoubleSlab);

        LootPoolEntryContainer.Builder<?> doubleSlabOneDrop = this.applyExplosionDecay(
                slab,
                LootItem.lootTableItem(item)
                        .setWeight(50)
                        .when(this.hasSilkTouch().invert())
                        .when(isDoubleSlab)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(fortune))
        );

        LootPoolEntryContainer.Builder<?> doubleSlabTwoDrops = this.applyExplosionDecay(
                slab,
                LootItem.lootTableItem(item)
                        .setWeight(25)
                        .when(this.hasSilkTouch().invert())
                        .when(isDoubleSlab)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(fortune))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops), true))
                        .apply(ApplyBonusCount.addOreBonusCount(fortune))
        );

        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(silkTouchDrop)
                                .add(singleSlabDrop)
                                .add(doubleSlabNothing)
                                .add(doubleSlabOneDrop)
                                .add(doubleSlabTwoDrops)
                );
    }
}
