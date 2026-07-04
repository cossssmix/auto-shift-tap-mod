package com.client.mixin.render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.client.Client;
import com.client.event.render.HudRenderEvent;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
	@Inject(method = "render", at = @At("TAIL"))
	private void onRender(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
		Client.getContext().getEventBus().post(new HudRenderEvent(context, tickCounter));
	}
}
