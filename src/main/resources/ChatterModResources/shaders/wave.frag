#ifdef GL_ES
#define LOWP
precision mediump float;
#else
#define LOWP
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
//varying vec4 v_pos;
//varying vec4 v_apos;

uniform sampler2D u_texture;
uniform float u_scale;//settings dot scale
uniform vec2 u_screenSize;//width, height
uniform vec2 u_mouse;
uniform float x_time;

void main() {
    vec2 uv = gl_FragCoord.xy / u_screenSize;
    uv.x += (sin((uv.y + (x_time * 0.05)) * 10.0) * 0.02) + (sin((uv.y + (x_time * 0.02)) * 32.0) * 0.01);
    vec4 texColor = texture(u_texture, uv);
    gl_FragColor = texColor;
}