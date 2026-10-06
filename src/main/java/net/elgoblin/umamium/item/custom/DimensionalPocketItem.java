package net.elgoblin.umamium.item.custom;

import net.elgoblin.umamium.client.DimensionalPocketCache;
import net.elgoblin.umamium.component.ModDataComponentTypes;
import net.elgoblin.umamium.item.ModItems;
import net.elgoblin.umamium.util.LegendaryItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.InstrumentComponent;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class DimensionalPocketItem extends Item {
    public DimensionalPocketItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Level level = context.getLevel();

        ItemStack dimensionalPocket = context.getItemInHand();
        boolean safeModeOn = dimensionalPocket.getOrDefault(ModDataComponentTypes.SAFE_MODE, false);
        BlockPos clickedPosition = context.getClickedPos();
        BlockEntity blockEntity = level.getBlockEntity(clickedPosition);

        if (blockEntity instanceof Container) {
            if (!safeModeOn) {
                if (!level.isClientSide()) {
                    Container container = LegendaryItemUtils.getContainer((ServerLevel) level, clickedPosition);
                    boolean linked = LegendaryItemUtils.linkOrUnlinkContainer(context, clickedPosition);
                    initializeOrRemoveColoredGroups(dimensionalPocket, container, linked);
                    return linked ? InteractionResult.SUCCESS : InteractionResult.FAIL;
                }
                return InteractionResult.PASS;
            }
        }

        Player user = context.getPlayer();
        if (user == null) { return InteractionResult.FAIL; }

        if (level.isClientSide()) {
            if (safeModeOn && DimensionalPocketCache.mainStackCount <= DimensionalPocketCache.mainStackDuplicateCount) {
                return InteractionResult.FAIL;
            }
            return useStackOn(DimensionalPocketCache.mainStack.copy(), context, clickedPosition, level, user);
        }

        ItemStack stackToUse = getStackToUse(dimensionalPocket, level, user, safeModeOn);
        if (isBannedItem(stackToUse)) { return InteractionResult.FAIL; }

        return useStackOn(stackToUse, context, clickedPosition, level, user);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {

        ItemStack dimensionalPocket = player.getItemInHand(hand);
        boolean safeModeOn = dimensionalPocket.getOrDefault(ModDataComponentTypes.SAFE_MODE, false);
        ItemStack copy = dimensionalPocket.copy();

        if (level.isClientSide()) {
            if (safeModeOn && DimensionalPocketCache.mainStackCount <= DimensionalPocketCache.mainStackDuplicateCount) {
                return InteractionResult.FAIL;
            }
            ItemStack stackToUse = DimensionalPocketCache.mainStack.copy();
            player.setItemSlot(hand.asEquipmentSlot(), stackToUse);
            InteractionResult result = stackToUse.use(level, player, hand);
            player.setItemSlot(hand.asEquipmentSlot(), copy);
            if (result.consumesAction()) {
                return InteractionResult.SUCCESS;
            }
            else {
                return InteractionResult.FAIL;
            }
        }

        ItemStack stackToUse = getStackToUse(dimensionalPocket, level, player, safeModeOn);
        if (isBannedItem(stackToUse)) { return InteractionResult.FAIL; }

        player.setItemSlot(hand.asEquipmentSlot(), stackToUse);
        InteractionResult result = stackToUse.use(level, player, hand);

        player.setItemSlot(hand.asEquipmentSlot(), copy);
        if (result.consumesAction()) {
            return InteractionResult.SUCCESS;
        }
        else {
            return InteractionResult.FAIL;
        }
    }

    private int findFirstNonEmptySlotOrZero(Container container) {
        int i = 0;
        if (container.isEmpty()) { return 0; }
        while (container.getItem(i).isEmpty()) { i++; }
        return i;
    }

    private void initializeOrRemoveColoredGroups(ItemStack dimensionalPocket, Container container, boolean linked) {
        if (linked) {
            int firstSlot = findFirstNonEmptySlotOrZero(container);
            ArrayList<Integer> selectedItemInEachColoredGroup = new ArrayList<>(Collections.nCopies(16, 0));
            selectedItemInEachColoredGroup.set(0,firstSlot);

            dimensionalPocket.set(ModDataComponentTypes.SELECTED_ITEM_IN_EACH_COLORED_GROUP, selectedItemInEachColoredGroup);
            dimensionalPocket.set(ModDataComponentTypes.SELECTED_COLORED_GROUP, 0);
            dimensionalPocket.set(ModDataComponentTypes.COLOR_INVENTORIES.get(DyeColor.byId(0)), IntStream.range(0,container.getContainerSize()).boxed().toList());

            for (int colorIndex = 1 ; colorIndex < 16 ; colorIndex++) {
                dimensionalPocket.set(ModDataComponentTypes.COLOR_INVENTORIES.get(DyeColor.byId(colorIndex)), Collections.emptyList());
            }
        }
        else {
            dimensionalPocket.remove(ModDataComponentTypes.SELECTED_COLORED_GROUP);
            dimensionalPocket.remove(ModDataComponentTypes.SELECTED_ITEM_IN_EACH_COLORED_GROUP);
            for (int colorIndex = 0 ; colorIndex < 16 ; colorIndex++) {
                dimensionalPocket.remove(ModDataComponentTypes.COLOR_INVENTORIES.get(DyeColor.byId(colorIndex)));
            }
        }
    }

    private boolean isOffBounds(int slot, int size) {
        return slot < 0 || slot >= size;
    }

    private boolean isBannedItem(ItemStack stack) {
        if (stack.is(ModItems.DIMENSIONAL_POCKET)) {return true;}
        if (stack.is(Items.ENDER_EYE)) {return true;}
        if (stack.is(ModItems.FLASH)) {return true;}
        if (stack.getItem() instanceof BucketItem) {return true;}
        if (stack.is(Items.POWDER_SNOW_BUCKET)) {return true;}
        return stack.isEmpty();
    }

    private ItemStack getStackToUse(ItemStack dimensionalPocket, Level level, Player user, boolean safeModeOn) {

        Container deposit = LegendaryItemUtils.getContainer(dimensionalPocket, level);
        if (deposit == null) { return ItemStack.EMPTY; }

        Integer selectedColoredGroup = dimensionalPocket.get(ModDataComponentTypes.SELECTED_COLORED_GROUP);
        List<Integer> selectedItemsInEachColoredGroup = dimensionalPocket.get(ModDataComponentTypes.SELECTED_ITEM_IN_EACH_COLORED_GROUP);
        if (selectedColoredGroup == null || selectedItemsInEachColoredGroup == null) { return ItemStack.EMPTY; }

        List<Integer> group = dimensionalPocket.get(ModDataComponentTypes.COLOR_INVENTORIES.get(DyeColor.byId(selectedColoredGroup)));
        if (group == null) { return ItemStack.EMPTY; }

        int currentSelectedItemIndex = selectedItemsInEachColoredGroup.get(selectedColoredGroup);

        if (group.isEmpty() || isOffBounds(currentSelectedItemIndex, deposit.getContainerSize())) {
            return ItemStack.EMPTY;
        }

        ItemStack stackToUse = getStackFromInventory(deposit, group.get(currentSelectedItemIndex));
        ItemStack fallbackStack = stackToUse;
        if (isBannedItem(stackToUse)) { return ItemStack.EMPTY; }

        // Da un stack distinto al seleccionado, para permitir gastar el cofre entero aunque no estes en safeMode
        stackToUse = findNonEmptyStackInGroupOfType(deposit, group, group.get(currentSelectedItemIndex), stackToUse.getItem(), safeModeOn);
        if (stackToUse.isEmpty()) {
            if (!user.isCreative() && notAllowedToUse(safeModeOn, fallbackStack)) {
                return ItemStack.EMPTY;
            }
            stackToUse = fallbackStack;
        }
        return stackToUse;
    }

    private ItemStack findNonEmptyStackInGroupOfType(Container deposit, List<Integer> group, int currentSelectedItemIndex, Item usedItem, boolean safeMode) {
        for (Integer depositIndex : group) {
            if (depositIndex != currentSelectedItemIndex) {
                if (!isOffBounds(depositIndex, deposit.getContainerSize())) {
                    ItemStack stack = deposit.getItem(depositIndex);
                    if (stack.is(usedItem) && stack.getCount() > (safeMode ? 1 : 0)) {
                        return stack;
                    }
                }
            }
        }
        return ItemStack.EMPTY;
    }

    private InteractionResult useStackOn(ItemStack stackToUse, UseOnContext context, BlockPos clickedPosition, Level level, Player user) {
        BlockHitResult hitResult = new BlockHitResult(context.getClickLocation(), context.getClickedFace(), clickedPosition, false);
        UseOnContext newContext = new UseOnContext(context.getLevel(), context.getPlayer(), context.getHand(), stackToUse, hitResult);

        InteractionResult result = stackToUse.useOn(newContext);
        if (result == InteractionResult.PASS) {
            result = use(level, user, context.getHand());
        }
        return result;
    }

    private void performAnimationsAndSound(InteractionResult result, Level level, UseOnContext context, Player user, InteractionHand hand) {
        if (result.consumesAction()) {
            BlockState placedState = level.getBlockState(context.getClickedPos().relative(context.getClickedFace()));

            if (placedState.isAir()) {
                placedState = level.getBlockState(context.getClickedPos());
            }

            if (!placedState.isAir()) {
                SoundType soundType = placedState.getSoundType();
                level.playSound(
                        null, // Player is null so EVERYONE (including the placing player) hears it
                        context.getClickedPos(),
                        soundType.getPlaceSound(),
                        net.minecraft.sounds.SoundSource.BLOCKS,
                        (soundType.getVolume() + 1.0F) / 2.0F,
                        soundType.getPitch() * 0.8F
                );
            }
            user.swing(hand, true);
        }
    }

    private void performAnimationsAndSound(InteractionResult result, ItemStack usedStack, Level level, Player user, InteractionHand hand) {
        if (result.consumesAction()) {
            if (usedStack.is(Items.GOAT_HORN)) {

                InstrumentComponent instrumentComponent = usedStack.get(DataComponents.INSTRUMENT);
                if (instrumentComponent == null) { return; }
                Instrument instrument = instrumentComponent.instrument().value();

                SoundEvent soundEvent = instrument.soundEvent().value();
                float f = instrument.range() / 16.0F;
                level.playSound (null, user, soundEvent, SoundSource.RECORDS, f, 1.0F);

                user.getCooldowns().addCooldown(usedStack, (int) Math.floor(instrument.useDuration() * 20.0F));
            }

            user.swing(hand, true);
        }
    }

    public static boolean notAllowedToUse(boolean safeModeOn, ItemStack stackToUse) {
        return safeModeOn
                && (stackToUse.isDamageableItem() && (stackToUse.getMaxDamage() - stackToUse.getDamageValue()) <= 1
                        || !stackToUse.isDamageableItem() && stackToUse.getCount() <= 1);
    }

    private static ItemStack getStackFromInventory(Container deposit, int index) {
        if (deposit.isEmpty()) { return ItemStack.EMPTY; }
        if (index >= deposit.getContainerSize()) { return ItemStack.EMPTY; }

        return deposit.getItem(index);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        LegendaryItemUtils.appendTooltip(itemStack, builder);
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
    }
}
