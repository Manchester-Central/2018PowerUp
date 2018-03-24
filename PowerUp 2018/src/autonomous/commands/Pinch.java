package autonomous.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import system.components.CubeManipulator;

public class Pinch extends TimeRestrictedCommand {
	
	public static final String NAME = "Pinch";

	CubeManipulator cubeManipulator;
	
	public Pinch(int argsLength, CubeManipulator cubeManipulator) {
		super(argsLength, NAME);
		this.cubeManipulator = cubeManipulator;
		
	}

	@Override
	protected boolean isFinished() {
		
		return cubeManipulator.isPinched() || super.isFinished();
	}
	
	@Override
	protected void initialize () {
		super.initialize();
		cubeManipulator.pinch();
		
	}

}

