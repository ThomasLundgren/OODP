package se.hig.thlu.view;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.Timer;

public class GuiBlinkAlarmAction implements GuiAlarmAction {
	
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
	
	@Override
	public void start() {
		blinkTimer.start();
	}
	
	@Override
	public void stop() {
		if (blinkTimer != null && blinkTimer.isRunning()) {
			blinkTimer.stop();
			component.setBackground(Color.WHITE);
		}
	}
}