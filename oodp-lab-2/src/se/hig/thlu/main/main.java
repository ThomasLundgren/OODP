package se.hig.thlu.main;

import se.hig.thlu.alarm.Alarm;
import se.hig.thlu.alarm.AlarmActionType;
import se.hig.thlu.alarm.AlarmType;
import se.hig.thlu.alarm.PrintAlarmAction;
import se.hig.thlu.clock.PrintTime;
import se.hig.thlu.clock.WeekAlarmClock;
import se.hig.thlu.time.Time;

public class main {

	public static void main(String[] args) {

		AlarmActionType printAlarmAction1 = new PrintAlarmAction();
		AlarmActionType printAlarmAction2 = new PrintAlarmAction();
		AlarmType alarm1 = new Alarm(new Time("00:00:05"), printAlarmAction1);
		AlarmType alarm2 = new Alarm(new Time("00:00:07"), printAlarmAction2);
		WeekAlarmClock alarmClock = new WeekAlarmClock(new Time("00:00:00"), alarm1);
		alarmClock.addAlarm(alarm2);
		alarmClock.startClock(PrintTime.YES);
	}
}
