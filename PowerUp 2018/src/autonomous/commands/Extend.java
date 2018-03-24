package autonomous.commands;

import system.components.CubeManipulator;

public class Extend extends TimeRestrictedCommand {
	
	public static final String NAME = "Extend";

	private CubeManipulator cubeManipulator;
	
	public Extend(int argsLength, CubeManipulator cubeManipulator) {
		super(argsLength, NAME);
		this.cubeManipulator = cubeManipulator;
	}

	@Override
	protected boolean isFinished() {
		return cubeManipulator.isExtended() || super.isFinished();
	}
	
	@Override
	protected void initialize () {
		super.initialize();
		cubeManipulator.extend();
		
	}

}
