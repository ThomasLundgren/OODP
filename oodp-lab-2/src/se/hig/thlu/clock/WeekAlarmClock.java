package se.hig.thlu.clock;

import java.util.Collection;
import java.util.Objects;

import se.hig.thlu.alarm.AlarmManager;
import se.hig.thlu.alarm.AlarmType;
import se.hig.thlu.time.Time;
import se.hig.thlu.time.TimeType;

public class WeekAlarmClock extends WeekClock implements AlarmClockType {

	private final AlarmManager alarmManager = new AlarmManager();

	public WeekAlarmClock(TimeType time, AlarmType alarm) {
		super(time);
		addAlarm(alarm);
	}

	public WeekAlarmClock(AlarmType alarm) {
		this(new Time(0, 0, 0, 0), alarm);
	}

	@Override
	public void tickTock() {
		super.tickTock();
		alarmManager.checkForAlarm(time);
	}

	@Override
	public void addAlarm(AlarmType alarm) {
		Objects.requireNonNull(alarm);
		alarmManager.addAlarm(alarm);
	}

	@Override
	public void removeAlarm(AlarmType alarm) {
		alarmManager.removeAlarm(alarm);
	}

	@Override
	public Collection<AlarmType> getAlarms() {
		return alarmManager.getAlarms();
	}
}
