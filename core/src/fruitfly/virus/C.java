package fruitfly.virus;

import com.badlogic.gdx.math.Vector3;

public class C {
	public static int buildNumber;
	public static final int screenScale = 4;
	public static final float fov = 60.0f; // fovy as defined by gluPerspectivce

	public static float secondsPerTick = 1.0f/60.0f;
	
	public static final Vector3 xAxis = new Vector3(1.0f, 0.0f, 0.0f);
	public static final Vector3 yAxis = new Vector3(0.0f, 1.0f, 0.0f);
	public static final Vector3 zAxis = new Vector3(0.0f, 0.0f, 1.0f);
	
	public enum Orientation {
		North,
		East,
		South,
		West
	} 
	
	public static boolean isDebug = false;
}
