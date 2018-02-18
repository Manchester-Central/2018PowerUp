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
	
	public void setfiles () {
		
		File folder = new File(filePath());
		System.out.println(folder.getAbsolutePath());
		
		if (folder == null)
			return;
		
		for (File file : folder.listFiles()) {
			
			if (!file.isDirectory() && file.getName().endsWith(".ini")) {
				
				files.add(file.getName().replaceAll(".ini", ""));
				
			}
			
		}
		
		
	}
	
	public ObservableList<String> getFileNames () {
		
		return FXCollections.observableArrayList(files);
		
	}
	
	public HashMap <String, String> getCommandPlaceholders () {
		return commandPlaceholders;
	}
	

}
