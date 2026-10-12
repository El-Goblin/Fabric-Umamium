package net.elgoblin.umamium.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.elgoblin.umamium.enchantment.ModEnchantments;
import net.elgoblin.umamium.item.ModItems;
import net.elgoblin.umamium.tags.ModTags;
import net.elgoblin.umamium.util.LegendaryItemUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(Entity.class)
public abstract class EntityMixin implements LegendaryItemUtils.KillerToolSaver{

    private ItemStack killerTool = ItemStack.EMPTY;


    @Override
    public void setKillerTool(ItemStack stack) {
        this.killerTool = stack;
    }

    @Override
    public ItemStack getKillerTool() {
        return this.killerTool;
    }

    @Inject(
            at = @At("HEAD"),
            method = "spawnAtLocation(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/entity/item/ItemEntity;",
            cancellable = true)
    private void carryDropsToLinkedChest(
            ServerLevel level, ItemStack itemStack, Vec3 offset, CallbackInfoReturnable<ItemEntity> cir) {

        if (level.isClientSide()) { return; }

        ItemStack tool = this.getKillerTool();

        if (!tool.is(ModTags.Items.LEGENDARY_TOOLS) || !(ModEnchantments.getLevel(tool, ModEnchantments.LINKER) > 0)) {
            return;
        }

        List<ItemStack> stackToCarry = new ArrayList<>();
        stackToCarry.add(itemStack);

        List<ItemStack> remainderDrops = LegendaryItemUtils.carryDropsToLinkedContainerAndGetRemainder(level, tool, stackToCarry);
        if (remainderDrops.isEmpty()) {
            cir.setReturnValue(null);
        }
        else {
            int remainderCount = remainderDrops.getFirst().getCount();
            if (remainderCount < itemStack.getCount()) {
                itemStack.setCount(remainderCount);
            }
        }
    }

    @Inject(
            method = "isSteppingCarefully",
            at = @At(
                    value = "TAIL"
            ),
            cancellable = true)
    private void isUsingLeatherBoots(CallbackInfoReturnable<Boolean> cir) {
        Entity entity = (Entity) (Object) this;

        if (entity instanceof Player player && player.getItemBySlot(EquipmentSlot.FEET).is(Items.LEATHER_BOOTS)) {
            cir.setReturnValue(true);
        }
    }
}
