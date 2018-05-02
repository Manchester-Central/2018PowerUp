package autonomous.scalecommands;

import autonomous.builder.GameData;
import autonomous.builder.TimeRestrictedCommand;
import system.components.CubeManipulator;

public class ScaleOutput extends TimeRestrictedCommand {
	
	public static final String NAME = "ScaleOutput";

	private GameData data;
	
	CubeManipulator cubeManipulator;

	private boolean setOn;
	
	public ScaleOutput(int argsLength, CubeManipulator cubeManipulator) {
		super(argsLength, NAME);
		this.cubeManipulator = cubeManipulator;
		setOn = false;
	}

	@Override
	protected boolean isFinished() {
		//TODO use with cube in sensor as well
		return super.isFinished() || !setOn;
	}
	
	
	
	@Override
	protected void execute () {
		
		if (data.scaleIsLeft()) {
					
			if (args[0].equals("1")) {
				
				cubeManipulator.output();
				setOn = true;
				
			}
			
				
		} else {
				
			if (args[1].equals("1")) {
				
				cubeManipulator.output();
				setOn = true;
				
			}
		}
		
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		data = new GameData ();
		maxTime = 700;
	}
	
	@Override
	protected void end() {
		cubeManipulator.stopSpeed();
	}


}
