package com.client.event.client;

import com.client.event.Event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.input.KeyInput;

@Getter
@AllArgsConstructor
public final class KeyboardEvent extends Event {
	private long window;
	private int action;
	private KeyInput input;
}
