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
    vec4 outputColor = v_color * texture2D(u_texture, v_texCoords);
    float r = min(u_screenSize.x, u_screenSize.y)/1.5;
    float d = distance(u_mouse, gl_FragCoord.xy)/r;
    gl_FragColor = vec4(outputColor.r-d, outputColor.g-d, outputColor.b-d, outputColor.a-d);
}