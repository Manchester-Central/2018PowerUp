package commands;

import auto.builder.GameData;
import system.components.CubeManipulator;

public class SwitchOutput extends TimeRestrictedCommand {
	
	public static final String NAME = "SwitchOutput";

	private GameData data;
	
	CubeManipulator cubeManipulator;
	
	public SwitchOutput(int argsLength, CubeManipulator cubeManipulator) {
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
