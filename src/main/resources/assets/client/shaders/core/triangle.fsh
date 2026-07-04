#version 150
#moj_import <client:common.glsl>

out vec4 OutColor;

in vec2 FragCoord;
in vec4 FragColor;

layout(std140) uniform Uniforms {
    vec2 size;
};

void main() {
    vec2 normCoord = FragCoord / size;

    OutColor = FragColor;
}
