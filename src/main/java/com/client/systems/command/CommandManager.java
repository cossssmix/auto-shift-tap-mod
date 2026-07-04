package com.client.systems.command;

// import static com.client.util.MinecraftVariables.mc;

import java.util.ArrayList;
import java.util.List;

import com.mojang.brigadier.CommandDispatcher;

// import net.minecraft.client.network.ClientCommandSource;
import net.minecraft.command.CommandSource;

public final class CommandManager {
	private final CommandDispatcher<CommandSource> dispatcher = new CommandDispatcher<>();
	// private final CommandSource source = new ClientCommandSource(mc.getNetworkHandler(), mc, true);
	private final List<Command> commands = new ArrayList<>();
	// private final String prefix = ".";

	public CommandManager() {
		// commands.addAll(List.of(
		// 	new TestCommand()
		// ));

		commands.forEach(command -> command.register(dispatcher));
	}
}
