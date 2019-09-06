package counter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import counter.AbstractCounter.Direction;

class Counter60Test {

	private Counter60 counter60;
	
	@BeforeEach
	void setUp() throws Exception {
		counter60 = new Counter60();
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
	void setDirection_decreasingAndCount59Times_shouldReturnMinus59() {
		counter60.setDirection(Direction.DECREASING);
		for (int i = 0; i < 59; i++) {
			counter60.count();
		}
		assertEquals(-59, counter60.getCount());
	}
	
	@Test
	void setDirection_decreasingAndCount60Times_shouldReturnMinus60() {
		counter60.setDirection(Direction.DECREASING);
		for (int i = 0; i < 60; i++) {
			counter60.count();
		}
		assertEquals(-60, counter60.getCount());
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
	void toString_onEmptyCounter_returnsString0() {
		assertEquals("0", counter60.toString());
	}
}
