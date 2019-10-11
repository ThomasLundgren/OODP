package se.hig.thlu.view;

import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import se.hig.thlu.controller.ClockController;
import se.hig.thlu.controller.ClockController.AlarmActions;
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
	private final GuiAlarmAction blinkAction;
	private final GuiAlarmAction soundAction;

	AlarmEntryPanel(List<AlarmActions> actionTypes, TimeType time, ClockController clockController) {
		this.actionTypes = actionTypes;
		this.alarmTime = time;
		this.clockController = clockController;

		dateLabel = new JLabel(TimeType.convertToDayString(alarmTime.getDay()));
		hourLabel = new JLabel(Integer.toString(alarmTime.getHour()));
		minuteLabel = new JLabel(Integer.toString(alarmTime.getMinute()));
		secondLabel = new JLabel(Integer.toString(alarmTime.getSecond()));

		blinkAction = new GuiBlinkAlarmAction(this);
		soundAction = new GuiSoundAlarmAction();

		setUpComponents();
	}

	@Override
	public void propertyChange(PropertyChangeEvent propertyChange) {
		if (propertyChange.getPropertyName() == BlinkAlarmAction.BLINK_ALARM_ACTIVATED) {
			System.out.println("AlarmEntryPanel reacted to blink alarm activation");
			if ((boolean) propertyChange.getNewValue() == true) {
				blinkAction.start();
			} else if ((boolean) propertyChange.getNewValue() == false) {
				blinkAction.stop();
			}
		}
		if (propertyChange.getPropertyName() == SoundAlarmAction.SOUND_ALARM_ACTIVATED) {
			System.out.println("AlarmEntryPanel reacted to sound alarm activation");
			if ((boolean) propertyChange.getNewValue() == true) {
				soundAction.start();
				setBorder(BorderFactory.createLineBorder(Color.RED));
			} else if ((boolean) propertyChange.getNewValue() == false) {
				soundAction.stop();
				setBorder(BorderFactory.createEmptyBorder());
			}
		}
	}

	void stopAlarmSignals() {
		blinkAction.stop();
		soundAction.stop();
	}

	TimeType getAlarmTime() {
		return new Time(alarmTime.toString());
	}
	
	void setActiveButton(boolean active) {
		if (active) {
			clockController.setAlarmActive(alarmTime, true);
			alarmActiveButton.setText("Active");
			alarmActiveButton.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		} else {
			clockController.setAlarmActive(alarmTime, false);
			alarmActiveButton.setText("Inactive");
			alarmActiveButton.setBorder(BorderFactory.createLineBorder(Color.RED));
		}
	}

	private void setUpComponents() {
		Dimension dim = new Dimension(80, 25);
		alarmActiveButton.setPreferredSize(dim);
		removeAlarmButton.setPreferredSize(dim);
		soundButton.setPreferredSize(dim);
		blinkButton.setPreferredSize(dim);
		
		if (actionTypes.contains(AlarmActions.SOUND)) {
			soundButton.setText("Sound active");
			soundButton.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		} else {
			soundButton.setText("Sound inactive");
			soundButton.setBorder(BorderFactory.createLineBorder(Color.RED));
		}

		if (actionTypes.contains(AlarmActions.BLINKING)) {
			blinkButton.setText("Blink active");
			blinkButton.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		} else {
			blinkButton.setText("Blink inactive");
			blinkButton.setBorder(BorderFactory.createLineBorder(Color.RED));
		}

		soundButton.addActionListener(click -> {
			if (soundButton.getText().contains("inactive")) {
				soundButton.setText("Sound active");
				soundButton.setBorder(BorderFactory.createLineBorder(Color.GREEN));
				clockController.addAlarmAction(alarmTime, AlarmActions.SOUND, this);
			} else {
				soundButton.setText("Sound inactive");
				soundButton.setBorder(BorderFactory.createLineBorder(Color.RED));
				soundAction.stop();
				clockController.removeAlarmAction(alarmTime, AlarmActions.SOUND);
			}
		});
		blinkButton.addActionListener(click -> {
			if (blinkButton.getText().contains("inactive")) {
				blinkButton.setText("Blink active");
				blinkButton.setBorder(BorderFactory.createLineBorder(Color.GREEN));
				clockController.addAlarmAction(alarmTime, AlarmActions.BLINKING, this);
			} else {
				blinkButton.setText("Blink inactive");
				blinkButton.setBorder(BorderFactory.createLineBorder(Color.RED));
				blinkAction.stop();
				clockController.removeAlarmAction(alarmTime, AlarmActions.BLINKING);
			}
		});


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
