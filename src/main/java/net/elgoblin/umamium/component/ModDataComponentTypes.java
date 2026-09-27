package net.elgoblin.umamium.component;

import com.mojang.serialization.Codec;
import net.elgoblin.umamium.Umamium;
import net.elgoblin.umamium.effect.LaLechonaConsumeEffect;
import net.elgoblin.umamium.entity.custom.PokeballEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.phys.Vec3;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

public class ModDataComponentTypes {

    public static final DataComponentType<ItemEnchantments> OTHER_ENCHANTMENTS = register(
            "other_enchantments", builder -> builder.persistent(ItemEnchantments.CODEC).networkSynchronized(ItemEnchantments.STREAM_CODEC).cacheEncoding()
    );
    public static final DataComponentType<BlockPos> LINKED_CHEST = register("linked_chest", builder -> builder.persistent(BlockPos.CODEC));
    public static final DataComponentType<BlockPos> OTHER_LINKED_CHEST = register("other_linked_chest", builder -> builder.persistent(BlockPos.CODEC));
    public static final DataComponentType<Vec3> COORDINATES = register("coordinates", builder -> builder.persistent(Vec3.CODEC));
    public static final DataComponentType<Identifier> SERVERWORLD = register("server_id", builder -> builder.persistent(Identifier.CODEC));
    public static final DataComponentType<Identifier> OTHER_SERVERWORLD = register("other_server_id", builder -> builder.persistent(Identifier.CODEC));
    public static final DataComponentType<String> CHEST_NAME = register("chest_name", builder -> builder.persistent(Codec.STRING));

    public static final DataComponentType<Integer> SELECTED_COLORED_GROUP = register("selected_colored_group", builder -> builder.persistent(Codec.INT));
    public static final DataComponentType<List<Integer>> SELECTED_ITEM_IN_EACH_COLORED_GROUP = register("selected_item_in_each_colored_group", builder -> builder.persistent(Codec.INT.listOf()));

    public static final DataComponentType<Boolean> SAFE_MODE = register("safe_mode", builder -> builder.persistent(Codec.BOOL));

    public static final Map<DyeColor, DataComponentType<List<Integer>>> COLOR_INVENTORIES = new EnumMap<>(DyeColor.class);

    public static final DataComponentType<PokeballEntity.CapturedLivingEntity> CAPTURED_LIVING_ENTITY = register("captured_living_entity", builder -> builder.persistent(PokeballEntity.CapturedLivingEntity.CODEC));

    public static void registerComponents() {
        for (DyeColor color : DyeColor.values()) {
            final String name = color.name().toLowerCase() + "_inventory_pointers";

            DataComponentType<List<Integer>> component = register(name, builder -> builder
                    .persistent(Codec.list(Codec.INT))
                    .networkSynchronized(ByteBufCodecs.INT.apply(ByteBufCodecs.list()))
            );

            COLOR_INVENTORIES.put(color, component);
        }
    }

    public static final DataComponentType<ItemStack> CHOSEN_INFINITE_ITEM = register("chosen_infinite_item",
            builder -> builder.persistent(ItemStack.CODEC).networkSynchronized(ItemStack.STREAM_CODEC).cacheEncoding());

    public static final Consumable LA_LECHONA = Consumables.defaultDrink()
            .onConsume(LaLechonaConsumeEffect.INSTANCE)
            .build();

    private static <T> DataComponentType<T> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return Registry.register(
                BuiltInRegistries.DATA_COMPONENT_TYPE,
                Identifier.fromNamespaceAndPath(Umamium.MOD_ID, name),
                builderOperator.apply(DataComponentType.builder()).build()
        );
    }

    public static void registerDataComponentTypes() {
        Umamium.LOGGER.info("Registering Data Components for " + Umamium.MOD_ID);
        registerComponents();
    }
}