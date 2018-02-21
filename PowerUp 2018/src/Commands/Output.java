package Commands;

import SystemComponents.CubeManipulator;

public class Output extends TimeRestrictedCommand {
	
	public static final String NAME = "Output";

	CubeManipulator cubeManipulator;
	
	public Output(int argsLength, CubeManipulator cubeManipulator) {
		super(argsLength);
		this.cubeManipulator = cubeManipulator;
	}

	@Override
	protected boolean isFinished() {
		return !cubeManipulator.cubeInCurrent() || super.isFinished();
	}
	
	@Override
	protected void execute () {
		
		cubeManipulator.output();
		
	}

}
