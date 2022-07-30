package fruitfly.virus;

public interface ITrigger {
	public void setTarget(ITriggerTarget t);
	public boolean isTriggered();
	public void trigger();
}
