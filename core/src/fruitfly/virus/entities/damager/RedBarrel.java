package fruitfly.virus.entities.damager;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector3;

import fruitfly.virus.G;

public class RedBarrel extends StaticEntity {

	private int health = 20;
	
	public RedBarrel(Vector3 position) {
		super(position, G.textureMap.barrelRed);
	}

	@Override
	public void tick(long tick) {
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void hit(IDamager damager, Vector3 position) {
		if (health <= 0) return;
		
		Sound sound = damager.getDamageSound(this);
		if (sound != null) damager.getDamageSound(this).play();
		
		health -= damager.getDamage();
		
		if (health <= 0) {
			explode();
		}
	}
	
	protected void explode() {
		Vector3 explosionPos = new Vector3(position);
		explosionPos.z = size.y/2.0f;
		G.world.entityManager.addEntity(new Explosion(explosionPos, 1.0f, true));
		G.world.entityManager.removeEntity(this);
	}
}
