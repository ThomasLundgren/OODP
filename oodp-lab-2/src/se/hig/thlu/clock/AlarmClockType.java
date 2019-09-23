package se.hig.thlu.clock;

import java.util.Collection;

import se.hig.thlu.alarm.AlarmType;

public interface AlarmClockType extends ClockType {

	public void addAlarm(AlarmType alarm);

	public void removeAlarm(AlarmType alarm);

	public Collection<AlarmType> getAlarms();
}
