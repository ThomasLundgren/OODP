package se.hig.thlu.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.LinkedList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class FileManager {

	public static final String ALARM_CLIP = "/se/hig/thlu/resources/audio/alarm.wav";
	public static final String ALARM_STORE_TXT = System.getProperty("user.dir") + "/src/se/hig/thlu/storage/alarms.txt";
	private static FileManager INSTANCE;
	private static Path TXT_FILE_PATH = Paths.get(ALARM_STORE_TXT);

	private FileManager() {
	}

	public static FileManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new FileManager();
		}
		return INSTANCE;
	}

	public void storeTxt(Collection<String> text) throws IOException {
		BufferedWriter writer = Files.newBufferedWriter(TXT_FILE_PATH, StandardCharsets.UTF_8, StandardOpenOption.WRITE,
				StandardOpenOption.TRUNCATE_EXISTING);
		for (String str : text) {
			writer.write(str + "\n");
		}
		writer.close();
	}

	public Collection<String> readTxtDoc() throws IOException {
		Collection<String> data = new LinkedList<String>();
		BufferedReader reader = Files.newBufferedReader(TXT_FILE_PATH, StandardCharsets.UTF_8);
		String currentLine = null;
		while ((currentLine = reader.readLine()) != null) {
			data.add(currentLine);
		}
		reader.close();
		return data;
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
