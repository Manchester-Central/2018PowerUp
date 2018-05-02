package autonomous.switchcommands;

import autonomous.builder.GameData;
import autonomous.builder.TimeRestrictedCommand;
import system.components.CubeManipulator;

public class SwitchPinch extends TimeRestrictedCommand {
	
	public static final String NAME = "SwitchPinch";
	private CubeManipulator cubeManipulator;
	private boolean setOn;

	public SwitchPinch(int argsLength, CubeManipulator cubeManipulator) {
		super(argsLength, NAME);
		this.cubeManipulator = cubeManipulator;
		setOn = false;
	}

	@Override
	protected boolean isFinished() {
		
		return cubeManipulator.isPinched() || super.isFinished() || !setOn;
	}
	
	@Override
	protected void initialize () {
		super.initialize();
		GameData data = new GameData ();
		if (data.closeSwitchIsLeft()) {
			
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
