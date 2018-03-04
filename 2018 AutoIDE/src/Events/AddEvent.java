package Events;

import java.util.ArrayList;

import application.AutoInfo;
import application.PreferenceLine;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public final class AddEvent implements EventHandler<ActionEvent> {
	private final ArrayList<PreferenceLine> stages;
	private final StackPane layout;
	private final AutoInfo info;
	private ArrayList <Node> movableElements;
	private TextField fileName;

	public AddEvent(ArrayList<PreferenceLine> stages, StackPane layout, AutoInfo info,
			ArrayList <Node> movableElements, TextField fileName) {
		this.movableElements = movableElements;
		this.fileName = fileName;
		this.stages = stages;
		this.layout = layout;
		this.info = info;
	}

	
	/**
	 * creates a new preference line and organizes it within the list
	 */
	@Override
	public void handle(ActionEvent arg0) {
		PreferenceLine line = new PreferenceLine (layout, stages, info, movableElements, fileName);
		stages.add(line);
		EventManager.reorganizeEntries(stages, layout, movableElements, fileName);
		
	}
}
