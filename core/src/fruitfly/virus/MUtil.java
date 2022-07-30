package fruitfly.virus;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class MUtil {
	public static float normalizeAngle(float angle) {
		while (angle >= 2 * MathUtils.PI) angle -= 2 * MathUtils.PI;
		while (angle < 0.0f) angle += 2 * MathUtils.PI;
		return angle;
	}
	
	public static float normalizeAngleDeg(float angle) {
		while (angle >= 360.0f) angle -= 360.0f;
		while (angle < 0.0f) angle += 360.0f;
		return angle;
	}
	
	public static void setDirectionFromAngle(float angle, Vector3 direction) {
		direction.x = MathUtils.cosDeg(angle);
		direction.y = MathUtils.sinDeg(angle);
		direction.z = 0.0f;
	}
	
	public static float angleFromDirection(Vector3 direction) {
		return MathUtils.atan2(direction.y, direction.x) * MathUtils.radiansToDegrees;
	}
	
	public static boolean isAlmostEqual(Vector3 v1, Vector3 v2) {
		if (v1.dot(v2) <= 0.1f) return true;
		return false;
	}
}
