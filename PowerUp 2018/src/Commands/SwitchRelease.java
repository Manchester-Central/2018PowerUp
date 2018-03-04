package Commands;

import NewAutoShell.ChaosCommand;
import NewAutoShell.GameData;
import SystemComponents.CubeManipulator;

public class SwitchRelease extends TimeRestrictedCommand {
	
	public static final String NAME = "SwitchRelease";
	private CubeManipulator cubeManipulator;

	public SwitchRelease(int argsLength, CubeManipulator cubeManipulator) {
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
		GameData data = new GameData ();
		if (data.closeSwitchIsLeft()) {
			
			if (args[0].equals("1")) {
				
				cubeManipulator.release();
				
			}
			
			
		} else {
			
			if (args[1].equals("1")) {
				
				cubeManipulator.release();
				
			}
			
		}
		
	}

}
