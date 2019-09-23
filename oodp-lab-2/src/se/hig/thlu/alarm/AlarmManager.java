package se.hig.thlu.alarm;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

import se.hig.thlu.time.TimeType;

public class AlarmManager {
	private HashMap<String, AlarmType> map = new HashMap<String, AlarmType>();

	public void addAlarm(AlarmType alarm) {
		Objects.requireNonNull(alarm, "Alarm cannot be null.");
		map.put(alarm.toString(), alarm);
	}

	public void removeAlarm(AlarmType alarm) {
		Objects.requireNonNull(alarm, "Alarm cannot be null.");
		map.remove(alarm.toString());
	}

	public Collection<AlarmType> getAlarms() {
		return map.values();
	}

	public void checkForAlarm(TimeType time) {
		Objects.requireNonNull(time, "Time cannot be null.");
		AlarmType alarm = map.get(time.toString());
		if (alarm != null && alarm.isActive())
			alarm.doAlarm();
	}
}
