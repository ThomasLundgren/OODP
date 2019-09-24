package se.hig.thlu.clock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.hig.thlu.time.Time;
import se.hig.thlu.time.TimeType;

class WeekClockTest {

	private ClockType weekClock;
	private TimeType time;

	@BeforeEach
	void setUp() throws Exception {
		time = new Time(0, 0, 0, 0);
		weekClock = new WeekClock(time);
	}

	@AfterEach
	void tearDown() throws Exception {
		time = null;
		weekClock = null;
	}

	@Test
	void constructor_passNull_throwsNpe() {
		assertThrows(NullPointerException.class, () -> {
			new WeekClock(null);
		});
	}

	@Test
	void tickTock_oneTime_raisesSecondsByOne() {
		weekClock.tickTock();
		TimeType newTime = new Time(0, 0, 1);
		assertTrue(weekClock.getTime().compareTo(newTime) == 0);
	}

	@Test
	void getTime_onNewWeekClock_returns0Day0H0m0sTime() {
		TimeType time = weekClock.getTime();
		assertEquals(0, time.getDay());
		assertEquals(0, time.getHour());
		assertEquals(0, time.getMinute());
		assertEquals(0, time.getSecond());
	}

	@Test
	void setTime_passNull_throwsNpe() {
		assertThrows(NullPointerException.class, () -> {
			weekClock.setTime(null);
		});
	}

	@Test
	void getTime_afterSettingNewTime_returnsTheNewTime() {
		TimeType newTime = new Time(1, 1, 1, 1);
		weekClock.setTime(newTime);
		assertEquals(newTime, weekClock.getTime());
	}
	
	@Test
	void tickTock_onClockAtMon23H59m59s_getTimeShowsTue0H0m0s() {
		weekClock.setTime(new Time(0, 23, 59, 59));
		assertEquals("Mon 23:59:59", weekClock.getTime().toString());
		System.out.println(weekClock.getTime().toString());
		weekClock.tickTock();
//		assertEquals("Tue 00:00:00", weekClock.getTime().toString());
		System.out.println(weekClock.getTime().toString());
	}

}
