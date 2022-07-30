#version 120
#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;

void main()
{
    gl_FragColor = vec4(gl_PointCoord.st, 0.0, 1.0);
}