package autonomous.commands;

import system.components.CubeManipulator;

public class Output extends TimeRestrictedCommand {
	
	public static final String NAME = "Output";

	CubeManipulator cubeManipulator;
	
	public Output(int argsLength, CubeManipulator cubeManipulator) {
		super(argsLength, NAME);
		this.cubeManipulator = cubeManipulator;
	}

	@Override
	protected boolean isFinished() {
		return !cubeManipulator.cubeInSensor() || super.isFinished();
	}
	
	@Override
	protected void execute () {
		
		cubeManipulator.output();
		
	}

}
