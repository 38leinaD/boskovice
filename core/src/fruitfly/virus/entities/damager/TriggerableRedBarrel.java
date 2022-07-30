package fruitfly.virus.entities.damager;

import com.badlogic.gdx.math.Vector3;

import fruitfly.virus.ITrigger;
import fruitfly.virus.ITriggerTarget;

public class TriggerableRedBarrel extends RedBarrel implements ITriggerTarget {

	public TriggerableRedBarrel(Vector3 position) {
		super(position);
	}

	@Override
	public void triggered(ITrigger t) {
		explode();
	}

}
