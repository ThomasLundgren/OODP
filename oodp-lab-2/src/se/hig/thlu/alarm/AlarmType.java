package se.hig.thlu.alarm;

import se.hig.thlu.time.TimeType;

public interface AlarmType {
	public void setActive(boolean active);

	public boolean isActive();

	public void setTime(TimeType time);

	public TimeType getTime();

	public void addAlarmAction(AlarmActionType alarmAction);

	public void doAlarm();
}
