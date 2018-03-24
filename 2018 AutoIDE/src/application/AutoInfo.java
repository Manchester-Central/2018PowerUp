package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AutoInfo {
	
	// Every command in the command box
	private ObservableList<String> commandList;

	// The names (without .ini) of every config file 
	private ArrayList<String> files;
	
	// Key and value for every configuration variable
	private HashMap <String, String> configMap;
	
	// Grayed background text for every command value
	private HashMap <String, String> commandPlaceholders;
	
	public AutoInfo() {
		commandList = FXCollections.observableArrayList();
		files = new ArrayList <String>();
		configMap = new HashMap <String, String> ();
		commandPlaceholders = new HashMap <String, String> ();
		
		// creates command list with placeholders
		try {
			File file = new File("C:\\Program Files\\AutoFolder\\Command_Config.txt");
			
			FileReader fr = new FileReader (file);
			BufferedReader reader = new BufferedReader (fr);
			
			String line;
			
			// for every line in the Command_Config file
			while ((line = reader.readLine()) != null) {
				
				// command:placeholder
				
				if (!match(line)) {
					continue;
				}
				
				String command = line.split(":")[0];
				//System.out.print (command + ": ");
				String value = line.split("\\|")[1].trim();
				//System.out.print(value + ": ");
				//System.out.println(match(line));
				
				
				commandList.add(command);
				
				if (line.split("\\|").length > 1) {
					commandPlaceholders.put(command, value);
				}
				
				
			}
			
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			//generates default command list
			commandList = 
				    FXCollections.observableArrayList(
				        "Test",
				        "Drive",
				        "Extend",
				        "Intake",
				        "Lift",
				        "Output",
				        "Retract",
				        "ScaleDrive",
				        "ScaleTurn",
				        "SwitchDrive",
				        "SwitchTurn",
				        "Turn",
				        "Wait",
				        "None"
				    );
			
			
			
		}
		
		// Generates config map.
		try {

			File file = new File ("C:\\Program Files\\AutoFolder\\Config.txt");
			
			FileReader fr = new FileReader (file);
			BufferedReader reader = new BufferedReader (fr);
			
			String line;
			
			// for every line in the Config.txt file
			while ((line = reader.readLine()) != null) {
				
				// config_variable=config_value
				String[] args = line.split("=");
				
				if (args.length == 2)
					configMap.put(args[0], args[1]);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	/**
	 * Gets save path under Save_Path config_variable (or desktop default if Save_Path doesn't 
	 * exist) and creates directory if it doesn't exist.
	 * 
	 * @return The path to the Save Folder
	 */
	public String filePath () {
		
		String savePath = configMap.getOrDefault("Save_Path", System.getProperty("user.home") + "\\Desktop\\Auto Modes");
		
		File file = new File (savePath);
		
		if (!file.exists())
			file.mkdirs();
		
		return savePath;
	}
	
	public ObservableList <String> getCommandList () {
		return commandList;
	}
	
	/**
	 * Adds each file name in the save folder to the files list
	 * @param folder - Save Path and embedded files
	 */
	private void recursiveFileArrayCreate (File folder) {
		
		for (File file : folder.listFiles()) {
			
			if (!file.isDirectory()) {
				
				if (file.getName().endsWith(".ini")) {
					
					files.add(file.getName().replaceAll(".ini", ""));
					
				}
				
			} else {
				
				recursiveFileArrayCreate (file);
				
			}
			
		}
		
	}
	
	/**
	 * Gets the file path for the specified file name in the save path
	 * @param folderPath - the save path and embedded folders
	 * @param name - the name (without .ini) of the file requested
	 * @return the file path or "Not Found" if not found
	 */
	public String getFilePath (String folderPath, String name) {
		
		File saveFolder = new File (folderPath);
		
		for (File file : saveFolder.listFiles()) {
			
			if (!file.isDirectory()) {
				
				if (file.getName().replaceAll(".ini", "").equals(name)) {
					
					System.out.println(file.getPath());
					return file.getPath();
					
				}
				
			} else {
				
				String result = getFilePath (file.getPath(), name);
				
				if (!result.equals("Not Found")) {
					
					return result;
					
				}
				
			}
			
		}
		
		return "Not Found";
		
	}
	
	/**
	 * Finds all saved files in the saved files folder and puts them in the load dropdown
	 */
	public void setfiles () {
		
		File folder = new File(filePath());
		System.out.println(folder.getAbsolutePath());
		
		if (folder == null)
			return;
		
		files.clear();
		
		recursiveFileArrayCreate(folder);
		
	}
	
	public ObservableList<String> getFileNames () {
		
		return FXCollections.observableArrayList(files);
		
	}
	
	public HashMap <String, String> getCommandPlaceholders () {
		return commandPlaceholders;
	}
	
	/**
	 * 
	 * @param check - the line to check
	 * @return - whether the line matches the syntax
	 */
	private boolean match(String check) {
		return Pattern.matches("[^:]+:[^:]*[|]*", check);
	}
	

}
