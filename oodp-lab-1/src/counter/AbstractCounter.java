package counter;

public abstract class AbstractCounter implements Counter {

	public enum Direction {INCREASING, DECREASING};
	protected final boolean IS_CIRCULAR;
	protected final int COUNT_SPACE;
	protected int countedValue;
	protected boolean isPaused;
	protected Direction direction;
//	protected Reactive nextReactive;
	
	/**
	 * 
	 * @param countSpace - The amount of counts until the AbstractCounter overflows.
	 * The AbstractCounter starts at zero and overflows to zero.
	 * @param direction - {@link Direction#INCREASING} or {@link Direction#DECREASING} sets
	 * the direction of the AbstractCounter.
	 * @param nextReactive 
	 */
	public AbstractCounter(int countSpace, Direction direction/*, Reactive nextReactive*/) {
		if (countSpace >= 2) {
			COUNT_SPACE = countSpace;
			IS_CIRCULAR = true;
		} else {
			COUNT_SPACE = 0;
			IS_CIRCULAR = false;
		}
		this.direction = direction;
//		this.nextReactive = nextReactive;
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
				countedValue--;
				if (IS_CIRCULAR && countedValue == COUNT_SPACE) {
					countedValue = 0;
				}
			}
		}
	}

	@Override
	public int getCount() {
		return countedValue;
	}

	@Override
	public void reset() {
		countedValue = 0;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
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
