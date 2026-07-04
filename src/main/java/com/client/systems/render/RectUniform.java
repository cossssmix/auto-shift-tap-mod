package com.client.systems.render;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.buffers.Std140SizeCalculator;
import net.minecraft.client.gl.DynamicUniformStorage;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.nio.ByteBuffer;

public class RectUniform {
    public static final int SIZE = new Std140SizeCalculator()
            .putVec2()
            .putVec4()
            .putFloat() 
            .get();
    
    private static final DynamicUniformStorage<UniformValue> storage = 
        new DynamicUniformStorage<>("Rect UBO", SIZE, 2);

    private final Vector2f size;
    private final Vector4f round;
    private final float smoothness;

    public RectUniform(final Vector2f size, final Vector4f round, final float smoothness) {
        this.size = size;
        this.round = round;
        this.smoothness = smoothness;
    }

    public record UniformValue(Vector2f size, Vector4f round, float smoothness)
            implements DynamicUniformStorage.Uploadable {
        @Override
        public void write(ByteBuffer buffer) {
            Std140Builder.intoBuffer(buffer)
                    .putVec2(size)
                    .putVec4(round)
                    .putFloat(smoothness);
        }
    }

    public GpuBufferSlice uniforms() {
        return storage.write(new UniformValue(this.size, this.round, this.smoothness));
    }
}