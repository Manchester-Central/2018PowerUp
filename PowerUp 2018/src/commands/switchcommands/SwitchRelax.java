package commands.switchcommands;

import auto.builder.GameData;
import commands.TimeRestrictedCommand;
import system.components.CubeManipulator;

public class SwitchRelax extends TimeRestrictedCommand {
	
	public static final String NAME = "SwitchRelax";
	private CubeManipulator cubeManipulator;

	public SwitchRelax(int argsLength, CubeManipulator cubeManipulator) {
		super(argsLength, NAME);
		this.cubeManipulator = cubeManipulator;
	}

	@Override
	protected boolean isFinished() {
		
		return !cubeManipulator.isRelaxed() || super.isFinished();
	}
	
	@Override
	protected void initialize () {
		super.initialize();
		GameData data = new GameData ();
		if (data.closeSwitchIsLeft()) {
			
			if (args[0].equals("1")) {
				
				cubeManipulator.relax();
				
			}
			
			
		} else {
			
			if (args[1].equals("1")) {
				
				cubeManipulator.relax();
				
			}
			
		}
		
	}
}