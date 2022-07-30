attribute vec3 a_position;
attribute vec4 a_color;
attribute float a_size;
attribute float a_distance;
attribute float a_s, a_t;

uniform mat4 u_worldView;
uniform float u_viewportWidth;
uniform float u_distance;

varying vec4 v_color;
varying float v_s, v_t;

void main()
{
	vec4 position = vec4(a_position, 1.0);
    gl_Position =  u_worldView * position;
    gl_PointSize = a_size * 0.6 * u_viewportWidth / a_distance;
    v_color = a_color;
    v_s = a_s;
    v_t = a_t;
}
