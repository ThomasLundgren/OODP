package se.hig.thlu.gui;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import se.hig.thlu.controller.ClockController;
import se.hig.thlu.model.alarm.AlarmType;
import se.hig.thlu.model.clock.WeekAlarmClock;

@SuppressWarnings("serial")
public class AlarmPanel extends JPanel implements PropertyChangeListener {

	private final ClockController clockController;
	private final List<AlarmEntryPanel> alarmEntries = new ArrayList<>();
	private final JButton deleteAllButton = new JButton("Delete all alarms");

	public AlarmPanel(ClockController clockController) {
		this.clockController = clockController;
		configureAlarmPanel();
	}

	@Override
	public void propertyChange(PropertyChangeEvent propertyChange) {
		if (propertyChange.getPropertyName() == WeekAlarmClock.ALARM_ADDED) {
			AlarmType alarm = (AlarmType) propertyChange.getNewValue();
			AlarmEntryPanel newAlarmPanel = new AlarmEntryPanel(alarm);
			newAlarmPanel.addPropertyChangeListener(this);
			clockController.addListenerForAlarm(alarm, newAlarmPanel);

			add(newAlarmPanel);
			alarmEntries.add(newAlarmPanel);
			System.out.println("Alarm was added and AlarmPanel created and added an AlarmEntry!");
		}
		if (propertyChange.getPropertyName() == AlarmEntryPanel.REMOVE_ALARM_ENTRY) {
			AlarmEntryPanel alarmEntry = (AlarmEntryPanel) propertyChange.getNewValue();
			clockController.removeAlarm(alarmEntry.getAlarm());
			removeAlarmEntry(alarmEntry);
		}
	}

	private void configureAlarmPanel() {
		TitledBorder title = new TitledBorder("Alarms");
		title.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		title.setTitleFont(title.getTitleFont().deriveFont(15.0f));
		title.setTitleJustification(TitledBorder.CENTER);
		setBorder(title);
		add(deleteAllButton);
		deleteAllButton.addActionListener(buttonClick -> {
			Iterator<AlarmEntryPanel> iterator = alarmEntries.iterator();
			while (iterator.hasNext()) {
				AlarmEntryPanel alarmEntryPanel = iterator.next();
				iterator.remove();
				removeAlarmEntry(alarmEntryPanel);
			}
			clockController.removeAllAlarms();
		});
	}

	private void removeAlarmEntry(AlarmEntryPanel alarmEntryPanel) {
		alarmEntryPanel.stopAlarmSignals();
		remove(alarmEntryPanel);
		repaint();
		alarmEntries.remove(alarmEntryPanel);
	}

}
