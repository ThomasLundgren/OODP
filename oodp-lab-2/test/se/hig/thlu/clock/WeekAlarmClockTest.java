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
	private TimeType time;

	@BeforeEach
	void setUp() throws Exception {
		time = new Time(0, 0, 0, 0);
		alarmAction = new PrintAlarmAction();
		alarm = new Alarm(time, alarmAction);
		alarmClock = new WeekAlarmClock(time, alarm);
	}

	@AfterEach
	void tearDown() throws Exception {
		time = null;
		alarmAction = null;
		alarm = null;
		alarmClock = null;
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
