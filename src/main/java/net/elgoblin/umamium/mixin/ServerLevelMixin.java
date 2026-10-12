package net.elgoblin.umamium.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.elgoblin.umamium.component.ModAttachmentTypes;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.SleepStatus;
import net.minecraft.world.clock.ClockTimeMarker;
import net.minecraft.world.clock.ClockTimeMarkers;
import net.minecraft.world.clock.ServerClockManager;
import net.minecraft.world.clock.WorldClock;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {

    @Shadow
    @Final
    private SleepStatus sleepStatus;

    @WrapOperation(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/clock/ServerClockManager;moveToTimeMarker(Lnet/minecraft/core/Holder;Lnet/minecraft/resources/ResourceKey;)Z"
            )
    )
    private boolean moveToNightIfNightOwlsSleeping(ServerClockManager instance, Holder<WorldClock> clock, ResourceKey<ClockTimeMarker> timeMarkerId, Operation<Boolean> original) {

        ServerLevel level = (ServerLevel) (Object) this;
        List<ServerPlayer> sleepingPlayers = level.players().stream().filter(LivingEntity::isSleeping).toList();
        List<ServerPlayer> allNightOwls = sleepingPlayers.stream().filter(serverPlayer -> serverPlayer.hasAttached(ModAttachmentTypes.NIGHT_OWL)).toList();

        if (sleepingPlayers.size() == allNightOwls.size()) {
            return instance.moveToTimeMarker(clock, ClockTimeMarkers.NIGHT);
        }
        return original.call(instance, clock, timeMarkerId);
    }

    @WrapOperation(
            method = "announceSleepStatus",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/chat/Component;translatable(Ljava/lang/String;)Lnet/minecraft/network/chat/MutableComponent;")
    )
    private MutableComponent sendSleepingThroughTheDayMessage(String key, Operation<MutableComponent> original) {
        ServerLevel level = (ServerLevel) (Object) this;
        List<ServerPlayer> sleepingPlayers = level.players().stream().filter(Player::isSleeping).toList();
        if (sleepingPlayers.getFirst().hasAttached(ModAttachmentTypes.NIGHT_OWL)) {
            return Component.translatable("umamium.sleep.skipping_day");
        }
        return null;
    }
}
