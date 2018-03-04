package Events;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import application.AutoInfo;
import application.PreferenceLine;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public final class MakeEvent implements EventHandler<ActionEvent> {
	private final ArrayList<PreferenceLine> stages;
	private PrintWriter output;
	private TextField fileName;
	private AutoInfo info;

	public MakeEvent(ArrayList<PreferenceLine> stages, TextField fileName, AutoInfo info) {
		this.stages = stages;
		this.fileName = fileName;
		this.info = info;
	}

	/**
	 * Makes auto file at previous path or main save path if there was no previous path
	 */
	@Override
	public void handle(ActionEvent event) {
		
		try {
			
			// sets the file path to main unless the file already exists, in which case it is
			// set to the previous file path
			String filePath = info.getFilePath(info.filePath(), fileName.getText());
			if (filePath.equals("Not Found")) {
				filePath = info.filePath() + "\\" + fileName.getText() + ".ini";
			}
			
			
			output = new PrintWriter(filePath, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return;
		}
		
		
		// the header of every preference file
		output.println("[NetworkTables Storage 3.0]");
		
		// writes each line
		for (PreferenceLine x : stages) {
			
			int uniqueNumber = x.getUniqueNumber();
			String symbol = (x.getIsParallel().isSelected()) ? "," : ".";
			String stageNumber = x.getStageNumber().getText();
			String command = x.getCommands().getSelectionModel().getSelectedItem().toString();
			String value = x.getInput().getText();
			
			// stageNumber=command;valuesymbol
			output.println(String.format("string \"/Preferences/%s Auto Stage %s\"=\"%s;%s%s\"",
					uniqueNumber, stageNumber, command, value, symbol));
		}
		
		output.close();
	}
}
