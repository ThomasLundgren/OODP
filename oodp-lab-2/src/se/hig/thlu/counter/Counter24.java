package se.hig.thlu.counter;

public class Counter24 extends SettableLinkedCounter {

	public Counter24(CounterType nextCounter) {
		super(24, Direction.INCREASING, nextCounter);
	}

}
