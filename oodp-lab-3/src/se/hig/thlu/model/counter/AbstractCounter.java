package se.hig.thlu.model.counter;

public abstract class AbstractCounter implements CounterType {

	public enum Direction {
		INCREASING, DECREASING
	};

	protected final boolean IS_CIRCULAR;
	protected final int COUNT_SPACE;
	protected int countedValue = 0;
	protected boolean isPaused;
	protected Direction direction;

	/**
	 * @param countSpace - The amount of counts until the AbstractCounter overflows.
	 *                   The AbstractCounter starts at zero and overflows to zero if
	 *                   the Direction is set to {@link Direction#INCREASING}. For
	 *                   {@link Direction#DECREASING} Counters, the Counter starts
	 *                   at the given count space and counts down. It overflows from
	 *                   zero back to the count space.
	 * @param direction  - {@link Direction#INCREASING} or
	 *                   {@link Direction#DECREASING} sets the direction of the
	 *                   AbstractCounter.
	 */
	public AbstractCounter(int countSpace, Direction direction) {
		if (countSpace >= 2) {
			COUNT_SPACE = countSpace;
			IS_CIRCULAR = true;
		} else {
			COUNT_SPACE = 0;
			IS_CIRCULAR = false;
		}
		this.direction = direction;
	}

	@Override
	public void count() {
		if (!isPaused) {
			if (direction == Direction.INCREASING) {
				countedValue++;
				if (IS_CIRCULAR && countedValue == COUNT_SPACE) {
					countedValue = 0;
				}
			} else {
				if (IS_CIRCULAR && countedValue == 0) {
					countedValue = COUNT_SPACE - 1;
				} else {
					countedValue--;
				}

			}
		}
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	@Override
	public int getCount() {
		return countedValue;
	}

	@Override
	public void reset() {
		countedValue = 0;
	}

	@Override
	public void pause() {
		isPaused = true;
	}

	@Override
	public void resume() {
		isPaused = false;
	}

	@Override
	public String toString() {
		return Integer.toString(countedValue);
	}
}
