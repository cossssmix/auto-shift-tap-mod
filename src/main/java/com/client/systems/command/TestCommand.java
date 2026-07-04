package com.client.systems.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.command.CommandSource;

public final class TestCommand extends Command {

	public TestCommand(String name) {
		setName("test");
	}

	@Override
	public void execute(LiteralArgumentBuilder<CommandSource> builder) {
	}
}
