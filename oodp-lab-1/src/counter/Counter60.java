package counter;

public class Counter60 extends LinkedAbstractCounter {

	public Counter60(Counter nextCounter) {
		super(60, Direction.INCREASING, nextCounter);
	}

}
