package se.hig.thlu.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import se.hig.thlu.alarm.Alarm;
import se.hig.thlu.alarm.AlarmActionType;
import se.hig.thlu.alarm.AlarmType;
import se.hig.thlu.alarm.PrintAlarmAction;
import se.hig.thlu.clock.PrintTime;
import se.hig.thlu.clock.WeekAlarmClock;
import se.hig.thlu.time.Time;
import se.hig.thlu.time.TimeType;

public class Main {
	
	private static WeekAlarmClock alarmClock;

	public static void main(String[] args) {

		AlarmActionType printAlarmAction1 = new PrintAlarmAction();
		AlarmActionType printAlarmAction2 = new PrintAlarmAction();
		AlarmType alarm1 = new Alarm(new Time("00:00:05"), printAlarmAction1);
		AlarmType alarm2 = new Alarm(new Time("00:00:07"), printAlarmAction2);
		alarmClock = new WeekAlarmClock(new Time("00:00:00"), alarm1);
		alarmClock.addAlarm(alarm2);
		alarmClock.startClock(PrintTime.YES);
		
//		alarmClock = new WeekAlarmClock(new Time("23:15:05"), alarm1);
//		JFrame frame = new JFrame();
//		
//		JButton button = new JButton();
//		button.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				System.out.println("First time: " + alarmClock.getTime().toString());
//				for (int i = 0; i < 60 * 60; i++) {
//					alarmClock.tickTock();
//				}
//				System.out.println("Updated time: " + alarmClock.getTime().toString());
//			}
//		});
//		JPanel panel = new JPanel();
//		button.setLabel("Increase hour");
//		panel.setSize(400, 400);
//		frame.add(panel);
//		panel.add(button);
//		frame.setSize(400, 400);
//		frame.setVisible(true);
	}
	
}
