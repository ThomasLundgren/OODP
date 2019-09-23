package se.hig.thlu.clock;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.hig.thlu.alarm.Alarm;
import se.hig.thlu.alarm.AlarmActionType;
import se.hig.thlu.alarm.AlarmType;
import se.hig.thlu.alarm.PrintAlarmAction;
import se.hig.thlu.time.Time;
import se.hig.thlu.time.TimeType;

class WeekAlarmClockTest {
	
	private AlarmType alarm;
	private AlarmActionType alarmAction;
	private AlarmClockType alarmClock;
	private TimeType alarmTime;
	private TimeType clockTime;

	@BeforeEach
	void setUp() throws Exception {
		alarmTime = new Time(0, 0, 0, 5);
		clockTime = new Time(0, 0, 0, 0);
		alarmAction = new PrintAlarmAction();
		alarm = new Alarm(alarmTime, alarmAction);
		alarmClock = new WeekAlarmClock(clockTime, alarm);
	}

	@AfterEach
	void tearDown() throws Exception {
		alarmTime = null;
		alarmAction = null;
		alarm = null;
		alarmClock = null;
	}
	
	@Test
	void timeAndAlarmConstructor_passNullTime_throwsNpe() {
		assertThrows(NullPointerException.class, () -> {
			new WeekAlarmClock(null, alarm);
		});
	}
	
	@Test
	void timeAndAlarmConstructor_passNullAlarm_throwsNpe() {
		assertThrows(NullPointerException.class, () -> {
			new WeekAlarmClock(clockTime, null);
		});
	}
	
	@Test
	void alarmOnlyConstructor_passNullAlarm_throwsNpe() {
		assertThrows(NullPointerException.class, () -> {
			new WeekAlarmClock(null);
		});
	}
	
	@Test
	void getAlarms_afterAddAlarm_alarmIsAdded() {
		TimeType newTime = new Time(1, 1, 1, 1);
		AlarmActionType newAlarmAction = new PrintAlarmAction();
		AlarmType newAlarm = new Alarm(newTime, newAlarmAction);
		alarmClock.addAlarm(newAlarm);
		assertTrue(alarmClock.getAlarms().contains(newAlarm));
	}
	
	@Test
	void getAlarms_afterRemoveAlarm_alarmIsRemoved() {
		TimeType newTime = new Time(1, 1, 1, 1);
		AlarmActionType newAlarmAction = new PrintAlarmAction();
		AlarmType newAlarm = new Alarm(newTime, newAlarmAction);
		alarmClock.addAlarm(newAlarm);
		
		assertTrue(alarmClock.getAlarms().contains(newAlarm));
		alarmClock.removeAlarm(newAlarm);
		assertFalse(alarmClock.getAlarms().contains(newAlarm));
	}
	
	@Test
	void tickTock_oneTime_increasesTimeByOne() {
		assertTrue(clockTime.getSecond() == 0);
		alarmClock.tickTock();
		assertTrue(clockTime.getSecond() == 1);
	}

}
