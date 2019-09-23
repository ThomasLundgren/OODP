package se.hig.thlu.alarm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.hig.thlu.time.Time;
import se.hig.thlu.time.TimeType;

class AlarmManagerTest {

	private AlarmManager alarmManager;
	private AlarmActionType alarmAction;
	private AlarmType alarm;
	private TimeType time;
	private PrintStream printStream;
	private PrintStream sysOut;
	private OutputStream outputStream;
	
	@BeforeEach
	void setUp() throws Exception {
		time = new Time(0, 0, 0);
		alarmAction = new PrintAlarmAction();
		alarm = new Alarm(time, alarmAction);
		alarmManager = new AlarmManager();
		outputStream = new ByteArrayOutputStream();
		sysOut = System.out;
		printStream = new PrintStream(outputStream);
		System.setOut(printStream);
	}

	@AfterEach
	void tearDown() throws Exception {
		alarmManager = null;
		time = null;
		alarmAction = null;
		alarm = null;
		outputStream = null;
		printStream = null;
		System.out.flush();
		System.setOut(sysOut);
	}

	@Test
	void addAlarm_passNull_throwsNpe() {
		assertThrows(NullPointerException.class, () -> {
			alarmManager.addAlarm(null);
		});
	}
	
	@Test
	void getAlarms_afterAddAlarm_alarmIsStored() {
		alarmManager.addAlarm(alarm);
		assertTrue(alarmManager.getAlarms().contains(alarm));
	}
	
	@Test
	void removeAlarm_afterAddAlarm_removesAlarm() {
		alarmManager.addAlarm(alarm);
		alarmManager.removeAlarm(alarm);
		assertTrue(alarmManager.getAlarms().isEmpty());
	}
	
	@Test
	void removeAlarm_passNull_throwsNpe() {
		assertThrows(NullPointerException.class, () -> {
			alarmManager.removeAlarm(null);
		});
	}
	
	@Test
	void checkForAlarm_passNull_throwsNpe() {
		assertThrows(NullPointerException.class, () -> {
			alarmManager.checkForAlarm(null);
		});
	}
	
	@Test
	void checkForAlarm_passContainingAlarmTime_triggersAlarmAction() {
		alarmManager.addAlarm(alarm);
		alarmManager.checkForAlarm(time);
		assertEquals("Alarm activated!" + System.lineSeparator(), outputStream.toString());
	}
	
	@Test
	void checkForAlarm_passNonContainedAlarmTime_doesNotTriggerAlarmAction() {
		alarmManager.addAlarm(alarm);
		TimeType newTime = new Time(1, 1, 1);
		assertTrue(newTime != time);
		alarmManager.checkForAlarm(newTime);
		assertFalse(("Alarm activated!" + System.lineSeparator()).equals(outputStream.toString()));
		
	}

}
