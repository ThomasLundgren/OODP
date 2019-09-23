package se.hig.thlu.counter;

public class SettableCounter extends AbstractCounter implements SettableCounterType {

	public SettableCounter(int countSpace, Direction direction) {
		super(countSpace, direction);
	}

	@Override
	public void setCount(int value) {
		if (value < 0 || value > COUNT_SPACE) {
			throw new IllegalArgumentException(
					"Value cannot be less than zero or exceed the specified" + " count space of the Counter!");
		}
		this.countedValue = value;
	}

}
