package net.elgoblin.umamium.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(FlintAndSteelItem.class)
public class FlintAndSteelItemMixin extends Item {

    public FlintAndSteelItemMixin(Properties properties) {
        super(properties);
    }

    @Inject(
            method = "useOn",
            at = @At("HEAD"),
            cancellable = true)
    private void turnOnFurnace(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        BlockState state = level.getBlockState(pos);

        if (level.getBlockEntity(pos) instanceof AbstractFurnaceBlockEntity furnace) {
            AbstractFurnaceBlockEntityAccessor accessor = (AbstractFurnaceBlockEntityAccessor) furnace;
            boolean wasLit = accessor.getLitTimeRemaining() > 0;
            accessor.setLitTimeRemaining(accessor.getLitTimeRemaining() + 201);

            level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
            if (player != null) {
                context.getItemInHand().hurtAndBreak(4, player, context.getHand().asEquipmentSlot());
            }

            if (!wasLit) {
                state = state.setValue(AbstractFurnaceBlock.LIT, true);
                level.setBlock(pos, state, 3);
                level.blockEntityChanged(pos);
                if (!state.isAir()) {
                    level.updateNeighbourForOutputSignal(pos, state.getBlock());
                }
            }

            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }
}

