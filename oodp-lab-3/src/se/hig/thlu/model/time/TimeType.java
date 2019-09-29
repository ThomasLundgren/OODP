package se.hig.thlu.model.time;

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
	
	public static String convertToDayString(int dayNumber) {
		switch (dayNumber) {
		case 0:
			return "Mon";
		case 1:
			return "Tue";
		case 2:
			return "Wed";
		case 3:
			return "Thu";
		case 4:
			return "Fri";
		case 5:
			return "Sat";
		case 6:
			return "Sun";
		default:
			return "Unknown day";
		}
	}
}
