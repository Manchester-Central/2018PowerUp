package Commands;

import SystemComponents.CubeManipulator;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Pinch extends TimeRestrictedCommand {
	
	public static final String NAME = "Pinch";

	CubeManipulator cubeManipulator;
	
	public Pinch(int argsLength, CubeManipulator cubeManipulator) {
		super(argsLength);
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

