package com.lockedme.filemanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.TreeSet;

/*
 * This class implements the main functionality of the project.
 * With this class the user can list, create, delete and search files in a folder.
 * The folder can be specified in a constructor.
 * If none is specified, the class uses the working director as the default folder and does not ignore case while sorting files.
 * It has a method for each of the functionalities required by the specifications.
 * 
 * @author Rafael López Benavente
 * @version 0.0.1
 */
public class FileManager {
	private static final String DEFAULT_PATH = ".";
	private static final boolean DEFAULT_IGNORE_CASE = false;

	private File parent;
	private boolean ignoreCase;

	/*
	 * DONE in sprint 1
	 * 
	 * Constructors with none or one argument. Calls the constructor with
	 * path and ignore case arguments using default values in DEFAULT_PATH and DEFAULT_IGNORE_CASE constants
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
	 * DONE in sprint 1
	 * 
	 * Constructor with path as argument. Throws a
	 * FileNotFoundException if:
	 *  - the path does not exists,
	 *  - it is not a folder,
	 *  - it is not readable or
	 *  - it is not writable.
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
	 * DONE in sprint 1
	 * 
	 * This function returns the absolute path specified by the
	 * user in the arguments. If the user does not specify a folder, the working
	 * directory is selected as default in the constructor of this class.
	 */
	public String getWorkingDirectory() {
		return parent.getAbsolutePath();
	}

	/*
	 * DONE in sprint 1
	 * 
	 * This function lists the files of the selected folder in
	 * ascending order. The output is indented for more clarity.
	 * The folder names have an slash at the end.
	 */
	public String listFiles() {

		TreeSet<String> treeSetFiles;
		if (isIgnoreCase()) {
			// TreeSet class with constructor with Comparator as constructor.
			// Invoked with a comparator provided by String class that ignore cases in Strings.
			treeSetFiles = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
		} else {
			// Here I use TreeSet class with default constructor.
			// The order is the default comparator of the stored object.
			// The default comparator for Strings is lexicographic case sensitive order. 
			treeSetFiles = new TreeSet<>();
		}
		File[] files;
		try {
			files = parent.listFiles();
		} catch (Exception ex) {
			// Cannot access the folder now
			String error = "Cannot list files of folder '" + getWorkingDirectory() + "'.\n";
			error = error + "ERROR: " + ex.getLocalizedMessage();
			return error;
		}
		if (files == null) {
			// Error accessing the folder due to file system permission
			String error = "Cannot list files of folder '" + getWorkingDirectory() + "'.\n";
			error = error + "ERROR: Abstract pathname does not denote a directory";
			return error;
		}
		if (files.length > 0) {
			// The folder has some files or other folders in it
			String message = "The list of files and folders in '" + getWorkingDirectory() + "' in ascending order are:\n";
			for (File file : files) {
				if (file.isDirectory()) {
					// If folder, add a trailing slash
					treeSetFiles.add(file.getName() + "/");
				} else {
					treeSetFiles.add(file.getName());
				}
			}
			return message + "\t" + String.join("\n\t", treeSetFiles);
		} else {
			// Empty folder
			String message = "The folder '" + getWorkingDirectory() + "' is empty.";
			return message;
		}
	}
	
	// Done in sprint 1 Getters and setters
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

	// TODO in sprint 2
	public String addFile() {
		return "TODO in sprint 2";
	}

	// TODO in sprint 2
	public String deleteFile() {
		return "TODO in sprint 2";
	}

	// TODO in sprint 2
	public String searchFile() {
		return "TODO in sprint 2";
	}
	

}
