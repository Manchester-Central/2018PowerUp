package Events;

import java.util.List;

import application.PreferenceLine;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class ParallelClickedEvent implements EventHandler<MouseEvent> {

	private List <PreferenceLine> stages;
	private StackPane layout;
	
	public ParallelClickedEvent(List <PreferenceLine> stages, StackPane layout) {
		
		this.stages = stages;
		this.layout = layout;
		
	}

	@Override
	public void handle(MouseEvent event) {
		
		NumberManager.reorganizeEntries(stages, layout);
		
	}

}
