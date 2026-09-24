package net.elgoblin.umamium.datagen;

import net.elgoblin.umamium.Umamium;
import net.elgoblin.umamium.block.ModBlocks;
import net.elgoblin.umamium.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
        return new RecipeProvider(provider, recipeOutput) {
            @Override
            public void buildRecipes() {

                List<ItemLike> COBBLESTONE = List.of(Items.COBBLESTONE);
                List<ItemLike> STONE = List.of(Items.STONE);
                List<ItemLike> NETHERRACK = List.of(Items.NETHERRACK);

                shaped(RecipeCategory.MISC, ModBlocks.PROTECTOR_BLOCK, 1)
                        .pattern("NEN")
                        .pattern("NHN")
                        .pattern("DDD")
                        .define('N', Items.NETHERITE_INGOT)
                        .define('E', Items.NETHER_STAR)
                        .define('H', Items.HEAVY_CORE)
                        .define('D', Blocks.DIAMOND_BLOCK)
                        .unlockedBy(getHasName(Items.NETHERITE_INGOT), has(Items.NETHERITE_INGOT))
                        .unlockedBy(getHasName(Items.END_CRYSTAL), has(Items.END_CRYSTAL))
                        .unlockedBy(getHasName(Items.HEAVY_CORE), has(Items.HEAVY_CORE))
                        .unlockedBy(getHasName(Blocks.DIAMOND_BLOCK), has(Blocks.DIAMOND_BLOCK))
                        .save(output);

                this.oreBlasting(COBBLESTONE, RecipeCategory.BUILDING_BLOCKS, CookingBookCategory.BLOCKS, Items.STONE, 0.1f, 100, "cobblestone");
                this.oreBlasting(STONE, RecipeCategory.BUILDING_BLOCKS, CookingBookCategory.BLOCKS, Items.SMOOTH_STONE, 0.1f, 100, "stone");
                this.oreBlasting(NETHERRACK, RecipeCategory.BUILDING_BLOCKS, CookingBookCategory.BLOCKS, Items.NETHER_BRICK, 0.1f, 100, "netherrack");

                shapeless(RecipeCategory.MISC, ModItems.MAGIC_MIRROR, 1)
                        .requires(ModItems.MEMORY_MIRROR)
                        .unlockedBy(getHasName(ModItems.MEMORY_MIRROR), has(ModItems.MEMORY_MIRROR))
                        .save(output);

                shapeless(RecipeCategory.MISC, ModItems.MOSS, 4)
                        .requires(Blocks.MOSS_BLOCK)
                        .unlockedBy(getHasName(Blocks.MOSS_BLOCK), has(Blocks.MOSS_BLOCK))
                        .save(output);
                shaped(RecipeCategory.BUILDING_BLOCKS, Blocks.MOSS_BLOCK, 1)
                        .pattern("##")
                        .pattern("##")
                        .define('#', ModItems.MOSS)
                        .unlockedBy(getHasName(ModItems.MOSS), has(ModItems.MOSS))
                        .save(output);

                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DARK_PRISMARINE_WALL, 12)
                        .pattern("###")
                        .pattern("###")
                        .define('#', Blocks.DARK_PRISMARINE)
                        .unlockedBy(getHasName(Blocks.DARK_PRISMARINE), has(Blocks.DARK_PRISMARINE))
                        .save(output);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DARK_PRISMARINE_WALL, Blocks.DARK_PRISMARINE, 2);

                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PRISMARINE_BRICK_WALL, 12)
                        .pattern("###")
                        .pattern("###")
                        .define('#', Blocks.PRISMARINE_BRICKS)
                        .unlockedBy(getHasName(Blocks.PRISMARINE_BRICKS), has(Blocks.PRISMARINE_BRICKS))
                        .save(output);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PRISMARINE_BRICK_WALL, Blocks.PRISMARINE_BRICKS, 2);

                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PURPUR_WALL, 12)
                        .pattern("###")
                        .pattern("###")
                        .define('#', Blocks.PURPUR_BLOCK)
                        .unlockedBy(getHasName(Blocks.PURPUR_BLOCK), has(Blocks.PURPUR_BLOCK))
                        .save(output);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PURPUR_WALL, Blocks.PURPUR_BLOCK, 2);

                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SMOOTH_QUARTZ_WALL, 12)
                        .pattern("###")
                        .pattern("###")
                        .define('#', Blocks.SMOOTH_QUARTZ)
                        .unlockedBy(getHasName(Blocks.SMOOTH_QUARTZ), has(Blocks.SMOOTH_QUARTZ))
                        .save(output);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SMOOTH_QUARTZ_WALL, Blocks.SMOOTH_QUARTZ, 2);

                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_ANDESITE_WALL, 12)
                        .pattern("###")
                        .pattern("###")
                        .define('#', Blocks.POLISHED_ANDESITE)
                        .unlockedBy(getHasName(Blocks.POLISHED_ANDESITE), has(Blocks.POLISHED_ANDESITE))
                        .save(output);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_ANDESITE_WALL, Blocks.POLISHED_ANDESITE, 2);

                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_DIORITE_WALL, 12)
                        .pattern("###")
                        .pattern("###")
                        .define('#', Blocks.POLISHED_DIORITE)
                        .unlockedBy(getHasName(Blocks.POLISHED_DIORITE), has(Blocks.POLISHED_DIORITE))
                        .save(output);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_DIORITE_WALL, Blocks.POLISHED_DIORITE, 2);

                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_GRANITE_WALL, 12)
                        .pattern("###")
                        .pattern("###")
                        .define('#', Blocks.POLISHED_GRANITE)
                        .unlockedBy(getHasName(Blocks.POLISHED_GRANITE), has(Blocks.POLISHED_GRANITE))
                        .save(output);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_GRANITE_WALL, Blocks.POLISHED_GRANITE, 2);

                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.STONE_WALL, 12)
                        .pattern("###")
                        .pattern("###")
                        .define('#', Blocks.STONE)
                        .unlockedBy(getHasName(Blocks.STONE), has(Blocks.STONE))
                        .save(output);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.STONE_WALL, Blocks.STONE, 2);

                // LONGSWORDS

                shaped(RecipeCategory.COMBAT, ModItems.WOODEN_LONGSWORD, 1)
                        .pattern("  I")
                        .pattern(" I ")
                        .pattern("S  ")
                        .define('I', ItemTags.PLANKS)
                        .define('S', Items.WOODEN_SWORD)
                        .unlockedBy(getHasName(Items.WOODEN_SWORD), has(Items.WOODEN_SWORD))
                        .save(output);
                shaped(RecipeCategory.COMBAT, ModItems.STONE_LONGSWORD, 1)
                        .pattern("  I")
                        .pattern(" I ")
                        .pattern("S  ")
                        .define('I', ItemTags.STONE_CRAFTING_MATERIALS)
                        .define('S', Items.STONE_SWORD)
                        .unlockedBy(getHasName(Items.STONE), has(Items.STONE_SWORD))
                        .save(output);
                shaped(RecipeCategory.COMBAT, ModItems.COPPER_LONGSWORD, 1)
                        .pattern("  I")
                        .pattern(" I ")
                        .pattern("S  ")
                        .define('I', Items.COPPER_INGOT)
                        .define('S', Items.COPPER_SWORD)
                        .unlockedBy(getHasName(Items.COPPER_SWORD), has(Items.COPPER_SWORD))
                        .save(output);
                shaped(RecipeCategory.COMBAT, ModItems.IRON_LONGSWORD, 1)
                        .pattern("  I")
                        .pattern(" I ")
                        .pattern("S  ")
                        .define('I', Items.IRON_INGOT)
                        .define('S', Items.IRON_SWORD)
                        .unlockedBy(getHasName(Items.IRON_SWORD), has(Items.IRON_SWORD))
                        .save(output);
                shaped(RecipeCategory.COMBAT, ModItems.GOLDEN_LONGSWORD, 1)
                        .pattern("  I")
                        .pattern(" I ")
                        .pattern("S  ")
                        .define('I', Items.GOLD_INGOT)
                        .define('S', Items.GOLDEN_SWORD)
                        .unlockedBy(getHasName(Items.GOLDEN_SWORD), has(Items.GOLDEN_SWORD))
                        .save(output);
                shaped(RecipeCategory.COMBAT, ModItems.DIAMOND_LONGSWORD, 1)
                        .pattern("  I")
                        .pattern(" I ")
                        .pattern("S  ")
                        .define('I', Items.DIAMOND)
                        .define('S', Items.DIAMOND_SWORD)
                        .unlockedBy(getHasName(Items.DIAMOND_SWORD), has(Items.COPPER_SWORD))
                        .save(output);
                netheriteSmithing(ModItems.DIAMOND_LONGSWORD, RecipeCategory.COMBAT, ModItems.NETHERITE_LONGSWORD);


                // GOLD

                generateSetRecipes(ModBlocks.GOLD_SET, Items.GOLD_INGOT);

                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GOLD_BRICKS, 4)
                        .pattern("##")
                        .pattern("##")
                        .define('#', Blocks.GOLD_BLOCK)
                        .unlockedBy(getHasName(Blocks.GOLD_BLOCK), has(Blocks.GOLD_BLOCK))
                        .save(output);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GOLD_BRICKS, Items.GOLD_BLOCK, 1);
                generateSetRecipes(ModBlocks.GOLD_BRICKS_SET);

                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_GOLD_BLOCK, Items.GOLD_BLOCK, 1);
                generateSetRecipes(ModBlocks.POLISHED_GOLD_SET);

                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CALCIFIED_GOLD_BLOCK, 2)
                        .requires(Blocks.GOLD_BLOCK)
                        .requires(Blocks.CALCITE)
                        .unlockedBy(getHasName(Blocks.GOLD_BLOCK), has(Blocks.GOLD_BLOCK))
                        .unlockedBy(getHasName(Blocks.CALCITE), has(Blocks.CALCITE))
                        .save(output);
                generateSetRecipes(ModBlocks.CALCIFIED_GOLD_SET);
                generateSetRecipes(ModBlocks.GOLD_ORE_SET);
                generateSetRecipes(ModBlocks.DEEPSLATE_GOLD_ORE_SET);
                generateSetRecipes(ModBlocks.NETHER_GOLD_ORE_SET);

                // DIAMOND

                generateSetRecipes(ModBlocks.DIAMOND_SET, Items.DIAMOND);

                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DIAMOND_BRICKS, 4)
                        .pattern("##")
                        .pattern("##")
                        .define('#', Blocks.DIAMOND_BLOCK)
                        .unlockedBy(getHasName(Blocks.DIAMOND_BLOCK), has(Blocks.DIAMOND_BLOCK))
                        .save(output);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DIAMOND_BRICKS, Items.DIAMOND_BLOCK, 1);
                generateSetRecipes(ModBlocks.DIAMOND_BRICKS_SET);

                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_DIAMOND_BLOCK, Items.DIAMOND_BLOCK, 1);
                generateSetRecipes(ModBlocks.POLISHED_DIAMOND_SET);

                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CALCIFIED_DIAMOND_BLOCK, 2)
                        .requires(Blocks.DIAMOND_BLOCK)
                        .requires(Blocks.CALCITE)
                        .unlockedBy(getHasName(Blocks.DIAMOND_BLOCK), has(Blocks.DIAMOND_BLOCK))
                        .unlockedBy(getHasName(Blocks.CALCITE), has(Blocks.CALCITE))
                        .save(output);
                generateSetRecipes(ModBlocks.CALCIFIED_DIAMOND_SET);
                generateSetRecipes(ModBlocks.DIAMOND_ORE_SET);
                generateSetRecipes(ModBlocks.DEEPSLATE_DIAMOND_ORE_SET);

                // IRON

                generateSetRecipes(ModBlocks.IRON_SET);

                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.IRON_BRICKS, 4)
                        .pattern("##")
                        .pattern("##")
                        .define('#', Blocks.IRON_BLOCK)
                        .unlockedBy(getHasName(Blocks.IRON_BLOCK), has(Blocks.IRON_BLOCK))
                        .save(output);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.IRON_BRICKS, Items.IRON_BLOCK, 1);
                generateSetRecipes(ModBlocks.IRON_BRICKS_SET);

                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_IRON_BLOCK, Items.IRON_BLOCK, 1);
                generateSetRecipes(ModBlocks.POLISHED_IRON_SET);

                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CALCIFIED_IRON_BLOCK, 2)
                        .requires(Blocks.IRON_BLOCK)
                        .requires(Blocks.CALCITE)
                        .unlockedBy(getHasName(Blocks.IRON_BLOCK), has(Blocks.IRON_BLOCK))
                        .unlockedBy(getHasName(Blocks.CALCITE), has(Blocks.CALCITE))
                        .save(output);
                generateSetRecipes(ModBlocks.CALCIFIED_IRON_SET);
                generateSetRecipes(ModBlocks.IRON_ORE_SET);
                generateSetRecipes(ModBlocks.DEEPSLATE_IRON_ORE_SET);

                // EMERALD

                generateSetRecipes(ModBlocks.EMERALD_SET, Items.EMERALD);

                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.EMERALD_BRICKS, 4)
                        .pattern("##")
                        .pattern("##")
                        .define('#', Blocks.EMERALD_BLOCK)
                        .unlockedBy(getHasName(Blocks.EMERALD_BLOCK), has(Blocks.EMERALD_BLOCK))
                        .save(output);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.EMERALD_BRICKS, Items.EMERALD_BLOCK, 1);
                generateSetRecipes(ModBlocks.EMERALD_BRICKS_SET);

                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_EMERALD_BLOCK, Items.EMERALD_BLOCK, 1);
                generateSetRecipes(ModBlocks.POLISHED_EMERALD_SET);

                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_EMERALD_BLOCK, Items.EMERALD_BLOCK, 1);
                generateSetRecipes(ModBlocks.CHISELED_EMERALD_SET);

                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CALCIFIED_EMERALD_BLOCK, 2)
                        .requires(Blocks.EMERALD_BLOCK)
                        .requires(Blocks.CALCITE)
                        .unlockedBy(getHasName(Blocks.EMERALD_BLOCK), has(Blocks.EMERALD_BLOCK))
                        .unlockedBy(getHasName(Blocks.CALCITE), has(Blocks.CALCITE))
                        .save(output);
                generateSetRecipes(ModBlocks.CALCIFIED_EMERALD_SET);
                generateSetRecipes(ModBlocks.EMERALD_ORE_SET);
                generateSetRecipes(ModBlocks.DEEPSLATE_EMERALD_ORE_SET);

                // AMETHYST

                generateSetRecipes(ModBlocks.AMETHYST_SET);
                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.AMETHYST_SET.door(), 1)
                        .pattern("##")
                        .pattern("##")
                        .pattern("##")
                        .define('#', Items.AMETHYST_SHARD)
                        .unlockedBy(getHasName(Items.AMETHYST_SHARD), has(Items.AMETHYST_SHARD))
                        .save(output);
//                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.AMETHYST_SET.trapdoor(), 1)
//                        .pattern("##")
//                        .pattern("##")
//                        .define('#', Items.AMETHYST_SHARD)
//                        .unlockedBy(getHasName(Items.AMETHYST_SHARD), has(Items.AMETHYST_SHARD))
//                        .save(output);

                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.AMETHYST_BRICKS, 4)
                        .pattern("##")
                        .pattern("##")
                        .define('#', Blocks.AMETHYST_BLOCK)
                        .unlockedBy(getHasName(Blocks.AMETHYST_BLOCK), has(Blocks.AMETHYST_BLOCK))
                        .save(output);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.AMETHYST_BRICKS, Items.AMETHYST_BLOCK, 1);
                generateSetRecipes(ModBlocks.AMETHYST_BRICKS_SET);

                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_AMETHYST_BLOCK, Items.AMETHYST_BLOCK, 1);
                generateSetRecipes(ModBlocks.POLISHED_AMETHYST_SET);

                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CALCIFIED_AMETHYST_BLOCK, 2)
                        .requires(Blocks.AMETHYST_BLOCK)
                        .requires(Blocks.CALCITE)
                        .unlockedBy(getHasName(Blocks.AMETHYST_BLOCK), has(Blocks.AMETHYST_BLOCK))
                        .unlockedBy(getHasName(Blocks.CALCITE), has(Blocks.CALCITE))
                        .save(output);
                generateSetRecipes(ModBlocks.CALCIFIED_AMETHYST_SET);

                shaped(RecipeCategory.BUILDING_BLOCKS, Blocks.AMETHYST_BLOCK, 1)
                        .pattern("###")
                        .pattern("###")
                        .pattern("###")
                        .define('#', Items.AMETHYST_SHARD)
                        .unlockedBy(getHasName(Items.AMETHYST_SHARD), has(Items.AMETHYST_SHARD))
                        .save(output, "amethyst_block_from_amethyst_shards");
                shapeless(RecipeCategory.BUILDING_BLOCKS, Items.AMETHYST_SHARD, 9)
                        .requires(Blocks.AMETHYST_BLOCK)
                        .unlockedBy(getHasName(Blocks.AMETHYST_BLOCK), has(Blocks.AMETHYST_BLOCK))
                        .save(output);

                // LAPIS

                generateSetRecipes(ModBlocks.LAPIS_SET, Items.LAPIS_LAZULI);

                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.LAPIS_BRICKS, 4)
                        .pattern("##")
                        .pattern("##")
                        .define('#', Blocks.LAPIS_BLOCK)
                        .unlockedBy(getHasName(Blocks.LAPIS_BLOCK), has(Blocks.LAPIS_BLOCK))
                        .save(output);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.LAPIS_BRICKS, Items.LAPIS_BLOCK, 1);
                generateSetRecipes(ModBlocks.LAPIS_BRICKS_SET);

                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_LAPIS_BLOCK, Items.LAPIS_BLOCK, 1);
                generateSetRecipes(ModBlocks.POLISHED_LAPIS_SET);

                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CALCIFIED_LAPIS_BLOCK, 2)
                        .requires(Blocks.LAPIS_BLOCK)
                        .requires(Blocks.CALCITE)
                        .unlockedBy(getHasName(Blocks.LAPIS_BLOCK), has(Blocks.LAPIS_BLOCK))
                        .unlockedBy(getHasName(Blocks.CALCITE), has(Blocks.CALCITE))
                        .save(output);
                generateSetRecipes(ModBlocks.CALCIFIED_LAPIS_SET);
                generateSetRecipes(ModBlocks.LAPIS_ORE_SET);
                generateSetRecipes(ModBlocks.DEEPSLATE_LAPIS_ORE_SET);

                // COAL

                generateSetRecipes(ModBlocks.COAL_SET, Items.COAL);

                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COAL_BRICKS, 4)
                        .pattern("##")
                        .pattern("##")
                        .define('#', Blocks.COAL_BLOCK)
                        .unlockedBy(getHasName(Blocks.COAL_BLOCK), has(Blocks.COAL_BLOCK))
                        .save(output);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COAL_BRICKS, Items.COAL_BLOCK, 1);
                generateSetRecipes(ModBlocks.COAL_BRICKS_SET);

                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_COAL_BLOCK, Items.COAL_BLOCK, 1);
                generateSetRecipes(ModBlocks.POLISHED_COAL_SET);

                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CALCIFIED_COAL_BLOCK, 2)
                        .requires(Blocks.COAL_BLOCK)
                        .requires(Blocks.CALCITE)
                        .unlockedBy(getHasName(Blocks.COAL_BLOCK), has(Blocks.COAL_BLOCK))
                        .unlockedBy(getHasName(Blocks.CALCITE), has(Blocks.CALCITE))
                        .save(output);
                generateSetRecipes(ModBlocks.CALCIFIED_COAL_SET);
                generateSetRecipes(ModBlocks.COAL_ORE_SET);
                generateSetRecipes(ModBlocks.DEEPSLATE_COAL_ORE_SET);

                // NETHERITE

                nineBlockStorageRecipes(RecipeCategory.MISC, ModItems.NETHERITE_NUGGET, RecipeCategory.BUILDING_BLOCKS, Items.NETHERITE_INGOT);
                shapeless(RecipeCategory.MISC, ModItems.NETHERITE_NUGGET, 9)
                        .requires(Items.NETHERITE_INGOT)
                        .unlockedBy(getHasName(Items.NETHERITE_INGOT), has(Items.NETHERITE_INGOT))
                        .save(output, "amethyst_nugget_from_netherite_ingot");

                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.NETHERITE_SET.stairs(), 1)
                        .requires(ModBlocks.DIAMOND_SET.stairs())
                        .requires(ModItems.NETHERITE_NUGGET)
                        .unlockedBy(getHasName(ModBlocks.DIAMOND_SET.stairs()), has(ModBlocks.DIAMOND_SET.stairs()))
                        .unlockedBy(getHasName(ModItems.NETHERITE_NUGGET), has(ModItems.NETHERITE_NUGGET))
                        .save(output);
                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.NETHERITE_SET.slab(), 1)
                        .requires(ModBlocks.DIAMOND_SET.slab())
                        .requires(ModItems.NETHERITE_NUGGET)
                        .unlockedBy(getHasName(ModBlocks.DIAMOND_SET.slab()), has(ModBlocks.DIAMOND_SET.slab()))
                        .unlockedBy(getHasName(ModItems.NETHERITE_NUGGET), has(ModItems.NETHERITE_NUGGET))
                        .save(output);
                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.NETHERITE_SET.wall(), 1)
                        .requires(ModBlocks.DIAMOND_SET.wall())
                        .requires(ModItems.NETHERITE_NUGGET)
                        .unlockedBy(getHasName(ModBlocks.DIAMOND_SET.wall()), has(ModBlocks.DIAMOND_SET.wall()))
                        .unlockedBy(getHasName(ModItems.NETHERITE_NUGGET), has(ModItems.NETHERITE_NUGGET))
                        .save(output);
                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.NETHERITE_SET.fence(), 1)
                        .requires(ModBlocks.DIAMOND_SET.fence())
                        .requires(ModItems.NETHERITE_NUGGET)
                        .unlockedBy(getHasName(ModBlocks.DIAMOND_SET.fence()), has(ModBlocks.DIAMOND_SET.fence()))
                        .unlockedBy(getHasName(ModItems.NETHERITE_NUGGET), has(ModItems.NETHERITE_NUGGET))
                        .save(output);
                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.NETHERITE_SET.fenceGate(), 1)
                        .requires(ModBlocks.DIAMOND_SET.fenceGate())
                        .requires(ModItems.NETHERITE_NUGGET)
                        .unlockedBy(getHasName(ModBlocks.DIAMOND_SET.fenceGate()), has(ModBlocks.DIAMOND_SET.stairs()))
                        .unlockedBy(getHasName(ModItems.NETHERITE_NUGGET), has(ModItems.NETHERITE_NUGGET))
                        .save(output);
                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.NETHERITE_SET.door(), 1)
                        .requires(ModBlocks.DIAMOND_SET.door())
                        .requires(ModItems.NETHERITE_NUGGET)
                        .unlockedBy(getHasName(ModBlocks.DIAMOND_SET.door()), has(ModBlocks.DIAMOND_SET.door()))
                        .unlockedBy(getHasName(ModItems.NETHERITE_NUGGET), has(ModItems.NETHERITE_NUGGET))
                        .save(output);
                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.NETHERITE_SET.trapdoor(), 1)
                        .requires(ModBlocks.DIAMOND_SET.trapdoor())
                        .requires(ModItems.NETHERITE_NUGGET)
                        .unlockedBy(getHasName(ModBlocks.DIAMOND_SET.trapdoor()), has(ModBlocks.DIAMOND_SET.trapdoor()))
                        .unlockedBy(getHasName(ModItems.NETHERITE_NUGGET), has(ModItems.NETHERITE_NUGGET))
                        .save(output);

                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.NETHERITE_BRICKS, 1)
                        .requires(ModBlocks.DIAMOND_BRICKS)
                        .requires(ModItems.NETHERITE_NUGGET)
                        .unlockedBy(getHasName(ModBlocks.DIAMOND_BRICKS), has(ModBlocks.DIAMOND_BRICKS))
                        .unlockedBy(getHasName(ModItems.NETHERITE_NUGGET), has(ModItems.NETHERITE_NUGGET))
                        .save(output);
                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.NETHERITE_BRICKS_SET.stairs(), 1)
                        .requires(ModBlocks.DIAMOND_BRICKS_SET.stairs())
                        .requires(ModItems.NETHERITE_NUGGET)
                        .unlockedBy(getHasName(ModBlocks.DIAMOND_BRICKS_SET.stairs()), has(ModBlocks.DIAMOND_BRICKS_SET.stairs()))
                        .unlockedBy(getHasName(ModItems.NETHERITE_NUGGET), has(ModItems.NETHERITE_NUGGET))
                        .save(output);
                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.NETHERITE_BRICKS_SET.slab(), 1)
                        .requires(ModBlocks.DIAMOND_BRICKS_SET.slab())
                        .requires(ModItems.NETHERITE_NUGGET)
                        .unlockedBy(getHasName(ModBlocks.DIAMOND_BRICKS_SET.slab()), has(ModBlocks.DIAMOND_BRICKS_SET.slab()))
                        .unlockedBy(getHasName(ModItems.NETHERITE_NUGGET), has(ModItems.NETHERITE_NUGGET))
                        .save(output);
                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.NETHERITE_BRICKS_SET.wall(), 1)
                        .requires(ModBlocks.DIAMOND_BRICKS_SET.wall())
                        .requires(ModItems.NETHERITE_NUGGET)
                        .unlockedBy(getHasName(ModBlocks.DIAMOND_BRICKS_SET.wall()), has(ModBlocks.DIAMOND_BRICKS_SET.wall()))
                        .unlockedBy(getHasName(ModItems.NETHERITE_NUGGET), has(ModItems.NETHERITE_NUGGET))
                        .save(output);
                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.NETHERITE_BRICKS_SET.fence(), 1)
                        .requires(ModBlocks.DIAMOND_BRICKS_SET.fence())
                        .requires(ModItems.NETHERITE_NUGGET)
                        .unlockedBy(getHasName(ModBlocks.DIAMOND_BRICKS_SET.fence()), has(ModBlocks.DIAMOND_BRICKS_SET.fence()))
                        .unlockedBy(getHasName(ModItems.NETHERITE_NUGGET), has(ModItems.NETHERITE_NUGGET))
                        .save(output);
                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.NETHERITE_BRICKS_SET.fenceGate(), 1)
                        .requires(ModBlocks.DIAMOND_BRICKS_SET.fenceGate())
                        .requires(ModItems.NETHERITE_NUGGET)
                        .unlockedBy(getHasName(ModBlocks.DIAMOND_BRICKS_SET.fenceGate()), has(ModBlocks.DIAMOND_BRICKS_SET.fenceGate()))
                        .unlockedBy(getHasName(ModItems.NETHERITE_NUGGET), has(ModItems.NETHERITE_NUGGET))
                        .save(output);

                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_NETHERITE_BLOCK, 1)
                        .requires(ModBlocks.POLISHED_DIAMOND_BLOCK)
                        .requires(ModItems.NETHERITE_NUGGET)
                        .unlockedBy(getHasName(ModBlocks.POLISHED_DIAMOND_BLOCK), has(ModBlocks.POLISHED_DIAMOND_BLOCK))
                        .unlockedBy(getHasName(ModItems.NETHERITE_NUGGET), has(ModItems.NETHERITE_NUGGET))
                        .save(output);
                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_NETHERITE_SET.stairs(), 1)
                        .requires(ModBlocks.POLISHED_DIAMOND_SET.stairs())
                        .requires(ModItems.NETHERITE_NUGGET)
                        .unlockedBy(getHasName(ModBlocks.POLISHED_DIAMOND_SET.stairs()), has(ModBlocks.POLISHED_DIAMOND_SET.stairs()))
                        .unlockedBy(getHasName(ModItems.NETHERITE_NUGGET), has(ModItems.NETHERITE_NUGGET))
                        .save(output);
                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_NETHERITE_SET.slab(), 1)
                        .requires(ModBlocks.POLISHED_DIAMOND_SET.slab())
                        .requires(ModItems.NETHERITE_NUGGET)
                        .unlockedBy(getHasName(ModBlocks.POLISHED_DIAMOND_SET.slab()), has(ModBlocks.POLISHED_DIAMOND_SET.slab()))
                        .unlockedBy(getHasName(ModItems.NETHERITE_NUGGET), has(ModItems.NETHERITE_NUGGET))
                        .save(output);
                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_NETHERITE_SET.wall(), 1)
                        .requires(ModBlocks.POLISHED_DIAMOND_SET.wall())
                        .requires(ModItems.NETHERITE_NUGGET)
                        .unlockedBy(getHasName(ModBlocks.POLISHED_DIAMOND_SET.wall()), has(ModBlocks.POLISHED_DIAMOND_SET.wall()))
                        .unlockedBy(getHasName(ModItems.NETHERITE_NUGGET), has(ModItems.NETHERITE_NUGGET))
                        .save(output);
                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_NETHERITE_SET.fence(), 1)
                        .requires(ModBlocks.POLISHED_DIAMOND_SET.fence())
                        .requires(ModItems.NETHERITE_NUGGET)
                        .unlockedBy(getHasName(ModBlocks.POLISHED_DIAMOND_SET.fence()), has(ModBlocks.POLISHED_DIAMOND_SET.fence()))
                        .unlockedBy(getHasName(ModItems.NETHERITE_NUGGET), has(ModItems.NETHERITE_NUGGET))
                        .save(output);
                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_NETHERITE_SET.fenceGate(), 1)
                        .requires(ModBlocks.POLISHED_DIAMOND_SET.fenceGate())
                        .requires(ModItems.NETHERITE_NUGGET)
                        .unlockedBy(getHasName(ModBlocks.POLISHED_DIAMOND_SET.fenceGate()), has(ModBlocks.POLISHED_DIAMOND_SET.fenceGate()))
                        .unlockedBy(getHasName(ModItems.NETHERITE_NUGGET), has(ModItems.NETHERITE_NUGGET))
                        .save(output);

                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CALCIFIED_NETHERITE_BLOCK, 1)
                        .requires(ModBlocks.CALCIFIED_DIAMOND_BLOCK)
                        .requires(ModItems.NETHERITE_NUGGET)
                        .unlockedBy(getHasName(ModBlocks.CALCIFIED_DIAMOND_BLOCK), has(ModBlocks.CALCIFIED_DIAMOND_BLOCK))
                        .unlockedBy(getHasName(ModItems.NETHERITE_NUGGET), has(ModItems.NETHERITE_NUGGET))
                        .save(output);
                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CALCIFIED_NETHERITE_SET.stairs(), 1)
                        .requires(ModBlocks.CALCIFIED_DIAMOND_SET.stairs())
                        .requires(ModItems.NETHERITE_NUGGET)
                        .unlockedBy(getHasName(ModBlocks.CALCIFIED_DIAMOND_SET.stairs()), has(ModBlocks.CALCIFIED_DIAMOND_SET.stairs()))
                        .unlockedBy(getHasName(ModItems.NETHERITE_NUGGET), has(ModItems.NETHERITE_NUGGET))
                        .save(output);
                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CALCIFIED_NETHERITE_SET.slab(), 1)
                        .requires(ModBlocks.CALCIFIED_DIAMOND_SET.slab())
                        .requires(ModItems.NETHERITE_NUGGET)
                        .unlockedBy(getHasName(ModBlocks.CALCIFIED_DIAMOND_SET.slab()), has(ModBlocks.CALCIFIED_DIAMOND_SET.slab()))
                        .unlockedBy(getHasName(ModItems.NETHERITE_NUGGET), has(ModItems.NETHERITE_NUGGET))
                        .save(output);
                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CALCIFIED_NETHERITE_SET.wall(), 1)
                        .requires(ModBlocks.CALCIFIED_DIAMOND_SET.wall())
                        .requires(ModItems.NETHERITE_NUGGET)
                        .unlockedBy(getHasName(ModBlocks.CALCIFIED_DIAMOND_SET.wall()), has(ModBlocks.CALCIFIED_DIAMOND_SET.wall()))
                        .unlockedBy(getHasName(ModItems.NETHERITE_NUGGET), has(ModItems.NETHERITE_NUGGET))
                        .save(output);
                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CALCIFIED_NETHERITE_SET.fence(), 1)
                        .requires(ModBlocks.CALCIFIED_DIAMOND_SET.fence())
                        .requires(ModItems.NETHERITE_NUGGET)
                        .unlockedBy(getHasName(ModBlocks.CALCIFIED_DIAMOND_SET.fence()), has(ModBlocks.CALCIFIED_DIAMOND_SET.fence()))
                        .unlockedBy(getHasName(ModItems.NETHERITE_NUGGET), has(ModItems.NETHERITE_NUGGET))
                        .save(output);
                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CALCIFIED_NETHERITE_SET.fenceGate(), 1)
                        .requires(ModBlocks.CALCIFIED_DIAMOND_SET.fenceGate())
                        .requires(ModItems.NETHERITE_NUGGET)
                        .unlockedBy(getHasName(ModBlocks.CALCIFIED_DIAMOND_SET.fenceGate()), has(ModBlocks.CALCIFIED_DIAMOND_SET.fenceGate()))
                        .unlockedBy(getHasName(ModItems.NETHERITE_NUGGET), has(ModItems.NETHERITE_NUGGET))
                        .save(output);

                // REDSTONE

                generateSetRecipes(ModBlocks.REDSTONE_SET, Items.REDSTONE);

                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.REDSTONE_BRICKS, 4)
                        .pattern("##")
                        .pattern("##")
                        .define('#', Blocks.REDSTONE_BLOCK)
                        .unlockedBy(getHasName(Blocks.REDSTONE_BLOCK), has(Blocks.REDSTONE_BLOCK))
                        .save(output);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.REDSTONE_BRICKS, Items.REDSTONE_BLOCK, 1);
                generateSetRecipes(ModBlocks.REDSTONE_BRICKS_SET);

                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_REDSTONE_BLOCK, Items.REDSTONE_BLOCK, 1);
                generateSetRecipes(ModBlocks.POLISHED_REDSTONE_SET);

                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CALCIFIED_REDSTONE_BLOCK, 2)
                        .requires(Blocks.REDSTONE_BLOCK)
                        .requires(Blocks.CALCITE)
                        .unlockedBy(getHasName(Blocks.REDSTONE_BLOCK), has(Blocks.REDSTONE_BLOCK))
                        .unlockedBy(getHasName(Blocks.CALCITE), has(Blocks.CALCITE))
                        .save(output);
                generateSetRecipes(ModBlocks.CALCIFIED_REDSTONE_SET);
                generateSetRecipes(ModBlocks.REDSTONE_ORE_SET);
                generateSetRecipes(ModBlocks.DEEPSLATE_REDSTONE_ORE_SET);

                // COPPER

                generateSetRecipes(ModBlocks.COPPER_ORE_SET);
                generateSetRecipes(ModBlocks.DEEPSLATE_COPPER_ORE_SET);

                // OBSIDIAN

                generateSetRecipes(ModBlocks.OBSIDIAN_SET);

                // SCULK

                generateSetRecipes(ModBlocks.SCULK_SET);

                // BEDROCK

                generateSetRecipes(ModBlocks.BEDROCK_SET);

                // CALCITE

                generateSetRecipes(ModBlocks.CALCITE_SET);

                // FLINT

                nineBlockStorageRecipes(RecipeCategory.MISC, Items.FLINT, RecipeCategory.BUILDING_BLOCKS, ModBlocks.FLINT_BLOCK);
                shapeless(RecipeCategory.BUILDING_BLOCKS, Items.FLINT, 9)
                        .requires(ModBlocks.FLINT_BLOCK)
                        .unlockedBy(getHasName(Items.FLINT), has(ModBlocks.FLINT_BLOCK))
                        .save(output, "flint_from_flint_block");

                generateSetRecipes(ModBlocks.FLINT_SET, Items.FLINT);

                // ICE

                generateSetRecipes(ModBlocks.ICE_SET);

                // PACKED_ICE

                generateSetRecipes(ModBlocks.PACKED_ICE_SET);

                // BLUE_ICE

                generateSetRecipes(ModBlocks.BLUE_ICE_SET);
            }

            private void generateSetRecipes(ModBlocks.BlockSet blockSet, Item ingredient) {
                Ingredient baseIngredient = Ingredient.of(blockSet.base());

                shaped(RecipeCategory.BUILDING_BLOCKS, blockSet.stairs(), 8)
                        .pattern("#  ")
                        .pattern("## ")
                        .pattern("###")
                        .define('#', baseIngredient)
                        .unlockedBy(getHasName(blockSet.base()), has(blockSet.base()))
                        .save(output);
                shaped(RecipeCategory.BUILDING_BLOCKS, blockSet.slab(), 6)
                        .pattern("###")
                        .define('#', baseIngredient)
                        .unlockedBy(getHasName(blockSet.base()), has(blockSet.base()))
                        .save(output);
                shaped(RecipeCategory.BUILDING_BLOCKS, blockSet.wall(), 12)
                        .pattern("###")
                        .pattern("###")
                        .define('#', baseIngredient)
                        .unlockedBy(getHasName(blockSet.base()), has(blockSet.base()))
                        .save(output);
                shaped(RecipeCategory.BUILDING_BLOCKS, blockSet.fence(), 16)
                        .pattern("#S#")
                        .pattern("#S#")
                        .define('#', blockSet.base())
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(blockSet.base()), has(blockSet.base()))
                        .save(output);
                shaped(RecipeCategory.BUILDING_BLOCKS, blockSet.fenceGate(), 8)
                        .pattern("S#S")
                        .pattern("S#S")
                        .define('#', blockSet.base())
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(blockSet.base()), has(blockSet.base()))
                        .save(output);
                if (blockSet.door() != null) {
                    shaped(RecipeCategory.BUILDING_BLOCKS, blockSet.door(), 1)
                            .pattern("##")
                            .pattern("##")
                            .pattern("##")
                            .define('#', ingredient)
                            .unlockedBy(getHasName(ingredient), has(ingredient))
                            .save(output);
                }
                if (blockSet.trapdoor() != null) {
                    shaped(RecipeCategory.BUILDING_BLOCKS, blockSet.trapdoor(), 1)
                            .pattern("##")
                            .pattern("##")
                            .define('#', ingredient)
                            .unlockedBy(getHasName(ingredient), has(ingredient))
                            .save(output);
                }
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, blockSet.wall(), blockSet.base(), 2);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, blockSet.slab(), blockSet.base(), 2);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, blockSet.stairs(), blockSet.base(), 1);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, blockSet.fence(), blockSet.base(), 4);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, blockSet.fenceGate(), blockSet.base(), 4);
            }

            private void generateSetRecipes(ModBlocks.BlockSet blockSet) {
                Ingredient baseIngredient = Ingredient.of(blockSet.base());

                shaped(RecipeCategory.BUILDING_BLOCKS, blockSet.stairs(), 8)
                        .pattern("#  ")
                        .pattern("## ")
                        .pattern("###")
                        .define('#', baseIngredient)
                        .unlockedBy(getHasName(blockSet.base()), has(blockSet.base()))
                        .save(output);
                shaped(RecipeCategory.BUILDING_BLOCKS, blockSet.slab(), 6)
                        .pattern("###")
                        .define('#', baseIngredient)
                        .unlockedBy(getHasName(blockSet.base()), has(blockSet.base()))
                        .save(output);
                shaped(RecipeCategory.BUILDING_BLOCKS, blockSet.wall(), 12)
                        .pattern("###")
                        .pattern("###")
                        .define('#', baseIngredient)
                        .unlockedBy(getHasName(blockSet.base()), has(blockSet.base()))
                        .save(output);
                shaped(RecipeCategory.BUILDING_BLOCKS, blockSet.fence(), 16)
                        .pattern("#S#")
                        .pattern("#S#")
                        .define('#', blockSet.base())
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(blockSet.base()), has(blockSet.base()))
                        .save(output);
                shaped(RecipeCategory.BUILDING_BLOCKS, blockSet.fenceGate(), 8)
                        .pattern("S#S")
                        .pattern("S#S")
                        .define('#', blockSet.base())
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(blockSet.base()), has(blockSet.base()))
                        .save(output);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, blockSet.wall(), blockSet.base(), 2);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, blockSet.slab(), blockSet.base(), 2);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, blockSet.stairs(), blockSet.base(), 1);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, blockSet.fence(), blockSet.base(), 4);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, blockSet.fenceGate(), blockSet.base(), 4);
            }
        };
    }



    @Override
    public String getName() {
        return Umamium.MOD_ID + " Recipes";
    }
}
