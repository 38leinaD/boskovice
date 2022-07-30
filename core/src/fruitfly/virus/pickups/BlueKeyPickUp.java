package fruitfly.virus.pickups;

import fruitfly.virus.G;

public class BlueKeyPickUp extends DoorKeyPickUp {

	public BlueKeyPickUp(float x, float y) {
		super(x, y);
		this.texture = G.textureMap.blueKeyPickUp;
	}
}
