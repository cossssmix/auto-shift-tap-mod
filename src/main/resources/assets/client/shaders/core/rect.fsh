#version 150

#moj_import <client:common.glsl>

out vec4 OutColor;

in vec2 FragCoord;
in vec4 FragColor;

layout(std140) uniform Uniforms {
    vec2 size;
    vec4 round;
    float smoothness;
};

void main() {
    float alpha = ralpha(size, FragCoord, round, smoothness);

    OutColor = vec4(FragColor.rgb, FragColor.a * alpha);
}
