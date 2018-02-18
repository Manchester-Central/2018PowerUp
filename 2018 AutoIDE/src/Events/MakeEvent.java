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

	@Override
	public void handle(ActionEvent event) {
		try {
			String filePath = info.filePath() + "\\" + fileName.getText() + ".ini";
			output = new PrintWriter(filePath, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			
			e.printStackTrace();
			return;
		}
		output.println("[NetworkTables Storage 3.0]");
		for (PreferenceLine x : stages) {
			String symbol = (x.getIsParallel().isSelected()) ? "," : ".";
			
			output.println("string \"/Preferences/Auto Stage " + x.getStageNumber().getText()
			+ "\"=\"" + x.getCommands().getSelectionModel().getSelectedItem().toString()
			+ ";" + x.getInput().getText() + symbol + "\"");
		}
		output.close();
	}
}
