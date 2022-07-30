package fruitfly.virus.pickups;

import fruitfly.virus.G;

public class RedKeyPickUp extends DoorKeyPickUp {

	public RedKeyPickUp(float x, float y) {
		super(x, y);
		this.texture = G.textureMap.redKeyPickUp;
	}

}
