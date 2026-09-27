package net.elgoblin.umamium.entity.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.elgoblin.umamium.Umamium;
import net.elgoblin.umamium.component.ModDataComponentTypes;
import net.elgoblin.umamium.entity.ModEntities;
import net.elgoblin.umamium.gamerule.ModGameRules;
import net.elgoblin.umamium.item.ModItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.boss.enderdragon.EnderDragonPart;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jspecify.annotations.Nullable;

import java.util.*;

public class PokeballEntity extends ThrowableItemProjectile {

    private PokeballEntity.CapturedLivingEntity capturedLivingEntity;

    private static final List<String> IGNORED_CAPTURED_ENTITY_TAGS = Arrays.asList(

            // Entity
            "Pos",
            "Motion",
            "Rotation",
            "fall_distance",
            "Fire",
            "Air",
            "OnGround",
            "PortalCooldown",
            "Passengers",
            "TicksFrozen",

            // LivingEntity
            "HurtTime",
            "DeathTime",
            "current_impulse_context_reset_grace_time",
            "current_explosion_impact_pos",
            "FallFlying",
            "sleeping_pos",
            "leash",

            // ANIMAL
            "InLove",

            // CAMEL

            "LastPoseTick",

            // ABSTRACT HORSE
            "EatingHaystack",

            // BEE
            "CannotEnterHiveTicks",
            "TicksSincePollination",
            "CropsGrownSincePollination",

            // FOX

            "Crouching",

            // GLOW SQUID

            "DarkTicksRemaining",

            // ABSTRACT CUBE MOB

            "wasOnGround",

            // SKELETON

            "StrayConversionTime",

            // ZOMBIE

            "DrownedConversionTime",
            "InWaterTime",

            // PHANTOM

            "anchor_pos"
    );

    public PokeballEntity(final EntityType<? extends PokeballEntity> type, final Level level) {
        super(type, level);
    }

    public PokeballEntity(final Level level, final LivingEntity mob, final ItemStack itemStack) {
        super(ModEntities.CHAOS_ORB, mob, level, itemStack);
    }

    public PokeballEntity(final Level level, final double x, final double y, final double z, final ItemStack itemStack) {
        super(ModEntities.CHAOS_ORB, x, y, z, level, itemStack);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.POKEBALL;
    }

    private ParticleOptions getParticle() {
        ItemStack item = this.getItem();
        return item.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemParticleOption(ParticleTypes.ITEM, ItemStackTemplate.fromNonEmptyStack(item));
    }

    @Override
    public void handleEntityEvent(final byte id) {
    }

    @Override
    protected void onHitEntity(final EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        if (this.level() instanceof ServerLevel serverLevel) {
            if (capturedLivingEntity == null) {
                if (!serverLevel.getGameRules().get(ModGameRules.CAN_CAPTURE_NON_MOB_ENTITIES) && !(hitResult.getEntity() instanceof LivingEntity)) {
                    ItemStack pokeball = this.getItem();
                    ItemEntity pokeballItem = this.spawnAtLocation(serverLevel, pokeball, 0);
                    if (pokeballItem != null) {
                        pokeballItem.setDeltaMovement(0, 0.5, 0);
                    }
                    return;
                }
                if (!serverLevel.getGameRules().get(ModGameRules.CAN_CAPTURE_PLAYERS) && hitResult.getEntity() instanceof Player) {
                    ItemStack pokeball = this.getItem();
                    ItemEntity pokeballItem = this.spawnAtLocation(serverLevel, pokeball, 0);
                    if (pokeballItem != null) {
                        pokeballItem.setDeltaMovement(0, 0.5, 0);
                    }
                    return;
                }
                ItemStack filledPokeball = this.getItem();
                filledPokeball.set(ModDataComponentTypes.CAPTURED_LIVING_ENTITY, PokeballEntity.CapturedLivingEntity.of(hitResult.getEntity()));
                if (hitResult.getEntity() instanceof EnderDragonPart part && serverLevel.getGameRules().get(ModGameRules.CAN_CAPTURE_ENDER_DRAGON)) {
                    filledPokeball.set(ModDataComponentTypes.CAPTURED_LIVING_ENTITY, PokeballEntity.CapturedLivingEntity.of(part.parentMob));
                    part.parentMob.discard();
                }
                ItemEntity pokeballItem = this.spawnAtLocation(serverLevel, filledPokeball, 0);
                if (pokeballItem != null) {
                    pokeballItem.setDeltaMovement(0, 0.5, 0);
                }
                hitResult.getEntity().discard();
            }
            else {
                Entity entityToRelease = capturedLivingEntity.createEntity(this.level());
                if (entityToRelease != null) {
                    entityToRelease.absSnapTo(this.getX(), this.getY(), this.getZ(), 0, 0);
                    this.level().addFreshEntity(entityToRelease);
                }
                ItemStack pokeball = this.getItem();
                pokeball.remove(ModDataComponentTypes.CAPTURED_LIVING_ENTITY);
                ItemEntity pokeballItem = this.spawnAtLocation(serverLevel, pokeball, 0);
                if (pokeballItem != null) {
                    pokeballItem.setDeltaMovement(0, 0.5, 0);
                }
            }
        }
    }

    @Override
    protected void onHit(final HitResult hitResult) {
        super.onHit(hitResult);
        if (!(hitResult instanceof EntityHitResult)) {
            if (this.capturedLivingEntity != null) {
                Entity entityToRelease = capturedLivingEntity.createEntity(this.level());
                if (entityToRelease != null) {
                    entityToRelease.absSnapTo(this.getX(), this.getY(), this.getZ(), 0, 0);
                    this.level().addFreshEntity(entityToRelease);
                }
                if (this.level() instanceof ServerLevel serverLevel) {
                    ItemStack pokeball = this.getItem();
                    pokeball.remove(ModDataComponentTypes.CAPTURED_LIVING_ENTITY);
                    ItemEntity pokeballItem = this.spawnAtLocation(serverLevel, pokeball, 0);
                    if (pokeballItem != null) {
                        pokeballItem.setDeltaMovement(0, 0.5, 0);
                    }
                }
            }
            else {
                if (this.level() instanceof ServerLevel serverLevel) {
                    ItemEntity pokeballItem = this.spawnAtLocation(serverLevel, this.getItem(), 0);
                    if (pokeballItem != null) {
                        pokeballItem.setDeltaMovement(0, 0.5, 0);
                    }
                }
            }
        }


        this.discard();
    }

    public void setCapturedLivingEntity(CapturedLivingEntity capturedLivingEntity) {
        this.capturedLivingEntity = capturedLivingEntity;
    }

    // CAPTURED MONSTER

    public record CapturedLivingEntity(TypedEntityData<EntityType<?>> entityData, String name) {
        public static final Codec<CapturedLivingEntity> CODEC = RecordCodecBuilder.create(
                i -> i.group(
                                TypedEntityData.codec(EntityType.CODEC).fieldOf("entity_data").forGetter(CapturedLivingEntity::entityData),
                                Codec.STRING.fieldOf("name").forGetter(PokeballEntity.CapturedLivingEntity::name)
                        )
                        .apply(i, CapturedLivingEntity::new)
        );
        public static final Codec<List<CapturedLivingEntity>> LIST_CODEC = CODEC.listOf();
        public static final StreamCodec<RegistryFriendlyByteBuf, CapturedLivingEntity> STREAM_CODEC = StreamCodec.composite(
                TypedEntityData.streamCodec(EntityType.STREAM_CODEC),
                CapturedLivingEntity::entityData,
                ByteBufCodecs.STRING_UTF8,
                PokeballEntity.CapturedLivingEntity::name,
                CapturedLivingEntity::new
        );

        public static CapturedLivingEntity of(final Entity entity) {
            try (ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(entity.problemPath(), Umamium.LOGGER)) {
                TagValueOutput output = TagValueOutput.createWithContext(reporter, entity.registryAccess());
                entity.save(output);
                PokeballEntity.IGNORED_CAPTURED_ENTITY_TAGS.forEach(output::discard);
                CompoundTag entityTag = output.buildResult();
                return new CapturedLivingEntity(TypedEntityData.of(entity.getType(), entityTag), entity.getName().getString());
            }
        }

        public @Nullable Entity createEntity(final Level level) {
            CompoundTag entityTag = this.entityData.copyTagWithoutId();
            PokeballEntity.IGNORED_CAPTURED_ENTITY_TAGS.forEach(entityTag::remove);
            Entity entity = EntityType.loadEntityRecursive(this.entityData.type(), entityTag, level, EntitySpawnReason.LOAD, EntityProcessor.NOP);
            if (entity != null) {
                return entity;
            } else {
                return null;
            }
        }
    }
}
