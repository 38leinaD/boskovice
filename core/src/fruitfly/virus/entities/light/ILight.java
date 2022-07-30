package fruitfly.virus.entities.light;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

import fruitfly.virus.entities.IEntity;
import fruitfly.virus.tiles.Tile;

public interface ILight extends IEntity {
	public Color getLightColor();
	public float getLightIntensity();
	public Vector3 getLightPosition();
}
