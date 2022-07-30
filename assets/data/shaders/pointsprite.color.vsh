attribute vec3 a_position;
attribute vec4 a_color;
attribute float a_size;
attribute float a_distance;

uniform mat4 u_worldView;
uniform float u_viewportWidth;

varying vec4 v_color;

void main()
{
	vec4 position = vec4(a_position, 1.0);
    gl_Position =  u_worldView * position;
    gl_PointSize = 0.6 * a_size * u_viewportWidth / a_distance;
    v_color = a_color;
}
