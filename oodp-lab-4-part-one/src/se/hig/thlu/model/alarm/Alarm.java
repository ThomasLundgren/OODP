package se.hig.thlu.model.alarm;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import se.hig.thlu.model.time.TimeType;

public class Alarm implements AlarmType {
	private boolean active;
	private TimeType time;
	private List<AlarmActionType> alarmActions = new LinkedList<AlarmActionType>();

	public Alarm(TimeType time, AlarmActionType alarmAction) {
		setTime(time);
		addAlarmAction(alarmAction);
		active = true;
	}
	
	public Alarm(TimeType time) {
		setTime(time);
		active = true;
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
		if (active == false) {
			alarmActions.forEach(alarmAction -> alarmAction.alarmDeactivated());
		}
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void setTime(TimeType time) {
		Objects.requireNonNull(time, "Time cannot be null.");
		this.time = time;
	}

	@Override
	public TimeType getTime() {
		return time;
	}

	@Override
	public void addAlarmAction(AlarmActionType alarmAction) {
		Objects.requireNonNull(alarmAction, "AlarmAction cannot be null.");
		alarmActions.add(alarmAction);
	}

	@Override
	public void doAlarm() {
		if (active)
			for (AlarmActionType alarmAction : alarmActions)
				alarmAction.alarmActivated();
	}

	public String toString() {
		return this.time.toString();
	}

	@Override
	public List<AlarmActionType> getAlarmActions() {
		return alarmActions;
	}
}
