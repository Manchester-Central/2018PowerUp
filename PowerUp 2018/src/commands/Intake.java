package commands;

import system.components.CubeManipulator;

public class Intake extends TimeRestrictedCommand {
	
	public static final String NAME = "Intake";
	

	CubeManipulator cubeManipulator;
	
	public Intake(int argsLength, CubeManipulator cubeManipulator) {
		super(argsLength, NAME);
		this.cubeManipulator = cubeManipulator;
		
	}

	@Override
	protected boolean isFinished() {
		return cubeManipulator.cubeInSensor() || super.isFinished();
	}
	
	@Override
	protected void execute () {
		
		cubeManipulator.intake();
		
	}

}
