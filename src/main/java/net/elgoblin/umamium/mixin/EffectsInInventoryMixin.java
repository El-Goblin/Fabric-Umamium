package net.elgoblin.umamium.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.elgoblin.umamium.UmamiumClient;
import net.elgoblin.umamium.enchantment.ModEnchantments;
import net.elgoblin.umamium.tags.ModTags;
import net.elgoblin.umamium.util.LegendaryItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EffectsInInventory;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.List;

@Mixin(EffectsInInventory.class)
public abstract class EffectsInInventoryMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    @Final
    private AbstractContainerScreen<?> screen;

    @Shadow
    abstract int extractBackground(
            GuiGraphicsExtractor graphics,
            Font font,
            Component effectName,
            Component duration,
            int x0,
            int y0,
            boolean isAmbient,
            int maxTextureWidth
    );

    @Shadow
    abstract void extractText(
            GuiGraphicsExtractor graphics,
            Component effectText,
            Component duration,
            Font font,
            int x0,
            int y0,
            int textureWidth,
            int yStep,
            int mouseX,
            int mouseY
    );

    @Inject(
            at = @At("TAIL"),
            method = "extractEffects"
    )
    private void renderLaLechonaEffectsInInventory(
            GuiGraphicsExtractor graphics, Collection<MobEffectInstance> activeEffects, int x0, int yStep, int mouseX, int mouseY, int maxWidth, CallbackInfo ci,
            @Local(name = "y0") int y0) {

        LocalPlayer player = this.minecraft.player;
        if (player == null) { return; }

        List<Identifier> spritePaths = UmamiumClient.getAttachmentSpritePaths(player);
        List<Component> effectNames = UmamiumClient.getAttachmentNames(player);

        x0 = this.screen.width - x0;
        //int y0 = this.screen.topPos;
        y0 -= yStep * activeEffects.size();
        Font font = this.screen.getFont();

        for (int i = 0 ; i < spritePaths.size() ; i++) {
            Identifier sprite = spritePaths.get(i);
            Component effectName = effectNames.get(i);

            Component duration = Component.translatable("effect.duration.infinite");
            int nameWidth = 32 + font.width(effectName) + 7;
            int durationWidth = 32 + font.width(duration) + 7;
            int textureWidth = Math.min(maxWidth, Math.max(nameWidth, durationWidth));

            this.extractBackground(graphics, font, effectName, duration, x0 - textureWidth, y0, false, maxWidth);
            this.extractText(graphics, effectName, duration, font, x0 - textureWidth, y0, textureWidth, yStep, mouseX, mouseY);
            graphics.blit(RenderPipelines.GUI_TEXTURED, sprite, x0 - textureWidth + 7, y0 + 7, 0, 0, 18, 18, 18, 18);
            y0 += yStep;
        }
    }

    @Redirect(
            method = "extractRenderState",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Collection;isEmpty()Z",
                    ordinal = 0
            )
    )
    private boolean alwaysRenderEffects(Collection<MobEffectInstance> activeEffects) {
        return false;
    }
}