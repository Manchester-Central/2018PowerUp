package autonomous.switchcommands;

import autonomous.builder.GameData;
import autonomous.builder.TimeRestrictedCommand;
import system.components.CubeManipulator;

public class SwitchIntake extends TimeRestrictedCommand {
	
	public static final String NAME = "SwitchIntake";

	private GameData data;
	
	CubeManipulator cubeManipulator;
	
	public SwitchIntake(int argsLength, CubeManipulator cubeManipulator) {
		super(argsLength, NAME);
		this.cubeManipulator = cubeManipulator;
	}

	@Override
	protected boolean isFinished() {
		return super.isFinished();
	}
	
	@Override
	protected void execute () {
		
		if (data.closeSwitchIsLeft()) {
					
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
