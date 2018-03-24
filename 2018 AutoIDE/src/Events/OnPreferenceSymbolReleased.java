package Events;

import java.util.ArrayList;

import application.AutoInfo;
import application.PreferenceLine;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class OnPreferenceSymbolReleased implements EventHandler <MouseEvent>{

	private final ArrayList<PreferenceLine> stages;
	private final StackPane layout;
	private final AutoInfo info;
	private ArrayList <Node> movableElements;
	private TextField fileName;
	double sceneHeight;
	PreferenceLine self;
	
	public OnPreferenceSymbolReleased (ArrayList<PreferenceLine> stages, StackPane layout, AutoInfo info,
			ArrayList <Node> movableElements, TextField fileName, double sceneHeight, PreferenceLine self) {
		this.movableElements = movableElements;
		this.fileName = fileName;
		this.stages = stages;
		this.layout = layout;
		this.info = info;
		this.sceneHeight = sceneHeight;
		this.self = self;
	}

	@Override
	public void handle(MouseEvent event) {
		
		double referencePosition = fileName.getTranslateY();
		
		double maxListY = stages.size() * 30;
		
		double objectPosition = event.getSceneY() - (sceneHeight/2) + 30;
		
		stages.remove(self);
		
		if (objectPosition < referencePosition) {
			stages.add(0, self);
		} else if (objectPosition > maxListY + referencePosition ) {
			stages.add(self);
		} else {
			stages.add(( (int) (Math.abs(referencePosition) - Math.abs(objectPosition - 15)) ) / 30, self);
		}
		
		EventManager.reorganizeEntries(stages, layout, movableElements, fileName);
		
		
	}

}
