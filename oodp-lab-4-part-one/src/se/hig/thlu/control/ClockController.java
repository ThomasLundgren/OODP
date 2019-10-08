package se.hig.thlu.control;

import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import se.hig.thlu.model.alarm.Alarm;
import se.hig.thlu.model.alarm.AlarmActionType;
import se.hig.thlu.model.alarm.AlarmType;
import se.hig.thlu.model.alarm.BlinkAlarmAction;
import se.hig.thlu.model.alarm.SoundAlarmAction;
import se.hig.thlu.model.clock.AlarmClockType;
import se.hig.thlu.model.clock.WeekAlarmClock;
import se.hig.thlu.model.time.Time;
import se.hig.thlu.model.time.TimeType;
import se.hig.thlu.storage.AlarmDao;
import se.hig.thlu.storage.Dao;

public class ClockController {

	public enum AlarmActions {
		BLINKING, SOUND
	}

	private final AlarmClockType clock = new WeekAlarmClock();
	private final Dao<AlarmType> alarmDao = new AlarmDao();
	
	public ClockController() {
		clock.setTime(getCurrentSystemTime());
	}

	public void addListenerForClock(PropertyChangeListener listener) {
		clock.addPropertyChangeListener(listener);
	}

	public void setTime(TimeType time) {
		clock.setTime(time);
	}
	
	public TimeType getTime() {
		return new Time(clock.getTime().toString());
	}

	public void setToCurrentSystemTime() {
		clock.setTime(getCurrentSystemTime());
	}

	public TimeType getCurrentSystemTime() {
		LocalDateTime currentTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		String uppercaseWeekday = currentTime.getDayOfWeek().toString();
		String weekday = uppercaseWeekday.substring(0, 1) + uppercaseWeekday.substring(1, 3).toLowerCase();
		String timeStr = currentTime.format(formatter);
		String currentTimeStr = weekday + " " + timeStr;
		return new Time(currentTimeStr);
	}

	public void addAlarm(TimeType time, List<AlarmActions> alarmActions) {
		if (alarmActions.size() == 0) {
			throw new IllegalArgumentException("Must specify alarm actions!");
		}
		AlarmType alarm = new Alarm(time);
		alarmActions.forEach(action -> {
			if (action == AlarmActions.BLINKING) {
				alarm.addAlarmAction(new BlinkAlarmAction());
			} else if (action == AlarmActions.SOUND) {
				alarm.addAlarmAction(new SoundAlarmAction());
			}
		});
		clock.addAlarm(alarm);
		System.out.println("Alarm active? " + alarm.isActive());
	}
	
	public void addAlarm(AlarmType alarm) {
		clock.addAlarm(alarm);
	}

	public Collection<AlarmType> getAlarms() {
		return clock.getAlarms();
	}

	public void startClock() {
		clock.startClock();
	}

	public void removeAllAlarms() {
		Iterator<AlarmType> iterator = getAlarms().iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
	}

	public void removeAlarm(TimeType alarmTime) {
		AlarmType alarm = new Alarm(alarmTime);
		clock.removeAlarm(alarm);
	}
	
	public void addAlarmAction(TimeType alarmTime, AlarmActions alarmAction, PropertyChangeListener listener) {
		AlarmActionType action = createAlarmAction(alarmAction);
		action.addPropertyChangeListener(listener);
		clock.getAlarm(alarmTime).addAlarmAction(action);
	}
	
	public void removeAlarmAction(TimeType alarmTime, AlarmActions alarmAction) {
		AlarmType alarm = clock.getAlarm(alarmTime);
		AlarmActionType type = createAlarmAction(alarmAction);
		Iterator<AlarmActionType> iterator = alarm.getAlarmActions().iterator();
		while (iterator.hasNext()) {
			AlarmActionType current = iterator.next();
			if (current.getClass() == type.getClass()) {
				iterator.remove();
			}
		}
	}
	
	public boolean isAlarmActive(TimeType time) {
		return clock.getAlarm(time).isActive();
	}
	
	public void setAlarmActive(TimeType time, boolean active) {
		clock.getAlarm(time).setActive(active);
	}

	public void addListenerForAlarm(AlarmType alarm, PropertyChangeListener listener) {
		for (AlarmActionType alarmAction : alarm.getAlarmActions()) {
			if (alarmAction instanceof BlinkAlarmAction) {
				BlinkAlarmAction blinkAlarmAction = (BlinkAlarmAction) alarmAction;
				blinkAlarmAction.addPropertyChangeListener(listener);
			} else if (alarmAction instanceof SoundAlarmAction) {
				SoundAlarmAction soundAlarmAction = (SoundAlarmAction) alarmAction;
				soundAlarmAction.addPropertyChangeListener(listener);
			}
		}
	}
	
	private AlarmActionType createAlarmAction(AlarmActions alarmAction) {
		switch (alarmAction) {
		case BLINKING:
			return new BlinkAlarmAction();
		case SOUND:
			return new SoundAlarmAction();
		default:
			return null;
		}
	}
	
	public Collection<AlarmType> getStoredAlarms() {
		return alarmDao.getAll();
	}
	
	public void storeAlarms() {
		alarmDao.saveAll(clock.getAlarms());
	}
}
