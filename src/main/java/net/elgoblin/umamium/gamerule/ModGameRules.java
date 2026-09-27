package net.elgoblin.umamium.gamerule;

import net.elgoblin.umamium.Umamium;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;

public class ModGameRules {

    public static final GameRuleCategory UMAMIUM_GAMERULE_CATEGORY = GameRuleCategory.register(
            Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "umamium"));

    public static final GameRule<Double> VOID_SPHERE_SIZE_MULTIPLIER = GameRuleBuilder
            .forDouble(1)
            .category(UMAMIUM_GAMERULE_CATEGORY)
            .buildAndRegister(Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "void_sphere_size_multiplier"));

    public static final GameRule<Integer> VOID_SPHERE_FIXED_SIZE = GameRuleBuilder
            .forInteger(-1)
            .category(UMAMIUM_GAMERULE_CATEGORY)
            .buildAndRegister(Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "void_sphere_fixed_size"));

    public static final GameRule<Integer> SNOW_GOLEM_LIFETIME = GameRuleBuilder
            .forInteger(1200)
            .category(UMAMIUM_GAMERULE_CATEGORY)
            .buildAndRegister(Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "snow_golem_lifetime"));

    public static final GameRule<Integer> SKYBLOCK_RADIUS = GameRuleBuilder
            .forInteger(7)
            .category(UMAMIUM_GAMERULE_CATEGORY)
            .buildAndRegister(Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "skyblock_radius"));

    public static final GameRule<Integer> BLOCKS_PER_TICK = GameRuleBuilder
            .forInteger(2500)
            .category(UMAMIUM_GAMERULE_CATEGORY)
            .buildAndRegister(Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "terrain_manager_blocks_per_tick"));

    public static final GameRule<Boolean> ENDERMAN_GRIEFING = GameRuleBuilder
            .forBoolean(true)
            .category(UMAMIUM_GAMERULE_CATEGORY)
            .buildAndRegister(Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "enderman_griefing"));

    public static final GameRule<Boolean> CHAOS_ORB_DEBUG_MESSAGES = GameRuleBuilder
            .forBoolean(false)
            .category(UMAMIUM_GAMERULE_CATEGORY)
            .buildAndRegister(Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "chaos_orb_debug_messages"));

    public static final GameRule<Boolean> CAN_CAPTURE_ENDER_DRAGON = GameRuleBuilder
            .forBoolean(false)
            .category(UMAMIUM_GAMERULE_CATEGORY)
            .buildAndRegister(Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "pokeball_can_capture_ender_dragon"));

    public static final GameRule<Boolean> CAN_CAPTURE_NON_MOB_ENTITIES = GameRuleBuilder
            .forBoolean(true)
            .category(UMAMIUM_GAMERULE_CATEGORY)
            .buildAndRegister(Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "pokeball_can_capture_non_living_entities"));

    public static final GameRule<Boolean> CAN_CAPTURE_PLAYERS = GameRuleBuilder
            .forBoolean(true)
            .category(UMAMIUM_GAMERULE_CATEGORY)
            .buildAndRegister(Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "pokeball_can_capture_players"));




    public static void init() {}
}
