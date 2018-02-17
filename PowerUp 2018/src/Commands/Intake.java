package Commands;

import NewAutoShell.ChaosCommand;
import SystemComponents.CubeManipulator;

public class Intake extends ChaosCommand {
	
	public static final String NAME = "Intake";
	final long maxTime = 5000;
	long startTime;
	

	CubeManipulator cubeManipulator;
	
	public Intake(int argsLength, CubeManipulator cubeManipulator) {
		super(argsLength);
		this.cubeManipulator = cubeManipulator;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean isFinished() {
		return cubeManipulator.cubeIn() || startTime + maxTime < System.currentTimeMillis();
	}
	
	@Override
	protected void execute () {
		
		cubeManipulator.intake();
		
	}
	
	@Override
	protected void initialize () {
		
		startTime = System.currentTimeMillis();
		
	}

}
