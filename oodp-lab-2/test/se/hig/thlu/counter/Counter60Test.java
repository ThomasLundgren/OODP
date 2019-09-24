package se.hig.thlu.counter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.hig.thlu.counter.AbstractCounter.Direction;

class Counter60Test {

	private SettableLinkedAbstractCounter counter60;
	private SettableLinkedAbstractCounter innerCounter;
	private SettableAbstractCounter innerInnerCounter;

	@BeforeEach
	void setUp() throws Exception {
		innerInnerCounter = new Counter7();
		innerCounter = new Counter24(innerInnerCounter);
		counter60 = new Counter60(innerCounter);
	}

	@AfterEach
	void tearDown() throws Exception {
		counter60 = null;
	}

	@Test
	void getCount_onNewCounter_shouldReturnZero() {
		assertEquals(0, counter60.getCount());
	}

	@Test
	void getCount_after59Counts_shouldReturn59() {
		for (int i = 0; i < 59; i++) {
			counter60.count();
		}
		assertEquals(59, counter60.getCount());
	}

	@Test
	void getCount_after60Counts_shouldOverflowAndReturnZero() {
		for (int i = 0; i < 60; i++) {
			counter60.count();
		}
		assertEquals(0, counter60.getCount());
	}

	@Test
	void getCount_after60Counts_overflowsInnerCounterIs1() {
		for (int i = 0; i < 60; i++) {
			counter60.count();
		}
		assertEquals(1, innerCounter.getCount());
	}

	@Test
	void reset_afterCount_getCountReturnZero() {
		counter60.count();
		assertEquals(1, counter60.getCount());
		counter60.reset();
		assertEquals(0, counter60.getCount());
	}

	@Test
	void pause_onCounterAtZero_shouldReturnZero() {
		counter60.pause();
		counter60.count();
		counter60.count();
		assertEquals(0, counter60.getCount());
	}

	@Test
	void resume_afterPausingAtZero_countIncreases() {
		counter60.pause();
		counter60.count();
		assertEquals(0, counter60.getCount());
		counter60.resume();
		counter60.count();
		assertEquals(1, counter60.getCount());
	}

	@Test
	void setDirection_countDownFromOne_overflowsTo59() {
		assertEquals(0, counter60.getCount());
		counter60.count();
		assertEquals(1, counter60.getCount());
		counter60.setDirection(Direction.DECREASING);
		counter60.count();
		assertEquals(0, counter60.getCount());
		counter60.count();
		assertEquals(59, counter60.getCount());
	}

	@Test
	void setDirection_countDownFromOne_overflowsTo59AndInnerCounterIsOne() {
		assertEquals(0, counter60.getCount());
		counter60.count();
		assertEquals(1, counter60.getCount());
		counter60.setDirection(Direction.DECREASING);
		counter60.count();
		assertEquals(0, counter60.getCount());
		counter60.count();
		assertEquals(59, counter60.getCount());
		assertEquals(1, innerCounter.getCount());
	}

	@Test
	void setCount_setBelowZero_shouldRaiseException() {
		assertThrows(IllegalArgumentException.class, () -> {
			counter60.setCount(-1);
		});
	}

	@Test
	void setCount_setAtCountSpaceLimit_shouldRaiseException() {
		assertThrows(IllegalArgumentException.class, () -> {
			counter60.setCount(60);
		});
	}

	@Test
	void toString_onEmptyCounter_returnsString0() {
		assertEquals("0", counter60.toString());
	}
}
