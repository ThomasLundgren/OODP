package se.hig.thlu.storage;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class FileManager {

	public static final String ALARM_CLIP = "/se/hig/thlu/resources/audio/alarm.wav";
	private static FileManager INSTANCE;

	private FileManager() {
	}

	public static FileManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new FileManager();
		}
		return INSTANCE;
	}

	public Clip getClip() {
		Clip clip = null;
		try {
			AudioInputStream inputStream = AudioSystem
					.getAudioInputStream(getClass().getResource(ALARM_CLIP));
			clip = AudioSystem.getClip();
			clip.open(inputStream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
		if (clip == null) {
			throw new NullPointerException("Could not retrieve Clip!");
		}
		return clip;
	}
	
}
