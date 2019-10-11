package se.hig.thlu.view;

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
		secondHandAngle = ((seconds-15)/60)*2*Math.PI;	// subtract one fourth to make the angle start at s = 0
		minuteHandAngle = ((minutes-15)/60)*2*Math.PI;	// subtract one fourth to make the angle start at m = 0
		hourHandAngle = ((hours-9)/24)*2*Math.PI;		// subtract one fourth to make the angle start at h = 0
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		int min = Math.min(getWidth(), getHeight());
		int x = min / 2;
		int y = min / 2;
		int xCenter = getWidth()/2;
		int displacement = min/2;
		int leftEdge = xCenter - displacement;
		int rightEdge = xCenter + displacement;
		int quarterLineLength = min/15;
		
		g.setColor(Color.white);
		g.drawOval(leftEdge, 0, min, min);
		g.drawLine(rightEdge, y, rightEdge - quarterLineLength, y); // draw line at 3 o'clock
		g.drawLine(xCenter, min, xCenter, min - quarterLineLength); // draw line at 6 o'clock
		g.drawLine(leftEdge, y, leftEdge + quarterLineLength, y); 	// draw line at 9 o'clock
		g.drawLine(xCenter, 0, xCenter, quarterLineLength);			// draw line at 12 o'clock
		drawSecondHand(g, xCenter, x, y);
		drawMinuteHand(g, xCenter, x, y);
		drawHourHand(g, xCenter, x, y);
	}

	private void drawSecondHand(Graphics g, int xCenter, int x, int y) {
		double yp = Math.sin(secondHandAngle);
		double xp = Math.cos(secondHandAngle);
		g.drawLine(xCenter, y, xCenter + (int) (xp*x), y + (int) (yp*y));
	}
	
	private void drawMinuteHand(Graphics g, int xCenter, int x, int y) {
		double yp = Math.sin(minuteHandAngle);
		double xp = Math.cos(minuteHandAngle);
		g.drawLine(xCenter, y, xCenter + (int) (xp*x*0.9), y + (int) (yp*y*0.9));
	}
	
	private void drawHourHand(Graphics g, int xCenter, int x, int y) {
		double yp = Math.sin(hourHandAngle);
		double xp = Math.cos(hourHandAngle);
		g.drawLine(xCenter, y, xCenter + (int) (xp*x*0.7), y + (int) (yp*y*0.7));
	}

}
