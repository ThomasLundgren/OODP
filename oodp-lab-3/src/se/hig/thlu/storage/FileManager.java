package se.hig.thlu.storage;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class FileManager {

	private static FileManager INSTANCE;

	public enum FilePath {
		ALARM_CLIP("se/hig/thlu/resources/audio/alarm.wav"),
		DIGITAL_7_FONT("se/hig/thlu/resources/digital-7.tff");

		private final String filePath;

		FilePath(final String filePath) {
			this.filePath = filePath;
		}

		@Override
		public String toString() {
			return filePath;
		}

	}

	private FileManager() {
	}

	public static FileManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new FileManager();
		}
		return INSTANCE;
	}

	public Clip getClip(FilePath clipFilePath) {
		Clip clip = null;
		try {
			System.out.println(clipFilePath.toString());
			AudioInputStream inputStream = AudioSystem
					.getAudioInputStream(getClass().getResource(clipFilePath.toString()));
			clip = AudioSystem.getClip();
			clip.open(inputStream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
		if (clip == null) {
			throw new RuntimeException();
		}
		return clip;
	}
	
	public Font getFont(FilePath fontFilePath) {
		Font font = null;
		try {
			System.out.println(this.getClass().getResource(fontFilePath.toString()));
			font = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getClassLoader().getResourceAsStream(fontFilePath.toString()));
			font = font.deriveFont(Font.BOLD, 28);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(font);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		return font;
	}

}
