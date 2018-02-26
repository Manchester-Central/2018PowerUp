package Events;

import application.AutoInfo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class CommandBoxChangeEvent implements ChangeListener <String> {

	private TextField input;
	private AutoInfo info;
	
	public CommandBoxChangeEvent(TextField input, AutoInfo info) {
		
		this.info = info;
		this.input = input;
		
	}

	@Override
	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		
		String placeholder = info.getCommandPlaceholders().getOrDefault(newValue, "");
		
		input.setPromptText(placeholder);
		
		if (placeholder.equals("void")) {
			input.setText("");
			input.setDisable(true);
		} else {
			input.setDisable(false);
		}
		
		
	}

}
