package com.client.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.client.Client;
import com.client.event.client.KeyboardEvent;

import static com.client.util.MinecraftVariables.mc;

import net.minecraft.client.Keyboard;
import net.minecraft.client.input.KeyInput;


@Mixin(Keyboard.class)
public abstract class KeyboardMixin {
	@Inject(method = "onKey", at = @At("HEAD"))
	private void onKey(long window, int action, KeyInput input, CallbackInfo ci) {
		if (window == mc.getWindow().getHandle()) {
			Client.getContext().getEventBus().post(new KeyboardEvent(window, action, input));
		}
	}
}
