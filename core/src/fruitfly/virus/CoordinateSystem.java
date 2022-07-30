package fruitfly.virus;

import com.badlogic.gdx.math.Vector3;

public class CoordinateSystem {
	public static final CoordinateSystem north = new CoordinateSystem(new Vector3(-1.0f, 0.0f, 0.0f), new Vector3(0.0f, 0.0f, 1.0f));
	public static final CoordinateSystem south = new CoordinateSystem(new Vector3(1.0f, 0.0f, 0.0f), new Vector3(0.0f, 0.0f, 1.0f));
	public static final CoordinateSystem west = new CoordinateSystem(new Vector3(0.0f, -1.0f, 0.0f), new Vector3(0.0f, 0.0f, 1.0f));
	public static final CoordinateSystem east = new CoordinateSystem(new Vector3(0.0f, 1.0f, 0.0f), new Vector3(0.0f, 0.0f, 1.0f));
	
	public Vector3 x, y, z;
	
	public CoordinateSystem(Vector3 x, Vector3 y) {
		this.x = x.cpy();
		this.y = y.cpy();
		this.z = x.crs(y);
	}
}
