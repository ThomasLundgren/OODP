package se.hig.thlu.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import se.hig.thlu.model.time.TimeType;
import se.hig.thlu.util.NumberConverter;

@SuppressWarnings("serial")
class DigitalClockFace extends ClockFace {

	private final JLabel dateLabel = new JLabel();
	private final JLabel hourLabel = new JLabel();
	private final JLabel minuteLabel = new JLabel();
	private final JLabel secondLabel = new JLabel();
	private final Font font = dateLabel.getFont().deriveFont(24.0f);

	public DigitalClockFace() {
		dateLabel.setFont(font);
		hourLabel.setFont(font);
		minuteLabel.setFont(font);
		secondLabel.setFont(font);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		dateLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		add(dateLabel, c);
		add(hourLabel, c);
		add(minuteLabel, c);
		add(secondLabel, c);
	}

	@Override
	protected void updateAfterTimeChanged(TimeType newTime) {
		dateLabel.setText(TimeType.convertToDayString(newTime.getDay()));
		hourLabel.setText(NumberConverter.convertIntToDoubleDigitString(newTime.getHour()) + ":");
		minuteLabel.setText(NumberConverter.convertIntToDoubleDigitString(newTime.getMinute()) + ":");
		secondLabel.setText(NumberConverter.convertIntToDoubleDigitString(newTime.getSecond()));
	}

}
