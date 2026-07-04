package com.client.systems.render.uniforms;

import java.nio.ByteBuffer;

import org.joml.Vector2f;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.buffers.Std140SizeCalculator;

import net.minecraft.client.gl.DynamicUniformStorage;

public final class TriangleUniform {
	public static final int SIZE = new Std140SizeCalculator()
		.putVec2()
		.get();

    private final Vector2f size;

    public TriangleUniform(final Vector2f size) {
        this.size = size;
    }

	private static final DynamicUniformStorage<UniformValue> storage = 
        new DynamicUniformStorage<>("Triangle UBO", SIZE, 1);

    public record UniformValue(Vector2f size)
            implements DynamicUniformStorage.Uploadable {
        @Override
        public void write(ByteBuffer buffer) {
            Std140Builder.intoBuffer(buffer)
                    .putVec2(size);
        }
    }

    public GpuBufferSlice uniforms() {
        return storage.write(new UniformValue(this.size));
    }
}
