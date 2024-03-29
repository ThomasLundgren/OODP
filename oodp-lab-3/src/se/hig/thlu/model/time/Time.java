package se.hig.thlu.model.time;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Time implements TimeType {

	private int second, minute, hour, day;
	private static final String[] DAY_NAMES = { "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun" };
	private boolean hasDay = false;

	public Time(int hour, int minute, int second) {
		setHour(hour);
		setMinute(minute);
		setSecond(second);
	}

	public Time(int day, int hour, int minute, int second) {
		this(hour, minute, second);
		setDay(day);
	}

	public Time(String time) {
		// kolla format Hh:Mm:Ss (H: 0-2, h: 0-9, M: 0-5, m: 0-9, S: 0-5, s: 0-9)
		Pattern plainPattern = Pattern.compile("^(2[0-3]|[01]?[0-9]):([0-5]?[0-9]):([0-5]?[0-9])$");
		Matcher plainMatcher = plainPattern.matcher(time);
		// kolla format Day Hh:Mm:Ss (Day: Mon, Tue, ..., H: 0-2, h: 0-9, M: 0-5, m:
		// 0-9, S: 0-5, s: 0-9)
		Pattern weekDayPattern = Pattern
				.compile("^(Mon|Tue|Wed|Thu|Fri|Sat|Sun) (2[0-3]|[01]?[0-9]):([0-5]?[0-9]):([0-5]?[0-9])$");
		Matcher weekDayMatcher = weekDayPattern.matcher(time);
		if (weekDayMatcher.find()) {
			String[] parts = time.split(" |:");
			for (int day = 0; day < DAY_NAMES.length; day++)
				if (DAY_NAMES[day].equals(parts[0]))
					setDay(day);
			setHour(Integer.parseInt(parts[1]));
			setMinute(Integer.parseInt(parts[2]));
			setSecond(Integer.parseInt(parts[3]));
		} else if (plainMatcher.find()) {
			String[] parts = time.split(":");
			setHour(Integer.parseInt(parts[0]));
			setMinute(Integer.parseInt(parts[1]));
			setSecond(Integer.parseInt(parts[2]));
		} else
			throw new RuntimeException("Illegal time format: " + time);
	}

	@Override
	public int getSecond() {
		return second;
	}

	@Override
	public void setSecond(int second) {
		if (isNegative(second)) {
			throw new IllegalArgumentException("Seconds cannot be less than zero!");
		}
		this.second = second % 60;
	}

	@Override
	public int getMinute() {
		return minute;
	}

	@Override
	public void setMinute(int minute) {
		if (isNegative(minute)) {
			throw new IllegalArgumentException("Minutes cannot be less than zero!");
		}
		this.minute = minute % 60;
	}

	@Override
	public int getHour() {
		return hour;
	}

	@Override
	public void setHour(int hour) {
		if (isNegative(hour)) {
			throw new IllegalArgumentException("Hours cannot be less than zero!");
		}
		this.hour = hour % 24;
	}

	@Override
	public boolean hasDay() {
		return hasDay;
	}

	@Override
	public int getDay() {
		return day;
	}

	@Override
	public void setDay(int day) {
		if (isNegative(day)) {
			throw new IllegalArgumentException("Day cannot be less than zero!");
		}
		this.day = day % 7;
		hasDay = true;
	}

	@Override
	public int compareTo(TimeType time) {
		int thisSeconds = 0;
		int compareToSeconds = 0;
		thisSeconds = second + minute * 60 + hour * (60 * 60) + day * (24 * 60 * 60);
		compareToSeconds = time.getSecond() + time.getMinute() * 60 + time.getHour() * (60 * 60)
				+ time.getDay() * (24 * 60 * 60);

		if (thisSeconds > compareToSeconds) {
			return 1;
		} else if (thisSeconds < compareToSeconds) {
			return -1;
		} else {
			return 0;
		}
	}

	@Override
	public String toString() {
		char[] array = new char[12];
		array[0] = DAY_NAMES[day].charAt(0);
		array[1] = DAY_NAMES[day].charAt(1);
		array[2] = DAY_NAMES[day].charAt(2);
		array[3] = ' ';
		array[4] = (char) ('0' + hour / 10);
		array[5] = (char) ('0' + hour % 10);
		array[6] = ':';
		array[7] = (char) ('0' + minute / 10);
		array[8] = (char) ('0' + minute % 10);
		array[9] = ':';
		array[10] = (char) ('0' + second / 10);
		array[11] = (char) ('0' + second % 10);
		return new String(array);

//    StringBuffer buffer = new StringBuffer("            ");
//    buffer.setCharAt(0, dayNames[day].charAt(0));
//    buffer.setCharAt(1, dayNames[day].charAt(1));
//    buffer.setCharAt(2, dayNames[day].charAt(2));
//    buffer.setCharAt(3, ' ');
//    buffer.setCharAt(4, (char)('0' + hour/10));
//    buffer.setCharAt(5, (char)('0' + hour%10));
//    buffer.setCharAt(6, ':');
//    buffer.setCharAt(7, (char)('0' + minute/10));
//    buffer.setCharAt(8, (char)('0' + minute%10));
//    buffer.setCharAt(9, ':');
//    buffer.setCharAt(10, (char)('0' + second/10));
//    buffer.setCharAt(11, (char)('0' + second%10));
//    return buffer.toString();
		// return String.format("%s %02d:%02d:%02d", dayNames[day], hour, minute,
		// second);
	}
	
	private boolean isNegative(int number) {
		return number < 0;
	}

}
