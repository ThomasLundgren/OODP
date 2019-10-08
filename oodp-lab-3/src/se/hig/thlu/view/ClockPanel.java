package se.hig.thlu.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;

import se.hig.thlu.controller.ClockController;

@SuppressWarnings("serial")
public class ClockPanel extends JPanel {
	
	private final ClockController clockController;
	private final ClockFace digitalClock = new DigitalClockFace();
	private final ClockFace analogClock = new AnalogClockFace();
	private final JButton switchToAnalog = new JButton();
	private final JButton switchToDigital = new JButton();
	private final JPanel clockContainer = new JPanel();

	public ClockPanel(ClockController clockController) {
		this.clockController = clockController;
		configureComponents();
		addListeners();
	}

	private void addListeners() {
		clockController.addListenerForClock(digitalClock);
		clockController.addListenerForClock(analogClock);
	}

	private void configureComponents() {
		CompoundBorder border = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 1, 0, 0),
				BorderFactory.createLineBorder(Color.BLACK));
		setBorder(border);
		// configure clockContainer
		clockContainer.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		clockContainer.add(digitalClock, gbc);
		clockContainer.add(analogClock, gbc);
		// add components
//		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.95;
		c.weightx = 1;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 0;
		add(clockContainer, c);
		c.gridwidth = 1;
		c.weighty = 0.05;
		c.gridy = 1;
		add(switchToDigital, c);
		c.gridx = 1;
		add(switchToAnalog, c);
		analogClock.setVisible(false);
		digitalClock.setVisible(true);
		// configure buttons
		switchToAnalog.setText("Analog");
		switchToAnalog.addActionListener(buttonClick -> {
			digitalClock.setVisible(false);
			analogClock.setVisible(true);
		});
		switchToDigital.setText("Digital");
		switchToDigital.addActionListener(buttonClick -> {
			analogClock.setVisible(false);
			digitalClock.setVisible(true);
		});
	}
	
}
