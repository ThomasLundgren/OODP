package se.hig.thlu.model;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class WordOccurrenceList extends AbstractTableModel {

	private List<WordOccurrence> wordOccurrences = new LinkedList<>();
	private static final String[] COLUMN_NAMES = {"Word", "Occurrences"};
	private int totalOccurrences = 0;
	
	public WordOccurrenceList(List<WordOccurrence> list) {
		wordOccurrences = list;
	}
	
	public void setTotalOccurrences(int words) {
		totalOccurrences = words;
	}
	
	public int getTotalOccurrences() {
		return totalOccurrences;
	}
	
	public void sort(Comparator<WordOccurrence> comparator) {
		wordOccurrences.sort(comparator);
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == 0) {
			return String.class;
		} else if (columnIndex == 1) {
			return Integer.class;
		} else {
			return Object.class;
		}
	}
	
	@Override
	public String getColumnName(int column) throws NoSuchElementException {
		try {
			return COLUMN_NAMES[column];
		} catch (IndexOutOfBoundsException e) {
			throw new NoSuchElementException();
		}
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowCount() {
		return wordOccurrences.size();
	}

	/**
	 * Throws {@link IndexOutOfBoundsException} if row < 1 or row > {@link WordOccurrenceList#getRowCount()} or if
	 * col < 1 or col > {@link WordOccurrenceList#getColumnCount()}.
	 */
	@Override
	public Object getValueAt(int row, int col) throws IndexOutOfBoundsException {
		if (col == 0) {
			return wordOccurrences.get(row).getWord();
		} else if (col == 1) {
			return wordOccurrences.get(row).getOccurrence();
		} else {
			return new Object();
		}
	}

}
