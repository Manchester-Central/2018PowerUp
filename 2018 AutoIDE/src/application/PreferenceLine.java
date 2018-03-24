package application;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import Events.CommandBoxChangeEvent;
import Events.DeleteEvent;
import Events.OnPreferenceSymbolDragged;
import Events.OnPreferenceSymbolReleased;
import Events.ParallelClickedEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

/**
 * Controls the components of each line
 * @author CHAOS131
 *
 */
public class PreferenceLine {

	private ComboBox <String> commands;
	
	private TextField input;
	
	private Label stageNumber;
	
	private CheckBox isParallel;
	private boolean isOff;
	
	private Button delete;
	private StackPane layout;
	
	private int uniqueNumber;
	
	ImageView dragAndDropSymbol;
	
	// The list of movable elements in the scene (the lines and the fileName Textfield)
	private ArrayList <Node> movableElements;
	
	public PreferenceLine (StackPane layout, List <PreferenceLine> stages, AutoInfo info,
			ArrayList <Node> movableElements, TextField fileName, double sceneHeight) {
		
		try {
			String imageLocation = "C:\\Program Files\\AutoFolder\\reorder symbol.png";
			FileInputStream x = new FileInputStream (imageLocation);
			Image logo 	= new Image (x);
			dragAndDropSymbol = new ImageView (logo);
		} catch (Exception e) {
			
		}
		
		ColorAdjust adjust = new ColorAdjust ();
		adjust.setBrightness(0.45);
		
		dragAndDropSymbol.setEffect(adjust);
		dragAndDropSymbol.preserveRatioProperty();
		dragAndDropSymbol.setFitHeight(40);
		dragAndDropSymbol.setFitWidth(60);
		
		dragAndDropSymbol.setOnMouseDragged(new OnPreferenceSymbolDragged(this, sceneHeight));
		dragAndDropSymbol.setOnMouseReleased(new OnPreferenceSymbolReleased((ArrayList<PreferenceLine>) stages, layout, info, movableElements, fileName, sceneHeight, this));
		
		isOff = false;
		commands = new ComboBox <String> (info.getCommandList());
		input = new TextField();
		isParallel = new CheckBox ();
		delete = new Button ();
		this.layout = layout;
		this.movableElements = movableElements;
		stageNumber = new Label ();
		
		uniqueNumber = 1;
		
		stageNumber.setMaxWidth(40);
		delete.setText("delete");
		stageNumber.setMaxWidth(20);
		input.setMaxWidth(70);
		
		delete.setOnAction(new DeleteEvent(layout, stages, this, movableElements, fileName));
		isParallel.setOnMouseReleased(new ParallelClickedEvent (stages, layout, movableElements, fileName));
		commands.valueProperty().addListener(new CommandBoxChangeEvent(input, info));

		addToScene ();
	}
	
	private void setXPositions () {
		
		commands.setTranslateX(-130);
		
		input.setTranslateX(-20);
		
		stageNumber.setTranslateX(40);
		
		isParallel.setTranslateX(70);
		
		delete.setTranslateX(110);
		
		dragAndDropSymbol.setTranslateX(170);
	}
	
	public void setYPostions (double y) {
		commands.setTranslateY(y);
		input.setTranslateY(y);
		isParallel.setTranslateY(y);
		delete.setTranslateY(y);
		stageNumber.setTranslateY(y);
		dragAndDropSymbol.setTranslateY(y);
	}
	
	public boolean getIsOff () {
		return isOff;
	}

	public void setIsOff (boolean value) {
		isOff = value;
	}
	
	public ComboBox <String> getCommands () {
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
	
	public ImageView getMoveSymbol () {
		return dragAndDropSymbol;
	}
	
	public void setStageNumber (int number) {
		stageNumber.setText(String.valueOf(number));
	}
	
	public void setUniqueNumber (int x) {
		uniqueNumber = x;
	}
	
	public int getUniqueNumber () {
		return uniqueNumber;
	}
	
	public void addToScene () {
		layout.getChildren().add(commands);
		layout.getChildren().add(input);
		layout.getChildren().add(isParallel);
		layout.getChildren().add(delete);
		layout.getChildren().add(stageNumber);
		layout.getChildren().add(dragAndDropSymbol);
		movableElements.add(commands);
		movableElements.add(input);
		movableElements.add(isParallel);
		movableElements.add(delete);
		movableElements.add(stageNumber);
		movableElements.add(dragAndDropSymbol);
		setXPositions ();
	}
	
	public void setComponents (String command, String value, boolean parallel, String number) {
		commands.getSelectionModel().select(command);
		input.setText(value);
		isParallel.setSelected(parallel);
		stageNumber.setText(number);
	}

}
