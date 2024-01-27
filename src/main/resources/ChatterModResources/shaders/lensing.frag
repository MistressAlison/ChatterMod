precision mediump float;

varying vec4 v_color;
varying vec2 v_texCoords;
//varying vec4 v_pos;
//varying vec4 v_apos;

uniform sampler2D u_texture;
uniform float u_scale;//settings dot scale
uniform vec2 u_screenSize;//width, height
uniform vec2 u_mouse;
uniform float size;

void main() {
    vec2 uv = gl_FragCoord.xy / u_screenSize;
    //uv.y = -uv.y;
    vec2 warp = normalize(u_mouse.xy - gl_FragCoord.xy) * pow(distance(u_mouse.xy, gl_FragCoord.xy), -2.0) * size;
    //warp.y = -warp.y;
    uv = uv + warp;

    float light = clamp(0.1/(size/1000.0)*distance(u_mouse.xy, gl_FragCoord.xy) - 1.5, 0.0, 1.0);

    vec4 color = texture2D(u_texture, uv);
    gl_FragColor = color * light;

}