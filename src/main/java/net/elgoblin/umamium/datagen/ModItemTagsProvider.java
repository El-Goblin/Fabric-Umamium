package net.elgoblin.umamium.datagen;

import net.elgoblin.umamium.block.ModBlocks;
import net.elgoblin.umamium.item.ModItems;
import net.elgoblin.umamium.tags.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends FabricTagsProvider.ItemTagsProvider {
    public ModItemTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {

        // LEGENDARY TOOLS

        tag(ItemTags.SWORDS).add(ModItems.getResourceKey(ModItems.LEGENDARY_SWORD));
        tag(ItemTags.PICKAXES).add(ModItems.getResourceKey(ModItems.LEGENDARY_PICKAXE));
        tag(ItemTags.AXES).add(ModItems.getResourceKey(ModItems.LEGENDARY_AXE));
        tag(ItemTags.SHOVELS).add(ModItems.getResourceKey(ModItems.LEGENDARY_SHOVEL));
        tag(ItemTags.HOES).add(ModItems.getResourceKey(ModItems.LEGENDARY_HOE));
        tag(ItemTags.SPEARS).add(ModItems.getResourceKey(ModItems.LEGENDARY_SPEAR));

        tag(ItemTags.MINING_ENCHANTABLE).add(ModItems.getResourceKey(Items.BRUSH));

        tag(ModTags.Items.LEGENDARY_TOOLS)
                .add(ModItems.getResourceKey(ModItems.LEGENDARY_SWORD))
                .add(ModItems.getResourceKey(ModItems.LEGENDARY_LONGSWORD))
                .add(ModItems.getResourceKey(ModItems.LEGENDARY_PICKAXE))
                .add(ModItems.getResourceKey(ModItems.LEGENDARY_AXE))
                .add(ModItems.getResourceKey(ModItems.LEGENDARY_SHOVEL))
                .add(ModItems.getResourceKey(ModItems.LEGENDARY_HOE))
                .add(ModItems.getResourceKey(ModItems.LEGENDARY_SPEAR))
        ;

        // LONGSWORDS

        tag(ModTags.Items.LONGSWORDS).add(ModItems.getResourceKey(ModItems.LEGENDARY_LONGSWORD));
        tag(ModTags.Items.LONGSWORDS).add(ModItems.getResourceKey(ModItems.WOODEN_LONGSWORD));
        tag(ModTags.Items.LONGSWORDS).add(ModItems.getResourceKey(ModItems.STONE_LONGSWORD));
        tag(ModTags.Items.LONGSWORDS).add(ModItems.getResourceKey(ModItems.COPPER_LONGSWORD));
        tag(ModTags.Items.LONGSWORDS).add(ModItems.getResourceKey(ModItems.IRON_LONGSWORD));
        tag(ModTags.Items.LONGSWORDS).add(ModItems.getResourceKey(ModItems.GOLDEN_LONGSWORD));
        tag(ModTags.Items.LONGSWORDS).add(ModItems.getResourceKey(ModItems.DIAMOND_LONGSWORD));
        tag(ModTags.Items.LONGSWORDS).add(ModItems.getResourceKey(ModItems.NETHERITE_LONGSWORD));
        tag(ModTags.Items.LONGSWORDS).add(ModItems.getResourceKey(ModItems.FLAMEBERGE_LONGSWORD));
        tag(ModTags.Items.LONGSWORDS).add(ModItems.getResourceKey(ModItems.FIRE_DRAGONSWORD_LONGSWORD));
        tag(ModTags.Items.LONGSWORDS).add(ModItems.getResourceKey(ModItems.FIENDBLADE_LONGSWORD));


        tag(ItemTags.SWORDS).addTag(ModTags.Items.LONGSWORDS);
    }
}
