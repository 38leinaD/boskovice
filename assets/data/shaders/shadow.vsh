attribute vec3 a_position;

uniform mat4 u_worldView;

const mat4 shadowMapping = mat4(	1.0, 0.0, 0.0, 0.0,
			0.0, 1.0, 0.0, 0.0,
			-0.5, -0.5, 0.0, 0.0,
			0.0, 0.0, 0.0, 1.0);

void main()
{
    vec4 position = vec4(a_position, 1.0);
     
 
    gl_Position =  u_worldView * shadowMapping * position;
}