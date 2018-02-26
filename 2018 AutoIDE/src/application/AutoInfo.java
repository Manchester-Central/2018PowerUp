package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AutoInfo {
	
	private ObservableList<String> commandList;
	
	private ArrayList<String> files;
	
	private HashMap <String, String> configMap;
	
	private HashMap <String, String> commandPlaceholders;
	
	public AutoInfo() {
		commandList = FXCollections.observableArrayList();
		files = new ArrayList <String>();
		configMap = new HashMap <String, String> ();
		commandPlaceholders = new HashMap <String, String> ();
		
		try {
			File file = new File("C:\\Program Files\\AutoFolder\\Command_Config.txt");
			
			FileReader reee = new FileReader (file);
			BufferedReader reader = new BufferedReader (reee);
			
			String line;
			
			while ((line = reader.readLine()) != null) {
				
				String command = line.split(":")[0];
				
				commandList.add(command);
				
				if (line.split("\\|").length > 1) {
					commandPlaceholders.put(command, line.split("\\|")[1].trim());
					System.out.println(command);
					System.out.println(line.split("\\|")[1].trim());
				}
				
				
			}
			
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
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
		
		try {

			File file = new File ("C:\\Program Files\\AutoFolder\\Config.txt");
			
			FileReader reee = new FileReader (file);
			BufferedReader reader = new BufferedReader (reee);
			
			String line;
			
			while ((line = reader.readLine()) != null) {
				
				String[] args = line.split("=");
				
				if (args.length == 2)
					configMap.put(args[0], args[1]);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
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
	

}
