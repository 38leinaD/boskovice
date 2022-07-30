package fruitfly.virus.pickups;

import fruitfly.virus.G;

public class LaserPistolPickUp extends PickUp {

	public LaserPistolPickUp(float x, float y) {
		super(x, y, 0.3f);
		this.texture = G.textureMap.laserPistolPickup;
	}
}
