package fruitfly.virus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class Camera {
	public final PerspectiveCamera camera;
	
	public Camera() {
		camera = new PerspectiveCamera();
//		q1.setEulerAngles(0.0f, 0.0f, 0.0f);
	}
	
	public void follow(Player player) {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera.view.idt();
		camera.near = 0.01f;
		camera.far = 100.0f;
		camera.fieldOfView = C.fov;
		camera.viewportWidth = Gdx.graphics.getWidth() / 4;
		camera.viewportHeight = Gdx.graphics.getHeight() / 4;

		camera.position.x = player.position.x;
		camera.position.y = player.position.y;
		camera.position.z = player.getEyeHeight();
		camera.up.x = 0.0f;
		camera.up.y = 0.0f;
		camera.up.z = 1.0f;
		camera.direction.x = MathUtils.cosDeg(player.rotation);
		camera.direction.y = MathUtils.sinDeg(player.rotation);
		camera.direction.z = 0.0f;
		camera.update();
	}
	
	//private Quaternion q1 = new Quaternion(new Vector3(0.0f, 1.0f, 0.0f), 0.0f);
	int tick = 0;
	public void tick(long tick) {
////		q1.setEulerAngles(tick/360.0f, 0.0f, 0.0f);
//		if (tick < 110) {
//			camera.position.z = 0.3f;
//			camera.rotate(new Vector3(0.0f, 1.0f, 0.0f), 90 - tick);
//			camera.update();
//		}
//		else if (tick >= 110 && tick <= 210){
//			//camera.rotate(new Vector3(0.0f, 1.0f, 0.0f), -20);
//			camera.rotate(new Vector3(0.0f, 1.0f, 0.0f), -20 * (1.0f - (tick - 110) / 100.0f));
//
//			camera.position.z = 0.3f + (G.player.eyeHeight - 0.3f) * (tick-110)/100.0f;
//			camera.update();
//		}
//
//		tick++;
	}
}
