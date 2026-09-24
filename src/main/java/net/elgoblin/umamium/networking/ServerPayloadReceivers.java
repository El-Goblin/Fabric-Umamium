package net.elgoblin.umamium.networking;

import net.elgoblin.umamium.client.DimensionalPocketCache;
import net.elgoblin.umamium.component.ModAttachmentTypes;
import net.elgoblin.umamium.component.ModDataComponentTypes;
import net.elgoblin.umamium.item.ModItems;
import net.elgoblin.umamium.item.custom.DimensionalPocketItem;
import net.elgoblin.umamium.networking.dimensionalpocket.*;
import net.elgoblin.umamium.tags.ModTags;
import net.elgoblin.umamium.util.LegendaryItemUtils;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class ServerPayloadReceivers {

    public static void registerServerGlobalReceivers() {

        // MANUAL BREATHE

        ServerPlayNetworking.registerGlobalReceiver(
                ManualBreathePayload.TYPE, (payload, context) -> {
                    context.server().execute(() -> {
                        ServerPlayer player = context.player();
                        if (player.hasAttached(ModAttachmentTypes.MANUAL_BREATHING)) {
                            player.setAirSupply(player.getAirSupply() + 6);
                        }
                    });
                }
        );

        // TOGGLE SAFE MODE
        ServerPlayNetworking.registerGlobalReceiver(
                SwitchEnchantmentToggleSafeModePayload.TYPE,
                (payload, context) -> {
                    context.server().execute(() -> {
                        ServerPlayer player = context.player();
                        ItemStack mainHandItem = player.getMainHandItem();
                        if (mainHandItem.is(ModTags.Items.LEGENDARY_TOOLS)) {
                            LegendaryItemUtils.switchEnchantmentSet(mainHandItem);
                        }
                        else {
                            ItemStack offHandItem = player.getOffhandItem();
                            if (offHandItem.is(ModTags.Items.LEGENDARY_TOOLS)) {
                                LegendaryItemUtils.switchEnchantmentSet(offHandItem);
                            }
                        }
                        if (mainHandItem.is(ModItems.DIMENSIONAL_POCKET)) {
                            boolean currentSafeMode = mainHandItem.getOrDefault(ModDataComponentTypes.SAFE_MODE, false);
                            if (currentSafeMode) {
                                mainHandItem.remove(ModDataComponentTypes.SAFE_MODE);
                            }
                            else {
                                mainHandItem.set(ModDataComponentTypes.SAFE_MODE, true);
                            }
                        }
                        else {
                            ItemStack offHandItem = player.getOffhandItem();
                            if (offHandItem.is(ModItems.DIMENSIONAL_POCKET)) {
                                boolean currentSafeMode = offHandItem.getOrDefault(ModDataComponentTypes.SAFE_MODE, false);
                                if (currentSafeMode) {
                                    offHandItem.remove(ModDataComponentTypes.SAFE_MODE);
                                }
                                else {
                                    offHandItem.set(ModDataComponentTypes.SAFE_MODE, true);
                                }
                            }
                        }
                    });
                }
        );

        registerDimensionalPocketServerGlobalReceivers();
    }

    private static void registerDimensionalPocketServerGlobalReceivers() {

        // A QUE ESTOY APUNTANDO EN EL COFRE?
        ServerPlayNetworking.registerGlobalReceiver(DimensionalPocketDepositContentsQueryPayload.TYPE, (payload, context) -> {context.server().execute(() -> {

            ItemStack dimensionalPocket = payload.itemStack();
            DimensionalPocketData dimPocketData = checkValidDimensionalPocket(dimensionalPocket, context.server());

            if (dimPocketData == null) {
                sendEmptyPayload(context);
                return;
            }

            Container deposit = LegendaryItemUtils.getContainer(dimPocketData.targetLevel(), dimPocketData.depositPos());
            if (deposit == null) {
                sendEmptyPayload(context);
                return;
            }

            StacksToShow stacksToShow = getStacksToShow(dimensionalPocket, deposit, dimPocketData);

            List<Integer> group = dimensionalPocket.get(ModDataComponentTypes.COLOR_INVENTORIES.get(DyeColor.byId(dimPocketData.selectedColoredGroup())));
            int mainStackDuplicates = 0;
            if (group != null) {
                ItemStack mainStack = stacksToShow.mainStack();

                for (int depositIndex : group) {
                    ItemStack stack = getStackFromInventory(deposit, depositIndex);
                    if (canMergeItems(stack, mainStack)) {
                        mainStackDuplicates++;
                    }
                }
            }


            context.responseSender().sendPacket(
                    new DimensionalPocketDepositContentsResponsePayload(
                            stacksToShow.mainStack(),
                            stacksToShow.mainStackCount(),
                            stacksToShow.sameGroupPrevStack(),
                            stacksToShow.sameGroupNextStack(),
                            stacksToShow.prevGroupStack(),
                            stacksToShow.prevGroupIndex(),
                            stacksToShow.nextGroupStack(),
                            stacksToShow.nextGroupIndex(),
                            stacksToShow.mainStack().isEmpty() ? -1 : mainStackDuplicates
                    )
            );
        });});


        // MIDDLE CLICK QUERY
        ServerPlayNetworking.registerGlobalReceiver(DimensionalPocketMiddleClickQueryPayload.TYPE, (payload, context) -> {context.server().execute(() -> {

            ServerPlayer player = context.player();
            ItemStack infiniteItem = player.getMainHandItem();

            if (!infiniteItem.is(ModItems.DIMENSIONAL_POCKET)) {
                infiniteItem = player.getOffhandItem();
            }
            if (!infiniteItem.is(ModItems.DIMENSIONAL_POCKET)) {
                return;
            }

            DimensionalPocketData dimPocketData = checkValidDimensionalPocket(infiniteItem, context.server());

            if (dimPocketData == null) { return; }

            ServerLevel targetWorld = dimPocketData.targetLevel();
            BlockPos storagePos = dimPocketData.depositPos();
            Integer interGroupPointer = dimPocketData.selectedColoredGroup();
            List<Integer> intraGroupPointers = dimPocketData.selectedItemInEachGroup();
            boolean safeMode = dimPocketData.safeMode();

            if (!(targetWorld.getBlockEntity(storagePos) instanceof Container)) { return; }

            Container deposit = getDeposit(targetWorld, storagePos);
            if (deposit == null) { return; }

            List<Integer> group = infiniteItem.get(
                    ModDataComponentTypes.COLOR_INVENTORIES.get(DyeColor.byId(interGroupPointer)));

            if (group == null) { return; }

            ArrayList<Integer> newIntraGroupPointers = new ArrayList<>(intraGroupPointers);

            for (int i = 0; i < group.size(); i++) {
                ItemStack currentStack = getStackFromInventory(deposit, group.get(i));

                if (currentStack.getCount() > (safeMode ? 1 : 0) && currentStack.getItem() == payload.clicked().asItem()) {
                    newIntraGroupPointers.set(interGroupPointer, i);
                    infiniteItem.set(ModDataComponentTypes.SELECTED_ITEM_IN_EACH_COLORED_GROUP, newIntraGroupPointers);
                    return;
                }
            }

            for (int offset = 0; offset < 16; offset++) {
                int newInterGroupPointer = Math.floorMod(interGroupPointer + offset, 16);
                group = infiniteItem.get(
                        ModDataComponentTypes.COLOR_INVENTORIES.get(DyeColor.byId(newInterGroupPointer)));

                if (group == null) { return; }

                for (int i = 0; i < group.size(); i++) {
                    ItemStack currentStack = getStackFromInventory(deposit, group.get(i));

                    if (currentStack.getCount() > (safeMode ? 1 : 0) && currentStack.getItem() == payload.clicked().asItem()) {

                        newIntraGroupPointers.set(newInterGroupPointer, i);
                        infiniteItem.set(ModDataComponentTypes.SELECTED_COLORED_GROUP, newInterGroupPointer);
                        infiniteItem.set(ModDataComponentTypes.SELECTED_ITEM_IN_EACH_COLORED_GROUP, newIntraGroupPointers);
                        return;
                    }
                }
            }
        });});

        // TOGGLE SLOT
        ServerPlayNetworking.registerGlobalReceiver(DimensionalPocketToggleSlotPayload.TYPE, (payload, context) -> {context.server().execute(() -> {

            ServerPlayer player = context.player();
            AbstractContainerMenu handler = player.containerMenu;
            Slot targetSlot = handler.getSlot(payload.targetSlotId());

            if (targetSlot.getItem().isEmpty()) { return; }

            ItemStack itemStack = targetSlot.getItem();
            if (!itemStack.is(ModItems.DIMENSIONAL_POCKET)) { return; }

            DyeColor dyeColor = DyeColor.byId(payload.colorIndex());
            DataComponentType<List<Integer>> colorComponent = ModDataComponentTypes.COLOR_INVENTORIES.get(dyeColor);

            if (colorComponent == null) { return; }

            List<Integer> existingSlots = itemStack.getOrDefault(colorComponent, List.of());
            List<Integer> updatedSlots = new ArrayList<>(existingSlots);
            int clickedSlotId = payload.clickedSlotId();

            if (updatedSlots.contains(clickedSlotId)) {
                updatedSlots.remove(Integer.valueOf(clickedSlotId));
            }
            else {
                updatedSlots.add(clickedSlotId);
            }

            updatedSlots.sort(Comparator.naturalOrder());

            itemStack.set(colorComponent, updatedSlots);
            targetSlot.set(itemStack);
            handler.broadcastChanges();
        });});

        // SELECT COLOR
        ServerPlayNetworking.registerGlobalReceiver(DimensionalPocketSelectColorPayload.TYPE, (payload, context) -> {context.server().execute(() -> {

            ServerPlayer player = context.player();
            AbstractContainerMenu currentHandler = player.containerMenu;
            int targetSlot = payload.slot();

            if (targetSlot >= 0 && targetSlot < currentHandler.slots.size()) {
                ItemStack stack = currentHandler.getSlot(targetSlot).getItem();

                if (stack.is(ModItems.DIMENSIONAL_POCKET)) {
                    stack.set(ModDataComponentTypes.SELECTED_COLORED_GROUP, payload.colorIndex());
                }
            }
        });});

        // SCROLL BETWEEN GROUPS
        ServerPlayNetworking.registerGlobalReceiver(DimensionalPocketScrollBetweenGroupsPayload.TYPE, (payload, context) -> {

            ServerPlayer player = context.player();
            ItemStack activeHand = ItemStack.EMPTY;

            if (player.getMainHandItem().is(ModItems.DIMENSIONAL_POCKET)) {
                activeHand = player.getMainHandItem();
            }
            else if (player.getOffhandItem().is(ModItems.DIMENSIONAL_POCKET)) {
                activeHand = player.getOffhandItem();
            }
            if (activeHand.isEmpty()) { return; }

            DimensionalPocketData dimPocketData = checkValidDimensionalPocket(activeHand, context.server());
            if (dimPocketData == null) { return; }

            Container deposit = LegendaryItemUtils.getContainer(activeHand, dimPocketData.targetLevel());
            if (deposit == null) { return; }

            boolean safeMode = dimPocketData.safeMode();
            int colorGroupIndex = dimPocketData.selectedColoredGroup();

            for (int offset = 1 ; offset < 16 ; offset++) {
                colorGroupIndex = Math.floorMod(colorGroupIndex + payload.scroll(), 16);
                List<Integer> group = activeHand.get(
                        ModDataComponentTypes.COLOR_INVENTORIES.get(DyeColor.byId(colorGroupIndex)));

                if (group == null || group.isEmpty()) { continue; }

                int firstNonEmptySlotIndex = getFirstNonEmptySlotIndex(deposit, group, safeMode);
                if (firstNonEmptySlotIndex == -1) { continue; }

                activeHand.set(ModDataComponentTypes.SELECTED_COLORED_GROUP, colorGroupIndex);
                List<Integer> newPointers = new ArrayList<>(dimPocketData.selectedItemInEachGroup());

                int oldSelectedItemInGroupIndex = dimPocketData.selectedItemInEachGroup().get(colorGroupIndex);
                int depositPointer = group.get(oldSelectedItemInGroupIndex);

                if (depositPointer < deposit.getContainerSize()) {
                    ItemStack oldSelectedStackInGroup = getStackFromInventory(deposit, depositPointer);

                    // Ya esta apuntando a la posicion correcta
                    if (!oldSelectedStackInGroup.isEmpty() && !DimensionalPocketItem.notAllowedToUse(safeMode, oldSelectedStackInGroup)) {
                        return;
                    }
                }

                newPointers.set(colorGroupIndex, firstNonEmptySlotIndex);
                activeHand.set(ModDataComponentTypes.SELECTED_ITEM_IN_EACH_COLORED_GROUP, newPointers);
                return;
            }

            // Di la vuelta
            List<Integer> currentGroup = activeHand.get(
                    ModDataComponentTypes.COLOR_INVENTORIES.get(DyeColor.byId(dimPocketData.selectedColoredGroup())));
            if (currentGroup == null) { return; }

            int firstNonEmptySlotIndexCurrentGroup = getFirstNonEmptySlotIndex(deposit, currentGroup, safeMode);
            if (firstNonEmptySlotIndexCurrentGroup == -1) { return; }

            int oldSelectedItemInGroupIndex = dimPocketData.selectedItemInEachGroup().get(dimPocketData.selectedColoredGroup());
            int depositPointer = currentGroup.get(oldSelectedItemInGroupIndex);

            if (depositPointer < deposit.getContainerSize()) {
                ItemStack oldSelectedStackInGroup = getStackFromInventory(deposit, depositPointer);
                if (!oldSelectedStackInGroup.isEmpty() && !DimensionalPocketItem.notAllowedToUse(safeMode, oldSelectedStackInGroup)) { return; }
            }
            List<Integer> newPointers = new ArrayList<>(dimPocketData.selectedItemInEachGroup());
            newPointers.set(dimPocketData.selectedColoredGroup(), firstNonEmptySlotIndexCurrentGroup);
            activeHand.set(ModDataComponentTypes.SELECTED_ITEM_IN_EACH_COLORED_GROUP, newPointers);
        });


        // SCROLL INSIDE GROUP
        ServerPlayNetworking.registerGlobalReceiver(DimensionalPocketScrollInsideGroupPayload.TYPE, (payload, context) -> {

            ServerPlayer player = context.player();

            ItemStack activeHand = ItemStack.EMPTY;
            if (player.getMainHandItem().is(ModItems.DIMENSIONAL_POCKET)) {
                activeHand = player.getMainHandItem();
            }
            else if (player.getOffhandItem().is(ModItems.DIMENSIONAL_POCKET)) {
                activeHand = player.getOffhandItem();
            }
            if (activeHand.isEmpty()) {
                return;
            }
            DimensionalPocketData dimPocketData = checkValidDimensionalPocket(activeHand, context.server());

            if (dimPocketData == null) { return; }

            Container deposit = getDeposit(dimPocketData.targetLevel(), dimPocketData.depositPos());
            if (deposit == null) { return; }

            List<Integer> group = activeHand.get(
                    ModDataComponentTypes.COLOR_INVENTORIES.get(DyeColor.byId(dimPocketData.selectedColoredGroup())));
            if (group == null) { return; }

            int itemIndexInGroup = dimPocketData.selectedItemInEachGroup().get(dimPocketData.selectedColoredGroup());
            ItemStack currentSelectedItemStack = getStackFromInventory(deposit, group.get(dimPocketData.selectedItemInEachGroup().get(dimPocketData.selectedColoredGroup())));

            ItemStack sameGroupPrevStack = DimensionalPocketCache.sameGroupPrevStack;
            ItemStack sameGroupNextStack = DimensionalPocketCache.sameGroupNextStack;

            ItemStack targetStack = payload.scroll() == -1 ? sameGroupNextStack : sameGroupPrevStack;
            if (canMergeItems(targetStack, currentSelectedItemStack)) { return; }

            for (int offset = 1; offset <= group.size(); offset++) {
                itemIndexInGroup = Math.floorMod(itemIndexInGroup - payload.scroll(), group.size());
                ItemStack currentStack = getStackFromInventory(deposit, group.get(itemIndexInGroup));
                if (!canMergeItems(currentStack, targetStack)) { continue; }

                List<Integer> newIntraGroupPointers = new ArrayList<>(dimPocketData.selectedItemInEachGroup());
                newIntraGroupPointers.set(dimPocketData.selectedColoredGroup(), itemIndexInGroup);
                activeHand.set(ModDataComponentTypes.SELECTED_ITEM_IN_EACH_COLORED_GROUP, newIntraGroupPointers);
                return;
            }
        });}

    private static int findNextNonEmptyGroup(ItemStack infiniteItemStack, Container deposit, int currentGroup, int direction, boolean safeMode) {

        int newGroup = currentGroup;
        for (int i = 1; i < 16; i++) {
            newGroup = Math.floorMod(newGroup + direction, 16);

            List<Integer> group = infiniteItemStack.get(
                    ModDataComponentTypes.COLOR_INVENTORIES.get(DyeColor.byId(newGroup)));

            if (group == null || group.isEmpty()) { continue; }

            for (Integer slotIndex : group) {
                ItemStack currentStack = getStackFromInventory(deposit, slotIndex);
                if (!currentStack.isEmpty() && !DimensionalPocketItem.notAllowedToUse(safeMode, currentStack)) {
                    return newGroup;
                }
            }
        }
        return currentGroup;
    }

    private static ItemStack getStackFromInventory(Container deposit, int index) {

        if (deposit.isEmpty()) { return ItemStack.EMPTY; }
        if (index >= deposit.getContainerSize()) { return ItemStack.EMPTY; }

        return deposit.getItem(index);
    }

    private static StacksToShow emptyStacksToShow() {
        return new StacksToShow(ItemStack.EMPTY, 0, ItemStack.EMPTY, ItemStack.EMPTY,
                ItemStack.EMPTY, 0, ItemStack.EMPTY, 0);
    }

    private static void sendEmptyPayload(ServerPlayNetworking.Context context) {
        context.responseSender().sendPacket(
                new DimensionalPocketDepositContentsResponsePayload(
                        ItemStack.EMPTY, 0, ItemStack.EMPTY, ItemStack.EMPTY,
                        ItemStack.EMPTY, 0, ItemStack.EMPTY, 0, -1)
        );
    }

    private static int getFirstNonEmptySlotIndex(Container deposit, List<Integer> group, boolean safeMode) {
        for (int i = 0; i < group.size(); i++) {
            ItemStack currentStack = getStackFromInventory(deposit, group.get(i));
            if (!currentStack.isEmpty() && !DimensionalPocketItem.notAllowedToUse(safeMode, currentStack)) {
                return i;
            }
        }
        return -1;
    }

    private static Container getDeposit(ServerLevel targetWorld, BlockPos storagePos) {

        BlockState blockState = targetWorld.getBlockState(storagePos);
        Block block = blockState.getBlock();
        Container deposit = null;

        if (block instanceof ChestBlock chest) {
            deposit = ChestBlock.getContainer(chest, blockState, targetWorld, storagePos, true);
        }
        if (deposit == null) {
            BlockEntity blockEntity = targetWorld.getBlockEntity(storagePos);
            if (blockEntity instanceof Container container) {
                deposit = container;
            }
        }
        return deposit;
    }

    private static boolean canMergeItems(ItemStack first, ItemStack second) {
        return first.getCount() <= first.getMaxStackSize()
                && ItemStack.isSameItemSameComponents(
                first,
                second
        );
    }

    private static StacksToShow getStacksToShow(ItemStack dimPocket, Container deposit, DimensionalPocketData dimPocketData) {

        if (deposit.isEmpty()) { return emptyStacksToShow(); }

        int selectedGroupIndex = dimPocketData.selectedColoredGroup();
        List<Integer> selectedColoredGroup = dimPocket.get(ModDataComponentTypes.COLOR_INVENTORIES.get(DyeColor.byId(selectedGroupIndex)));

        if (selectedColoredGroup == null || selectedColoredGroup.isEmpty()) { return emptyStacksToShow(); }

        boolean safeMode = dimPocketData.safeMode();
        List<Integer> selectedItemInEachGroup = dimPocketData.selectedItemInEachGroup();
        int selectedDepositIndex = selectedColoredGroup.get(selectedItemInEachGroup.get(selectedGroupIndex));

        int groupSize = selectedColoredGroup.size();
        int depositSize = deposit.getContainerSize();

        List<ItemStack> orderedNonEmptyStacks = new ArrayList<>();
        List<Integer> counts = new ArrayList<>();

        int nonEmptyIndex = 0; // El deposito no esta vacio asi que alguno va a haber
        int nonEmptySelectedItem = -1;
        // Si queda en -1, el slot seleccionado actualmente esta vacio o fuera de rango

        ItemStack exampleSelectedItemStack = ItemStack.EMPTY;
        if (selectedDepositIndex < depositSize) {
            exampleSelectedItemStack = getStackFromInventory(deposit, selectedDepositIndex);
        }

        for (int i = 0 ; i < groupSize ; i++) {
            int depositIndex = selectedColoredGroup.get(i);
            if (depositIndex >= depositSize) { break; } // Testear si puedo asumir que esta ordenado

            ItemStack stack = getStackFromInventory(deposit, depositIndex);
            if (stack.isEmpty()) { continue; }

            boolean notMerged = true;
            for (int j = 0 ; j < orderedNonEmptyStacks.size() ; j++) {
                if (canMergeItems(orderedNonEmptyStacks.get(j), stack)) {
                    notMerged = false;

                    // Si son del tipo que estoy seleccionando, quiero que los muestre
                    // Aunque sean slots de 1, van a sumar al total.
                    if (!DimensionalPocketItem.notAllowedToUse(safeMode, stack) ||
                            canMergeItems(exampleSelectedItemStack, stack)) {
                        counts.set(j, counts.get(j) + stack.getCount());
                    }
                }
            }
            if (notMerged) {
                boolean isOfSelectedType = canMergeItems(stack, exampleSelectedItemStack);

                if (!DimensionalPocketItem.notAllowedToUse(safeMode, stack) || isOfSelectedType) {
                    // Si es del tipo que estoy seleccionando, lo quiero aunque este en safe y con cantidad 1
                    if (isOfSelectedType) {
                        nonEmptySelectedItem = orderedNonEmptyStacks.size();
                    }
                    orderedNonEmptyStacks.add(stack);
                    counts.add(stack.getCount());
                }
            }
        }

        if (orderedNonEmptyStacks.isEmpty()) { return emptyStacksToShow();}

        int mainIndex = nonEmptySelectedItem != -1 ? nonEmptySelectedItem : nonEmptyIndex;

        // MAIN
        ItemStack mainStack = orderedNonEmptyStacks.get(mainIndex);
        int mainStackCount = counts.get(mainIndex);

        // SAME GROUP
        ItemStack prevStack = orderedNonEmptyStacks.get(Math.floorMod(mainIndex-1, orderedNonEmptyStacks.size()));
        ItemStack nextStack = orderedNonEmptyStacks.get(Math.floorMod(mainIndex+1, orderedNonEmptyStacks.size()));

        // DIFFERENT GROUPS
        int nextGroupIndex = findNextNonEmptyGroup(dimPocket, deposit, selectedGroupIndex, 1, safeMode);
        int prevGroupIndex = findNextNonEmptyGroup(dimPocket, deposit, selectedGroupIndex, -1, safeMode);

        List<Integer> prevGroup = dimPocket.get(ModDataComponentTypes.COLOR_INVENTORIES.get(DyeColor.byId(prevGroupIndex)));
        List<Integer> nextGroup = dimPocket.get(ModDataComponentTypes.COLOR_INVENTORIES.get(DyeColor.byId(nextGroupIndex)));

        ItemStack prevGroupStack = prevGroup != null
                ? getStackFromInventory(deposit, prevGroup.get(selectedItemInEachGroup.get(prevGroupIndex)))
                : ItemStack.EMPTY;

        ItemStack nextGroupStack = nextGroup != null
                ? getStackFromInventory(deposit, nextGroup.get(selectedItemInEachGroup.get(nextGroupIndex)))
                : ItemStack.EMPTY;

        if (nonEmptySelectedItem == -1) {
            ItemStack leftItemStack = orderedNonEmptyStacks.getFirst();
            ItemStack rightItemStack = orderedNonEmptyStacks.size() > 1 ? orderedNonEmptyStacks.getLast() : leftItemStack;

            mainStack = ItemStack.EMPTY;
            mainStackCount = 0;
            prevStack = leftItemStack;
            nextStack = rightItemStack;
        }

        return new StacksToShow(mainStack, mainStackCount,
                prevStack, nextStack,
                prevGroupStack, prevGroupIndex, nextGroupStack, nextGroupIndex);
    }

    private record StacksToShow(ItemStack mainStack, Integer mainStackCount,
                                ItemStack sameGroupPrevStack, ItemStack sameGroupNextStack,
                                ItemStack prevGroupStack, Integer prevGroupIndex,
                                ItemStack nextGroupStack, Integer nextGroupIndex) {}

    public record DimensionalPocketData(BlockPos depositPos,
                                        ServerLevel targetLevel,
                                        List<Integer> selectedItemInEachGroup,
                                        Integer selectedColoredGroup,
                                        boolean safeMode) {}

    private static DimensionalPocketData checkValidDimensionalPocket(ItemStack dimensionalPocket, MinecraftServer server){
        if (!dimensionalPocket.is(ModItems.DIMENSIONAL_POCKET)) { return null; }

        BlockPos depositPos = dimensionalPocket.get(ModDataComponentTypes.LINKED_CHEST);
        Identifier dimension = dimensionalPocket.get(ModDataComponentTypes.SERVERWORLD);
        List<Integer> selectedItemInEachGroup = dimensionalPocket.get(ModDataComponentTypes.SELECTED_ITEM_IN_EACH_COLORED_GROUP);
        Integer selectedColoredGroup = dimensionalPocket.get(ModDataComponentTypes.SELECTED_COLORED_GROUP);
        Boolean safeMode = dimensionalPocket.getOrDefault(ModDataComponentTypes.SAFE_MODE, false);

        if (depositPos == null || dimension == null || selectedItemInEachGroup == null || selectedColoredGroup == null) {;
            return null;
        }

        ServerLevel targetLevel = server.getLevel(ResourceKey.create(Registries.DIMENSION, dimension));
        if (targetLevel == null || !targetLevel.isLoaded(depositPos)) { return null; }

        return new DimensionalPocketData(depositPos, targetLevel, selectedItemInEachGroup, selectedColoredGroup, safeMode);
    }
}