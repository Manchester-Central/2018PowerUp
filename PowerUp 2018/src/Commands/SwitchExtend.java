package Commands;

import NewAutoShell.GameData;
import SystemComponents.CubeManipulator;

public class SwitchExtend extends TimeRestrictedCommand {
	
	public static final String NAME = "SwitchExtend";
	private CubeManipulator cubeManipulator;

	public SwitchExtend(int argsLength, CubeManipulator cubeManipulator) {
		super(argsLength, NAME);
		this.cubeManipulator = cubeManipulator;
	}

	@Override
	protected boolean isFinished() {
		
		return cubeManipulator.isExtended() || super.isFinished();
	}
	
	@Override
	protected void initialize () {
		super.initialize();
		GameData data = new GameData ();
		if (data.closeSwitchIsLeft()) {
			
			if (args[0].equals("1")) {
				
				cubeManipulator.extend();
				
			}
			
			
		} else {
			
			if (args[1].equals("1")) {
				
				cubeManipulator.extend();
				
			}
			
		}
		
	}

}
