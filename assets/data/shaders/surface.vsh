#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

attribute vec3 a_position;
attribute vec2 a_texCoord0;
attribute vec2 a_texCoord1;
attribute vec3 a_normal;

uniform mat4 u_projectionMatrix;
uniform mat4 u_modelViewMatrix;

varying vec2 v_texCoords0;
varying vec2 v_texCoords1;
varying vec3 v_normal;


void main()
{
    v_texCoords0 = a_texCoord0;
    v_texCoords1 = a_texCoord1;
    v_normal = a_normal;

	vec4 position = vec4(a_position, 1.0);
    
    gl_Position =  u_projectionMatrix * u_modelViewMatrix *position;
}