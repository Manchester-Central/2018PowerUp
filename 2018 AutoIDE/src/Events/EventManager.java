package Events;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import application.AutoInfo;
import application.PreferenceLine;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class EventManager {
	
	/**
	 * organizes each line and gives them the appropriate stage number
	 * removes entries that are to be deleted 
	 * @param stages - the list of lines
	 * @param layout - the layout for entries to be removed from and added to
	 * @param movableElements - Node elements that can be moved (fileName and PreferenceLines)
	 * @param fileName - the TextField for the title
	 */
	public static void reorganizeEntries(List <PreferenceLine>stages, 
			StackPane layout, ArrayList<Node> movableElements, TextField fileName) {
		
		int stagePosition = 1;
		int stageNumber   = 1;
		
		List <PreferenceLine> removedEntries = new ArrayList <PreferenceLine> ();
		
		// 
		for (PreferenceLine x : stages) {
			if (!x.getIsOff()) {
				
				x.setYPostions((stagePosition * 30) + fileName.getTranslateY());
				x.setStageNumber(stageNumber);
				
				if (!x.getIsParallel().isSelected()) {
					stageNumber++;
				}
				x.setUniqueNumber(stagePosition);
				stagePosition++;;
				
			} else {
				
				movableElements.remove(x.getCommands());
				movableElements.remove(x.getInput());
				movableElements.remove(x.getIsParallel());
				movableElements.remove(x.getDelete());
				movableElements.remove(x.getStageNumber());
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
	
	/**
	 * loads a file from the save folder into the editor
	 * @param file - the file to load
	 * @param primaryStage - the stage to add PrefenceLine elements to
	 * @param fileName - the TextField for the title
	 * @param stages - the list of lines
	 * @param layout - the layout for entries to be removed from and added to
	 * @param info - the build info for getting corresponding commands and placeholders
	 * @param movableElements - Node elements that can be moved (fileName and PreferenceLines)
	 */
	public static void load (File file, Stage primaryStage, TextField fileName,
			List <PreferenceLine> stages, StackPane layout, AutoInfo info, 
			ArrayList <Node> movableElements) {
		
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
		EventManager.reorganizeEntries(stages, layout, movableElements, fileName);
		
		stages.clear();
		
		for (String line : lines) {
			if (!line.equals("string \"/Preferences/.type\"=\"RobotPreferences\"") && !line.equals("[NetworkTables Storage 3.0]")) {
				
				PreferenceLine newLine = new PreferenceLine (layout, stages, info, movableElements, fileName);
				
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
		fileName.setTranslateY(-200);
		EventManager.reorganizeEntries(stages, layout, movableElements, fileName);
	}

}
