package se.hig.thlu.gui;

import java.awt.Color;
import java.awt.Graphics;

import se.hig.thlu.model.time.TimeType;

@SuppressWarnings("serial")
public class AnalogClockFace extends ClockFace {

	private double secondHandAngle, minuteHandAngle, hourHandAngle;

	public AnalogClockFace() {
		setBackground(Color.BLACK);
	}

	@Override
	protected void updateAfterTimeChanged(TimeType newTime) {
		double seconds = (double) newTime.getSecond();
		double minutes = (double) newTime.getMinute();
		double hours = (double) newTime.getHour();
		secondHandAngle = ((seconds*0.75)/60)*2*Math.PI;
		minuteHandAngle = ((minutes*0.75)/60)*2*Math.PI;
		hourHandAngle = ((hours*0.75)/24)*2*Math.PI;
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int min = Math.min(getWidth(), getHeight());
		int width = min, height = min;
		
		int x = width / 2;
		int y = height / 2;
		g.setColor(Color.white);
		g.drawLine(width, y, width - width/15, y); 		// draw line at 3 o'clock
		g.drawLine(x, height, x, height - height/15); 	// draw line at 6 o'clock
		g.drawLine(0, y, width/15, y); 					// draw line at 9 o'clock
		g.drawLine(x, 0, x, height/15);					// draw line at 12 o'clock
		g.drawOval(0, 0, width, height);
		drawSecondHand(g, x, y);
		drawMinuteHand(g, x, y);
		drawHourHand(g, x, y);
	}

	private void drawSecondHand(Graphics g, int x, int y) {
		double yp = Math.sin(secondHandAngle);
		double xp = Math.cos(secondHandAngle);
		g.drawLine(x, y, x + (int) (xp*x), y + (int) (yp*y));
	}
	
	private void drawMinuteHand(Graphics g, int x, int y) {
		double yp = Math.sin(minuteHandAngle);
		double xp = Math.cos(minuteHandAngle);
		g.drawLine(x, y, x + (int) (xp*x*0.9), y + (int) (yp*y*0.9));
	}
	
	private void drawHourHand(Graphics g, int x, int y) {
		double yp = Math.sin(hourHandAngle);
		double xp = Math.cos(hourHandAngle);
		g.drawLine(x, y, x + (int) (xp*x*0.7), y + (int) (yp*y*0.7)); // TODO: change length of hand
	}

}
