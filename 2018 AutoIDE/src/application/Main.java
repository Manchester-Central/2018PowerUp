package application;
	
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import Events.AddEvent;
import Events.ArrowEvent;
import Events.DropDownLoadEvent;
import Events.LoadEvent;
import Events.MakeEvent;
import Events.OnDropDownClickEvent;
import Events.OpenInfoEvent;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application {

	StackPane layout;
	ComboBox<String> dropDownLoad;
	Button add;
	Button make;
	Button load;
	Button openInfo;
	PrintWriter output;
	TextField fileName;
	Image logo;
	ImageView imageView;
	Label instructions;
	AutoInfo info;
	TextField description;
	ArrayList<Node> movableElements;
	String imageLocation;
	
	Button up;
	Button down;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		movableElements 					= new ArrayList<Node> ();
		description 						= new TextField ();
		add 								= new Button();
		layout 								= new StackPane ();
		load 								= new Button ();
		info 								= new AutoInfo();
		up 									= new Button();
		down 								= new Button();
		openInfo 							= new Button ();
		dropDownLoad 						= new ComboBox <String>();
		instructions 						= new Label();
		imageLocation 						= "C:\\Program Files\\AutoFolder\\Logo.jpg";
		FileInputStream x 					= new FileInputStream (imageLocation);
		logo 								= new Image (x);
		imageView 							= new ImageView (logo);
		make 								= new Button ();
		fileName 							= new TextField ();
		ArrayList <PreferenceLine> stages 	= new ArrayList <PreferenceLine>();
		
		
		instructions.setMaxWidth(175);
		instructions.setTranslateX(250);
		instructions.setTranslateY(0);
		instructions.setText(getInstructions());
		
		imageView.preserveRatioProperty();
		imageView.setFitHeight(140);
		imageView.setFitWidth(260);
		imageView.setTranslateX(270);
		imageView.setTranslateY(-280);
		
		
		fileName.setMaxWidth(250);
		fileName.setPromptText("Title");
		fileName.setTranslateX(0);
		fileName.setTranslateY(-200);
		movableElements.add(fileName);
		layout.getChildren().add(fileName);
		
		
		add.setOnAction(new AddEvent(stages, layout, info, movableElements, fileName));
		add.setTranslateX(-240);
		add.setTranslateY(-140);
		add.setText("add");
		layout.getChildren().add(add);
		
		
		dropDownLoad.setOnMouseClicked(new OnDropDownClickEvent(info, dropDownLoad));
		dropDownLoad.valueProperty().addListener(new DropDownLoadEvent(fileName, 
				stages, primaryStage, layout, info, movableElements));
		dropDownLoad.setTranslateX(-300);
		dropDownLoad.setTranslateY(-100);
		dropDownLoad.setPromptText("Drop-Down load");
		layout.getChildren().add(dropDownLoad);
		
		
		openInfo.setOnAction(new OpenInfoEvent ());
		openInfo.setTranslateX(240);
		openInfo.setTranslateY(200);
		openInfo.setText("open info");
		layout.getChildren().add(openInfo);
		
		
		make.setOnAction(new MakeEvent(stages, fileName, info));
		make.setTranslateX(-240);
		make.setTranslateY(-170);
		make.setText("make");
		layout.getChildren().add(make);
		
		
		load.setOnAction(new LoadEvent (fileName, stages, primaryStage, layout, info, movableElements));
		load.setTranslateX(-240);
		load.setTranslateY(-200);
		load.setText("load");
		layout.getChildren().add(load);
		
		
		up.setOnAction(new ArrowEvent (movableElements, false));
		up.setTranslateX(-280);
		up.setTranslateY(-280);
		up.setText("▲");
		layout.getChildren().add(up);
		
		
		down.setOnAction(new ArrowEvent (movableElements, true));
		down.setTranslateX(-280);
		down.setTranslateY(-240);
		down.setText("▼");
		layout.getChildren().add(down);
		
		
		layout.getChildren().add(imageView);
		layout.getChildren().add(instructions);
		
		Scene scene = new Scene(layout,800,800);
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public String getInstructions () {
		StringBuilder s = new StringBuilder();
		s.append("Title: names file.\n\n");
		s.append("Add: adds Preference Line.\n\n");
		s.append("Load: loads Prefs file.\n\n");
		s.append("Make: creates Prefs file.\n\n");
		s.append("Drop-down box: command.\n\n");
		s.append("Text-field: value given.\n\n");
		s.append("Label: stage number.\n\n");
		s.append("Check-box: Toggles parallel \n(on: parallel, off: sequential)\n\n");
		s.append("Delete: deletes stage.");
		return s.toString();
	}
	

	
	public static void main(String[] args) {
		launch(args);
	}
}
