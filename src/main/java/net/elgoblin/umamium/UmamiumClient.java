package net.elgoblin.umamium;

import net.elgoblin.umamium.client.*;
import net.elgoblin.umamium.client.models.ModModelLayers;
import net.elgoblin.umamium.client.renderer.ModSpecialModelRenderers;
import net.elgoblin.umamium.component.ModAttachmentTypes;
import net.elgoblin.umamium.component.ModDataComponentTypes;
import net.elgoblin.umamium.entity.ModEntities;
import net.elgoblin.umamium.item.ModItems;
import net.elgoblin.umamium.util.LegendaryItemUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.*;
import java.util.List;

public class UmamiumClient implements ClientModInitializer {

	private static final Identifier NIGHT_OWL = Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "textures/mob_effect/night_owl.png");
	private static final Identifier MISSCLICK = Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "textures/mob_effect/missclick.png");
	private static final Identifier MANUAL_BREATHING = Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "textures/mob_effect/slimey.png");
	private static final Identifier RANGE = Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "textures/mob_effect/range.png");
	private static final Identifier SCALE = Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "textures/mob_effect/scale.png");
	private static final Identifier CUMULATIVE_EXTRA_DAMAGE = Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "textures/mob_effect/slimey.png");

	private static final Identifier EFFECT_BACKGROUND_AMBIENT_SPRITE = Identifier.withDefaultNamespace("hud/effect_background_ambient");
	private static final Identifier EFFECT_BACKGROUND_SPRITE = Identifier.withDefaultNamespace("hud/effect_background");


	@Override
	public void onInitializeClient() {
		ClientEvents.registerClientEvents();
		ModKeybinds.registerModKeybinds();
		DimensionalPocketOverlay.register();
		EntityRendererRegistry.register(ModEntities.CHAOS_ORB, ThrownItemRenderer::new);
		EntityRendererRegistry.register(ModEntities.POKEBALL, ThrownItemRenderer::new);

		ModModelLayers.register();
		ModSpecialModelRenderers.register();

//		ParticleProviderRegistry.getInstance().register(
//				ModParticles.CHAOS_ORB_FRAGILE_PARTICLE,
//				2
//		);

		HudElementRegistry.attachElementAfter(
				VanillaHudElements.HOTBAR,
				Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "dimensional_pocket_render_main_stack"),
				(guiGraphics, deltaTracker) -> {

					Minecraft minecraft = Minecraft.getInstance();

					if (minecraft.player == null) {
						return;
					}

					ItemStack mainHand = minecraft.player.getMainHandItem();
					ItemStack offHand = minecraft.player.getOffhandItem();

					if (mainHand.is(ModItems.DIMENSIONAL_POCKET)) {
						renderSelectedStack(guiGraphics, minecraft, mainHand);
					} else if (offHand.is(ModItems.DIMENSIONAL_POCKET)) {
						renderSelectedStack(guiGraphics, minecraft, offHand);
					}
				}
		);

		HudElementRegistry.attachElementAfter(VanillaHudElements.HOTBAR,
				Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "la_lechona_effects"),
				(graphics, deltaTracker) -> {

			        Minecraft minecraft = Minecraft.getInstance();
					LocalPlayer player = minecraft.player;
			        if (player == null) { return; }

					List<Identifier> attachmentSpritePaths = getAttachmentSpritePaths(player);

					Screen screen = minecraft.gui.screen();

					if (!(screen instanceof CreativeModeInventoryScreen) && !(screen instanceof InventoryScreen)) {
						int x = graphics.guiWidth();
						int y = 53;
						for (Identifier sprite : attachmentSpritePaths) {
							x -= 25;
							graphics.blitSprite(RenderPipelines.GUI_TEXTURED, EFFECT_BACKGROUND_SPRITE, x, y, 24, 24);
							graphics.blit(RenderPipelines.GUI_TEXTURED, sprite, x+3, y+3, 0, 0, 18, 18, 18, 18);
						}
					}
				});
	}

	private void renderSelectedStack(GuiGraphicsExtractor guiGraphicsExtractor, Minecraft client, ItemStack stack) {
		if (!LegendaryItemUtils.isLinked(stack)) { return; }

		ItemStack selectedStack = DimensionalPocketCache.mainStack;
		int stackCount = DimensionalPocketCache.mainStackCount;

		if (!selectedStack.isEmpty() && client.player != null) {
			int width = client.getWindow().getGuiScaledWidth();
			int height = client.getWindow().getGuiScaledHeight();

			int hotbarLeftX = (width / 2) - 90;
			int selectedSlot = client.player.getInventory().getSelectedSlot();
			int x = hotbarLeftX + (selectedSlot * 20) + 2;
			int y = height - 19;

			if (client.player.getOffhandItem() == stack) {
				x = (width / 2) - 117;
			}

			boolean safeMode = stack.getOrDefault(ModDataComponentTypes.SAFE_MODE, false);

//			guiGraphicsExtractor.item(selectedStack, x, y);
			if (safeMode && stackCount <= DimensionalPocketCache.mainStackDuplicateCount) {
				guiGraphicsExtractor.text(
						client.font,
						String.valueOf(stackCount),
						x + 17 - client.font.width(String.valueOf(stackCount)),
						y + 9,
						0xFFD46763,
						true
				);
			}
			else {
				guiGraphicsExtractor.itemDecorations(
						client.font,
						selectedStack,
						x,
						y,
						String.valueOf(stackCount));
			}
		}
	}

	public static List<Identifier> getAttachmentSpritePaths(LocalPlayer player) {
		List<Identifier> attachmentSpritePaths = new ArrayList<>();

		if (player.hasAttached(ModAttachmentTypes.MANUAL_BREATHING)) {
			attachmentSpritePaths.add(MANUAL_BREATHING);
		}
		if (player.hasAttached(ModAttachmentTypes.SCARRED)) {
			attachmentSpritePaths.add(CUMULATIVE_EXTRA_DAMAGE);
		}
		if (player.hasAttached(ModAttachmentTypes.NIGHT_OWL)) {
			attachmentSpritePaths.add(NIGHT_OWL);
		}
		if (player.hasAttached(ModAttachmentTypes.MISSCLICK)) {
			attachmentSpritePaths.add(MISSCLICK);
		}
		AttributeInstance entityInteractionRange = player.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);
		if (entityInteractionRange != null && entityInteractionRange.hasModifier(Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "entity_interaction_range_modifier"))) {
			attachmentSpritePaths.add(RANGE);
		}
		if (player.hasAttached(ModAttachmentTypes.SCALE)) {
			attachmentSpritePaths.add(SCALE);
		}

		return attachmentSpritePaths;
	}

	public static List<Component> getAttachmentNames(LocalPlayer player) {
		List<Component> attachmentNames = new ArrayList<>();

		if (player.hasAttached(ModAttachmentTypes.MANUAL_BREATHING)) {
			attachmentNames.add(Component.translatable("effect." +  Umamium.MOD_ID + ".manual_breathing"));
		}
		if (player.hasAttached(ModAttachmentTypes.SCARRED)) {
			attachmentNames.add(Component.translatable("effect." +  Umamium.MOD_ID + ".cumulative_extra_damage"));
		}
		if (player.hasAttached(ModAttachmentTypes.NIGHT_OWL)) {
			attachmentNames.add(Component.translatable("effect." +  Umamium.MOD_ID + ".night_owl"));
		}
		if (player.hasAttached(ModAttachmentTypes.MISSCLICK)) {
			attachmentNames.add(Component.translatable("effect." +  Umamium.MOD_ID + ".missclick"));
		}
		AttributeInstance entityInteractionRange = player.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);
		if (entityInteractionRange != null && entityInteractionRange.hasModifier(Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "entity_interaction_range_modifier"))) {
			attachmentNames.add(Component.translatable("effect." +  Umamium.MOD_ID + ".range"));
		}
		if (player.hasAttached(ModAttachmentTypes.SCALE)) {
			attachmentNames.add(Component.translatable("effect." +  Umamium.MOD_ID + ".scale_" + player.getAttached(ModAttachmentTypes.SCALE)));
		}

		return attachmentNames;
	}
}