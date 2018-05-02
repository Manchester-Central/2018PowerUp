package autonomous.switchcommands;

import autonomous.builder.GameData;
import autonomous.builder.TimeRestrictedCommand;
import system.components.CubeManipulator;

public class SwitchOutput extends TimeRestrictedCommand {
	
	public static final String NAME = "SwitchOutput";

	private GameData data;
	
	CubeManipulator cubeManipulator;
	
	private boolean setOn;
	
	public SwitchOutput(int argsLength, CubeManipulator cubeManipulator) {
		super(argsLength, NAME);
		this.cubeManipulator = cubeManipulator;
		setOn = false;
	}

	@Override
	protected boolean isFinished() {
		return super.isFinished() || !setOn;
	}
	
	@Override
	protected void execute () {
		
		if (data.closeSwitchIsLeft()) {
					
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
