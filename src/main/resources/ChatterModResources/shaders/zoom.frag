precision mediump float;

varying vec4 v_color;
varying vec2 v_texCoords;
//varying vec4 v_pos;
//varying vec4 v_apos;

uniform sampler2D u_texture;
uniform float u_scale;//settings dot scale
uniform vec2 u_screenSize;//width, height
uniform vec2 u_mouse;
uniform float zoom;

void main() {
    vec2 uv = gl_FragCoord.xy / u_screenSize;
    uv = (uv - u_mouse/u_screenSize) * zoom + u_mouse/u_screenSize;
    gl_FragColor = v_color * texture2D(u_texture, uv);
}