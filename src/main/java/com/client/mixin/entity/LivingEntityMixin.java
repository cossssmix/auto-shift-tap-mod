package com.client.mixin.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.client.Client;
import com.client.event.player.PlayerJumpEvent;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@Inject(method = "jump", at = @At("HEAD"))
	private void onJumpPre(CallbackInfo ci) {
		if (((Object) this) instanceof ClientPlayerEntity) {
			Client.getContext().getEventBus().post(new PlayerJumpEvent.Pre());
		}
	}

	@Inject(method = "jump", at = @At("TAIL"))
	private void onJumpPost(CallbackInfo ci) {
		if (((Object) this) instanceof ClientPlayerEntity) {
			Client.getContext().getEventBus().post(new PlayerJumpEvent.Post());
		}
	}
}
