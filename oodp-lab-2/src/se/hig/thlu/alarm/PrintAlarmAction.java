package se.hig.thlu.alarm;

public class PrintAlarmAction implements AlarmActionType {

	@Override
	public void alarmActivated() {
		System.out.println("Alarm activated!");
	}

	@Override
	public void alarmDeactivated() {
		System.out.println("Alarm deactivated!");
	}

}
