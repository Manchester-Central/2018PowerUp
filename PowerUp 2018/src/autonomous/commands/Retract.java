package autonomous.commands;

import autonomous.builder.ChaosCommand;
import system.components.CubeManipulator;

public class Retract extends TimeRestrictedCommand {
	
	public static final String NAME = "Retract";

	CubeManipulator cubeManipulator;
	
	public Retract(int argsLength, CubeManipulator cubeManipulator) {
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
		cubeManipulator.retract();
		
	}

}
