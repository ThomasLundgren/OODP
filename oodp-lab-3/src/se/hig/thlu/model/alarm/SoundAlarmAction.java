package se.hig.thlu.model.alarm;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SoundAlarmAction implements AlarmActionType {

	public static final String SOUND_ALARM_ACTIVATED = "SOUND ALARM ACTIVATED";
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	
	@Override
	public void alarmActivated() {
		System.out.println("SoundAlarmAction activated in SoundAlarmAction");
		propertyChangeSupport.firePropertyChange(SOUND_ALARM_ACTIVATED, false, true);
	}

	@Override
	public void alarmDeactivated() {
		propertyChangeSupport.firePropertyChange(SOUND_ALARM_ACTIVATED, true, false);
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		System.out.println("Listener added to SoundAlarmAction: " + listener.toString());
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	
}
