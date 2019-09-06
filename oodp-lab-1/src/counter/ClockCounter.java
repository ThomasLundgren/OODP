package counter;

import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

public class ClockCounter {
	
	private final Counter secondsCounter = new Counter60(); 
	private final Counter minutesCounter = new Counter60();
	private final Counter hoursCounter = new Counter24();
	
	public void count() {
		secondsCounter.count();
		if (secondsCounter.getCount() == 0) {
			minutesCounter.count();
			if (minutesCounter.getCount() == 0) {
				hoursCounter.count();
			}
		}
	}
	
	public LocalTime getTime() {
		return LocalTime.of(hoursCounter.getCount(),
				minutesCounter.getCount(), secondsCounter.getCount());
	}
	
	public void startCount() {
		new Timer().scheduleAtFixedRate(new TimerTask() {
			public void run()  {
				count();
				System.out.println(getTime().toString());
			}
			}, 0, 1000);
	}
	
	
	public static void main(String[] args) {
		ClockCounter counter = new ClockCounter();
		counter.startCount();
	}
	
}
