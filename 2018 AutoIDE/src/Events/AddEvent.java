package Events;

import java.util.ArrayList;

import application.AutoInfo;
import application.PreferenceLine;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;

public final class AddEvent implements EventHandler<ActionEvent> {
	private final ArrayList<PreferenceLine> stages;
	private final StackPane layout;
	private final AutoInfo info;

	public AddEvent(ArrayList<PreferenceLine> stages, StackPane layout, AutoInfo info) {
		this.stages = stages;
		this.layout = layout;
		this.info = info;
	}

	@Override
	public void handle(ActionEvent arg0) {
		PreferenceLine line = new PreferenceLine (layout, stages, info);
		stages.add(line);
		NumberManager.reorganizeEntries(stages, layout);
		
		
			
		
	}
}
