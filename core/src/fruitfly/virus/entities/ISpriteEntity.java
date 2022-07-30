package fruitfly.virus.entities;

import java.nio.FloatBuffer;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

import fruitfly.virus.entities.damager.IDamager;

public interface ISpriteEntity extends IEntity {
	public BoundingBox getBoundingBox();
	public float getAlpha();
	public void writeVertexData(FloatBuffer buffer);
	public void hit(IDamager damager, Vector3 position);
}
