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

public class FileManager {

	private static FileManager INSTANCE;
	private static final String DEFAULT_FILE = System.getProperty("user.dir")
			+ "/src/se/hig/thlu/storage/alice-in-wonderland.txt";
	private static Path TXT_FILE_PATH = Paths.get(DEFAULT_FILE);

	private FileManager() {}

	public static FileManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new FileManager();
		}
		return INSTANCE;
	}
	
	public void setFilePath(Path path) {
		TXT_FILE_PATH = path;
	}

	public void storeTxt(Collection<String> text) throws IOException {
		System.out.println(TXT_FILE_PATH.toAbsolutePath());
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
}
