#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_texCoords;

uniform sampler2D u_texture;
uniform vec4 u_lightColor;
uniform float u_alpha;
uniform bool u_useAlpha;

void main()
{
	vec4 color = texture2D(u_texture, v_texCoords);
	if (color.rgb == vec3(1.0, 0.0, 1.0)) discard;
	if (u_useAlpha) gl_FragColor = vec4((color * u_lightColor).rgb, u_alpha);
	else gl_FragColor = color * u_lightColor;
}