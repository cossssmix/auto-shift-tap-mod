package com.client.systems.module;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.client.core.ClientContext;
import com.client.event.client.KeyboardEvent;
import com.client.gui.screen.ClickGuiScreen;
import com.client.systems.module.combat.ShiftTapModule;
import com.client.systems.module.movement.AutoSprintModule;
import com.client.systems.module.visuals.*;
import com.google.common.eventbus.Subscribe;

import lombok.Getter;

import static com.client.util.MinecraftVariables.mc;

public final class ModuleRepository {
	private final ClientContext ctx;
	@Getter
	private List<AbstractModule> modules;

	public ModuleRepository(final ClientContext ctx) {
		this.ctx = ctx;

		modules = new ArrayList<>();

		ctx.getEventBus().register(this);

		modules.addAll(List.of(
			new ShiftTapModule(),
			new AutoSprintModule(),
			new ClickGui(this)
		));

		for (final AbstractModule module : modules) {
			if (module.isEnabled()) {
				this.ctx.getEventBus().register(module);
			}
		}
	}

	public void toggle(final AbstractModule module) {
		module.setEnabled(!module.isEnabled());

		if (module.isEnabled()) {
			module.onEnable();
			this.ctx.getEventBus().register(module);
		} else {
			module.onDisable();
			this.ctx.getEventBus().unregister(module);
		}
	}

	@Subscribe
	public void onKeyboard(final KeyboardEvent event) {
		boolean isValidScreen = mc.currentScreen == null || mc.currentScreen instanceof ClickGuiScreen;

		if (event.getAction() == GLFW.GLFW_PRESS && isValidScreen) {
			this.modules.stream()
				.filter(module -> module.getKey() == event.getInput().getKeycode())
				.forEach(this::toggle);
		}
	}

	public <T extends AbstractModule> T getModule(final Class<T> module) {
		return this.modules.stream()
					.filter(module::isInstance)
					.map(module::cast)
					.findFirst()
					.orElse(null);
	}
}
