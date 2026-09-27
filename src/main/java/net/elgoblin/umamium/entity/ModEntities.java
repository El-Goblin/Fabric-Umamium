package net.elgoblin.umamium.entity;

import net.elgoblin.umamium.Umamium;
import net.elgoblin.umamium.entity.custom.ChaosOrbEntity;
import net.elgoblin.umamium.entity.custom.PokeballEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities {

    public static final EntityType<ChaosOrbEntity> CHAOS_ORB = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "chaos_orb"),
            EntityType.Builder.<ChaosOrbEntity>of(ChaosOrbEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "chaos_orb")))
    );
    public static final EntityType<PokeballEntity> POKEBALL = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "pokeball"),
            EntityType.Builder.<PokeballEntity>of(PokeballEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "pokeball")))
    );

    public static void registerModEntities() {
        Umamium.LOGGER.info("Registering Mod Entities for " + Umamium.MOD_ID);
    }
}
