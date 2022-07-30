package fruitfly.virus.entities.light;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

import fruitfly.virus.tiles.Tile;

public class FlickerLight implements ILight {

	private Vector3 position;
	private Color color;
	private float intensity;
	
	public FlickerLight(Vector3 position, Color color, float intensity) {
		this.position = position;
		this.color = color;
		this.intensity = intensity;
	}
	
	@Override
	public Vector3 getLightPosition() {
		return position;
	}
	
	@Override
	public Vector3 getPosition() {
		return position;
	}

	@Override
	public Color getLightColor() {
		return color;
	}

	@Override
	public float getLightIntensity() {
		return intensity * MathUtils.random();
	}

	@Override
	public void tick(long tick) {
	
	}

	@Override
	public void dispose() {
		
	}
}
