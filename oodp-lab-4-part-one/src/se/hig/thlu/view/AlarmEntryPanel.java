package se.hig.thlu.view;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import se.hig.thlu.control.ClockController;
import se.hig.thlu.control.ClockController.AlarmActions;
import se.hig.thlu.model.PropertyChangeBroadcaster;
import se.hig.thlu.model.alarm.BlinkAlarmAction;
import se.hig.thlu.model.alarm.SoundAlarmAction;
import se.hig.thlu.model.time.Time;
import se.hig.thlu.model.time.TimeType;

@SuppressWarnings("serial")
class AlarmEntryPanel extends JPanel implements PropertyChangeBroadcaster, PropertyChangeListener {

	public static final String REMOVE_ALARM_ENTRY = "REMOVE ALARM ENTRY";
	private final ClockController clockController;
	private final List<AlarmActions> actionTypes;
	private final TimeType alarmTime;
	private final JButton alarmActiveButton = new JButton("Active");
	private final JButton removeAlarmButton = new JButton("Remove");
	private final JButton soundButton = new JButton();
	private final JButton blinkButton = new JButton();
	private final JLabel dateLabel;
	private final JLabel hourLabel;
	private final JLabel minuteLabel;
	private final JLabel secondLabel;
	private final GuiBlinkAlarmAction blinkAction;
	private final GuiSoundAlarmAction soundAction;

	AlarmEntryPanel(List<AlarmActions> actionTypes, TimeType time, ClockController clockController) {
		this.actionTypes = actionTypes;
		this.alarmTime = time;
		this.clockController = clockController;

		dateLabel = new JLabel(TimeType.convertToDayString(alarmTime.getDay()));
		hourLabel = new JLabel(Integer.toString(alarmTime.getHour()));
		minuteLabel = new JLabel(Integer.toString(alarmTime.getMinute()));
		secondLabel = new JLabel(Integer.toString(alarmTime.getSecond()));

		blinkAction = new GuiBlinkAlarmAction(this);
		soundAction = new GuiSoundAlarmAction(this);

		setUpComponents();
	}

	@Override
	public void propertyChange(PropertyChangeEvent propertyChange) {
		if (propertyChange.getPropertyName() == BlinkAlarmAction.BLINK_ALARM_ACTIVATED) {
			System.out.println("AlarmEntryPanel reacted to blink alarm activation");
			if ((boolean) propertyChange.getNewValue() == true) {
				blinkAction.startBlinking();
			}
			if ((boolean) propertyChange.getNewValue() == false) {
				blinkAction.stopBlinking();
			}
		}
		if (propertyChange.getPropertyName() == SoundAlarmAction.SOUND_ALARM_ACTIVATED) {
			System.out.println("AlarmEntryPanel reacted to sound alarm activation");
			if ((boolean) propertyChange.getNewValue() == true) {
				soundAction.startAlarmSound();
			}
			if ((boolean) propertyChange.getNewValue() == false) {
				soundAction.stopAlarmSound();
			}
		}
	}

	void stopAlarmSignals() {
		blinkAction.stopBlinking();
		soundAction.stopAlarmSound();
	}

	TimeType getAlarmTime() {
		return new Time(alarmTime.toString());
	}
	
	void setActiveButton(boolean active) {
		if (active) {
			clockController.setAlarmActive(alarmTime, true);
			alarmActiveButton.setText("Active");
			alarmActiveButton.setBackground(Color.GREEN);
		} else {
			clockController.setAlarmActive(alarmTime, false);
			alarmActiveButton.setText("Inactive");
			alarmActiveButton.setBackground(Color.RED);
		}
	}

	private void setUpComponents() {
		if (actionTypes.contains(AlarmActions.SOUND)) {
			soundButton.setText("Sound active");
			soundButton.setBackground(Color.GREEN);
		} else {
			soundButton.setText("Sound inactive");
			soundButton.setBackground(Color.RED);
		}

		if (actionTypes.contains(AlarmActions.BLINKING)) {
			blinkButton.setText("Blink active");
			blinkButton.setBackground(Color.GREEN);
		} else {
			blinkButton.setText("Blink inactive");
			blinkButton.setBackground(Color.RED);
		}

		soundButton.addActionListener(click -> {
			if (soundButton.getText().contains("inactive")) {
				soundButton.setText("Sound active");
				soundButton.setBackground(Color.GREEN);
				clockController.addAlarmAction(alarmTime, AlarmActions.SOUND, this);
			} else {
				soundButton.setText("Sound inactive");
				soundButton.setBackground(Color.RED);
				soundAction.stopAlarmSound();
				clockController.removeAlarmAction(alarmTime, AlarmActions.SOUND);
			}
		});
		blinkButton.addActionListener(click -> {
			if (blinkButton.getText().contains("inactive")) {
				blinkButton.setText("Blink active");
				blinkButton.setBackground(Color.GREEN);
				clockController.addAlarmAction(alarmTime, AlarmActions.BLINKING, this);
			} else {
				blinkButton.setText("Blink inactive");
				blinkButton.setBackground(Color.RED);
				blinkAction.stopBlinking();
				clockController.removeAlarmAction(alarmTime, AlarmActions.BLINKING);
			}
		});

		alarmActiveButton.setBackground(Color.GREEN);

		alarmActiveButton.addActionListener(buttonClick -> {
			if (clockController.isAlarmActive(alarmTime)) {
				setActiveButton(false);
			} else {
				setActiveButton(true);
			}
		});

		removeAlarmButton.addActionListener(buttonClick -> {
			firePropertyChange(REMOVE_ALARM_ENTRY, false, this);
		});

		add(dateLabel);
		add(hourLabel);
		add(minuteLabel);
		add(secondLabel);
		add(alarmActiveButton);
		add(blinkButton);
		add(soundButton);
		add(removeAlarmButton);
	}
}
