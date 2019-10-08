package se.hig.thlu.model.alarm;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import se.hig.thlu.model.time.TimeType;

public class AlarmManager {
	private Map<String, AlarmType> map = new HashMap<String, AlarmType>();

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

	public AlarmType getAlarm(TimeType time) {
		return map.get(time.toString());
	}
	
	public void checkForAlarm(TimeType time) {
		Objects.requireNonNull(time, "Time cannot be null.");
		AlarmType alarm = map.get(time.toString());
		if (alarm != null && alarm.isActive()) {
			alarm.doAlarm();
			System.out.println(alarm.toString());
		}
			
	}
}
