package Events;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import application.AutoInfo;
import application.PreferenceLine;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class LoadEvent implements EventHandler<ActionEvent>{

	private FileChooser fileChooser;
	private TextField fileName;
	private List<PreferenceLine> stages;
	private StackPane layout;
	private AutoInfo info;
	private ArrayList <Node> movableElements;
	private double sceneHeight;
	
	private Stage primaryStage;
	
	public LoadEvent(TextField fileName, List<PreferenceLine> stages,
			Stage primaryStage, StackPane layout, AutoInfo info, ArrayList<Node> movableElements,
			double sceneHeight) {
		fileChooser = new FileChooser ();
		this.movableElements = movableElements;
		this.fileName = fileName;
		this.info = info;
		this.stages = stages;
		this.layout = layout;
		this.primaryStage = primaryStage;
		this.sceneHeight = sceneHeight;
	}

	/**
	 * Calls the load method with open dialog file
	 */
	@Override
	public void handle(ActionEvent event) {
		
		File file = fileChooser.showOpenDialog(primaryStage);
		
		EventManager.load(file, primaryStage, fileName, stages, layout, info, movableElements, sceneHeight);
		
	}

}
