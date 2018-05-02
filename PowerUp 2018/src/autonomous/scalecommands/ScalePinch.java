package autonomous.scalecommands;

import autonomous.builder.GameData;
import autonomous.builder.TimeRestrictedCommand;
import system.components.CubeManipulator;

public class ScalePinch extends TimeRestrictedCommand {
	
	public static final String NAME = "ScalePinch";
	private CubeManipulator cubeManipulator;
	private boolean setOn;

	public ScalePinch(int argsLength, CubeManipulator cubeManipulator) {
		super(argsLength, NAME);
		this.cubeManipulator = cubeManipulator;
	}

	@Override
	protected boolean isFinished() {
		
		return cubeManipulator.isPinched() || super.isFinished() || !setOn;
	}
	
	@Override
	protected void initialize () {
		super.initialize();
		GameData data = new GameData ();
		if (data.scaleIsLeft()) {
			
			if (args[0].equals("1")) {
				
				cubeManipulator.pinch();
				setOn = true;
				
			}
			
			
		} else {
			
			if (args[1].equals("1")) {
				
				cubeManipulator.pinch();
				setOn = true;
				
			}
			
		}
		
	}
}
