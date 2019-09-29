package se.hig.thlu.clock;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import se.hig.thlu.counter.Counter24;
import se.hig.thlu.counter.Counter60;
import se.hig.thlu.counter.Counter7;
import se.hig.thlu.counter.SettableCounterType;
import se.hig.thlu.time.Time;
import se.hig.thlu.time.TimeType;

public class WeekClock implements ClockType {

	public static final String TIME_PROPERTY = "TIME";
	protected PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	protected TimeType time;
	protected Timer timer;
	protected final SettableCounterType dayCounter = new Counter7();
	protected final SettableCounterType hourCounter = new Counter24(dayCounter);
	protected final SettableCounterType minuteCounter = new Counter60(hourCounter);
	protected final SettableCounterType secondCounter = new Counter60(minuteCounter);

	public WeekClock() {
		setTime(new Time(0, 0, 0, 0));
	}

	public WeekClock(TimeType time) {
		setTime(time);

		dayCounter.setCount(time.getDay());
		hourCounter.setCount(time.getHour());
		minuteCounter.setCount(time.getMinute());
		secondCounter.setCount(time.getSecond());
	}

	@Override
	public void tickTock() {
		secondCounter.count();
		TimeType oldTime = new Time(time.toString());
		time.setDay(dayCounter.getCount());
		time.setHour(hourCounter.getCount());
		time.setMinute(minuteCounter.getCount());
		time.setSecond(secondCounter.getCount());
		propertyChangeSupport.firePropertyChange(TIME_PROPERTY, oldTime, time);
	}

	@Override
	public void setTime(TimeType time) {
		this.time = Objects.requireNonNull(time);
	}

	@Override
	public TimeType getTime() {
		return time;
	}

	@Override
	public void startClock(PrintTime printTime) {
		if (timer == null) {
			timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {
				public void run() {
					tickTock();
					if (printTime == PrintTime.YES) {
						System.out.println(getTime().toString());
					}
				}
			}, 0, 1000);
		}
	}

	@Override
	public void stopClock() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	@Override
	public void resetClock() {
		stopClock();
		dayCounter.reset();
		hourCounter.reset();
		minuteCounter.reset();
		secondCounter.reset();
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}
}
