package se.hig.thlu.model.alarm;

import java.util.List;

import se.hig.thlu.model.time.TimeType;

public interface AlarmType {
	public void setActive(boolean active);

	public boolean isActive();

	public void setTime(TimeType time);

	public TimeType getTime();

	public void addAlarmAction(AlarmActionType alarmAction);

	public List<AlarmActionType> getAlarmActions();
	
	public void doAlarm();
}
