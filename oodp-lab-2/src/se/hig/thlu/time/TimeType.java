package se.hig.thlu.time;

public interface TimeType extends Comparable<TimeType> {
	public int getSecond();

	public void setSecond(int second);

	public int getMinute();

	public void setMinute(int minute);

	public int getHour();

	public void setHour(int hour);

	public int getDay();

	public void setDay(int day);

	public boolean hasDay();

	public String toString();
}
