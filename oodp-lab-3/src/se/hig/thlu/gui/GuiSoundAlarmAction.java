package se.hig.thlu.gui;

import java.awt.Color;

import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.JComponent;

import se.hig.thlu.storage.FileManager;
import se.hig.thlu.storage.FileManager.FilePath;

public class GuiSoundAlarmAction {
	
	private final JComponent component;
	private final Clip alarmClip;
	
	public GuiSoundAlarmAction(JComponent component) {
		this.component = component;
		alarmClip = FileManager.getInstance().getClip(FilePath.ALARM_CLIP);
		alarmClip.setLoopPoints(0, -1);
	}
	
	public void startAlarmSound() {
		alarmClip.loop(Clip.LOOP_CONTINUOUSLY);
		component.setBorder(BorderFactory.createLineBorder(Color.RED));
	}

	public void stopAlarmSound() {
		if (alarmClip != null) {
			if (alarmClip.isActive()) {
				alarmClip.stop();
				component.setBorder(BorderFactory.createEmptyBorder());
			}
		}
	}
	
}
