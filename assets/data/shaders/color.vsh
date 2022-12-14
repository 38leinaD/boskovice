attribute vec3 a_position;
attribute vec2 a_texCoord0;

uniform mat4 u_worldView;
varying vec2 v_texCoords;

void main()
{
    v_texCoords = a_texCoord0;
    vec4 position = vec4(a_position, 1.0);
    gl_Position =  u_worldView * position;
}
