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
	private ArrayList <Node> movableElements;
	private TextField fileName;

	public DeleteEvent(StackPane layout, List<PreferenceLine> stages, PreferenceLine line,
			ArrayList<Node> movableElements, TextField fileName) {
		this.layout = layout;
		this.fileName = fileName;
		this.stages = stages;
		this.line = line;
		this.movableElements = movableElements;
	}

	/**
	 * Determines if the button was clicked and if it was, then it sets 
	 * its parent to be deleted reorganizes the entries
	 */
	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == line.getDelete()) {
			
			line.setIsOff(true);
			EventManager.reorganizeEntries(stages, layout, movableElements, fileName);
			
		}
	}
}