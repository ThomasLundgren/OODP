package se.hig.thlu.alarm;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.hig.thlu.time.Time;
import se.hig.thlu.time.TimeType;

class AlarmTest {

	private AlarmType alarm;
	private TimeType time;
	private AlarmActionType printAlarmAction;
	private PrintStream printStream;
	private PrintStream sysOut;
	private OutputStream outputStream;
	
	
	@BeforeEach
	void setUp() throws Exception {
		time = new Time(1, 0, 0, 0);
		printAlarmAction = new PrintAlarmAction();
		alarm = new Alarm(time, printAlarmAction);
		outputStream = new ByteArrayOutputStream();
		sysOut = System.out;
		printStream = new PrintStream(outputStream);
		System.setOut(printStream);
	}

	@AfterEach
	void tearDown() throws Exception {
		time = null;
		printAlarmAction = null;
		alarm = null;
		outputStream = null;
		printStream = null;
		System.out.flush();
		System.setOut(sysOut);
	}
	
	@Test
	void constructor_passNullTimeObject_throwsNpe() {
		assertThrows(NullPointerException.class, () -> {
			new Alarm(null, printAlarmAction);
		});
	}

	@Test
	void constructor_passNullAlarmActionObject_throwsNpe() {
		assertThrows(NullPointerException.class, () -> {
			new Alarm(time, null);
		});
	}
	
	@Test
	void isActive_onNewAlarm_returnsTrue() {
		assertTrue(alarm.isActive());
	}
	
	@Test
	void setActive_onNewAlarm_isActiveFalse() {
		alarm.setActive(false);
		assertFalse(alarm.isActive());
	}
	
	@Test
	void getTime_onNewAlarm_returnsMonday0H0m0s() {
		TimeType newTime = alarm.getTime();
		assertEquals(1, newTime.getDay());
		assertEquals(0, newTime.getHour());
		assertEquals(0, newTime.getMinute());
		assertEquals(0, newTime.getSecond());
	}
	
	@Test
	void getTime_afterSetTime_returnsNewTimeObjectReference() {
		TimeType newTime = new Time(1, 1, 1);
		alarm.setTime(newTime);
		assertEquals(newTime, alarm.getTime());
	}
	
	@Test
	void setTime_passingNullObject_throwsException() {
		assertThrows(NullPointerException.class, () -> {
			alarm.setTime(null);
		});
	}
	
	@Test
	void addAlarmActionHandler_passNullReference_throwsNpe() {
		assertThrows(NullPointerException.class, () -> {
			alarm.addAlarmAction(null);
		});
	}
	
	@Test
	void doAlarm_withPrintAlarmAction_printsAlarmActivatedToSystemOut() {
		assertTrue(printAlarmAction instanceof PrintAlarmAction);
		alarm.doAlarm();
		assertEquals("Alarm activated!" + System.lineSeparator(), outputStream.toString());
	}
	
	
	

}
