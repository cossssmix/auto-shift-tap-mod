package com.client.event.render;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import com.client.event.Event;
import com.mojang.blaze3d.buffers.GpuBufferSlice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.ObjectAllocator;

@Getter
@AllArgsConstructor
public final class WorldRenderEvent extends Event {
	private ObjectAllocator allocator;
	private RenderTickCounter tickCounter;
	private boolean renderBlockOutline;
	private Camera camera;
	private Matrix4f positionMatrix;
	private Matrix4f matrix4f;
	private Matrix4f projectionMatrix;
	private GpuBufferSlice fogBuffer;
	private Vector4f fogColor;
	private boolean renderSky;
}
