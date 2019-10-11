package se.hig.thlu.view;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import se.hig.thlu.controller.ClockController;
import se.hig.thlu.controller.ClockController.AlarmActions;
import se.hig.thlu.model.alarm.AlarmActionType;
import se.hig.thlu.model.alarm.AlarmType;
import se.hig.thlu.model.alarm.BlinkAlarmAction;
import se.hig.thlu.model.alarm.SoundAlarmAction;
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
			List<AlarmActions> actionTypes = new LinkedList<AlarmActions>();
			for (AlarmActionType action : alarm.getAlarmActions()) {
				if (action instanceof BlinkAlarmAction) {
					actionTypes.add(AlarmActions.BLINKING);
				}
				if (action instanceof SoundAlarmAction) {
					actionTypes.add(AlarmActions.SOUND);
				}
			}
			AlarmEntryPanel newAlarmPanel = new AlarmEntryPanel(actionTypes, alarm.getTime(), clockController);
			newAlarmPanel.addPropertyChangeListener(this);
			if (alarm.isActive()) {
				newAlarmPanel.setActiveButton(true);
			} else {
				newAlarmPanel.setActiveButton(false);
			}
			clockController.addListenerForAlarm(alarm, newAlarmPanel);

			add(newAlarmPanel);
			alarmEntries.add(newAlarmPanel);
		}
		if (propertyChange.getPropertyName() == AlarmEntryPanel.REMOVE_ALARM_ENTRY) {
			AlarmEntryPanel alarmEntry = (AlarmEntryPanel) propertyChange.getNewValue();
			clockController.removeAlarm(alarmEntry.getAlarmTime());
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
