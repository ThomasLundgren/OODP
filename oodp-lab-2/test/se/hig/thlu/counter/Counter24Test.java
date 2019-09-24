package se.hig.thlu.counter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.hig.thlu.counter.AbstractCounter.Direction;

class Counter24Test {

	private SettableLinkedAbstractCounter counter24;
	private SettableAbstractCounter counter7;

	@BeforeEach
	void setUp() throws Exception {
		counter7 = new Counter7();
		counter24 = new Counter24(counter7);
	}

	@AfterEach
	void tearDown() throws Exception {
		counter24 = null;
		counter7 = null;
	}

	@Test
	void getCount_onNewCounter_shouldReturnZero() {
		assertEquals(0, counter24.getCount());
	}

	@Test
	void getCount_after23Counts_shouldReturn23() {
		for (int i = 0; i < 23; i++) {
			counter24.count();
		}
		assertEquals(23, counter24.getCount());
	}

	@Test
	void getCount_after24Counts_shouldOverflowAndReturnZero() {
		for (int i = 0; i < 24; i++) {
			counter24.count();
		}
		assertEquals(0, counter24.getCount());
	}

	@Test
	void getCount_after24Counts_shouldOverflowAndCounter7Count() {
		for (int i = 0; i < 24; i++) {
			counter24.count();
		}
		assertEquals(1, counter7.getCount());
	}

	@Test
	void reset_afterCount_getCountReturnZero() {
		counter24.count();
		assertEquals(1, counter24.getCount());
		counter24.reset();
		assertEquals(0, counter24.getCount());
	}

	@Test
	void pause_onCounterAtZero_shouldReturnZero() {
		counter24.pause();
		counter24.count();
		counter24.count();
		assertEquals(0, counter24.getCount());
	}

	@Test
	void resume_afterPausingAtZero_countIncreases() {
		counter24.pause();
		counter24.count();
		assertEquals(0, counter24.getCount());
		counter24.resume();
		counter24.count();
		assertEquals(1, counter24.getCount());
	}

	@Test
	void setDirection_countDownFromZero_overflowsTo23() {
		assertEquals(0, counter24.getCount());
		counter24.count();
		assertEquals(1, counter24.getCount());
		counter24.setDirection(Direction.DECREASING);
		counter24.count();
		assertEquals(0, counter24.getCount());
		counter24.count();
		assertEquals(23, counter24.getCount());
	}

	@Test
	void setDirection_countDownFromZero_overflowsAndCounter7Count() {
		assertEquals(0, counter24.getCount());
		counter24.count();
		assertEquals(1, counter24.getCount());
		counter24.setDirection(Direction.DECREASING);
		counter24.count();
		assertEquals(0, counter24.getCount());
		counter24.count();
		assertEquals(23, counter24.getCount());
		assertEquals(1, counter7.getCount());
	}

	@Test
	void toString_onEmptyCounter_returnsString0() {
		assertEquals("0", counter24.toString());
	}
}
