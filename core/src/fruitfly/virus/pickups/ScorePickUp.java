package fruitfly.virus.pickups;

public abstract class ScorePickUp extends PickUp {

	public ScorePickUp(float x, float y, float size) {
		super(x, y, size);
	}

	public abstract int getScore();
}
