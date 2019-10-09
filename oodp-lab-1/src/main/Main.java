package main;

import java.time.LocalTime;

import counter.ClockCounter;
import counter.Counter;
import counter.Counter24;
import counter.ClockCounter.PrintTime;

public class Main {
	
	public static void main(String[] args) {
		ClockCounter counter = new ClockCounter();
		counter.startCount(PrintTime.YES);
	}
}
