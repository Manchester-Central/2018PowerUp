package autonomous.scalecommands;

import autonomous.builder.GameData;
import autonomous.builder.TimeRestrictedCommand;
import system.components.CubeManipulator;

public class ScaleExtend extends TimeRestrictedCommand {
	
	public static final String NAME = "ScaleExtend";
	private CubeManipulator cubeManipulator;
	private boolean setOn;

	public ScaleExtend(int argsLength, CubeManipulator cubeManipulator) {
		super(argsLength, NAME);
		this.cubeManipulator = cubeManipulator;
		setOn = false;
	}

	@Override
	protected boolean isFinished() {
		
		return cubeManipulator.isExtended() || super.isFinished() || !setOn;
	}
	
	@Override
	protected void initialize () {
		super.initialize();
		GameData data = new GameData ();
		if (data.scaleIsLeft()) {
			
			if (args[0].equals("1")) {
				
				cubeManipulator.extend();
				setOn = true;
				
			}
			
			
		} else {
			
			if (args[1].equals("1")) {
				
				cubeManipulator.extend();
				setOn = true;
				
			}
			
		}
		
	}


}
