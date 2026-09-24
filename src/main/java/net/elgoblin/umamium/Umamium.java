package net.elgoblin.umamium;

import net.elgoblin.umamium.block.ModBlocks;
import net.elgoblin.umamium.client.DimensionalPocketCache;
import net.elgoblin.umamium.component.ModAttachmentTypes;
import net.elgoblin.umamium.component.ModDataComponentTypes;
import net.elgoblin.umamium.creativemodetab.ModCreativeModeTabs;
import net.elgoblin.umamium.effect.BlinkingEffect;
import net.elgoblin.umamium.effect.ModEffects;
import net.elgoblin.umamium.enchantment.ModEnchantmentEffects;
import net.elgoblin.umamium.entity.ModEntities;
import net.elgoblin.umamium.gamerule.ModGameRules;
import net.elgoblin.umamium.item.ModItems;
import net.elgoblin.umamium.networking.ModPayloads;
import net.elgoblin.umamium.networking.ServerPayloadReceivers;
import net.elgoblin.umamium.particle.ModParticles;
import net.elgoblin.umamium.terrain.TerrainJobsManager;
import net.elgoblin.umamium.util.ArrowShootersManager;
import net.elgoblin.umamium.util.ModLootTableModifiers;
import net.elgoblin.umamium.util.SnowGolemLifetimes;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.effect.ServerMobEffectEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.ItemEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.golem.SnowGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Umamium implements ModInitializer {
	public static final String MOD_ID = "umamium";
	public static final RandomSource random = RandomSource.create();
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModCreativeModeTabs.registerModCreativeModeTabs();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModDataComponentTypes.registerDataComponentTypes();
		ModAttachmentTypes.registerAttachmentTypes();
		ModEffects.registerEffects();
		ModEnchantmentEffects.registerEnchantmentEffects();
		ModPayloads.registerPayloads();
		ServerPayloadReceivers.registerServerGlobalReceivers();
		DimensionalPocketCache.init();
		ModParticles.registerParticles();
		ModEntities.registerModEntities();
		ModLootTableModifiers.modifyLootTables();
		TerrainJobsManager.init();
		ModGameRules.init();

		ServerTickEvents.END_SERVER_TICK.register(minecraftServer -> {
			TerrainJobsManager.TERRAIN_MANAGER.tick(minecraftServer);

			if (minecraftServer.getGameRules().get(ModGameRules.SNOW_GOLEM_LIFETIME) > 0) {
				minecraftServer.getAllLevels().forEach(dimension -> SnowGolemLifetimes.get(dimension).tick());
			}
			minecraftServer.getAllLevels().forEach(dimension -> ArrowShootersManager.get(dimension).tick());
		});

		ServerLivingEntityEvents.AFTER_DAMAGE.register(Umamium::applyAfterDamageEffects);
		ServerLivingEntityEvents.ALLOW_DEATH.register(Umamium::applyAllowDeathEvents);
		ServerLivingEntityEvents.AFTER_DEATH.register(Umamium::applyAfterDeathEvents);

		ItemEvents.USE_ON.register((context -> {
			Player player = context.getPlayer();
			if (player == null) { return null; }
			if (!player.hasAttached(ModAttachmentTypes.MISSCLICK)) { return null; }

			ItemStack stack = context.getItemInHand();
			if (!(stack.getItem() instanceof BlockItem blockItem)) { return null; }

			long seed = Double.doubleToLongBits(Math.floor(context.getClickLocation().x * 100000))
							^ Double.doubleToLongBits(Math.floor(context.getClickLocation().y * 100000))
							^ Double.doubleToLongBits(Math.floor(context.getClickLocation().z * 100000))
							^ Double.doubleToLongBits(Math.floor(player.getX() * 100000))
							^ Double.doubleToLongBits(Math.floor(player.getY() * 100000))
							^ Double.doubleToLongBits(Math.floor(player.getZ() * 100000));
			seed ^= seed >>> 32;
			seed *= 0x9E3779B97F4A7C15L;
			seed ^= seed >>> 29;

			List<Direction> directions = new ArrayList<>(List.of(
					Direction.DOWN,
					Direction.UP,
					Direction.EAST,
					Direction.WEST,
					Direction.NORTH,
					Direction.SOUTH
			));

			Collections.shuffle(directions, new Random(seed));

			for (Direction face : directions) {
				if (!context.getLevel().isClientSide()) {
					System.out.println("direction = " + face);
					System.out.println("blockPos = " + context.getClickedPos().relative(context.getClickedFace()));
				}
				BlockPos position = context.getClickedPos().relative(context.getClickedFace());

				if (!context.getLevel().getBlockState(position.relative(face)).canBeReplaced()) {
					continue;
				}

				BlockHitResult newHitResult = new BlockHitResult(
						Vec3.atCenterOf(position),
						face,
						position.relative(face),
						context.isInside()
				);

				BlockPlaceContext placeContext = new BlockPlaceContext(player, context.getHand(), stack, newHitResult);
				InteractionResult result = blockItem.place(placeContext);

				if (result.consumesAction()) {
					return InteractionResult.SUCCESS;
				}
			}
			return null;
		}));
	}

	private static boolean applyAllowDeathEvents(LivingEntity entity, DamageSource source, float v) {
		if (source.getEntity() instanceof Player player) {
			player.heal(v/4);
		}
		return true;
	}

	private static void applyAfterDeathEvents(LivingEntity entity, DamageSource source) {
		if (source.getEntity() instanceof Player player) {
			ItemStack damagingItem = player.getMainHandItem();
			if (damagingItem.is(ModItems.FIENDBLADE_LONGSWORD)) {
				player.getFoodData().eat(2, 0.0F);
			}
		}
	}

	private static void applyAfterDamageEffects(LivingEntity entity, DamageSource source, float baseDamageTaken, float damageTaken, boolean blocked) {
		if (entity.level() instanceof ServerLevel serverLevel) {
			if (entity.hasEffect(ModEffects.FRAGILE) && !blocked) {
                DamageSource newSource = serverLevel.damageSources().generic();

                if (source.typeHolder() != newSource.typeHolder()) {
                    entity.hurtServer(serverLevel, newSource, damageTaken * 2.0F);
                }
            }
			if (entity.hasEffect(ModEffects.COUNTER_BLINK) && !blocked) {
                BlinkingEffect.teleportNearby(entity, serverLevel);
            }
			if (entity.hasEffect(ModEffects.SNOWY_BODYGUARDS) && !blocked) {

				SnowGolem golem = new SnowGolem(EntityTypes.SNOW_GOLEM, serverLevel);
				golem.setPos(entity.position().add(new Vec3(random.nextIntBetweenInclusive(-3, 3), 0, random.nextIntBetweenInclusive(-3, 3))));
				golem.setAggressive(true);
				serverLevel.addFreshEntity(golem);
				SnowGolemLifetimes.get(serverLevel).addEntity(golem);
			}
			if (source.getEntity() instanceof Player player) {
				ItemStack damagingItem = player.getMainHandItem();
				if (damagingItem.is(ModItems.FIENDBLADE_LONGSWORD)) {
					player.heal(damageTaken/4);
				}
			}
		}
	}


	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
