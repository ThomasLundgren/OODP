package se.hig.thlu.model;

import java.util.Comparator;

public class WordOccurrence {
	
	private final String word;
	private final int occurrence;
	
	public static class CompareByWords implements Comparator<WordOccurrence> {

		@Override
		public int compare(WordOccurrence word1, WordOccurrence word2) {
			return word1.getWord().compareTo(word2.getWord());
		}
	}
	
	public static class CompareByOccurrence implements Comparator<WordOccurrence> {

		@Override
		public int compare(WordOccurrence word1, WordOccurrence word2) {
			return word1.getOccurrence().compareTo(word2.getOccurrence());
		}
	}
	
	public WordOccurrence(String word, Integer occurrence) {
		this.word = word;
		this.occurrence = occurrence;
	}

	public String getWord() {
		return word;
	}

	public Integer getOccurrence() {
		return occurrence;
	}

}
