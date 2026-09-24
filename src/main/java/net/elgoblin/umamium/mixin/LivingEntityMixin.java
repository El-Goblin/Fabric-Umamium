package net.elgoblin.umamium.mixin;

import net.elgoblin.umamium.component.ModAttachmentTypes;
import net.elgoblin.umamium.effect.ModEffects;
import net.elgoblin.umamium.util.LegendaryItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow
    public abstract boolean hasEffect(Holder<MobEffect> effect);

    @Shadow
    protected abstract int decreaseAirSupply(int air);

    @Shadow
    protected abstract int increaseAirSupply(int air);

    @Shadow
    protected abstract boolean shouldTakeDrowningDamage();

    @Inject(
            method = "dropAllDeathLoot",
            at = @At("HEAD")
    )
    private void captureTool(ServerLevel level, DamageSource source, CallbackInfo ci) {
        if (source.getEntity() instanceof LivingEntity killer) {
            ((LegendaryItemUtils.KillerToolSaver) this).setKillerTool(killer.getMainHandItem());
        }
    }

    @Inject(
            method = "dropAllDeathLoot",
            at = @At("TAIL")
    )
    private void clearTool(ServerLevel level, DamageSource source, CallbackInfo ci) {
        ((LegendaryItemUtils.KillerToolSaver) this).setKillerTool(ItemStack.EMPTY);
    }

    @Inject(
            method = "isSensitiveToWater",
            at = @At("HEAD"),
            cancellable = true)
    private void sensitiveIfWaterWeakness(CallbackInfoReturnable<Boolean> cir) {
        if (this.hasEffect(ModEffects.WATER_WEAKNESS)) {cir.setReturnValue(true);}
    }

    @Inject(
            method = "increaseAirSupply",
            at = @At("TAIL"),
            cancellable = true)
    private void doNotIncreaseAirIfManualBreathing(int currentSupply, CallbackInfoReturnable<Integer> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (self instanceof ServerPlayer player && player.hasAttached(ModAttachmentTypes.MANUAL_BREATHING)) {
            cir.setReturnValue(currentSupply);
        }
    }

    @Inject(
            method = "baseTick",
            at = @At("TAIL")
    )
    private void decreaseAirSupplyIfManualBreathing(CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (!(self.level() instanceof ServerLevel level)) { return; }
        if (!(self instanceof ServerPlayer player)) { return; }

        if (player.hasAttached(ModAttachmentTypes.MANUAL_BREATHING)) {
            // Esta en aire
            boolean isInWater = player.isEyeInFluid(FluidTags.WATER);
            boolean isInAirBubble = level.getBlockState(BlockPos.containing(player.getX(), player.getEyeY(), player.getZ())).is(Blocks.BUBBLE_COLUMN);
            boolean hasWaterBreathing = MobEffectUtil.hasWaterBreathing(player);

            if (!(isInWater && !isInAirBubble) || hasWaterBreathing) {
                boolean canDrown = !player.getAbilities().invulnerable;
                if (canDrown) {
                    player.setAirSupply(decreaseAirSupply(player.getAirSupply()));
                    if (shouldTakeDrowningDamage()) {
                        player.setAirSupply(0);
                        level.broadcastEntityEvent(player, (byte) 67);
                        player.hurtServer(level, player.damageSources().drown(), 2.0F);
                    }
                }

                if (player.isPassenger() && player.getVehicle() != null && player.getVehicle().dismountsUnderwater()) {
                    player.stopRiding();
                }
                if (player.getAirSupply() >= 320) {
                    player.hurtServer(level, player.damageSources().drown(), 2.0F);
                    player.setAirSupply(300);
                }
            }
            else {
                if (MobEffectUtil.hasWaterBreathing(player)) {
                    boolean canDrown = !player.getAbilities().invulnerable;
                    if (canDrown) {
                        player.setAirSupply(decreaseAirSupply(player.getAirSupply()));
                        if (shouldTakeDrowningDamage()) {
                            player.setAirSupply(0);
                            level.broadcastEntityEvent(player, (byte) 67);
                            player.hurtServer(level, player.damageSources().drown(), 2.0F);
                        }
                    }

                    if (player.isPassenger() && player.getVehicle() != null && player.getVehicle().dismountsUnderwater()) {
                        player.stopRiding();
                    }
                    if (player.getAirSupply() >= 320) {
                        player.hurtServer(level, player.damageSources().drown(), 2.0F);
                        player.setAirSupply(300);
                    }
                }
            }
        }
    }
}