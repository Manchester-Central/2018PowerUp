package autonomous.switchcommands;

import autonomous.builder.GameData;
import autonomous.builder.TimeRestrictedCommand;
import system.components.CubeManipulator;

public class SwitchRetract extends TimeRestrictedCommand {
	
	public static final String NAME = "SwitchRetract";
	private CubeManipulator cubeManipulator;

	public SwitchRetract(int argsLength, CubeManipulator cubeManipulator) {
		super(argsLength, NAME);
		this.cubeManipulator = cubeManipulator;
	}

	@Override
	protected boolean isFinished() {
		
		return cubeManipulator.isRetracted() || super.isFinished();
	}
	
	@Override
	protected void initialize () {
		super.initialize();
		GameData data = new GameData ();
		if (data.closeSwitchIsLeft()) {
			
			if (args[0].equals("1")) {
				
				cubeManipulator.retract();
				
			}
			
			
		} else {
			
			if (args[1].equals("1")) {
				
				cubeManipulator.retract();
				
			}
			
		}
		
	}


}
