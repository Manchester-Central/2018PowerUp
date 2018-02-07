package Commands;

import NewAutoShell.ChaosCommand;
import SystemComponents.CubeManipulator;

public class Output extends ChaosCommand {

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
