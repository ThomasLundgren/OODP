package se.hig.thlu.gui;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import se.hig.thlu.model.PropertyChangeBroadcaster;
import se.hig.thlu.model.alarm.AlarmActionType;
import se.hig.thlu.model.alarm.AlarmType;
import se.hig.thlu.model.alarm.BlinkAlarmAction;
import se.hig.thlu.model.alarm.SoundAlarmAction;
import se.hig.thlu.model.time.TimeType;

@SuppressWarnings("serial")
class AlarmEntryPanel extends JPanel implements PropertyChangeBroadcaster, PropertyChangeListener {
	
	public static final String REMOVE_ALARM_ENTRY = "REMOVE ALARM ENTRY";
	private final AlarmType alarm;
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
	
	
	AlarmEntryPanel(AlarmType alarm) {
		this.alarm = alarm;
		this.alarmTime = alarm.getTime();
		
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
	
	public void stopAlarmSignals() {
		blinkAction.stopBlinking();
		soundAction.stopAlarmSound();
	}

	AlarmType getAlarm() {
		return alarm;
	}

	private void setUpComponents() {
		boolean hasBlink = false;
		boolean hasSound = false;
		for (AlarmActionType action : alarm.getAlarmActions()) {
			if (action instanceof BlinkAlarmAction) {
				hasBlink = true;
			}
			if (action instanceof SoundAlarmAction) {
				hasSound = true;
			}
		}
		
		if (hasSound) {
			soundButton.setText("Sound active");
			soundButton.setBackground(Color.GREEN);
		} else {
			soundButton.setText("Sound inactive");
			soundButton.setBackground(Color.RED);
		}
		
		if (hasBlink) {
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
				AlarmActionType soundAction = new SoundAlarmAction();
				soundAction.addPropertyChangeListener(this);
				alarm.addAlarmAction(soundAction);
			} else {
				soundButton.setText("Sound inactive");
				soundButton.setBackground(Color.RED);
				Iterator<AlarmActionType> iterator = alarm.getAlarmActions().iterator();
				soundAction.stopAlarmSound();
				while (iterator.hasNext()) {
					AlarmActionType alarmAction = iterator.next();
					if (alarmAction instanceof SoundAlarmAction) {
						iterator.remove();
					}
				}
			}
		});
		blinkButton.addActionListener(click -> {
			if (blinkButton.getText().contains("inactive")) {
				blinkButton.setText("Blink active");
				blinkButton.setBackground(Color.GREEN);
				AlarmActionType blinkAction = new BlinkAlarmAction();
				blinkAction.addPropertyChangeListener(this);
				alarm.addAlarmAction(blinkAction);
			} else {
				blinkButton.setText("Blink inactive");
				blinkButton.setBackground(Color.RED);
				Iterator<AlarmActionType> iterator = alarm.getAlarmActions().iterator();
				blinkAction.stopBlinking();
				while (iterator.hasNext()) {
					AlarmActionType alarmAction = iterator.next();
					if (alarmAction instanceof BlinkAlarmAction) {
						iterator.remove();
					}
				}
			}
		});
		
		alarmActiveButton.setBackground(Color.GREEN);
		
		alarmActiveButton.addActionListener(buttonClick -> {
			if (alarm.isActive()) {
				alarm.setActive(false);
				alarmActiveButton.setText("Inactive");
				alarmActiveButton.setBackground(Color.RED);
			} else {
				alarm.setActive(true);
				alarmActiveButton.setText("Active");
				alarmActiveButton.setBackground(Color.GREEN);
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
