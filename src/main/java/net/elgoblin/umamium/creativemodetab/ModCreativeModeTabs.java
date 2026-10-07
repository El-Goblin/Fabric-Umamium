package net.elgoblin.umamium.creativemodetab;

import net.elgoblin.umamium.Umamium;
import net.elgoblin.umamium.block.ModBlocks;
import net.elgoblin.umamium.item.ModItems;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTabs {

    public static final CreativeModeTab BLOCKS_TAB = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
            Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "blocks_tab"),
            FabricCreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModBlocks.GOLD_BRICKS.asItem()))
                    .title(Component.translatable("creativemodetab." + Umamium.MOD_ID + ".blocks_tab"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModBlocks.PROTECTOR_BLOCK);

                        addToTab(output, ModBlocks.GOLD_SET);
                        addToTab(output, ModBlocks.GOLD_BRICKS_SET);
                        addToTab(output, ModBlocks.CALCIFIED_GOLD_SET);
                        addToTab(output, ModBlocks.POLISHED_GOLD_SET);
                        addToTab(output, ModBlocks.GOLD_ORE_SET);
                        addToTab(output, ModBlocks.DEEPSLATE_GOLD_ORE_SET);
                        addToTab(output, ModBlocks.NETHER_GOLD_ORE_SET);

                        addToTab(output, ModBlocks.DIAMOND_SET);
                        addToTab(output, ModBlocks.DIAMOND_BRICKS_SET);
                        addToTab(output, ModBlocks.CALCIFIED_DIAMOND_SET);
                        addToTab(output, ModBlocks.POLISHED_DIAMOND_SET);
                        addToTab(output, ModBlocks.DIAMOND_ORE_SET);
                        addToTab(output, ModBlocks.DEEPSLATE_DIAMOND_ORE_SET);

                        addToTab(output, ModBlocks.IRON_SET);
                        addToTab(output, ModBlocks.IRON_BRICKS_SET);
                        addToTab(output, ModBlocks.CALCIFIED_IRON_SET);
                        addToTab(output, ModBlocks.POLISHED_IRON_SET);
                        addToTab(output, ModBlocks.IRON_ORE_SET);
                        addToTab(output, ModBlocks.DEEPSLATE_IRON_ORE_SET);

                        addToTab(output, ModBlocks.EMERALD_SET);
                        addToTab(output, ModBlocks.EMERALD_BRICKS_SET);
                        addToTab(output, ModBlocks.CALCIFIED_EMERALD_SET);
                        addToTab(output, ModBlocks.POLISHED_EMERALD_SET);
                        addToTab(output, ModBlocks.CHISELED_EMERALD_SET);
                        addToTab(output, ModBlocks.EMERALD_ORE_SET);
                        addToTab(output, ModBlocks.DEEPSLATE_EMERALD_ORE_SET);

                        addToTab(output, ModBlocks.AMETHYST_SET);
                        addToTab(output, ModBlocks.AMETHYST_BRICKS_SET);
                        addToTab(output, ModBlocks.CALCIFIED_AMETHYST_SET);
                        addToTab(output, ModBlocks.POLISHED_AMETHYST_SET);

                        addToTab(output, ModBlocks.LAPIS_SET);
                        addToTab(output, ModBlocks.LAPIS_BRICKS_SET);
                        addToTab(output, ModBlocks.CALCIFIED_LAPIS_SET);
                        addToTab(output, ModBlocks.POLISHED_LAPIS_SET);
                        addToTab(output, ModBlocks.LAPIS_ORE_SET);
                        addToTab(output, ModBlocks.DEEPSLATE_LAPIS_ORE_SET);

                        addToTab(output, ModBlocks.COAL_SET);
                        addToTab(output, ModBlocks.COAL_BRICKS_SET);
                        addToTab(output, ModBlocks.CALCIFIED_COAL_SET);
                        addToTab(output, ModBlocks.POLISHED_COAL_SET);
                        addToTab(output, ModBlocks.COAL_ORE_SET);
                        addToTab(output, ModBlocks.DEEPSLATE_COAL_ORE_SET);

                        addToTab(output, ModBlocks.NETHERITE_SET);
                        addToTab(output, ModBlocks.NETHERITE_BRICKS_SET);
                        addToTab(output, ModBlocks.CALCIFIED_NETHERITE_SET);
                        addToTab(output, ModBlocks.POLISHED_NETHERITE_SET);

                        addToTab(output, ModBlocks.REDSTONE_SET);
                        addToTab(output, ModBlocks.REDSTONE_BRICKS_SET);
                        addToTab(output, ModBlocks.CALCIFIED_REDSTONE_SET);
                        addToTab(output, ModBlocks.POLISHED_REDSTONE_SET);
                        addToTab(output, ModBlocks.REDSTONE_ORE_SET);
                        addToTab(output, ModBlocks.DEEPSLATE_REDSTONE_ORE_SET);

                        addToTab(output, ModBlocks.COPPER_ORE_SET);
                        addToTab(output, ModBlocks.DEEPSLATE_COPPER_ORE_SET);

                        addToTab(output, ModBlocks.OBSIDIAN_SET);
                        addToTab(output, ModBlocks.SCULK_SET);
                        addToTab(output, ModBlocks.BEDROCK_SET);
                        addToTab(output, ModBlocks.CALCITE_SET);
                        addToTab(output, ModBlocks.FLINT_SET);
                        addToTab(output, ModBlocks.ICE_SET);
                        addToTab(output, ModBlocks.PACKED_ICE_SET);
                        addToTab(output, ModBlocks.BLUE_ICE_SET);

                        output.accept(ModBlocks.NETHER_BRICK_FENCE_GATE);
                        output.accept(ModBlocks.SMOOTH_QUARTZ_WALL);
//                        output.accept(ModBlocks.QUARTZ_WALL);
                        output.accept(ModBlocks.POLISHED_DIORITE_WALL);
                        output.accept(ModBlocks.POLISHED_GRANITE_WALL);
                        output.accept(ModBlocks.POLISHED_ANDESITE_WALL);
                        output.accept(ModBlocks.STONE_WALL);
                        output.accept(ModBlocks.PURPUR_WALL);
                        output.accept(ModBlocks.PRISMARINE_BRICK_WALL);
                        output.accept(ModBlocks.DARK_PRISMARINE_WALL);
                    })
                    .build()
    );

public static final CreativeModeTab ITEMS_TAB = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
            Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "items_tab"),
            FabricCreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.CHAOS_ORB))
                    .title(Component.translatable("creativemodetab." + Umamium.MOD_ID + ".items_tab"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.MOSS);
                        output.accept(ModItems.LA_LECHONA);
                        output.accept(ModItems.NETHERITE_NUGGET);
                        output.accept(ModItems.FLASH);
                        output.accept(ModItems.LEGENDARY_PICKAXE);
                        output.accept(ModItems.LEGENDARY_SHOVEL);
                        output.accept(ModItems.LEGENDARY_AXE);
                        output.accept(ModItems.LEGENDARY_HOE);
                        output.accept(ModItems.LEGENDARY_SWORD);
                        output.accept(ModItems.LEGENDARY_LONGSWORD);
                        output.accept(ModItems.LEGENDARY_SPEAR);
                        output.accept(ModItems.WOODEN_LONGSWORD);
                        output.accept(ModItems.STONE_LONGSWORD);
                        output.accept(ModItems.COPPER_LONGSWORD);
                        output.accept(ModItems.IRON_LONGSWORD);
                        output.accept(ModItems.GOLDEN_LONGSWORD);
                        output.accept(ModItems.DIAMOND_LONGSWORD);
                        output.accept(ModItems.NETHERITE_LONGSWORD);
                        output.accept(ModItems.FLAMEBERGE_LONGSWORD);
                        output.accept(ModItems.FIRE_DRAGONSWORD_LONGSWORD);
                        output.accept(ModItems.FIENDBLADE_LONGSWORD);
                        output.accept(ModItems.LEGENDARY_ROCKET);
                        output.accept(ModItems.MAGIC_MIRROR);
                        output.accept(ModItems.MEMORY_MIRROR);
                        output.accept(ModItems.CHAOS_MIRROR);
                        output.accept(ModItems.POKEBALL);
                        output.accept(ModItems.DIMENSIONAL_POCKET);
                        output.accept(ModItems.CHAOS_ORB);
                    })
                    .build()
    );


    public static void registerModCreativeModeTabs() {
        Umamium.LOGGER.info("Registering Creative Mode Tabs for " + Umamium.MOD_ID);
    }

    private static void addToTab(CreativeModeTab.Output output, ModBlocks.BlockSet blockSet) {
        output.accept(blockSet.base());
        output.accept(blockSet.stairs());
        output.accept(blockSet.slab());
        output.accept(blockSet.fence());
        output.accept(blockSet.fenceGate());
        output.accept(blockSet.wall());
        if (blockSet.door() != null) {output.accept(blockSet.door());}
        if (blockSet.trapdoor() != null) {output.accept(blockSet.trapdoor());}
    }
}
