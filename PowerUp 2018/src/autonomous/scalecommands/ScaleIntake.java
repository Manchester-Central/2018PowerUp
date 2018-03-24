package autonomous.scalecommands;

import autonomous.builder.GameData;
import autonomous.builder.TimeRestrictedCommand;
import system.components.CubeManipulator;

public class ScaleIntake extends TimeRestrictedCommand {
	
	public static final String NAME = "ScaleIntake";

	private GameData data;
	
	CubeManipulator cubeManipulator;
	
	public ScaleIntake(int argsLength, CubeManipulator cubeManipulator) {
		super(argsLength, NAME);
		this.cubeManipulator = cubeManipulator;
	}

	@Override
	protected boolean isFinished() {
		//TODO use with cube in sensor as well
		return super.isFinished();
	}
	
	@Override
	protected void execute () {
		
		if (data.scaleIsLeft()) {
					
			if (args[0].equals("1")) {
				
				cubeManipulator.intake();
				
			}
			
				
		} else {
				
			if (args[1].equals("1")) {
				
				cubeManipulator.intake();
				
			}
		}
		
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		data = new GameData ();
	}
	
	@Override
	protected void end() {
		cubeManipulator.stopSpeed();
	}


}
