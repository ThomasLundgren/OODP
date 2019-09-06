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
	void test() {
		fail("Not yet implemented");
	}

}
