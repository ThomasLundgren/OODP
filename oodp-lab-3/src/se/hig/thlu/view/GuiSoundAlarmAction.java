package se.hig.thlu.view;

import javax.sound.sampled.Clip;

import se.hig.thlu.storage.FileManager;

public class GuiSoundAlarmAction implements GuiAlarmAction {
	
	private final Clip alarmClip;
	
	public GuiSoundAlarmAction() {
		alarmClip = FileManager.getInstance().getClip();
		alarmClip.setLoopPoints(0, -1);
	}
	
	@Override
	public void start() {
		alarmClip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	@Override
	public void stop() {
		if (alarmClip != null) {
			if (alarmClip.isActive()) {
				alarmClip.stop();
			}
		}
	}
	
}
