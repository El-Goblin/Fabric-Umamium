package net.elgoblin.umamium.effect;

import net.elgoblin.umamium.entity.ModEntities;
import net.elgoblin.umamium.entity.custom.ChaosOrbEntity;
import net.elgoblin.umamium.item.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.fox.Fox;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class ChaosEffect extends MobEffect {

    private int ticksUntilNextOrb = 20;

    public ChaosEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
        if (!level.isClientSide()) {
            spawnChaosOrb(entity, level);
        }
        return super.applyEffectTick(level, entity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        ticksUntilNextOrb--;
        if (ticksUntilNextOrb <= 0) {
            ticksUntilNextOrb = RandomSource.create().nextIntBetweenInclusive(100, 300);
            return true;
        }
        return false;
    }

    public static void spawnChaosOrb(LivingEntity entity, ServerLevel level) {
        float x = (float) (entity.getX() + RandomSource.create().nextInt(15));
        float z = (float) (entity.getZ() + RandomSource.create().nextInt(15));
        ChaosOrbEntity chaosOrb = new ChaosOrbEntity(level, entity, ModItems.CHAOS_ORB.getDefaultInstance());
        chaosOrb.setPos(x, entity.getY(), z);
        level.addFreshEntity(chaosOrb);
    }
}