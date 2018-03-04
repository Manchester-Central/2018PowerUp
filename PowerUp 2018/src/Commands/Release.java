package Commands;

import SystemComponents.CubeManipulator;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Release extends TimeRestrictedCommand {
	
	public static final String NAME = "Release";

	CubeManipulator cubeManipulator;
	
	public Release(int argsLength, CubeManipulator cubeManipulator) {
		super(argsLength, NAME);
		this.cubeManipulator = cubeManipulator;
		
	}

	@Override
	protected boolean isFinished() {
		
		return !cubeManipulator.isPinched() || super.isFinished();
	}
	
	@Override
	protected void initialize () {
		super.initialize();
		cubeManipulator.release();
		
	}
	
	@Override
	protected void execute () {
		cubeManipulator.release();
	}



}
