package Events;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

public class ArrowEvent implements EventHandler <ActionEvent>{

	private ArrayList<Node> nodes;
	private boolean down;
	
	public ArrowEvent(ArrayList<Node> nodes, boolean down) {
		this.nodes = nodes;
		this.down = down;
	}

	@Override
	public void handle(ActionEvent event) {
		
		for (Node node : nodes) {
			
			if (down) {
				node.setTranslateY(node.getTranslateY() - 50);
			} else {
				node.setTranslateY(node.getTranslateY() + 50);
			}
			
		}
		
	}

}
