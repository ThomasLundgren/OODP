package se.hig.thlu.model.alarm;

import se.hig.thlu.model.PropertyChangeBroadcaster;

public interface AlarmActionType extends PropertyChangeBroadcaster {
	public void alarmActivated();

	public void alarmDeactivated();
}
