package net.elgoblin.umamium.client;

import net.elgoblin.umamium.item.ModItems;
import net.elgoblin.umamium.networking.ManualBreathePayload;
import net.elgoblin.umamium.networking.SwitchEnchantmentToggleSafeModePayload;
import net.elgoblin.umamium.networking.dimensionalpocket.DimensionalPocketScrollBetweenGroupsPayload;
import net.elgoblin.umamium.networking.dimensionalpocket.DimensionalPocketScrollInsideGroupPayload;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;

public class ClientEvents {

    public static final Minecraft minecraft = Minecraft.getInstance();
    private static int tickCounter = 0;

    public static void registerClientEvents() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            tickCounter++;
            if (tickCounter < 0) {tickCounter = 0;}

            while (ModKeybinds.SWITCH_ENCHANTMENTS_TOGGLE_SAFE_MODE.consumeClick()) {
                ClientPlayNetworking.send(new SwitchEnchantmentToggleSafeModePayload());
            }

            if (ModKeybinds.MANUAL_BREATHE.isDown()) {
                ClientPlayNetworking.send(new ManualBreathePayload());
            }

            if (client.player != null && tickCounter % 2 == 0) {
                ItemStack mainHoldedStack = client.player.getMainHandItem();
                ItemStack offHoldedStack = client.player.getOffhandItem();
                ItemStack activeItem = ItemStack.EMPTY;

                if (mainHoldedStack.is(ModItems.DIMENSIONAL_POCKET)) {
                    activeItem = mainHoldedStack;
                } else if (offHoldedStack.is(ModItems.DIMENSIONAL_POCKET)) {
                    activeItem = offHoldedStack;
                }

                if (!activeItem.isEmpty()) {
                    DimensionalPocketCache.requestUpdate(activeItem);
                }
            }
        });
    }

    public static boolean onScroll(double delta) {

        LocalPlayer player = minecraft.player;
        if (player != null) {

            ItemStack mainHand = player.getMainHandItem();
            ItemStack offHand = player.getOffhandItem();

            boolean isHoldingInfiniteItem = mainHand.is(ModItems.DIMENSIONAL_POCKET) || offHand.is(ModItems.DIMENSIONAL_POCKET);
            boolean tabPressed = ModKeybinds.SCROLL_INSIDE_GROUP.isDown();
            boolean gravePressed = ModKeybinds.SCROLL_BETWEEN_GROUPS.isDown();

            if (isHoldingInfiniteItem && delta != 0) {
                int scroll = delta > 0 ? 1 : -1;

                if (tabPressed) {
                    ClientPlayNetworking.send(
                            new DimensionalPocketScrollInsideGroupPayload(
                                    scroll,
                                    DimensionalPocketCache.sameGroupPrevStack,
                                    DimensionalPocketCache.sameGroupNextStack));
                    return true;
                }
                else if (gravePressed) {
                    ClientPlayNetworking.send(new DimensionalPocketScrollBetweenGroupsPayload(scroll));
                    return true;
                }
            }
        }
        return false;
    }
}
