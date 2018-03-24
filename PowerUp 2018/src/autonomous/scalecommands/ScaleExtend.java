package autonomous.scalecommands;

import autonomous.builder.GameData;
import autonomous.commands.TimeRestrictedCommand;
import system.components.CubeManipulator;

public class ScaleExtend extends TimeRestrictedCommand {
	
	public static final String NAME = "ScaleExtend";
	private CubeManipulator cubeManipulator;

	public ScaleExtend(int argsLength, CubeManipulator cubeManipulator) {
		super(argsLength, NAME);
		this.cubeManipulator = cubeManipulator;
	}

	@Override
	protected boolean isFinished() {
		
		return cubeManipulator.isExtended() || super.isFinished();
	}
	
	@Override
	protected void initialize () {
		super.initialize();
		GameData data = new GameData ();
		if (data.scaleIsLeft()) {
			
			if (args[0].equals("1")) {
				
				cubeManipulator.extend();
				
			}
			
			
		} else {
			
			if (args[1].equals("1")) {
				
				cubeManipulator.extend();
				
			}
			
		}
		
	}


}
