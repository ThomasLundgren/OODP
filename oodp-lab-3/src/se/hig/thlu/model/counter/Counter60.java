package se.hig.thlu.model.counter;

public class Counter60 extends SettableLinkedAbstractCounter {

	public Counter60(CounterType nextCounter) {
		super(60, Direction.INCREASING, nextCounter);
	}
}
