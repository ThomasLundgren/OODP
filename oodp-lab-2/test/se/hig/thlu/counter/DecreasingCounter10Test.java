package se.hig.thlu.counter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.hig.thlu.counter.AbstractCounter.Direction;

class DecreasingCounter10Test {

	private SettableAbstractCounter decreasingCounter10;

	@BeforeEach
	void setUp() throws Exception {
		decreasingCounter10 = new DecreasingCounter10();
	}

	@AfterEach
	void tearDown() throws Exception {
		decreasingCounter10 = null;
	}

	@Test
	void getCount_onNewDecreasingCounter_shouldReturnZero() {
		assertEquals(0, decreasingCounter10.getCount());
	}

	@Test
	void getCount_afterTenCounts_shouldReturnZero() {
		for (int i = 0; i < 10; i++) {
			decreasingCounter10.count();
		}
		assertEquals(0, decreasingCounter10.getCount());
	}

	@Test
	void getCount_afterElevenCounts_shouldOverflowAndReturnNine() {
		for (int i = 0; i < 11; i++) {
			decreasingCounter10.count();
		}
		assertEquals(9, decreasingCounter10.getCount());
	}

	@Test
	void reset_afterCount_getCountReturnTen() {
		decreasingCounter10.count();
		assertEquals(9, decreasingCounter10.getCount());
		decreasingCounter10.reset();
		assertEquals(0, decreasingCounter10.getCount());
	}

	@Test
	void pause_onCounterAtZero_shouldReturnZero() {
		decreasingCounter10.pause();
		decreasingCounter10.count();
		assertEquals(0, decreasingCounter10.getCount());
	}

	@Test
	void resume_afterPausingAtZero_countDecreases() {
		decreasingCounter10.pause();
		decreasingCounter10.count();
		assertEquals(0, decreasingCounter10.getCount());
		decreasingCounter10.resume();
		decreasingCounter10.count();
		assertEquals(9, decreasingCounter10.getCount());
	}

	@Test
	void setDirection_countUpFromNine_overflowsToZero() {
		assertEquals(0, decreasingCounter10.getCount());
		decreasingCounter10.count();
		assertEquals(9, decreasingCounter10.getCount());
		decreasingCounter10.setDirection(Direction.INCREASING);
		decreasingCounter10.count();
		assertEquals(0, decreasingCounter10.getCount());
		decreasingCounter10.count();
		assertEquals(1, decreasingCounter10.getCount());
	}

	@Test
	void toString_onEmptyCounter_returnsString0() {
		assertEquals("0", decreasingCounter10.toString());
	}

}
