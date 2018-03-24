package autonomous.scalecommands;

import autonomous.builder.GameData;
import autonomous.commands.TimeRestrictedCommand;
import system.components.CubeManipulator;

public class ScaleRelease extends TimeRestrictedCommand {
	
	public static final String NAME = "ScaleRelease";
	private CubeManipulator cubeManipulator;

	public ScaleRelease(int argsLength, CubeManipulator cubeManipulator) {
		super(argsLength, NAME);
		this.cubeManipulator = cubeManipulator;
	}

	@Override
	protected boolean isFinished() {
		
		return !cubeManipulator.isPinched() || super.isFinished();
	}
	
	@Override
	protected void initialize () {
		super.initialize();
		GameData data = new GameData ();
		if (data.scaleIsLeft()) {
			
			if (args[0].equals("1")) {
				
				cubeManipulator.release();
				
			}
			
			
		} else {
			
			if (args[1].equals("1")) {
				
				cubeManipulator.release();
				
			}
			
		}
		
	}


}
