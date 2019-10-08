package se.hig.thlu.control;

import java.io.IOException;
import java.nio.file.Path;

import se.hig.thlu.model.WordOccurrenceList;
import se.hig.thlu.storage.FileReader;

public class WordOccurenceController {
	
	private FileReader reader;
	
	public WordOccurrenceList readFile(Path path) throws IOException {
		reader = new FileReader(path);
		return reader.getWordOccurrenceList();
	}
	
	
}
