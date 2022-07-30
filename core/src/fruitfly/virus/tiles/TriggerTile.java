package fruitfly.virus.tiles;

import fruitfly.virus.ITrigger;
import fruitfly.virus.ITriggerTarget;
import fruitfly.virus.TextureMap.SubTexture;

public class TriggerTile extends VoidTile implements ITrigger {
	private boolean isTriggered = false;
	private ITriggerTarget target;
	
	public TriggerTile(int x, int y, SubTexture floor) {
		super(x, y, floor);
	}

	@Override
	public void setTarget(ITriggerTarget t) {
		target = t;
	}

	@Override
	public boolean isTriggered() {
		return isTriggered;
	}

	@Override
	public void trigger() {
		target.triggered(this);
		isTriggered = true;
	}
}
