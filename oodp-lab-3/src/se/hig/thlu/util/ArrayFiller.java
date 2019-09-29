package se.hig.thlu.util;

public class ArrayFiller {

	public static String[] fillIncrementallyWithLeadingZeros(String[] array) {
		for (int i = 0; i < array.length; i++) {
			array[i] = NumberConverter.convertIntToDoubleDigitString(i);
		}
		return array;
	}
	
}
