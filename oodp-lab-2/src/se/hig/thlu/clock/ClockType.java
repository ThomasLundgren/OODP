package se.hig.thlu.clock;

import se.hig.thlu.time.TimeType;

public interface ClockType {

	public void tickTock();
	
	public void setTime(TimeType time);

	public TimeType getTime();
	
	public void startClock(PrintTime printTime);
	
	public void stopClock();
	
	public void resetClock();

	public String toString();
	
}
