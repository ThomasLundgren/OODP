package se.hig.thlu.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import se.hig.thlu.controller.ClockController;

public class Gui implements Runnable {
	
	private final JFrame frame = new JFrame("AlarmClock app");
	private final ClockController clockController = new ClockController();
	private final AlarmPanel alarmPanel = new AlarmPanel(clockController);
	private final ControlPanel controlPanel = new ControlPanel(clockController);
	private final ClockPanel clockPanel = new ClockPanel(clockController);
	private final GridBagConstraints c = new GridBagConstraints();
	
	@Override
	public void run() {
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        	System.out.println("Could not set look&feel!");
        }
		configureFrame();
		addListeners();
		configureComponents();
		addComponents();
		
		clockController.startClock();
		showFrame();
	}

	private void configureComponents() {
		clockPanel.setPreferredSize(new Dimension(200, 200));
		alarmPanel.setPreferredSize(new Dimension(200, 200));
		controlPanel.setPreferredSize(new Dimension(200, 400));
	}

	private void configureFrame() {
		frame.setLayout(new GridBagLayout());
		frame.setMinimumSize(new Dimension(650, 400));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void addComponents() {
		c.gridy = 0;
		c.gridx = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.7;
		c.weightx = 1;
		frame.add(clockPanel, c);
		c.gridheight = 2;
		c.gridx = 1;
		c.weighty = 1;
		c.weightx = 0;
		frame.add(controlPanel, c);
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 1;
		c.weighty = 0.3;
		c.weightx = 0.5;
		frame.add(alarmPanel, c);
	}

	private void addListeners() {
		clockController.addListenerForClock(alarmPanel);
	}

	public void showFrame() {
		frame.pack();
		frame.setVisible(true);
	}
	
}
