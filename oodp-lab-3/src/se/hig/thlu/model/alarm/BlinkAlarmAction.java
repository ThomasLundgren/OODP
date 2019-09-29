package se.hig.thlu.model.alarm;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class BlinkAlarmAction implements AlarmActionType {

	public static final String BLINK_ALARM_ACTIVATED = "BLINK ALARM ACTIVATED";
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	
	@Override
	public void alarmActivated() {
		System.out.println("Blinkaction was activated");
		propertyChangeSupport.firePropertyChange(BLINK_ALARM_ACTIVATED, false, true);
	}

	@Override
	public void alarmDeactivated() {
		propertyChangeSupport.firePropertyChange(BLINK_ALARM_ACTIVATED, true, false);
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}
	
}
