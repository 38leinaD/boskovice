#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_texCoords;

uniform sampler2D u_texture;
uniform vec4 u_lightColor;

void main()
{
	vec4 color = texture2D(u_texture, v_texCoords);
	if (color.rgb == vec3(1.0, 0.0, 1.0)) discard;
    gl_FragColor = color * u_lightColor;
}