package com.client.systems.render;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gl.UniformType;
// import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

import static net.minecraft.client.gl.RenderPipelines.TRANSFORMS_AND_PROJECTION_SNIPPET;

public class PipelinesRepository {
    public static final RenderPipeline RECTANGLE = RenderPipelines.register(RenderPipeline.builder(TRANSFORMS_AND_PROJECTION_SNIPPET)
            .withVertexShader(Identifier.of("client", "core/rect"))
            .withFragmentShader(Identifier.of("client", "core/rect"))
            .withBlend(BlendFunction.TRANSLUCENT)
            .withUniform("Uniforms", UniformType.UNIFORM_BUFFER)
            .withVertexFormat(VertexFormats.POSITION_COLOR, VertexFormat.DrawMode.QUADS)
            .withLocation("pipeline/client_rect")
            .build());

	public static final RenderPipeline TRIANGLE = RenderPipelines.register(
		RenderPipeline.builder(TRANSFORMS_AND_PROJECTION_SNIPPET)
			.withVertexShader(Identifier.of("client", "core/triangle"))
			.withFragmentShader(Identifier.of("client", "core/triangle"))
			.withBlend(BlendFunction.TRANSLUCENT)
			.withUniform("Uniforms", UniformType.UNIFORM_BUFFER)
			.withVertexFormat(VertexFormats.POSITION_COLOR, VertexFormat.DrawMode.TRIANGLES)
			.withLocation("pipeline/client_triangle")
			.build()
	);

	// public static final RenderLayer RECT_LAYER = RenderLayer.of(
    //         "rect_layer", 1536, false, true, RECTANGLE, RenderLayer.MultiPhaseParameters.builder().build(false)
    // );
}