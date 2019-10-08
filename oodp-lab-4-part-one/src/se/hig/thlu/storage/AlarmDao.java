package se.hig.thlu.storage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import se.hig.thlu.model.alarm.Alarm;
import se.hig.thlu.model.alarm.AlarmActionType;
import se.hig.thlu.model.alarm.AlarmType;
import se.hig.thlu.model.alarm.BlinkAlarmAction;
import se.hig.thlu.model.alarm.SoundAlarmAction;
import se.hig.thlu.model.time.Time;

public class AlarmDao implements Dao<AlarmType> {

	private final FileManager fileManager;

	public AlarmDao() {
		fileManager = FileManager.getInstance();
	}

	@Override
	public void saveAll(Collection<AlarmType> alarms) {
		Collection<String> alarmStrings = new LinkedList<>();
		try {
			for (AlarmType alarm : alarms) {
				String alarmStr = alarm.toString().replaceAll(":", " ");
				if (alarm.isActive()) {
					alarmStr += " ACTIVE";
				} else {
					alarmStr += " INACTIVE";
				}
				for (AlarmActionType action : alarm.getAlarmActions()) {
					if (action instanceof SoundAlarmAction) {
						alarmStr += " SOUND";
					} else if (action instanceof BlinkAlarmAction) {
						alarmStr += " BLINK";
					}
				}
				alarmStrings.add(alarmStr);
				System.out.println(alarmStr);
			}
			fileManager.storeTxt(alarmStrings);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	@Override
	public Collection<AlarmType> getAll() {
		Collection<AlarmType> alarms = new LinkedList<AlarmType>();
		try {
			for (String storedAlarm : fileManager.readTxtDoc()) {
				String[] parts = storedAlarm.split(" ");
				List<String> partsList = Arrays.asList(parts);
				String timeStr = partsList.get(0) + " " + partsList.get(1) + ":" + partsList.get(2) + ":" + partsList.get(3);
				AlarmType alarm = new Alarm(new Time(timeStr));
				if (partsList.contains("INACTIVE")) {
					alarm.setActive(false);
				} else {
					alarm.setActive(true);
				}
				if (partsList.contains("SOUND")) {
					alarm.addAlarmAction(new SoundAlarmAction());
				}
				if (partsList.contains("BLINK")) {
					alarm.addAlarmAction(new BlinkAlarmAction());
				}
				alarms.add(alarm);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return alarms;
	}
	
}
