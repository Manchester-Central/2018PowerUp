package Events;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import application.AutoInfo;
import application.PreferenceLine;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class DropDownLoadEvent implements ChangeListener <String> {

	private FileChooser fileChooser;
	private TextField fileName;
	private List<PreferenceLine> stages;
	private StackPane layout;
	private AutoInfo info;
	private ArrayList <Node> movableElements;
	
	private Stage primaryStage;
	
	public DropDownLoadEvent(TextField fileName, List<PreferenceLine> stages,
			Stage primaryStage, StackPane layout, AutoInfo info, ArrayList <Node> movableElements) {
		
		this.movableElements  = movableElements;
		fileChooser = new FileChooser ();
		this.fileName = fileName;
		this.info = info;
		this.stages = stages;
		this.layout = layout;
		this.primaryStage = primaryStage;
	}

	/**
	 * Determines if the the user selected a different file and if they have,
	 * then it finds the file path of the new file and loads that file
	 */
	@Override
	public void changed(ObservableValue<? extends String> observable, 
			String oldValue, String newValue) {
		
		if (newValue == null)
			return;
		
		if (oldValue == null || !newValue.equals(oldValue)) {
			File file = new File (info.getFilePath(info.filePath(), newValue));
			EventManager.load(file, primaryStage, fileName, stages, layout, info, movableElements);
		}
		
	}

}
