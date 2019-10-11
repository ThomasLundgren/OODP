package se.hig.thlu.model.clock;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import se.hig.thlu.model.alarm.AlarmManager;
import se.hig.thlu.model.alarm.AlarmType;
import se.hig.thlu.model.counter.Counter24;
import se.hig.thlu.model.counter.Counter60;
import se.hig.thlu.model.counter.Counter7;
import se.hig.thlu.model.counter.SettableCounterType;
import se.hig.thlu.model.time.Time;
import se.hig.thlu.model.time.TimeType;

public class WeekAlarmClock implements AlarmClockType {

	private final AlarmManager alarmManager = new AlarmManager();
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private TimeType time;
	private Timer timer;
	private final SettableCounterType dayCounter = new Counter7();
	private final SettableCounterType hourCounter = new Counter24(dayCounter);
	private final SettableCounterType minuteCounter = new Counter60(hourCounter);
	private final SettableCounterType secondCounter = new Counter60(minuteCounter);

	public WeekAlarmClock(TimeType time, AlarmType alarm) {
		setTime(time);

		dayCounter.setCount(time.getDay());
		hourCounter.setCount(time.getHour());
		minuteCounter.setCount(time.getMinute());
		secondCounter.setCount(time.getSecond());
		addAlarm(alarm);
	}

	public WeekAlarmClock(AlarmType alarm) {
		this(new Time(0, 0, 0, 0), alarm);
	}

	public WeekAlarmClock() {}

	@Override
	public void tickTock() {
		secondCounter.count();
		TimeType oldTime = new Time(time.toString());

		time.setDay(dayCounter.getCount());
		time.setHour(hourCounter.getCount());
		time.setMinute(minuteCounter.getCount());
		time.setSecond(secondCounter.getCount());

		propertyChangeSupport.firePropertyChange(TIME_CHANGED, oldTime, time);
		alarmManager.checkForAlarm(time);
	}

	@Override
	public void addAlarm(AlarmType alarm) {
		Objects.requireNonNull(alarm);
		alarmManager.addAlarm(alarm);
		propertyChangeSupport.firePropertyChange(ALARM_ADDED, null, alarm);
		System.out.println("Alarm was added in WeekAlarmClock: " + alarm.getTime());
	}

	@Override
	public void removeAlarm(AlarmType alarm) {
		alarmManager.removeAlarm(alarm);
		propertyChangeSupport.firePropertyChange(ALARM_REMOVED, null, alarm);
	}

	@Override
	public Collection<AlarmType> getAlarms() {
		return alarmManager.getAlarms();
	}
	
	@Override
	public AlarmType getAlarm(TimeType time) {
		return alarmManager.getAlarm(time);
	}

	@Override
	public void setTime(TimeType time) {
		TimeType oldTime = this.time;
		propertyChangeSupport.firePropertyChange(TIME_CHANGED, oldTime, time);
		this.time = new Time(Objects.requireNonNull(time).toString());
		dayCounter.setCount(time.getDay());
		hourCounter.setCount(time.getHour());
		minuteCounter.setCount(time.getMinute());
		secondCounter.setCount(time.getSecond());
	}

	@Override
	public TimeType getTime() {
		return new Time(time.toString());
	}

	@Override
	public void startClock() {
		if (timer == null) {
			timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {
				public void run() {
					tickTock();
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
