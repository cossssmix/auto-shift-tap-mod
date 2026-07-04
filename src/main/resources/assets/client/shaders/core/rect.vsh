#version 150

#moj_import <client:common.glsl>

in vec3 Position;
in vec4 Color;

out vec2 FragCoord;
out vec4 FragColor;

#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <minecraft:projection.glsl>

void main() {
    FragCoord = rvertexcoord(gl_VertexID);
    FragColor = Color;

    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
}