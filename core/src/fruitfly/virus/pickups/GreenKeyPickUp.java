package fruitfly.virus.pickups;

import fruitfly.virus.G;

public class GreenKeyPickUp extends DoorKeyPickUp {

	public GreenKeyPickUp(float x, float y) {
		super(x, y);
		this.texture = G.textureMap.greenKeyPickUp;
	}

}
