package fruitfly.virus.timer;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Timer {
	private class TimeoutEvent {
		int ticks;
		int period;
		ITimeoutListener listener;
		Object data;
	}
	
	private List<TimeoutEvent> newEvents = new LinkedList<TimeoutEvent>();
	private List<TimeoutEvent> events = new LinkedList<TimeoutEvent>();
	private List<ITimeoutListener> removedListeners = new LinkedList<ITimeoutListener>();

	public void registerTimer(int ticks, ITimeoutListener listener) {
		TimeoutEvent event = new TimeoutEvent();
		event.ticks = ticks;
		event.listener = listener;
		event.period = 0;
	
		newEvents.add(event);
	}
	
	public void registerPeriodicTimer(int ticks, ITimeoutListener listener) {
		TimeoutEvent event = new TimeoutEvent();
		event.ticks = ticks;
		event.listener = listener;
		event.period = ticks;
	
		newEvents.add(event);
	}

	public void unregisterTimer(ITimeoutListener listener) {
		removedListeners.add(listener);
	}
	
	public void tick(long tick) {
		for (ITimeoutListener listener : removedListeners) {
			Iterator<TimeoutEvent> eventIterator = events.iterator();
			while (eventIterator.hasNext()) {
				TimeoutEvent event = eventIterator.next();
				if (event.listener == listener) eventIterator.remove();
			}
		}
		removedListeners.clear();
		
		for (TimeoutEvent event : newEvents) {
			events.add(event);
		}
		newEvents.clear();
		
		Iterator<TimeoutEvent> iterator = events.iterator();
		while (iterator.hasNext()) {
			TimeoutEvent event = iterator.next();
			
			if (--event.ticks == 0) {
				if (event.period == 0) {
					iterator.remove();
				}
				else {
					event.ticks = event.period;
				}
				event.listener.timeout(event.data);
			}
		}
	}
}
