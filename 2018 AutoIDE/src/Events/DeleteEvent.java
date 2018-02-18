package Events;

import java.util.ArrayList;
import java.util.List;

import application.PreferenceLine;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;

public final class DeleteEvent implements EventHandler<ActionEvent> {
	private final StackPane layout;
	private final List<PreferenceLine> stages;
	private final PreferenceLine line;

	public DeleteEvent(StackPane layout, List<PreferenceLine> stages, PreferenceLine line) {
		this.layout = layout;
		this.stages = stages;
		this.line = line;
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == line.getDelete()) {
			
			line.setIsOff(true);
			NumberManager.reorganizeEntries(stages, layout);
			
		}
	}
}