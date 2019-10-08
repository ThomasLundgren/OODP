package se.hig.thlu.spikes;

import java.io.IOException;
import java.nio.file.Paths;

import se.hig.thlu.storage.FileReader;

public class FileReaderSpike {
	
	public static void main(String[] args) {
		String filePath = System.getProperty("user.dir").replace("oodp-lab-4-part-two", "") + "alice-in-wonderland-short.txt";
		System.out.println(filePath);
		FileReader reader = new FileReader(Paths.get(filePath));
		try {
			reader.getWordOccurrenceList();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
