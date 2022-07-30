package fruitfly.virus.pickups;

import fruitfly.virus.G;

public class CoinPickUp extends ScorePickUp {

	public CoinPickUp(float x, float y) {
		super(x, y, 0.3f);
		this.texture = G.textureMap.coinPickUp;
	}

	@Override
	public int getScore() {
		return 100;
	}

}
