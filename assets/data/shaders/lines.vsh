attribute vec3 a_position;

uniform mat4 u_worldView;

void main()
{
    vec4 position = vec4(a_position, 1.0);
    gl_Position =  u_worldView * position;
}
