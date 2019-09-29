package se.hig.thlu.gui;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;

import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import se.hig.thlu.model.PropertyChangeBroadcaster;
import se.hig.thlu.model.alarm.AlarmActionType;
import se.hig.thlu.model.alarm.AlarmType;
import se.hig.thlu.model.alarm.BlinkAlarmAction;
import se.hig.thlu.model.alarm.SoundAlarmAction;
import se.hig.thlu.model.time.TimeType;
import se.hig.thlu.storage.FileManager;
import se.hig.thlu.storage.FileManager.FilePath;

@SuppressWarnings("serial")
class AlarmEntryPanel extends JPanel implements PropertyChangeBroadcaster, PropertyChangeListener {

	/*
	 * TODO: Extract GUI alarm actions to own classes.
	 * Methods: start(), stop()
	 */
	
	public static final String REMOVE_ALARM_ENTRY = "REMOVE ALARM ENTRY";
	private Timer blinkTimer;
	private Clip alarmClip;
	private final AlarmType alarm;
	private final TimeType alarmTime;
	private final FileManager fileManager = FileManager.getInstance();
	private final JButton alarmActiveButton = new JButton("Active");
	private final JButton removeAlarmButton = new JButton("Remove");
	private final JButton soundButton = new JButton();
	private final JButton blinkButton = new JButton();
	private final JLabel dateLabel;
	private final JLabel hourLabel;
	private final JLabel minuteLabel;
	private final JLabel secondLabel;
	
	
	AlarmEntryPanel(AlarmType alarm) {
		this.alarm = alarm;
		this.alarmTime = alarm.getTime();
		
		dateLabel = new JLabel(TimeType.convertToDayString(alarmTime.getDay()));
		hourLabel = new JLabel(Integer.toString(alarmTime.getHour()));
		minuteLabel = new JLabel(Integer.toString(alarmTime.getMinute()));
		secondLabel = new JLabel(Integer.toString(alarmTime.getSecond()));
		
		setUpComponents();
	}

	@Override
	public void propertyChange(PropertyChangeEvent propertyChange) {
		if (propertyChange.getPropertyName() == BlinkAlarmAction.BLINK_ALARM_ACTIVATED) {
			System.out.println("AlarmEntryPanel reacted to blink alarm activation");
			if ((boolean) propertyChange.getNewValue() == true) {
				startBlinkTimer();
			}
			if ((boolean) propertyChange.getNewValue() == false) {
				stopBlinking();
			}
		}
		if (propertyChange.getPropertyName() == SoundAlarmAction.SOUND_ALARM_ACTIVATED) {
			System.out.println("AlarmEntryPanel reacted to sound alarm activation");
			if ((boolean) propertyChange.getNewValue() == true) {
				startAlarmSound();
			}
			if ((boolean) propertyChange.getNewValue() == false) {
				stopAlarmSound();
			}
		}
	}
	
	public void stopAlarmSignals() {
		stopBlinking();
		stopAlarmSound();
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
				stopAlarmSound();
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
				stopBlinking();
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
	
	private void startBlinkTimer() {
		blinkTimer = new Timer(500, event -> {
			if (getBackground() == Color.RED) {
				setBackground(Color.WHITE);
			} else {
				setBackground(Color.RED);
				System.out.println("Blinking!");
			}
		});
		blinkTimer.start();
	}
	
	private void startAlarmSound() {
		if (alarmClip == null) {
			alarmClip = fileManager.getClip(FilePath.ALARM_CLIP);
			alarmClip.setLoopPoints(0, -1);
			alarmClip.loop(Clip.LOOP_CONTINUOUSLY);
			setBorder(BorderFactory.createLineBorder(Color.RED));
		}
	}

	private void stopAlarmSound() {
		if (alarmClip != null) {
			if (alarmClip.isActive()) {
				alarmClip.stop();
				setBorder(BorderFactory.createEmptyBorder());
			}
		}
	}

	private void stopBlinking() {
		if (blinkTimer != null && blinkTimer.isRunning()) {
			blinkTimer.stop();
			setBackground(Color.WHITE);
		}
	}

}
