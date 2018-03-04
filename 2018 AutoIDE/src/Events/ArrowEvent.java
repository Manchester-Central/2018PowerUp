package Events;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

public class ArrowEvent implements EventHandler <ActionEvent>{

	private ArrayList<Node> movableElements;
	private boolean down;
	
	public ArrowEvent(ArrayList<Node> movableElements, boolean down) {
		this.movableElements = movableElements;
		this.down = down;
	}

	/**
	 * Moves down if it is set to down, and up if set to up
	 * (-y values go up, +y values go down)
	 * (up and down is relative to the stages; up moves the elements down the scene and 
	 * down moves the elements up the scene)
	 */
	@Override
	public void handle(ActionEvent event) {
		
		for (Node node : movableElements) {
			
			if (down) {
				node.setTranslateY(node.getTranslateY() - 50);
			} else {
				node.setTranslateY(node.getTranslateY() + 50);
			}
			
		}
		
	}

}
