package net.elgoblin.umamium.item.custom;

import net.elgoblin.umamium.util.LegendaryItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.function.Consumer;

public class FiendbladLongswordItem extends LongswordItem {


    public FiendbladLongswordItem(Properties properties, ToolMaterial material, float attackDamageBaseline, float attackSpeedBaseline, float entityInteractionRange, float sweepingDamage) {
        super(properties, material, attackDamageBaseline, attackSpeedBaseline, entityInteractionRange, sweepingDamage);
    }

    //    @Override
//    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
//        LegendaryItemUtils.appendTooltip(itemStack, builder);
//        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
//    }
}
