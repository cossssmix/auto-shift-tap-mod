package com.client.mixin.render;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.client.Client;
import com.client.event.render.WorldRenderEvent;
import com.mojang.blaze3d.buffers.GpuBufferSlice;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.ObjectAllocator;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
	@Inject(method = "render", at = @At("HEAD"))
	private void onRender(
		ObjectAllocator allocator,
		RenderTickCounter tickCounter,
		boolean renderBlockOutline,
		Camera camera,
		Matrix4f positionMatrix,
		Matrix4f matrix4f,
		Matrix4f projectionMatrix,
		GpuBufferSlice fogBuffer,
		Vector4f fogColor,
		boolean renderSky,
		CallbackInfo ci
	) {
		Client.getContext().getEventBus().post(new WorldRenderEvent(
			allocator,
			tickCounter,
			renderBlockOutline,
			camera,
			positionMatrix,
			matrix4f,
			projectionMatrix,
			fogBuffer,
			fogColor,
			renderSky
		));
	}
}
