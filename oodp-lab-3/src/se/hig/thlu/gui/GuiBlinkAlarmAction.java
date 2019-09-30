package se.hig.thlu.gui;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.Timer;

public class GuiBlinkAlarmAction {
	
	private final Timer blinkTimer;
	private final JComponent component;
	
	
	public GuiBlinkAlarmAction(JComponent component) {
		this.component = component;
		blinkTimer = new Timer(500, event -> {
			if (component.getBackground() == Color.RED) {
				component.setBackground(Color.WHITE);
			} else {
				component.setBackground(Color.RED);
				System.out.println("Blinking!");
			}
		});
	}
	
	public void startBlinking() {
		blinkTimer.start();
	}
	
	public void stopBlinking() {
		if (blinkTimer != null && blinkTimer.isRunning()) {
			blinkTimer.stop();
			component.setBackground(Color.WHITE);
		}
	}
}