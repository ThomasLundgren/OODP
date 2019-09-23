package se.hig.thlu.time;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TimeTest {

	private TimeType timeWithDay;
	private TimeType timeWithoutDay;
	private TimeType timeStringConstructorWithDay;
	private TimeType timeStringConstructorWithoutDay;
	
	@BeforeEach
	void setUp() throws Exception {
		timeWithDay = new Time(1, 0, 0, 0);
		timeWithoutDay = new Time(0, 0, 0);
		timeStringConstructorWithDay = new Time("Tue 00:00:00");
		timeStringConstructorWithoutDay = new Time("00:00:00");
	}

	@AfterEach
	void tearDown() throws Exception {
		timeWithDay = null;
		timeWithoutDay = null;
		timeStringConstructorWithDay = null;
		timeStringConstructorWithoutDay = null;
	}
	
	@Test
	void timeStringConstructorWithDay_24hours_throwsException() {
		assertThrows(RuntimeException.class, () -> {
			new Time("Mon 24:00:00");
		});
	}
	
	@Test
	void timeStringConstructorWithDay_misspelledDay_throwsException() {
		assertThrows(RuntimeException.class, () -> {
			new Time("Mond 24:00:00");
		});
		assertThrows(RuntimeException.class, () -> {
			new Time("Mo 24:00:00");
		});
	}
	
	@Test
	void getDaySecMinHour_onNewTimeStringConstructorWithDay_is1Day0H0m0s() {
		assertEquals(1, timeStringConstructorWithDay.getDay());
		assertEquals(0, timeStringConstructorWithDay.getHour());
		assertEquals(0, timeStringConstructorWithDay.getMinute());
		assertEquals(0, timeStringConstructorWithDay.getSecond());
	}
	
	@Test
	void timeStringConstructorWithoutDay_24hours_throwsException() {
		assertThrows(RuntimeException.class, () -> {
			new Time("Mon 24:00:00");
		});
	}
	
	@Test
	void getDaySecMinHour_onNewTimeStringConstructorWithoutDay_is1Day0H0m0s() {
		assertEquals(0, timeStringConstructorWithoutDay.getDay());
		assertEquals(0, timeStringConstructorWithoutDay.getHour());
		assertEquals(0, timeStringConstructorWithoutDay.getMinute());
		assertEquals(0, timeStringConstructorWithoutDay.getSecond());
	}

	@Test
	void hourMinSecConstructor_withTooLowHour_throwsException() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Time(-1, 0, 0);
		});
	}
	
	@Test
	void hourMinSecConstructor_withTooLowMin_throwsException() {
		assertThrows(IllegalArgumentException.class, () -> {
			timeWithDay = new Time(0, -1, 0);
		});
	}

	@Test
	void hourMinSecConstructor_withTooLowSec_throwsException() {
		assertThrows(IllegalArgumentException.class, () -> {
			timeWithDay = new Time(0, 0, -1);
		});
	}
	
	@Test
	void getDaySecMinHour_onNewTimeWithDay_is1Day0H0m0s() {
		assertEquals(1, timeWithDay.getDay());
		assertEquals(0, timeWithDay.getHour());
		assertEquals(0, timeWithDay.getMinute());
		assertEquals(0, timeWithDay.getSecond());
	}
	
	@Test
	void getDaySecMinHour_onNewTimeWithoutDay_is0Day0H0m0s() {
		assertEquals(0, timeWithoutDay.getDay());
		assertEquals(0, timeWithoutDay.getHour());
		assertEquals(0, timeWithoutDay.getMinute());
		assertEquals(0, timeWithoutDay.getSecond());
	}
	
	@Test
	void hasDay_onTimeWithoutDay_returnsFalse() {
		assertFalse(timeWithoutDay.hasDay());
	}
	
	@Test
	void hasDay_onDayHourMinSecConstructor_returnsTrue() {
		assertTrue(timeWithDay.hasDay());
	}
	
	@Test
	void hasDay_onTimeStringConstructorWithoutDay_returnsFalse() {
		assertFalse(timeStringConstructorWithoutDay.hasDay());
	}
		
	@Test
	void setSecond_143_returns23() {
		timeWithDay.setSecond(143);
		assertEquals(23, timeWithDay.getSecond());
	}
	
	@Test
	void setMinute_121_returnsOne() {
		timeWithDay.setMinute(121);
		assertEquals(1, timeWithDay.getMinute());
	}
	
	@Test
	void setHour_49_returnsOne() {
		timeWithDay.setHour(49);
		assertEquals(1, timeWithDay.getHour());
	}
}
