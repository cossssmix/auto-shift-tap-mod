// package com.client.systems.render;

// import com.mojang.blaze3d.buffers.GpuBuffer;
// import com.mojang.blaze3d.buffers.GpuBufferSlice;
// import com.mojang.blaze3d.systems.ProjectionType;
// import com.mojang.blaze3d.systems.RenderPass;
// import com.mojang.blaze3d.systems.RenderSystem;
// import com.mojang.blaze3d.vertex.VertexFormat;

// import lombok.Getter;
// import net.minecraft.client.MinecraftClient;
// import net.minecraft.client.gl.Framebuffer;
// import net.minecraft.client.render.BufferBuilder;
// import net.minecraft.client.render.BuiltBuffer;
// import net.minecraft.client.render.ProjectionMatrix2;
// import net.minecraft.client.render.VertexFormats;
// import net.minecraft.client.util.BufferAllocator;
// import net.minecraft.client.util.Window;
// import net.minecraft.client.util.math.MatrixStack;
// import org.joml.Matrix4f;
// import org.joml.Vector2f;
// import org.joml.Vector3f;
// import org.joml.Vector4f;

// import java.awt.*;
// import java.util.*;
// import java.util.function.Supplier;

// public class ClientRenderer {
// 	@Getter
// 	private static ClientRenderer instance = new ClientRenderer();

//     private final ProjectionMatrix2 guiProjectionMatrix = new ProjectionMatrix2("rect renderer", 1000.0F, 11000.0F, true);
//     private final Map<Integer, RenderData> renderCache = new HashMap<>();
//     private final ArrayList<RenderData> renderQueue = new ArrayList<>();
//     private final BufferAllocator allocator = new BufferAllocator(12);
// 	@Getter
//     private final MatrixStack stack = new MatrixStack();
//     private final Supplier<String> bufferName = () -> "rect render";

//     public ClientRenderer() {
//         stack.peek().getPositionMatrix().setTranslation(0, 0, -10000.f);
//     }

//     public void rect(float x, float y, float width, float height, Vector4f round, float smooth, Color color1, Color color2, Color color3, Color color4) {
//         int hash = hashFrom(width + x + y + x * y, height, round, color1.hashCode() + color2.hashCode() + color3.hashCode() + color4.hashCode());
        
//         RenderData renderData = renderCache.get(hash);
        
//         if (renderData == null) {
//             int indices = 6;
            
//             try (BuiltBuffer builtBuffer = rectVertices(x, y, width, height, color1, color2, color3, color4).end()) {
//                 GpuBufferSlice uniforms = new RectUniform(new Vector2f(width, height), round, smooth).uniforms();
//                 renderData = new RenderData(
//                     RenderSystem.getDevice().createBuffer(bufferName, 1, builtBuffer.getBuffer()),
//                     uniforms,
//                     indices,
//                     stack.peek()
//                 );
//                 renderCache.put(hash, renderData);
//             }
//         }
        
//         renderQueue.add(renderData);
//     }

//     public void drawBuffer() {
//         MinecraftClient minecraftClient = MinecraftClient.getInstance();
//         Window window = minecraftClient.getWindow();
        
//         RenderSystem.setProjectionMatrix(
//             this.guiProjectionMatrix.set(
//                 (float)window.getFramebufferWidth() / (float)window.getScaleFactor(),
//                 (float)window.getFramebufferHeight() / (float)window.getScaleFactor()
//             ),
//             ProjectionType.ORTHOGRAPHIC
//         );
        
//         Framebuffer framebuffer = minecraftClient.getFramebuffer();
//         GpuBufferSlice[] dynamicUniforms = new GpuBufferSlice[renderQueue.size()];
        
//         for (int i = 0; i < dynamicUniforms.length; i++) {
//             RenderData renderData = renderQueue.get(i);
//             dynamicUniforms[i] = RenderSystem.getDynamicUniforms().write(
//                 renderData.matrix.getPositionMatrix(),
//                 new Vector4f(1.0F, 1.0F, 1.0F, 1.0F),
//                 new Vector3f(),
//                 new Matrix4f(),
//                 0.0F
//             );
//         }
        
//         RenderSystem.ShapeIndexBuffer shapeIndexBuffer = RenderSystem.getSequentialBuffer(VertexFormat.DrawMode.QUADS);
//         GpuBuffer gpuBuffer = shapeIndexBuffer.getIndexBuffer(renderQueue.size());
//         VertexFormat.IndexType indexType = shapeIndexBuffer.getIndexType();
        
//         try (RenderPass renderPass = RenderSystem.getDevice()
//                 .createCommandEncoder()
//                 .createRenderPass(
//                     () -> "rect renderer",
//                     framebuffer.getColorAttachmentView(),
//                     OptionalInt.empty(),
//                     framebuffer.useDepthAttachment ? framebuffer.getDepthAttachmentView() : null,
//                     OptionalDouble.empty()
//                 )) {
            
//             RenderSystem.bindDefaultUniforms(renderPass);
            
//             for (int i = 0; i < renderQueue.size(); i++) {
//                 RenderData entry = renderQueue.get(i);
                
//                 renderPass.setUniform("DynamicTransforms", dynamicUniforms[i]);
//                 renderPass.setPipeline(PipelinesRepository.RECTANGLE);
//                 renderPass.setUniform("Uniforms", entry.customUniforms);
//                 renderPass.setVertexBuffer(0, entry.vertexBuffer);
//                 renderPass.disableScissor();
//                 renderPass.setIndexBuffer(gpuBuffer, indexType);
//                 renderPass.drawIndexed(0, 0, entry.indices, 1);
//             }
//         }
        
//         renderQueue.clear();
//     }

//     private BufferBuilder rectVertices(float x, float y, float width, float height, Color color1, Color color2, Color color3, Color color4) {
//         BufferBuilder bufferBuilder = new BufferBuilder(allocator, VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        
//         bufferBuilder.vertex(x, y, 0).color(color1.getRGB());
//         bufferBuilder.vertex(x, y + height, 0).color(color2.getRGB());
//         bufferBuilder.vertex(x + width, y + height, 0).color(color3.getRGB());
//         bufferBuilder.vertex(x + width, y, 0).color(color4.getRGB());
        
//         return bufferBuilder;
//     }

//     private int hashFrom(Object a, Object b, Object c, Object e) {
//         return a.hashCode() + b.hashCode() + c.hashCode() + e.hashCode();
//     }

//     private static class RenderData {
//         GpuBuffer vertexBuffer;
//         GpuBufferSlice customUniforms;
//         int indices;
//         MatrixStack.Entry matrix;

//         public RenderData(GpuBuffer vertexBuffer, GpuBufferSlice customUniforms, int indices, MatrixStack.Entry matrix) {
//             this.vertexBuffer = vertexBuffer;
//             this.customUniforms = customUniforms;
//             this.indices = indices;
//             this.matrix = matrix.copy();
//         }
//     }
// }