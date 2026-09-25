package net.elgoblin.umamium.component;

import com.mojang.serialization.Codec;
import net.elgoblin.umamium.Umamium;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;

public class ModAttachmentTypes {

    public static final AttachmentType<Boolean> NIGHT_OWL =
            AttachmentRegistry.create(
                    Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "night_owl"),
                    builder -> builder
                            .persistent(Codec.BOOL)
                            .copyOnDeath()
                            .syncWith(
                                    ByteBufCodecs.BOOL,
                                    AttachmentSyncPredicate.targetOnly()
                            )
            );

    public static final AttachmentType<Boolean> MISSCLICK =
            AttachmentRegistry.create(
                    Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "missclick"),
                    builder -> builder
                            .persistent(Codec.BOOL)
                            .copyOnDeath()
                            .syncWith(
                                    ByteBufCodecs.BOOL,
                                    AttachmentSyncPredicate.targetOnly()
                            )
            );

    public static final AttachmentType<Boolean> MANUAL_BREATHING =
            AttachmentRegistry.create(
                    Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "manual_breathing"),
                    builder -> builder
                            .persistent(Codec.BOOL)
                            .copyOnDeath()
                            .syncWith(
                                    ByteBufCodecs.BOOL,
                                    AttachmentSyncPredicate.targetOnly()
                            )
            );

    public static final AttachmentType<Integer> SCALE =
            AttachmentRegistry.create(
                    Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "scale"),
                    builder -> builder
                            .persistent(Codec.INT)
                            .copyOnDeath()
                            .syncWith(
                                    ByteBufCodecs.INT,
                                    AttachmentSyncPredicate.targetOnly()
                            )
            );

    public static void registerAttachmentTypes() {
        Umamium.LOGGER.info("Registering Attachments for " + Umamium.MOD_ID);
    }
}
