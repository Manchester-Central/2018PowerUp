package Commands;

import SystemComponents.CubeManipulator;

public class Intake extends TimeRestrictedCommand {
	
	public static final String NAME = "Intake";
	

	CubeManipulator cubeManipulator;
	
	public Intake(int argsLength, CubeManipulator cubeManipulator) {
		super(argsLength);
		this.cubeManipulator = cubeManipulator;
		
	}

	@Override
	protected boolean isFinished() {
		return cubeManipulator.cubeIn() || super.isFinished();
	}
	
	@Override
	protected void execute () {
		
		cubeManipulator.intake();
		
	}

}
