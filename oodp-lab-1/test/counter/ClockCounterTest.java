package counter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClockCounterTest {
	
	private ClockCounter clockCounter;

	@BeforeEach
	void setUp() throws Exception {
		clockCounter = new ClockCounter();
	}

	@AfterEach
	void tearDown() throws Exception {
		clockCounter = null;
	}

	@Test
	void getTime_onNewClockCounter_returnsZero() {
		assertEquals(0, clockCounter.getTime().getSecond());
		assertEquals(0, clockCounter.getTime().getMinute());
		assertEquals(0, clockCounter.getTime().getHour());
	}
	
	@Test
	void getTime_after60Counts_returns0H1Min0Sec() {
		for (int i = 0; i < 60; i++) {
			clockCounter.count();
		}
		assertEquals(0, clockCounter.getTime().getSecond());
		assertEquals(1, clockCounter.getTime().getMinute());
		assertEquals(0, clockCounter.getTime().getHour());
	}
	
	@Test
	void getTime_after3600Counts_returns1H0Min0Sec() {
		for (int i = 0; i < 3600; i++) {
			clockCounter.count();
		}
		assertEquals(0, clockCounter.getTime().getSecond());
		assertEquals(0, clockCounter.getTime().getMinute());
		assertEquals(1, clockCounter.getTime().getHour());
	}
	
	@Test
	void getTime_after86400Counts_returns0H0Min0Sec() {
		for (int i = 0; i < 86400; i++) {
			clockCounter.count();
		}
		assertEquals(0, clockCounter.getTime().getSecond());
		assertEquals(0, clockCounter.getTime().getMinute());
		assertEquals(0, clockCounter.getTime().getHour());
	}
	
}
