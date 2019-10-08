package se.hig.thlu.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.nio.file.Path;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import se.hig.thlu.control.WordOccurenceController;
import se.hig.thlu.model.WordOccurrenceList;

public class Gui {

	private final WordOccurenceController controller = new WordOccurenceController();
	private final JFrame frame = new JFrame();
	private final JPanel background = new JPanel();
	private final JFileChooser fileChooser = new JFileChooser();
	private final JButton loadFileButton = new JButton();
	private final JLabel fileLabel = new JLabel();
	private final JTable table = new JTable();

	public Gui() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(frame);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
			System.out.println("Could not set look&feel!");
		}
		configureComponents();
		addComponents();
		showFrame();
	}

	private void configureComponents() {
		loadFileButton.setText("Load file");
		loadFileButton.setFont(loadFileButton.getFont().deriveFont(20f));
		fileChooser.updateUI();
		loadFileButton.addActionListener(click -> {
			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
			int returnValue = fileChooser.showOpenDialog(background);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				Path filePath = fileChooser.getSelectedFile().toPath();
				WordOccurrenceList list;
				try {
					list = controller.readFile(filePath);
					fileLabel.setText("File: " + filePath.getFileName().toString() + " {" + list.getRowCount() + " words} {"
							+ list.getTotalOccurrences() + " total occurrences}");
					table.setModel(list);
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(background, "Could not load file."
							+ " Please make sure to choose a valid .txt file.");
				}
			}
		});
		table.setFillsViewportHeight(true);
		table.setAutoCreateRowSorter(true);
		fileLabel.setText("File: ");
		fileLabel.setFont(fileLabel.getFont().deriveFont(14f));
		background.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	}

	private void addComponents() {
		background.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = 2;
		c.gridy = 0;
		c.gridx = 0;
		c.weightx = 0;
		c.weighty = 0;
		background.add(fileLabel, c);
		c.gridwidth = 1;
		c.gridy = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.NONE;
		background.add(loadFileButton, c);
		c.gridy = 1;
		c.gridx = 1;
		c.weightx = 1;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0, 5, 0, 0);
		background.add(new JScrollPane(table), c);
		frame.add(background);
	}

	private void showFrame() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(600, 400));
		frame.pack();
		frame.setVisible(true);
	}

}
