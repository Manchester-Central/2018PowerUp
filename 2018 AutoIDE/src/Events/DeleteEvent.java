package Events;

import java.util.ArrayList;
import java.util.List;

import application.PreferenceLine;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public final class DeleteEvent implements EventHandler<ActionEvent> {
	private final StackPane layout;
	private final List<PreferenceLine> stages;
	private final PreferenceLine line;
	private ArrayList <Node> nodes;
	private TextField fileName;

	public DeleteEvent(StackPane layout, List<PreferenceLine> stages, PreferenceLine line,
			ArrayList<Node> nodes, TextField fileName) {
		this.layout = layout;
		this.fileName = fileName;
		this.stages = stages;
		this.line = line;
		this.nodes = nodes;
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == line.getDelete()) {
			
			line.setIsOff(true);
			NumberManager.reorganizeEntries(stages, layout, nodes, fileName);
			
		}
	}
}