package com.lockedme.filemanager;

import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;

/*
 * Class to interact with the user using Scanner.
 * 
 * @author Rafael López Benavente
 * @version 0.0.2
 */
public class UserInput {
	/*
	 * Scanner object defined as static final (class variable).
	 * Only one Scanner on System.in to avoid unexpected side-effects
	 * 
	 */
	private static final Scanner SCANNER = new Scanner(System.in);
	// Here I use TreeMap class with default constructor.
	// The order in the Map is the default comparator of the key object.
	// The default comparator for Integer is numeric ascending order.
	// Using a Map gives the possibility of reorganize the numbers in the option
	// list easily
	private TreeMap<Integer, String> options;

	/*
	 * Constructor with options as parameter. No default constructor.
	 */
	public UserInput(TreeMap<Integer, String> options) {
		super();
		this.setOptions(options);
	}

	/*
	 * This function ask the user to select an option with Scanner. The selected
	 * option is returned as an Integer. It checks if the user input is valid:
	 *  - it must be possible to parse it to an Integer and
	 *  - it should be in the key set of the options
	 */
	public Integer getOption(String message) {
		// Print initial message and available options to user
		System.out.println(message);
		for (Entry<Integer, String> option : options.entrySet()) {
			System.out.println(option.getKey() + ".- " + option.getValue());
		}

		Integer selectedOption = null;
		do {
			System.out.print("Enter an option: ");
			try {
				// Ask the user for input
				selectedOption = Integer.valueOf(SCANNER.nextLine());
			} catch (NumberFormatException ex) {
				// Cannot read input as an Integer
				System.out.println("Invalid option. Please, try again.");
				selectedOption = null;
			}
			if (selectedOption != null) {
				if (!options.keySet().contains(selectedOption)) {
					// Selection option is not in the list of options
					System.out.println("Selection option is not in the list of available options. Please, try again.");
					selectedOption = null;
				}
			}
		} while (selectedOption == null);
		System.out.println();
		return selectedOption;
	}

	/*
	 * Function to read a String from the user
	 */
	public String getString(String message) {
		System.out.print(message);
		return SCANNER.nextLine();
	}

	/*
	 * Function to read a text from the user until a EOF tag specified in the
	 * parameter until.
	 */
	public String getTextUntil(String message, String until) {
		System.out.println(message);
		String line;
		// StringBuilder to construct the final text
		StringBuilder text=new StringBuilder();
		do {
			line = SCANNER.nextLine();
			text.append(line);
			text.append(System.getProperty("line.separator"));
			// Exit if the line ends with specified tag
		} while (!line.endsWith(until));
		// Return text removing tag and last line separator
		return text.substring(0, text.length() - until.length()-System.getProperty("line.separator").length());
	}

	/*
	 * Getters and setters
	 */
	public TreeMap<Integer, String> getOptions() {
		return options;
	}

	public void setOptions(TreeMap<Integer, String> options) {
		this.options = options;
	}

}