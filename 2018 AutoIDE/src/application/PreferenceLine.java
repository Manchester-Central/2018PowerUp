package application;

import java.util.ArrayList;
import java.util.List;

import Events.CommandBoxChangeEvent;
import Events.DeleteEvent;
import Events.ParallelClickedEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class PreferenceLine {

	private ComboBox <String> commands;
	
	private TextField input;
	
	private Label stageNumber;
	
	private CheckBox isParallel;
	private boolean isOff;
	
	private Button delete;
	private StackPane layout;
	
	private AutoInfo info;
	private ArrayList <Node> nodes;
	
	public PreferenceLine (StackPane layout, List <PreferenceLine> stages, AutoInfo info,
			ArrayList <Node> nodes, TextField fileName) {
		isOff = false;
		this.info = info;
		commands = new ComboBox <String> (info.getCommandList());
		input = new TextField();
		isParallel = new CheckBox ();
		
		delete = new Button ();
		
		delete.setOnAction(new DeleteEvent(layout, stages, this, nodes, fileName));

		this.layout = layout;
		this.nodes = nodes;
		
		stageNumber = new Label ();
		stageNumber.setMaxWidth(40);
		

		isParallel.setOnMouseReleased(new ParallelClickedEvent (stages, layout, nodes, fileName));
		commands.valueProperty().addListener(new CommandBoxChangeEvent(input, info));

		addToScene ();
	}
	
	private void setPositions () {
		
		commands.setTranslateX(-120);
		input.setTranslateX(-20);
		stageNumber.setTranslateX(40);
		stageNumber.setMaxWidth(20);
		input.setMaxWidth(70);
		isParallel.setTranslateX(70);
		delete.setTranslateX(110);
		delete.setText("delete");
	}
	
	public void setYPostions (double y) {
		commands.setTranslateY(y);
		input.setTranslateY(y);
		isParallel.setTranslateY(y);
		delete.setTranslateY(y);
		stageNumber.setTranslateY(y);
	}
	
	public boolean getIsOff () {
		return isOff;
	}

	public void setIsOff (boolean value) {
		isOff = value;
	}
	
	public ComboBox getCommands () {
		return commands;
	}
	
	public TextField getInput () {
		return input;
	}
	
	public CheckBox getIsParallel () {
		return isParallel;
	}
	
	public Button getDelete () {
		return delete;
	}
	
	public Label getStageNumber () {
		return stageNumber;
	}
	
	public void setStageNumber (int number) {
		stageNumber.setText(String.valueOf(number));
	}
	
	public void addToScene () {
		layout.getChildren().add(commands);
		layout.getChildren().add(input);
		layout.getChildren().add(isParallel);
		layout.getChildren().add(delete);
		layout.getChildren().add(stageNumber);
		nodes.add(commands);
		nodes.add(input);
		nodes.add(isParallel);
		nodes.add(delete);
		nodes.add(stageNumber);
		setPositions ();
	}
	
	public void setComponents (String command, String value, boolean parallel, String number) {
		commands.getSelectionModel().select(command);
		input.setText(value);
		isParallel.setSelected(parallel);
		stageNumber.setText(number);
	}

}
