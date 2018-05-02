package autonomous.switchcommands;

import autonomous.builder.GameData;
import autonomous.builder.TimeRestrictedCommand;
import system.components.CubeManipulator;

public class SwitchRelax extends TimeRestrictedCommand {
	
	public static final String NAME = "SwitchRelax";
	private CubeManipulator cubeManipulator;
	private boolean setOn;

	public SwitchRelax(int argsLength, CubeManipulator cubeManipulator) {
		super(argsLength, NAME);
		this.cubeManipulator = cubeManipulator;
		setOn = false;
	}

	@Override
	protected boolean isFinished() {
		
		return cubeManipulator.isRelaxed() || super.isFinished() || !setOn;
	}
	
	@Override
	protected void initialize () {
		super.initialize();
		GameData data = new GameData ();
		if (data.closeSwitchIsLeft()) {
			
			if (args[0].equals("1")) {
				
				cubeManipulator.relax();
				setOn = true;
				
			}
			
			
		} else {
			
			if (args[1].equals("1")) {
				
				cubeManipulator.relax();
				setOn = true;
				
			}
			
		}
		
	}
}