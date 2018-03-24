package Events;

import application.PreferenceLine;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class OnPreferenceSymbolDragged implements EventHandler <MouseEvent> {

	private PreferenceLine line;
	
	double sceneHeight;
	
	public OnPreferenceSymbolDragged(PreferenceLine line, double sceneHeight) {
		
		this.line = line;
		this.sceneHeight = sceneHeight;
		
	}

	@Override
	public void handle(MouseEvent event) {
		
		line.setYPostions(event.getSceneY() - (sceneHeight/2) + 30);
		
	}

}
