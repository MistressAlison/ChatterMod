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

#define M_PI 3.14159265358979323846
vec2 hash( vec2 p ) // replace this by something better
{
    p = vec2( dot(p,vec2(127.1,311.7)), dot(p,vec2(269.5,183.3)) );
    return -1.0 + 2.0*fract(sin(p)*43758.5453123);
}

float noise( in vec2 p )
{
    const float K1 = 0.366025404; // (sqrt(3)-1)/2;
    const float K2 = 0.211324865; // (3-sqrt(3))/6;

    vec2  i = floor( p + (p.x+p.y)*K1 );
    vec2  a = p - i + (i.x+i.y)*K2;
    float m = step(a.y,a.x);
    vec2  o = vec2(m,1.0-m);
    vec2  b = a - o + K2;
    vec2  c = a - 1.0 + 2.0*K2;
    vec3  h = max( 0.5-vec3(dot(a,a), dot(b,b), dot(c,c) ), 0.0 );
    vec3  n = h*h*h*h*vec3( dot(a,hash(i+0.0)), dot(b,hash(i+o)), dot(c,hash(i+1.0)));
    return dot( n, vec3(70.0) );
}


float simp(vec2 uv) {
    uv *= 5.0;
    mat2 m = mat2( 1.6,  1.2, -1.2,  1.6 );
    float f = 0.5000*noise( uv ); uv = m*uv;
    f += 0.2500*noise( uv ); uv = m*uv;
    f += 0.1250*noise( uv ); uv = m*uv;
    f += 0.0625*noise( uv ); uv = m*uv;
    f = 0.2 + 0.8*f;
    return f;
}


vec4 bumpFromDepth(vec2 uv, vec2 resolution, float scale) {
    vec2 step = 1. / resolution;

    float height = simp(uv);

    vec2 dxy = height - vec2(
        simp(uv + vec2(step.x, 0.)),
        simp(uv + vec2(0., step.y))
    );

    return vec4(normalize(vec3(dxy * scale / step, 1.)), height);
}

vec4 bumpFromTex(vec2 uv, vec2 resolution, float scale) {
    vec2 step = 1. / resolution;

    float height = simp(uv);

    vec2 dxy = height - vec2(
        length(texture(u_texture, uv + vec2(step.x, 0.))),
        length(texture(u_texture, uv + vec2(0., step.y)))
    );

    return vec4(normalize(vec3(dxy * scale / step, 1.)), height);
}


float sdRoundedBox( in vec2 p, in vec2 b, in vec4 r )
{
    r.xy = (p.x>0.0)?r.xy : r.zw;
    r.x  = (p.y>0.0)?r.x  : r.y;
    vec2 q = abs(p)-b+r.x;
    return min(max(q.x,q.y),0.0) + length(max(q,0.0)) - r.x;
}

void main()
{
    // Normalized pixel coordinates (from 0 to 1)
    vec2 uv = v_texCoords;
    vec4 offset = bumpFromDepth(uv + vec2(floor(mod(x_time, 10.0)*4.0)/4.0), u_screenSize.xy, .1)/80.0;


    // Output to screen
    texture(u_texture, uv + offset.xy)*0.4;


    gl_FragColor = (texture(u_texture, uv + offset.xy)*0.4) + (texture(u_texture, uv)*0.6);
    if (gl_FragColor.a > 0.0) {
        gl_FragColor += length(bumpFromDepth(uv, u_screenSize.xy, .1))*0.25;
    }
    //
    //fragColor += smoothstep(-0.4, 0.0, sdRoundedBox((uv + offset.xy) - vec2(0.5), vec2(0.65), vec4(0.25)));

}