package com.client.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.client.Client;
import com.client.event.render.RenderEvent;

import net.minecraft.client.MinecraftClient;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
	@Inject(method = "render", at = @At("HEAD"))
	private void onRender(boolean tick, CallbackInfo ci) {
		Client.getContext().getEventBus().post(new RenderEvent(tick));
	}
}
