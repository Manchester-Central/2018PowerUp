package autonomous.switchcommands;

import autonomous.builder.GameData;
import autonomous.builder.TimeRestrictedCommand;
import system.components.CubeManipulator;

public class SwitchPinch extends TimeRestrictedCommand {
	
	public static final String NAME = "SwitchPinch";
	private CubeManipulator cubeManipulator;

	public SwitchPinch(int argsLength, CubeManipulator cubeManipulator) {
		super(argsLength, NAME);
		this.cubeManipulator = cubeManipulator;
	}

	@Override
	protected boolean isFinished() {
		
		return cubeManipulator.isPinched() || super.isFinished();
	}
	
	@Override
	protected void initialize () {
		super.initialize();
		GameData data = new GameData ();
		if (data.closeSwitchIsLeft()) {
			
			if (args[0].equals("1")) {
				
				cubeManipulator.pinch();
				
			}
			
			
		} else {
			
			if (args[1].equals("1")) {
				
				cubeManipulator.pinch();
				
			}
			
		}
		
	}
}
