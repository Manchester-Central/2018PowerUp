package Events;

import java.util.ArrayList;
import java.util.List;

import application.PreferenceLine;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class ParallelClickedEvent implements EventHandler<MouseEvent> {

	private List <PreferenceLine> stages;
	private StackPane layout;
	private ArrayList <Node> movableElements;
	private TextField fileName;
	
	public ParallelClickedEvent(List <PreferenceLine> stages, StackPane layout,
			ArrayList <Node> movableElements, TextField fileName) {
		this.movableElements = movableElements;
		this.fileName = fileName;
		this.stages = stages;
		this.layout = layout;
		
	}

	/**
	 * Reorganizes entries after ticking box to update stage numbers
	 */
	@Override
	public void handle(MouseEvent event) {
		
		EventManager.reorganizeEntries(stages, layout, movableElements, fileName);
		
	}

}
