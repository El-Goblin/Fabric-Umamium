package net.elgoblin.umamium.item.custom;

import net.elgoblin.umamium.component.ModDataComponentTypes;
import net.elgoblin.umamium.entity.custom.ChaosOrbEntity;
import net.elgoblin.umamium.entity.custom.PokeballEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SnowballItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PokeballItem extends SnowballItem {

    public PokeballItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);

        world.playSound(
                null,
                user.getX(),
                user.getY(),
                user.getZ(),
                SoundEvents.SNOWBALL_THROW,
                SoundSource.NEUTRAL,
                0.5F,
                0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F)
        );
        if (!world.isClientSide()) {

            PokeballEntity pokeballEntity = new PokeballEntity(world, user, itemStack);
            pokeballEntity.setItem(itemStack);
            pokeballEntity.shootFromRotation(user, user.getXRot(), user.getYRot(), 0.0F, 1.5F, 1.0F);
            if (itemStack.has(ModDataComponentTypes.CAPTURED_LIVING_ENTITY)) {
                pokeballEntity.setCapturedLivingEntity(itemStack.get(ModDataComponentTypes.CAPTURED_LIVING_ENTITY));
            }
            world.addFreshEntity(pokeballEntity);
            user.awardStat(Stats.ITEM_USED.get(this));
            itemStack.consume(1, user);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        PokeballEntity.CapturedLivingEntity capturedEntity = itemStack.get(ModDataComponentTypes.CAPTURED_LIVING_ENTITY);

        if (capturedEntity != null) {
            builder.accept(Component.literal(capturedEntity.name())
                    .withStyle(ChatFormatting.GOLD));
        }
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
    }
}
