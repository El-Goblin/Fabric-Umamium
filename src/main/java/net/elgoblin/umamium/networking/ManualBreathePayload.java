package net.elgoblin.umamium.networking;

import net.elgoblin.umamium.Umamium;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record ManualBreathePayload() implements CustomPacketPayload {

    public static final Type<ManualBreathePayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "manual_breathe_payload"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ManualBreathePayload> CODEC =
            StreamCodec.unit(new ManualBreathePayload());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}