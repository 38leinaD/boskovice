package fruitfly.virus.weapons;

import com.badlogic.gdx.math.Vector3;

import fruitfly.virus.G;
import fruitfly.virus.entities.IEntity;
import fruitfly.virus.entities.damager.LaserShot;
import fruitfly.virus.timer.ITimeoutListener;

public class LaserPistol extends WeaponBase {
	
	private float charge = 0.2f;
	private float capacity = 0.2f;
	private ITimeoutListener timeout = new ITimeoutListener() {
		
		@Override
		public void timeout(Object data) {
			if (charge < capacity) {
				charge += 0.03f;
				if (charge > capacity) charge = capacity;
			}
		}
	};
	
	public LaserPistol() {
		sound = G.audio.laserShot;
		textures = G.textureMap.laserPistol;
		G.timer.registerPeriodicTimer(20, timeout);
	}
	
	private ITimeoutListener clickDelay = null;
	
	@Override
	public void fire() {
		if (charge < 0.099999f) {

			if (clickDelay == null) {
				G.audio.click.play();
				clickDelay = new ITimeoutListener() {
					
					@Override
					public void timeout(Object data) {
						clickDelay = null;
					}
				};
				G.timer.registerTimer(30, clickDelay);
			}
			return;
		}
		super.fire();

		charge -= 0.1f;
	}

	protected IEntity createBullet(Vector3 position, Vector3 direction, Vector3 inacurateDirection) {
		LaserShot ls = new LaserShot(position, inacurateDirection);
		return ls;
	}

	public float getCharge() {
		return charge;
	}
	
	public float getCapacity() {
		return capacity;
	}

	public void setCharge(float charge) {
		this.charge = charge;
	}

	public void setCapacity(float capacity) {
		this.capacity = capacity;
	}
}
