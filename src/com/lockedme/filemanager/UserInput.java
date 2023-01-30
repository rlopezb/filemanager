package com.lockedme.filemanager;

import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;

/*
 * Class to interact with the user using Scanner
 * I use ArraList for the options because it preserves the insertion order
 * 
 * @author Rafael López Benavente
 * @version 0.0.1
 */
public class UserInput {
	/*
	 * Scanner object defined as static final. Only one Scanner on System.in to
	 * avoid unexpected side-effects
	 */
	private static final Scanner SCANNER = new Scanner(System.in);
	private String message;
	// Here I use TreeMap class with default constructor.
	// The order in the Map is the default comparator of the key object.
	// The default comparator for Integer is numeric ascending order.
	// Using a Map gives the possibility of reorganize the numbers in the option list easily
	private TreeMap<Integer, String> options;

	/*
	 * DONE in sprint 1
	 * 
	 * Constructor with parameters.
	 * No default constructor.
	 */
	public UserInput(String message, TreeMap<Integer, String> options) {
		super();
		this.setMessage(message);
		this.setOptions(options);
	}

	/*
	 * DONE in sprint 1
	 * 
	 * This function ask the user to select an option with Scanner.
	 * The selected option is returned as an Integer. It checks if the user input is
	 * valid: - it must be possible to parse it to an Integer and - it should be
	 * between 1 and the size of the list options
	 */
	public Integer getOption() {
		System.out.println(message);
		for (Entry<Integer, String> option : options.entrySet()) {
			System.out.println(option.getKey() + ".- " + option.getValue());
		}

		Integer selectedOption = null;
		do {
			System.out.print("Enter an option: ");
			try {
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
	 * DONE in sprint 1
	 * 
	 * Getters and setters
	 */
	public TreeMap<Integer, String> getOptions() {
		return options;
	}

	public void setOptions(TreeMap<Integer, String> options) {
		this.options = options;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}