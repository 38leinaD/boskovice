package fruitfly.virus.entities.damager;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector3;

import fruitfly.virus.entities.IEntity;

public interface IDamager {
	public Vector3 getPosition();
	public Vector3 getDirection();
	public int getDamage();
	public Sound getDamageSound(IEntity e);
}
