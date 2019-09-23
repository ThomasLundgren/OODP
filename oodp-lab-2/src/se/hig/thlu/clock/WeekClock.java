package se.hig.thlu.clock;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import se.hig.thlu.counter.Counter24;
import se.hig.thlu.counter.Counter60;
import se.hig.thlu.counter.Counter7;
import se.hig.thlu.counter.SettableCounterType;
import se.hig.thlu.time.Time;
import se.hig.thlu.time.TimeType;

public class WeekClock implements ClockType {

	protected TimeType time;
	protected Timer timer;
	protected final SettableCounterType dayCounter = new Counter7();
	protected final SettableCounterType hoursCounter = new Counter24(dayCounter);
	protected final SettableCounterType minutesCounter = new Counter60(hoursCounter);
	protected final SettableCounterType secondsCounter = new Counter60(minutesCounter);

	public WeekClock() {
		setTime(new Time(0, 0, 0, 0));
	}

	public WeekClock(TimeType time) {
		setTime(time);

		dayCounter.setCount(time.getDay());
		hoursCounter.setCount(time.getHour());
		minutesCounter.setCount(time.getMinute());
		secondsCounter.setCount(time.getSecond());
	}

	@Override
	public void tickTock() {
		secondsCounter.count();
		time.setDay(dayCounter.getCount());
		time.setHour(hoursCounter.getCount());
		time.setMinute(minutesCounter.getCount());
		time.setSecond(secondsCounter.getCount());
	}

	@Override
	public void setTime(TimeType time) {
		this.time = Objects.requireNonNull(time);
	}

	@Override
	public TimeType getTime() {
		return time;
	}

	@Override
	public void startClock(PrintTime printTime) {
		if (timer == null) {
			timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {
				public void run() {
					tickTock();
					if (printTime == PrintTime.YES) {
						System.out.println(getTime().toString());
					}
				}
			}, 0, 1000);
		}
	}

	@Override
	public void stopClock() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	@Override
	public void resetClock() {
		stopClock();
		dayCounter.reset();
		hoursCounter.reset();
		minutesCounter.reset();
		secondsCounter.reset();
	}

}
