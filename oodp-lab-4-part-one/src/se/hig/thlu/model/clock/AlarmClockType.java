package se.hig.thlu.model.clock;

import java.util.Collection;

import se.hig.thlu.model.PropertyChangeBroadcaster;
import se.hig.thlu.model.alarm.AlarmType;
import se.hig.thlu.model.time.TimeType;

public interface AlarmClockType extends PropertyChangeBroadcaster {

	public static final String TIME_CHANGED = "TIME CHANGED";
	public static final String ALARM_ADDED = "ALARM ADDED";
	public static final String ALARM_REMOVED = "ALARM REMOVED";

	public void tickTock();

	public void setTime(TimeType time);

	public TimeType getTime();

	public void startClock();

	public void stopClock();

	public void resetClock();

	public String toString();

	public void addAlarm(AlarmType alarm);

	public void removeAlarm(AlarmType alarm);

	public AlarmType getAlarm(TimeType time);
	
	public Collection<AlarmType> getAlarms();
}
