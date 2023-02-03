package com.lockedme.filemanager;

import java.io.FileNotFoundException;
import java.util.TreeMap;

/*
 * File manager from Lockers Pvt. Ltd. - www.lokedme.com
 * Rafael López Benavente - rafael.lopez@vodafone.com
 * 
 * @author Rafael López Benavente
 * @version 0.0.2
 */
public class App {
	// Exit codes
	private static final int NO_ERROR = 0;
	private static final int WRONG_ARGUMENTS = -1;
	private static final int INCORRECT_PATH = -2;
	private static final int RUNTIME_EXCEPTION = -3;

	// Option numbers for main menu
	private static final int MAIN_MENU_LIST_FILES = 1;
	private static final int MAIN_MENU_MANAGE_FILES = 2;
	private static final int MAIN_MENU_EXIT = 3;

	// Option numbers for main menu
	private static final int SECOND_MENU_ADD_FILE = 1;
	private static final int SECOND_MENU_DELETE_FILE = 2;
	private static final int SECOND_MENU_SEARCH_FILES = 3;
	private static final int SECOND_MENU_EXIT = 4;

	// Default options
	private static final boolean DEFAULT_IGNORE_CASE = true;
	private static final String DEFAULT_PATH = ".";
	private static final String EOF_TAG = "EOF";

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
			System.out.println("Cannot run program in " + (path == null ? "current folder." : "path '" + path + "'."));
			System.out.println(ex.getMessage());
			System.exit(INCORRECT_PATH);
		} catch (Exception ex) {
			System.out.println("A runtime exception occurred.");
			System.out.println(ex.getMessage());
			System.exit(RUNTIME_EXCEPTION);
		}
		System.out.println("Working directory is: '" + fileManager.getWorkingDirectory() + "'.");
		System.out.println();

		// First level menu
		TreeMap<Integer, String> firstOptions = new TreeMap<Integer, String>();
		firstOptions.put(MAIN_MENU_LIST_FILES, "List files");
		firstOptions.put(MAIN_MENU_MANAGE_FILES, "Manage files");
		firstOptions.put(MAIN_MENU_EXIT, "Exit");
		UserInput firstUserInput = new UserInput(firstOptions);
		Integer firstOption = null;
		try {
			do {
				// Reading user input for first level menu
				firstOption = firstUserInput.getOption("The available options are:");
				switch (firstOption) {
				case MAIN_MENU_LIST_FILES:
					System.out.println(fileManager.listFiles());
					break;
				case MAIN_MENU_MANAGE_FILES:
					// Enter second level menu to manage the files
					TreeMap<Integer, String> secondOptions = new TreeMap<Integer, String>();
					secondOptions.put(SECOND_MENU_ADD_FILE, "Add a file");
					secondOptions.put(SECOND_MENU_DELETE_FILE, "Delete a file");
					secondOptions.put(SECOND_MENU_SEARCH_FILES, "Search files");
					secondOptions.put(SECOND_MENU_EXIT, "Back to the main menu");
					UserInput secondUserInput = new UserInput(secondOptions);
					Integer secondOption = null;
					do {
						// Reading user input for second level menu
						secondOption = secondUserInput.getOption("The options for files management are:");
						switch (secondOption) {
						case SECOND_MENU_ADD_FILE:
							String name = secondUserInput.getString("Enter the complete file name to create, please: ");
							if (fileManager.exists(name)) {
								System.out.println("File '" + name + "' already exists in folder '" + fileManager.getWorkingDirectory() + "'.");
							} else {
								String content = secondUserInput
										.getTextUntil("Enter the file content. To finish, enter '" + EOF_TAG + "' and the end.", EOF_TAG);
								System.out.println(fileManager.addFile(name, content));
							}
							break;
						case SECOND_MENU_DELETE_FILE:
							name = secondUserInput.getString("Enter the complete file or folder name to delete, please: ");
							System.out.println(fileManager.deletePath(name));
							break;
						case SECOND_MENU_SEARCH_FILES:
							name = secondUserInput.getString("Enter a regular expression, please: ");
							System.out.println(fileManager.searchFiles(name));
							break;
						case SECOND_MENU_EXIT:
							// Back to main menu
							break;
						}
					} while (secondOption != SECOND_MENU_EXIT);
					break;
				case MAIN_MENU_EXIT:
					// Exit the application normally
					System.out.println("Bye bye!");
					System.exit(NO_ERROR);
					break;
				}
			} while (true);
		} catch (Exception ex) {
			// The application should not throw any exception.
			System.out.println("A runtime exception occurred.");
			System.out.println(ex.getMessage());
			System.exit(RUNTIME_EXCEPTION);
		}
	}
}
