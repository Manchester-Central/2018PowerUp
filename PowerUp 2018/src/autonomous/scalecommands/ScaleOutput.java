package autonomous.scalecommands;

import autonomous.builder.GameData;
import autonomous.builder.TimeRestrictedCommand;
import system.components.CubeManipulator;

public class ScaleOutput extends TimeRestrictedCommand {
	
	public static final String NAME = "ScaleOutput";

	private GameData data;
	
	CubeManipulator cubeManipulator;
	
	public ScaleOutput(int argsLength, CubeManipulator cubeManipulator) {
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
				
				cubeManipulator.output();
				
			}
			
				
		} else {
				
			if (args[1].equals("1")) {
				
				cubeManipulator.output();
				
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
