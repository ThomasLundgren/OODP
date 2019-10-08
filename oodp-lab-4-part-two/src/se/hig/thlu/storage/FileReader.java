package se.hig.thlu.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.hig.thlu.model.WordOccurrence;
import se.hig.thlu.model.WordOccurrenceList;

public class FileReader {

	private final FileManager fileManager = FileManager.getInstance();
	private final Map<String, Integer> map = new HashMap<String, Integer>();

	public FileReader(Path path) {
		fileManager.setFilePath(path);
	}

	public WordOccurrenceList getWordOccurrenceList() throws IOException {
		List<WordOccurrence> wordList = new ArrayList<WordOccurrence>(10000);
		Collection<String> lines = fileManager.readTxtDoc();
		lines.forEach(line -> {
			line = line.replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit} ]", "");
			line = line.toLowerCase();
			String[] words = line.split(" ");
			for (String word : words) {
				if (!word.trim().isEmpty()) {
					map.merge(word, 1, Integer::sum);
				}
			}
		});
		int totalOccurrences = 0;
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			wordList.add(new WordOccurrence(entry.getKey(), entry.getValue()));
			totalOccurrences += entry.getValue();
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
		WordOccurrenceList occurrenceList = new WordOccurrenceList(wordList);
		occurrenceList.setTotalOccurrences(totalOccurrences);
		return occurrenceList;
	}

}
