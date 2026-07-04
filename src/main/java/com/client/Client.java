package com.client;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.client.core.ClientContext;
import com.client.systems.module.visuals.ClickGui;

import lombok.Getter;

public final class Client implements ModInitializer {
	public static final String MOD_ID = "shifttap";
	@Getter
	private static ClientContext context;
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static KeyBinding clickGuiKeyBinding;

	@Override
	public void onInitialize() {
		context = ClientContext.getInstance();

		clickGuiKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key.shifttap.clickgui",
			InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_J,
			KeyBinding.Category.MISC
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (clickGuiKeyBinding.wasPressed()) {
				final ClickGui clickGuiModule = context.getModuleRepository().getModule(ClickGui.class);
				if (clickGuiModule != null) {
					context.getModuleRepository().toggle(clickGuiModule);
				}
			}
		});

		LOGGER.info("Modules loaded: {}", context.getModuleRepository().getModules().size());
		LOGGER.info("Client initialized");
	}
}
