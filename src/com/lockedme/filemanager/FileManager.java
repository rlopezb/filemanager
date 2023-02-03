package com.lockedme.filemanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.TreeSet;

/*
 * This class implements the main functionality of the project.
 * With this class the user can list, create, delete and search files in a folder.
 * The folder can be specified in a constructor.
 * 
 * If none is specified, the class uses the working director as the default folder and does not ignore case while sorting files.
 * It has a method for each of the functionalities required by the specifications.
 * 
 * @author Rafael López Benavente
 * @version 0.0.2
 */
public class FileManager {
	// Default values
	private static final String DEFAULT_PATH = ".";
	private static final boolean DEFAULT_IGNORE_CASE = false;

	// Main folder and ignore case flag
	private File parent;
	private boolean ignoreCase;

	/*
	 * Constructors with none or one argument. Calls the constructor with path and
	 * ignore case arguments using default values in DEFAULT_PATH and
	 * DEFAULT_IGNORE_CASE constants
	 */
	public FileManager() throws FileNotFoundException {
		this(DEFAULT_PATH, DEFAULT_IGNORE_CASE);
	}

	public FileManager(boolean ignoreCase) throws FileNotFoundException {
		this(DEFAULT_PATH, ignoreCase);
	}

	public FileManager(String path) throws FileNotFoundException {
		this(path, DEFAULT_IGNORE_CASE);
	}

	/*
	 * Constructor with path and ignore case as arguments. Throws a
	 * FileNotFoundException if: - the path does not exists, - it is not a folder, -
	 * it is not readable or - it is not writable.
	 */
	public FileManager(String path, boolean ignoreCase) throws FileNotFoundException {
		super();
		this.parent = new File(path);
		this.ignoreCase = ignoreCase;
		if (!parent.exists()) {
			// Path does not exists
			throw new FileNotFoundException("Path " + path + " does not exists!");
		}
		if (!parent.isDirectory()) {
			// Path is not a folder
			throw new FileNotFoundException("Path " + path + " is not a folder!");
		}
		if (!parent.canRead()) {
			// Folder is not readable
			throw new FileNotFoundException("Folder " + path + " is not readable!");
		}
		if (!parent.canWrite()) {
			// Folder is not writable
			throw new FileNotFoundException("Folder " + path + " is not writable!");
		}
	}

	/*
	 * This function returns the absolute path specified by the user in the
	 * arguments. If the user does not specify a folder, the working directory is
	 * selected as default in the constructor of this class.
	 */
	public String getWorkingDirectory() {
		return parent.getAbsolutePath();
	}

	/*
	 * Function to retrieve a list of files following a pattern. If the parameter
	 * pattern is set to null, the function will return all files in the folder.
	 */
	private TreeSet<String> getFiles(String pattern) throws IOException {
		TreeSet<String> treeSetFiles;
		if (isIgnoreCase()) {
			// New TreeSet using a constructor with Comparator as parameter
			// provided by String class that ignore cases.
			treeSetFiles = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
		} else {
			// Here I use TreeSet class with default constructor.
			// The order is the default comparator of the stored object.
			// The default comparator for Strings is lexicographic case sensitive order.
			treeSetFiles = new TreeSet<>();
		}
		FilenameFilter filter = null;
		if (pattern != null) {
			filter = new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					// Using regular expressions gives more possibilities to the end user
					return name.matches(pattern);
				}
			};
		}
		File[] files;
		files = parent.listFiles(filter);
		if (files == null) {
			// Error accessing the folder due to file system permission or error
			throw new IOException(
					"The pathname '" + getWorkingDirectory() + "' does not denote a directory, or an I/O error occurred");
		}
		for (File file : files) {
			if (file.isDirectory()) {
				// If folder, add a trailing slash for readability
				treeSetFiles.add(file.getName() + "/");
			} else {
				treeSetFiles.add(file.getName());
			}
		}
		return treeSetFiles;
	}

	/*
	 * This function lists the files of the selected folder in ascending order. The
	 * output is indented for more clarity. The folder names have an slash at the
	 * end.
	 */
	public String listFiles() {
		TreeSet<String> treeSetFiles;
		try {
			treeSetFiles = getFiles(null);
		} catch (IOException ex) {
			// Error accessing the folder due to file system permission or error
			String error = "Cannot list files in folder '" + getWorkingDirectory() + "'.\n";
			error = error + ex.getMessage();
			return error;
		}

		if (treeSetFiles.size() > 0) {
			// The folder has some files or other folders in it
			String message = "The list of files and folders in '" + getWorkingDirectory() + "' in ascending order are:\n";
			return message + "\t" + String.join("\n\t", treeSetFiles);
		} else {
			// Empty folder
			String message = "The folder '" + getWorkingDirectory() + "' is empty.";
			return message;
		}
	}

	/*
	 * Function that checks if a file already exists in the main folder
	 */
	public boolean exists(String name) {
		File file = new File(getWorkingDirectory() + System.getProperty("file.separator") + name);
		return file.exists();
	}

	/*
	 * Function to create a text file with name and content specified in the
	 * parameters. The function checks if the file name is free.
	 * 
	 */
	public String addFile(String name, String content) {
		File file;
		file = new File(getWorkingDirectory() + System.getProperty("file.separator") + name);
		if (file.exists()) {
			// File already exists
			String error = "A file with name '" + name + "' already exists in '" + getWorkingDirectory() + "'.";
			return error;
		}
		try {
			// Open the file, write and close
			FileWriter writer = new FileWriter(file);
			writer.write(content);
			writer.close();
			return "File '" + name + "' created.";
		} catch (IOException ex) {
			// Cannot access the file now
			String error = "Cannot create the file '" + name + "'.\n";
			error = error + ex.getMessage();
			return error;
		}
	}

	/*
	 * Delete a single file or folder by its name. The function checks if the path
	 * exists and it can be deleted.
	 */
	public String deletePath(String name) {
		File path;
		path = new File(getWorkingDirectory() + File.separator + name);
		if (!path.exists()) {
			// Path does not exist
			String error = "File or folder '" + name + "' does not exists in '" + getWorkingDirectory() + "'.";
			return error;
		}
		if (!path.canWrite()) {
			String error = "File or folder '" + name + "' is not writable.";
			return error;
		}
		if (path.isDirectory()) {
			// The path is a folder
			if(path.list() == null) {
				// Cannot list files in folder
				String error = "Cannot list files in folder '" + name + "'.";
				return error;
			}
			if (path.list().length > 0) {
				// Folder is not empty
				String error = "Cannot delete folder '" + name + "'.\n";
				error = error + "Folder is not empty.";
				return error;
			}
			if (path.delete()) {
				return "Folder '" + name + "' deleted!";
			} else {
				// Cannot delete the folder
				String error = "Cannot delete folder '" + name + "'.\n";
				return error;
			}
		} else {
			if (path.delete()) {
				return "File '" + name + "' deleted.";
			} else {
				// Cannot delete the folder
				String error = "Cannot delete file '" + name + "'.\n";
				return error;
			}
		}
	}

	/*
	 * Function to search files in the folder that match a regular expression. Using
	 * a regular expression gives more possibilities to the user, including complete
	 * name of the file.
	 */
	public String searchFiles(String pattern) {
		TreeSet<String> treeSetFiles;
		try {
			treeSetFiles = getFiles(pattern);
		} catch (IOException e) {
			// Error accessing the folder due to file system permission
			String error = "Cannot list files of folder '" + getWorkingDirectory() + "'.\n";
			error = error + "Abstract pathname does not denote a directory";
			return error;
		}

		if (treeSetFiles.size() > 0) {
			// The folder has some files or other folders in it
			String message = "The list of files and folders in '" + getWorkingDirectory() + "' matching the expression '"
					+ pattern + "' in ascending order are:\n";
			return message + "\t" + String.join("\n\t", treeSetFiles);
		} else {
			// Empty folder
			String message = "There aren't any file matching the expresion '" + pattern + "' in folder '"
					+ getWorkingDirectory() + "'.";
			return message;
		}
	}

	// Getters and setters
	public File getParent() {
		return parent;
	}

	public void setParent(File parent) {
		this.parent = parent;
	}

	public boolean isIgnoreCase() {
		return ignoreCase;
	}

	public void setIgnoreCase(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

}
