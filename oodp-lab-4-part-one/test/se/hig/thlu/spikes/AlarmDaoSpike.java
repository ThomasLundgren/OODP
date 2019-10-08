package se.hig.thlu.spikes;

import java.util.ArrayList;
import java.util.List;

import se.hig.thlu.model.alarm.Alarm;
import se.hig.thlu.model.alarm.AlarmActionType;
import se.hig.thlu.model.alarm.AlarmType;
import se.hig.thlu.model.alarm.BlinkAlarmAction;
import se.hig.thlu.model.alarm.SoundAlarmAction;
import se.hig.thlu.model.time.Time;
import se.hig.thlu.storage.AlarmDao;
import se.hig.thlu.storage.Dao;

public class AlarmDaoSpike {

	public static void main(String[] args) {
		// Save some arbitrary alarms
		Dao<AlarmType> dao = new AlarmDao();
		List<AlarmType> alarmList = new ArrayList<AlarmType>(5);
		for (int i = 0; i < 5; i++) {
			AlarmType alarm = new Alarm(new Time(i, 15, 15, 15));
			if (i%2 == 0) {
				alarm.addAlarmAction(new SoundAlarmAction());
			} else {
				alarm.addAlarmAction(new BlinkAlarmAction());
			}
			alarmList.add(alarm);
		}
		dao.saveAll(alarmList);
		
		// Get all alarms
		for (AlarmType storedAlarm : dao.getAll()) {
			String alarmStr = storedAlarm.toString();
			for (AlarmActionType alarmAction : storedAlarm.getAlarmActions()) {
				if (alarmAction instanceof SoundAlarmAction) {
					alarmStr += " SOUND";
				}
				if (alarmAction instanceof BlinkAlarmAction) {
					alarmStr += " BLINK";
				}
			}
			System.out.println(alarmStr);
		}
	}
	
}
