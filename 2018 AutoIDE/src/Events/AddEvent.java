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
	private ArrayList <Node> nodes;
	private TextField fileName;

	public AddEvent(ArrayList<PreferenceLine> stages, StackPane layout, AutoInfo info,
			ArrayList <Node> nodes, TextField fileName) {
		this.nodes = nodes;
		this.fileName = fileName;
		this.stages = stages;
		this.layout = layout;
		this.info = info;
	}

	@Override
	public void handle(ActionEvent arg0) {
		PreferenceLine line = new PreferenceLine (layout, stages, info, nodes, fileName);
		stages.add(line);
		NumberManager.reorganizeEntries(stages, layout, nodes, fileName);
		
			
		
	}
}
