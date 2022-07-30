#version 120
#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D u_texture;
uniform float u_texSize;

varying vec4 v_color;
varying float v_s, v_t;

void main()
{
	vec4 color = texture2D(u_texture, vec2(v_s, v_t) + gl_PointCoord * u_texSize);
	if (color.rgb == vec3(1.0, 0.0, 1.0)) discard;
    gl_FragColor = color * v_color;
}