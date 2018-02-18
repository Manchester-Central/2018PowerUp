package Events;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import application.AutoInfo;
import application.PreferenceLine;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class NumberManager {

	public NumberManager() {
		// TODO reorganize entries on isParallel check, refactor reorganization in other events
	}
	
	public static void reorganizeEntries(List <PreferenceLine>stages, StackPane layout) {
		int stagePosition = 1;
		int stageNumber =   1;
		
		List <PreferenceLine> removedEntries = new ArrayList <PreferenceLine> ();
		
		for (PreferenceLine x : stages) {
			if (!x.getIsOff()) {
				x.setYPostions((stagePosition * 30) - 200);
				x.setStageNumber(stageNumber);
				if (!x.getIsParallel().isSelected()) {
					stageNumber++;
				}
				stagePosition++;
			} else {
				layout.getChildren().remove(x.getCommands());
				layout.getChildren().remove(x.getInput());
				layout.getChildren().remove(x.getIsParallel());
				layout.getChildren().remove(x.getDelete());
				layout.getChildren().remove(x.getStageNumber());
				removedEntries.add(x);
			}
		}
		
		
		stages.removeAll(removedEntries);
	

	}
	
	public static void load (File file, Stage primaryStage, TextField fileName,
			List <PreferenceLine> stages, StackPane layout, AutoInfo info) {
		
		ArrayList <String> lines = new ArrayList<String> ();
		
		try {
			
			
			FileReader fileReader = new FileReader (file);
			BufferedReader br = new BufferedReader (fileReader);
			String line;
			fileName.setText(file.getName().substring(0, file.getName().length() - 4));
			
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		for (PreferenceLine line : stages) {
			line.setIsOff(true);
		}
		NumberManager.reorganizeEntries(stages, layout);
		
		stages.clear();
		
		for (String line : lines) {
			if (!line.equals("string \"/Preferences/.type\"=\"RobotPreferences\"") && !line.equals("[NetworkTables Storage 3.0]")) {
				
				PreferenceLine newLine = new PreferenceLine (layout, stages, info);
				
				String comma = ",";
				
				String valueLine = line.split("\"")[3].split(";")[1];
				String numberLine = line.split("\"")[1];
				
				String command = line.split("\"")[3].split(";")[0];
				String value = valueLine.substring(0, valueLine.length() - 1);
				boolean isParallel = comma.equals(valueLine.substring(valueLine.length() - 1));
				String stageNumber = numberLine.substring(numberLine.length() - 1);
				
				newLine.setComponents(command, value, isParallel, stageNumber);
				
				stages.add(newLine);
				
				
				
			}
		}
		NumberManager.reorganizeEntries(stages, layout);
	}

}
