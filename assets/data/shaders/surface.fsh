#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

varying vec2 v_texCoords0;
varying vec2 v_texCoords1;
varying vec3 v_normal;

uniform sampler2D u_texture;
uniform sampler2D u_lightmap;

void main()
{
	vec4 color = texture2D(u_texture, v_texCoords0);
	if (color.rgb == vec3(1.0, 0.0, 1.0)) discard;
	    
    gl_FragColor = color * texture2D(u_lightmap, v_texCoords1);
}