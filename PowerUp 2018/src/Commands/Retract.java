package Commands;

import NewAutoShell.ChaosCommand;
import SystemComponents.CubeManipulator;

public class Retract extends ChaosCommand {
	
	public static final String NAME = "Retract";

	CubeManipulator cubeManipulator;
	
	public Retract(int argsLength, CubeManipulator cubeManipulator) {
		super(argsLength);
		this.cubeManipulator = cubeManipulator;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	protected void initialize () {
		
		cubeManipulator.retract();
		
	}

}
