package counter;

import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

public class ClockCounter {

	private Counter hoursCounter;
	private Counter secondsCounter;
	private Counter minutesCounter;
	private Timer timer;
	
	public enum PrintTime {YES, NO};
	
	public ClockCounter() {
		hoursCounter = new Counter24();
		minutesCounter = new Counter60(hoursCounter);
		secondsCounter = new Counter60(minutesCounter);
	}

	public void count() {
		secondsCounter.count();
	}
	
	public LocalTime getTime() {
		return LocalTime.of(hoursCounter.getCount(), minutesCounter.getCount(), secondsCounter.getCount());
	}
	
	public void startCount(PrintTime printTime) {
		if (timer == null) {
			timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {
				public void run()  {
					count();
					if (printTime == PrintTime.YES) {
						System.out.println(getTime().toString());
					}
				}
			}, 0, 1000);
		}
	}
	
	public void stopCount() {
		timer.cancel();
	}
	
	public void resetClock() {
		stopCount();
		hoursCounter = new Counter24();
		minutesCounter = new Counter60(hoursCounter);
		secondsCounter = new Counter60(minutesCounter);
	}
}
