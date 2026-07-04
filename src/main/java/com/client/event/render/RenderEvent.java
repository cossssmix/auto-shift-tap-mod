package com.client.event.render;

import com.client.event.Event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class RenderEvent extends Event {
	private boolean tick;
}
