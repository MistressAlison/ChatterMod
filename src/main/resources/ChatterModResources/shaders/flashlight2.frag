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
uniform int torches;
uniform vec2 u_torch[8];

void main() {
    vec4 outputColor = v_color * texture2D(u_texture, v_texCoords);
    float mouse_r = min(u_screenSize.x, u_screenSize.y)/3;
    float torch_r = mouse_r/1.5;
    float d = max(0.0, 1.0 - distance(u_mouse, gl_FragCoord.xy)/mouse_r);
    for (int i = 0 ; i < 8 ; i++) {
        if (i < torches) {
            d += max(0.0, 1.0 - distance(u_torch[i].xy, gl_FragCoord.xy)/torch_r);
        }
    }
    //float dt1 = 0;
    //if (torches == 1) {
    //    dt1 = max(0.0, 1.0 - distance(u_torch, gl_FragCoord.xy)/torch_r);
    //}
    float fd = 1 - min(1.0f, d);
    float fa = min(1.0f, d);
    gl_FragColor = vec4(outputColor.r*fa, outputColor.g*fa, outputColor.b*fa, outputColor.a*fa);
}