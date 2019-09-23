package se.hig.thlu.counter;

import java.util.Objects;

public class SettableLinkedCounter extends LinkedAbstractCounter implements SettableCounterType {

	public SettableLinkedCounter(int countSpace, Direction direction, CounterType nextCounter) {
		super(countSpace, direction, Objects.requireNonNull(nextCounter));
	}

	@Override
	public void setCount(int value) {
		if (value < 0 || value >= COUNT_SPACE) {
			throw new IllegalArgumentException("Value cannot be set to less than zero or more "
					+ "than the specified count space of the Counter!");
		}
		this.countedValue = value;
	}

}
