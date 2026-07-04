package com.client.systems.module.visuals;

import static com.client.util.MinecraftVariables.mc;

import org.lwjgl.glfw.GLFW;

import com.client.systems.module.AbstractModule;
import com.client.systems.module.Category;
import com.client.systems.module.ModuleRepository;
import com.client.gui.screen.ClickGuiScreen;

public final class ClickGui extends AbstractModule {
	private final ClickGuiScreen clickGuiScreen;

    public ClickGui(final ModuleRepository moduleRepository) {
		super(
			"click gui",
			"",
			Category.Visuals
		);

        setKey(GLFW.GLFW_KEY_UNKNOWN);

		this.clickGuiScreen = new ClickGuiScreen("click gui", moduleRepository);
    }

	@Override
	public void onEnable() {
		mc.setScreen(this.clickGuiScreen);
	}

	@Override
	public void onDisable() {
		this.clickGuiScreen.close();
	}
}
