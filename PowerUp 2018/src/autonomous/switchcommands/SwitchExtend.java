package autonomous.switchcommands;

import autonomous.builder.GameData;
import autonomous.builder.TimeRestrictedCommand;
import system.components.CubeManipulator;

public class SwitchExtend extends TimeRestrictedCommand {
	
	public static final String NAME = "SwitchExtend";
	private CubeManipulator cubeManipulator;
	private boolean setExtended;

	public SwitchExtend(int argsLength, CubeManipulator cubeManipulator) {
		super(argsLength, NAME);
		this.cubeManipulator = cubeManipulator;
		setExtended = false;
	}

	@Override
	protected boolean isFinished() {
		
		return cubeManipulator.isExtended() || super.isFinished() || !setExtended;
	}
	
	@Override
	protected void initialize () {
		super.initialize();
		GameData data = new GameData ();
		if (data.closeSwitchIsLeft()) {
			
			if (args[0].equals("1")) {
				
				cubeManipulator.extend();
				setExtended = true;
				
			}
			
			
		} else {
			
			if (args[1].equals("1")) {
				
				cubeManipulator.extend();
				setExtended = true;
				
			}
			
		}
		
	}

}
