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
	private ArrayList <Node> nodes;
	
	private Stage primaryStage;
	
	public LoadEvent(TextField fileName, List<PreferenceLine> stages,
			Stage primaryStage, StackPane layout, AutoInfo info, ArrayList<Node> nodes) {
		fileChooser = new FileChooser ();
		this.nodes = nodes;
		this.fileName = fileName;
		this.info = info;
		this.stages = stages;
		this.layout = layout;
		this.primaryStage = primaryStage;
	}

	@Override
	public void handle(ActionEvent event) {
		
		File file = fileChooser.showOpenDialog(primaryStage);
		
		NumberManager.load(file, primaryStage, fileName, stages, layout, info, nodes);
		
	}

}
