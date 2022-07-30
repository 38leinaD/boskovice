package fruitfly.virus.entities.light;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

public class StaticLight implements ILight {

	private Vector3 position;
	private Color color;
	private float intensity;
	
	public StaticLight(Vector3 position, Color color, float intensity) {
		this.position = position.cpy();
		this.color = color;
		this.intensity = intensity;
	}
	
	@Override
	public Vector3 getLightPosition() {
		return position;
	}

	@Override
	public Color getLightColor() {
		return color;
	}

	@Override
	public float getLightIntensity() {
		return intensity;
	}

	@Override
	public void tick(long tick) {
	
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public Vector3 getPosition() {
		return position;
	}
}
