package net.elgoblin.umamium.effect;

import com.mojang.serialization.MapCodec;
import net.elgoblin.umamium.component.ModAttachmentTypes;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;

public record LaLechonaConsumeEffect() implements ConsumeEffect {
    public static final LaLechonaConsumeEffect INSTANCE = new LaLechonaConsumeEffect();
    public static final MapCodec<LaLechonaConsumeEffect> CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, LaLechonaConsumeEffect> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    public static final ConsumeEffect.Type<LaLechonaConsumeEffect> TYPE = new ConsumeEffect.Type<>(CODEC, STREAM_CODEC);

    @Override
    public ConsumeEffect.Type<LaLechonaConsumeEffect> getType() {
        return TYPE;
    }

    @Override
    public boolean apply(Level level, ItemStack stack, LivingEntity entity) {
        applyAttributeChange(Attributes.SCALE, 1, entity);
        applyAttributeChange(Attributes.MAX_HEALTH, 20, entity);
        applyAttributeChange(Attributes.STEP_HEIGHT, 0.6, entity);
        applyAttributeChange(Attributes.SAFE_FALL_DISTANCE, 3, entity);
        applyAttributeChange(Attributes.MOVEMENT_SPEED, 0.1, entity);
        applyAttributeChange(Attributes.JUMP_STRENGTH, 0.42, entity);
        applyAttributeChange(Attributes.BLOCK_INTERACTION_RANGE, 4.5, entity);
        applyAttributeChange(Attributes.ENTITY_INTERACTION_RANGE, 3, entity);
        applyAttributeChange(Attributes.MINING_EFFICIENCY, 1, entity);
        entity.removeAttached(ModAttachmentTypes.NIGHT_OWL);
        entity.removeAttached(ModAttachmentTypes.MISSCLICK);
        return entity.removeAllEffects();
    }

    private void applyAttributeChange(Holder<Attribute> attribute, double value, LivingEntity entity) {
        AttributeInstance currentStat = entity.getAttribute(attribute);
        if (currentStat != null) {
            currentStat.setBaseValue(value);
        }
    }
}