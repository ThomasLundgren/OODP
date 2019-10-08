package se.hig.thlu.view;

import java.awt.Color;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import se.hig.thlu.control.ClockController;
import se.hig.thlu.control.ClockController.AlarmActions;
import se.hig.thlu.model.time.Time;
import se.hig.thlu.model.time.TimeType;
import se.hig.thlu.util.ArrayFiller;
import se.hig.thlu.util.NumberConverter;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel {

	private static final String BLINKING = "Blinking";
	private static final String SOUND = "Sound";
	private static final String BLINKING_AND_SOUND = "Blinking & Sound";
	private final ClockController clockController;
	private final JButton addAlarmButton = new JButton();
	private final JButton setTimeButton = new JButton();
	private final String[] alarmOptions = {BLINKING, SOUND, BLINKING_AND_SOUND};
	private final String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
	private final String[] hours = ArrayFiller.fillIncrementallyWithLeadingZeros(new String[24]);
	private final String[] minutes = ArrayFiller.fillIncrementallyWithLeadingZeros(new String[60]);
	private final String[] seconds = minutes;
	private final JComboBox<String> alarmOptionList = new JComboBox<>(alarmOptions);
	private final JComboBox<String> dayList = new JComboBox<>(days);
	private final JComboBox<String> hourList = new JComboBox<>(hours);
	private final JComboBox<String> minuteList = new JComboBox<>(minutes);
	private final JComboBox<String> secondList = new JComboBox<>(seconds);

	public ControlPanel(ClockController clockController) {
		this.clockController = clockController;
		configureComponents();
		addComponents();
	}

	private void configureComponents() {
		TitledBorder title = new TitledBorder("Control Panel");
		title.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		title.setTitleFont(title.getTitleFont().deriveFont(15.0f));
		title.setTitleJustification(TitledBorder.CENTER);
		setBorder(title);
		// configure Dialogs
		setComboBoxOptionsToCurrentTime();
		JComponent[] setAlarmDialogInputs = new JComponent[] {
				new JLabel("Day:"), dayList,
				new JLabel("Hour:"), hourList,
				new JLabel("Minute"), minuteList,
				new JLabel("Second"), secondList,
				new JLabel("Alarm actions"), alarmOptionList
		};
		JComponent[] setTimeDialogInputs = Arrays.copyOfRange(setAlarmDialogInputs, 0, setAlarmDialogInputs.length -2);
		// configure buttons
		addAlarmButton.setText("Add alarm");
		addAlarmButton.addActionListener(buttonClick -> {
			setComboBoxOptionsToCurrentTime();
			int result = JOptionPane.showConfirmDialog(null, setAlarmDialogInputs, "Add alarm", JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.OK_OPTION) {
				String timeStr = getChosenTimeOptions();
				String alarmStr = (String) alarmOptionList.getSelectedItem();
				
				List<AlarmActions> alarmActions = new LinkedList<AlarmActions>();
				if (alarmStr.equals(BLINKING)) {
					alarmActions.add(AlarmActions.BLINKING);
				} else if (alarmStr.equals(SOUND)) {
					System.out.println("SoundAlarmAction added to alarm");
					alarmActions.add(AlarmActions.SOUND);
				} else if (alarmStr.equals(BLINKING_AND_SOUND)) {
					alarmActions.add(AlarmActions.BLINKING);
					alarmActions.add(AlarmActions.SOUND);
				}
				clockController.addAlarm(new Time(timeStr), alarmActions);
			}
		});
		setTimeButton.setText("Set time");
		setTimeButton.addActionListener(buttonClick -> {
			setComboBoxOptionsToCurrentTime();
			int result = JOptionPane.showConfirmDialog(null, setTimeDialogInputs, "Set time", JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.OK_OPTION) {
				String timeStr = getChosenTimeOptions();
				clockController.setTime(new Time(timeStr));
				setComboBoxOptionsToCurrentTime();
			}
		});
	}

	private void setComboBoxOptionsToCurrentTime() {
		TimeType currentTime = clockController.getTime();
		dayList.setSelectedItem(TimeType.convertToDayString(currentTime.getDay()));
		hourList.setSelectedItem(NumberConverter.convertIntToDoubleDigitString(currentTime.getHour()));
		minuteList.setSelectedItem(NumberConverter.convertIntToDoubleDigitString(currentTime.getMinute()));
		secondList.setSelectedItem(NumberConverter.convertIntToDoubleDigitString(currentTime.getSecond()));
	}

	private String getChosenTimeOptions() {
		String timeStr = "" + dayList.getSelectedItem();
		timeStr += " " + hourList.getSelectedItem();
		timeStr += ":" + minuteList.getSelectedItem();
		timeStr += ":" + secondList.getSelectedItem();
		return timeStr;
	}
	
	private void addComponents() {
		add(addAlarmButton);
		add(setTimeButton);
	}

}
