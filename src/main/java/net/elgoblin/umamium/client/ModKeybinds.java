package net.elgoblin.umamium.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.elgoblin.umamium.Umamium;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class ModKeybinds {

    public static final KeyMapping.Category LEGENDARY_TOOLS = KeyMapping.Category.register(
            Identifier.fromNamespaceAndPath(Umamium.MOD_ID, "legendary_tools")
    );

    public static KeyMapping SWITCH_ENCHANTMENTS_TOGGLE_SAFE_MODE;
    public static KeyMapping SCROLL_INSIDE_GROUP;
    public static KeyMapping SCROLL_BETWEEN_GROUPS;
    public static KeyMapping MANUAL_BREATHE;

    public static void registerModKeybinds() {
        SWITCH_ENCHANTMENTS_TOGGLE_SAFE_MODE = KeyMappingHelper.registerKeyMapping(
                new KeyMapping(
                        "key." + Umamium.MOD_ID +".switch_enchantments_toggle_safe_mode",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_K,
                        LEGENDARY_TOOLS
                )
        );

        SCROLL_INSIDE_GROUP = KeyMappingHelper.registerKeyMapping(
                new KeyMapping(
                        "key." + Umamium.MOD_ID +".scroll_inside_group",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_TAB,
                        LEGENDARY_TOOLS
                )
        );

        SCROLL_BETWEEN_GROUPS = KeyMappingHelper.registerKeyMapping(
                new KeyMapping(
                        "key." + Umamium.MOD_ID +".scroll_between_groups",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_GRAVE_ACCENT,
                        LEGENDARY_TOOLS
                )
        );

        MANUAL_BREATHE = KeyMappingHelper.registerKeyMapping(
                new KeyMapping(
                        "key." + Umamium.MOD_ID +".manual_breathe",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_Z,
                        LEGENDARY_TOOLS
                )
        );
    }
}
