package se.hig.thlu.util;

public class NumberConverter {

	public static String convertIntToDoubleDigitString(int number) {
		return (number < 10 ? "0" : "") + number;
	}

}
