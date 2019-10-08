package se.hig.thlu.model.alarm;

import java.beans.PropertyChangeListener;

public class PrintAlarmAction implements AlarmActionType {

	@Override
	public void alarmActivated() {
		System.out.println("Alarm activated!");
	}

	@Override
	public void alarmDeactivated() {
		System.out.println("Alarm deactivated!");
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

}
