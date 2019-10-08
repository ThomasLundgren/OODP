package se.hig.thlu.model;

import java.beans.PropertyChangeListener;

public interface PropertyChangeBroadcaster {

	public void addPropertyChangeListener(PropertyChangeListener listener);

	public void removePropertyChangeListener(PropertyChangeListener listener);

}
