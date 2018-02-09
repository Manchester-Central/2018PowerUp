package Commands;

import NewAutoShell.ChaosCommand;
import SystemComponents.CubeManipulator;

public class Output extends ChaosCommand {
	
	public static final String NAME = "Output";

	CubeManipulator cubeManipulator;
	
	public Output(int argsLength, CubeManipulator cubeManipulator) {
		super(argsLength);
		this.cubeManipulator = cubeManipulator;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return !cubeManipulator.cubeIn();
	}
	
	@Override
	protected void execute () {
		
		cubeManipulator.output();
		
	}

}
