package autonomous.commands;

import system.components.CubeManipulator;

public class Relax extends TimeRestrictedCommand {
	
	public static final String NAME = "Relax";

	CubeManipulator cubeManipulator;
	
	public Relax(int argsLength, CubeManipulator cubeManipulator) {
		super(argsLength, NAME);
		this.cubeManipulator = cubeManipulator;
		
	}

	@Override
	protected boolean isFinished() {
		
		return cubeManipulator.isRelaxed() || super.isFinished();
	}
	
	@Override
	protected void initialize () {
		super.initialize();
		cubeManipulator.relax();
		
	}

}

