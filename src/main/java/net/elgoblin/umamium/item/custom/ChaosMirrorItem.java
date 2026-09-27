package net.elgoblin.umamium.item.custom;

import net.elgoblin.umamium.component.ModDataComponentTypes;
import net.elgoblin.umamium.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ChaosMirrorItem extends Item {

    private final RandomSource random = RandomSource.create();

    public ChaosMirrorItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player user, InteractionHand hand) {

        ItemStack itemStack = user.getItemInHand(hand);

        if (!level.isClientSide()) {
            MinecraftServer server = level.getServer();

            if (server != null) {
                Vec3 newCoordinates = new Vec3(
                        random.nextIntBetweenInclusive(-29999980, 29999980),
                        user.getY(),
                        random.nextIntBetweenInclusive(-29999980, 29999980)
                );

                while (level.getBlockState(BlockPos.containing(newCoordinates)).isAir()) {
                    newCoordinates = newCoordinates.add(new Vec3(0,-1,0));
                }
                while (!level.getBlockState(BlockPos.containing(newCoordinates)).isAir()) {
                    newCoordinates = newCoordinates.add(new Vec3(0,1,0));
                }
                newCoordinates = newCoordinates.add(new Vec3(0,1,0));

                List<ServerPlayer> players = server.getPlayerList().getPlayers();

                for (ServerPlayer playerEntity : players) {
                    if (playerEntity == null) {
                        continue;
                    }

                    playerEntity.level();
                    ServerLevel dimension = playerEntity.level().getServer().getLevel(playerEntity.level().dimension());

                    if (dimension != null) {
                        Identifier world_ID = dimension.dimension().identifier();

                        Vec3 currentCoordinates = playerEntity.position();
                        ItemStack memoryMirror = ModItems.MEMORY_MIRROR.getDefaultInstance();
                        memoryMirror.set(ModDataComponentTypes.COORDINATES, currentCoordinates);
                        memoryMirror.set(ModDataComponentTypes.SERVERWORLD, world_ID);

                        TeleportTransition teleportTarget = new TeleportTransition(
                                (ServerLevel) level,
                                newCoordinates,
                                new Vec3(0, 0, 0),
                                playerEntity.getYRot(),
                                playerEntity.getXRot(),
                                TeleportTransition.DO_NOTHING
                        );
                        user.hurtMarked = true;
                        user.fallDistance = 0;
                        if (playerEntity.isPassenger()) {
                            playerEntity.stopRiding();
                        }
                        playerEntity.teleport(teleportTarget);
                        playerEntity.getCooldowns().addCooldown(memoryMirror, 1200);
                        playerEntity.getInventory().placeItemBackInInventory(memoryMirror);

                    }
                }
            }
            itemStack.consume(1, user);
        }
        return InteractionResult.PASS;
    }
}