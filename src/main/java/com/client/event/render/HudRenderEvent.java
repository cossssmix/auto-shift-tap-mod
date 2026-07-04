package com.client.event.render;

import com.client.event.Event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

@Getter
@AllArgsConstructor
public final class HudRenderEvent extends Event {
	private DrawContext context;
	private RenderTickCounter tickCounter;
}
