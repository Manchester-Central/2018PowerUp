package Events;

import java.awt.Desktop;
import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class OpenInfoEvent implements EventHandler<ActionEvent> {

	public OpenInfoEvent() {
		
	}

	/**
	 * Opens up the info file (Command_Config.ini)
	 */
	@Override
	public void handle(ActionEvent event) {
		
		File config = new File ("C:\\Program Files\\AutoFolder\\Command_Config.txt");
		if (config.exists()) {
			
			if (Desktop.isDesktopSupported()) {
				try {
					Desktop.getDesktop().open(config);
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}
			}
			
		}
		
	}

}
