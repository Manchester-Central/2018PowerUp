package Commands;

import NewAutoShell.ChaosCommand;
import SystemComponents.CubeManipulator;

public class Extend extends ChaosCommand {
	
	public static final String NAME = "Extend";

	private CubeManipulator cubeManipulator;
	
	public Extend(int argsLength, CubeManipulator cubeManipulator) {
		super(argsLength);
		this.cubeManipulator = cubeManipulator;
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	protected void initialize () {
		
		cubeManipulator.extend();
		
	}

}
