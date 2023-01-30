package com.lockedme.filemanager;

import java.io.FileNotFoundException;
import java.util.TreeMap;

/*
 * File manager from Lockers Pvt. Ltd. - www.lokedme.com
 * Rafael López Benavente - rafael.lopez@vodafone.com
 * 
 * @author Rafael López Benavente
 * @version 0.0.1
 */
public class App {
	// Exit codes
	private static final int NO_ERROR = 0;
	private static final int WRONG_ARGUMENTS = -1;
	private static final int INCORRECT_PATH = -2;

	// Option numbers for main menu
	private static final int MAIN_MENU_LIST_FILES = 1;
	private static final int MAIN_MENU_EXIT = 2;
	
	// Default options
	private static final boolean DEFAULT_IGNORE_CASE = true;
	private static final String DEFAULT_PATH = ".";
	
	public static void main(String[] args) {
		// Print some information regarding the company and the developer
		System.out.println("File manager from Lockers Pvt. Ltd. - www.lokedme.com");
		System.out.println("Rafael Lopez Benavente - rafael.lopez@vodafone.com");
		System.out.println();

		// Check program arguments
		if (args.length > 1) {
			System.out.println("Usage: java -jar FileManajer.jar [path]");
			System.exit(WRONG_ARGUMENTS);
		}
		String path = null;
		if (args.length == 1) {
			path = args[0];
		}

		// Create file manager object
		FileManager fileManager = null;
		try {
			if (path == null) {
				fileManager = new FileManager(DEFAULT_PATH, DEFAULT_IGNORE_CASE);
			} else {
				fileManager = new FileManager(path, DEFAULT_IGNORE_CASE);
			}
		} catch (FileNotFoundException ex) {
			System.out.println("Cannot run program in " + (path == null ? "current directory." : "path '" + path + "'."));
			System.out.println("EEROR: " + ex.getMessage());
			System.exit(INCORRECT_PATH);
		}
		System.out.println("Working directory is: '" + fileManager.getWorkingDirectory() + "'.");
		System.out.println();

		// First level menu
		TreeMap<Integer, String> firstOptions = new TreeMap<Integer, String>();
		firstOptions.put(MAIN_MENU_LIST_FILES, "List files");
		firstOptions.put(MAIN_MENU_EXIT, "Exit");
		UserInput firstUserInput = new UserInput("The available options are:", firstOptions);
		Integer firstOption = null;
		do {
			// Reading user input for first level menu
			firstOption = firstUserInput.getOption();
			switch (firstOption) {
			case MAIN_MENU_LIST_FILES:
				// Listing files
				System.out.println(fileManager.listFiles());
				System.out.println();
				break;
			case MAIN_MENU_EXIT:
				// Exit the application
				System.out.println("Bye bye!");
				System.exit(NO_ERROR);
				break;
			}
		} while (true);
	}
}
