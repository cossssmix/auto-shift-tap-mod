package com.client.mixin.render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.client.Client;
import com.client.event.render.UpdateRenderStateEvent;

import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.LivingEntity;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> {
	@Inject(method = "updateRenderState", at = @At("TAIL"))
	private void updateRenderState(T livingEntity, S livingEntityRenderState, float f, CallbackInfo ci) {
		Client.getContext().getEventBus().post(
			new UpdateRenderStateEvent(livingEntity, livingEntityRenderState, f)
		);
	}
}
