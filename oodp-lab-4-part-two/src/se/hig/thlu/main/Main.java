package se.hig.thlu.main;

import javax.swing.SwingUtilities;

import se.hig.thlu.view.Gui;

public class Main {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Gui());
	}
	
}
