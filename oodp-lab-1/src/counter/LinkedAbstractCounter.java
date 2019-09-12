package counter;

import java.util.Objects;

public abstract class LinkedAbstractCounter extends AbstractCounter {

	private Counter nextCounter;
	
	public LinkedAbstractCounter(int countSpace, Direction direction, Counter nextCounter) {
		super(countSpace, direction);
		this.nextCounter = Objects.requireNonNull(nextCounter, "A linked Counter must be linked to another Counter!");
	}
	
	@Override
	public void count() {
		if (!isPaused) {
			if (direction == Direction.INCREASING) {
				countedValue++;
				if (IS_CIRCULAR && countedValue == COUNT_SPACE) {
					countedValue = 0;
					if (nextCounter != null) {
						nextCounter.count();
					}
				}
			} else {
				countedValue--;
				if (IS_CIRCULAR && countedValue == 0) {
					countedValue = COUNT_SPACE;
					if (nextCounter != null) {
						nextCounter.count();
					}
				}
			}
		}
	}
}
