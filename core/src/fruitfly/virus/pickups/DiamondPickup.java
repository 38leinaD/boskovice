package fruitfly.virus.pickups;

import fruitfly.virus.G;

public class DiamondPickup extends ScorePickUp {

	public DiamondPickup(float x, float y) {
		super(x, y, 0.3f);
		this.texture = G.textureMap.diamondPickUp;
	}

	@Override
	public int getScore() {
		return 500;
	}
}
