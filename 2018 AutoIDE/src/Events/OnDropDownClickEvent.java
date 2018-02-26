package Events;

import application.AutoInfo;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

public class OnDropDownClickEvent implements EventHandler<MouseEvent> {

	private AutoInfo info;
	ComboBox <String> box;
	
	public OnDropDownClickEvent(AutoInfo info, ComboBox <String> box) {
		
		this.info = info;
		this.box = box;
		
	}

	@Override
	public void handle(MouseEvent event) {
		
		info.setfiles();
		box.itemsProperty().get().clear();
		box.setItems(info.getFileNames());
		
	}

}
