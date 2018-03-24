package autonomous.commands;

import autonomous.builder.TimeRestrictedCommand;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import system.components.CubeManipulator;

public class Release extends TimeRestrictedCommand {
	
	public static final String NAME = "Release";

	CubeManipulator cubeManipulator;
	
	public Release(int argsLength, CubeManipulator cubeManipulator) {
		super(argsLength, NAME);
		this.cubeManipulator = cubeManipulator;
		
	}

	@Override
	protected boolean isFinished() {
		
		return cubeManipulator.isReleased() || super.isFinished();
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
