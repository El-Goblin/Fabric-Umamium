package net.elgoblin.umamium.mixin;

import net.elgoblin.umamium.enchantment.ModEnchantments;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BrushableBlockEntity.class)
public abstract class BrushableBlockEntityMixin {

    @ModifyConstant(
            method = "brush",
            constant = @Constant(intValue = 10)
    )
    private int umamium$modifyRequiredBrushes(int original,
                                              long gameTime,
                                              ServerLevel level,
                                              LivingEntity user,
                                              Direction direction,
                                              ItemStack brush) {
        int efficiency = ModEnchantments.getLevel(brush, Enchantments.EFFICIENCY);

        return (int) Math.max(1, original - efficiency * 1.5);
    }
}