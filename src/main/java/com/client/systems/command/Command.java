package com.client.systems.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;

// import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.command.CommandSource;

@Getter @Setter
public abstract class Command {
	private String name;

	public abstract void execute(final LiteralArgumentBuilder<CommandSource> builder);

	public void register(final CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> builder = LiteralArgumentBuilder.literal(name);
        execute(builder);
        dispatcher.register(builder);
    }

    protected <T> RequiredArgumentBuilder<CommandSource, T> arg(final String name, final ArgumentType<T> type) {
        return RequiredArgumentBuilder.argument(name, type);
    }

    protected LiteralArgumentBuilder<CommandSource> literal(final String name) {
        return LiteralArgumentBuilder.literal(name);
    }
}
