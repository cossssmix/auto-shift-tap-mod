package com.client.core;

import com.client.systems.command.CommandManager;
import com.client.systems.module.ModuleRepository;
import com.client.util.player.MovementController;
import com.client.util.rotation.RotationController;
import com.google.common.eventbus.EventBus;

import lombok.Getter;

@Getter
public final class ClientContext {
	@Getter
	private static final ClientContext instance = new ClientContext();

	private final EventBus eventBus;
	private final RotationController rotationController;
	private final MovementController movementController;
	private final CommandManager commandManager;
	private final ModuleRepository moduleRepository;

	private ClientContext() {
		this.eventBus = new EventBus();
		this.rotationController = new RotationController(this);
		this.movementController = new MovementController(this.rotationController);
		this.commandManager = new CommandManager();
		this.moduleRepository = new ModuleRepository(this);
	}
}
