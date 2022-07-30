package fruitfly.virus.entities;

import com.badlogic.gdx.math.Vector3;

public interface IEntity {
	public Vector3 getPosition();
	public void tick(long tick);
	public void dispose();
}
