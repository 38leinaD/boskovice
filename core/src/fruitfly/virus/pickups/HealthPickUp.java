package fruitfly.virus.pickups;

import fruitfly.virus.G;

public class HealthPickUp extends PickUp {

	public HealthPickUp(float x, float y) {
		super(x, y, 0.3f);
		this.texture = G.textureMap.healthPickUp;
	}
}
