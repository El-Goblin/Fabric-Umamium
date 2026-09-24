package net.elgoblin.umamium.networking;

import net.elgoblin.umamium.networking.dimensionalpocket.*;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class ModPayloads {

    public static void registerPayloads() {
        PayloadTypeRegistry.serverboundPlay().register(SwitchEnchantmentToggleSafeModePayload.TYPE, SwitchEnchantmentToggleSafeModePayload.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(ManualBreathePayload.TYPE, ManualBreathePayload.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(DimensionalPocketSelectColorPayload.TYPE, DimensionalPocketSelectColorPayload.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(DimensionalPocketMiddleClickQueryPayload.TYPE, DimensionalPocketMiddleClickQueryPayload.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(DimensionalPocketScrollInsideGroupPayload.TYPE, DimensionalPocketScrollInsideGroupPayload.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(DimensionalPocketScrollBetweenGroupsPayload.TYPE, DimensionalPocketScrollBetweenGroupsPayload.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(DimensionalPocketToggleSlotPayload.TYPE, DimensionalPocketToggleSlotPayload.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(DimensionalPocketDepositContentsQueryPayload.TYPE, DimensionalPocketDepositContentsQueryPayload.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(DimensionalPocketDepositContentsResponsePayload.TYPE, DimensionalPocketDepositContentsResponsePayload.CODEC);
    }
}
