package se.hig.thlu.alarm;

public class PrintAlarmAction2 implements AlarmActionType {

	@Override
	public void alarmActivated() {
		System.out.println("Alarm activated2!");
	}

	@Override
	public void alarmDeactivated() {
		System.out.println("Alarm deactivated2!");
	}

}
