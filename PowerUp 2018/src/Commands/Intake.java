package Commands;

import NewAutoShell.ChaosCommand;
import SystemComponents.CubeManipulator;

public class Intake extends ChaosCommand {

	CubeManipulator cubeManipulator;
	
	public Intake(int argsLength, CubeManipulator cubeManipulator) {
		super(argsLength);
		this.cubeManipulator = cubeManipulator;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean isFinished() {
		return cubeManipulator.cubeIn();
	}
	
	@Override
	protected void execute () {
		
		cubeManipulator.intake();
		
	}

}
