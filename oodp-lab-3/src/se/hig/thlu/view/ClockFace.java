package se.hig.thlu.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import se.hig.thlu.model.clock.AlarmClockType;
import se.hig.thlu.model.time.TimeType;

@SuppressWarnings("serial")
public abstract class ClockFace extends JPanel implements PropertyChangeListener {
	
	@Override
	public void propertyChange(PropertyChangeEvent propertyChange) {
		if (propertyChange.getPropertyName() == AlarmClockType.TIME_CHANGED) {
			TimeType newTime = (TimeType) propertyChange.getNewValue();
			updateAfterTimeChanged(newTime);
		}
	}
	
	protected abstract void updateAfterTimeChanged(TimeType newTime);
	
}
