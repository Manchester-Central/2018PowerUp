package autonomous.scalecommands;

import autonomous.builder.GameData;
import autonomous.builder.TimeRestrictedCommand;
import system.components.CubeManipulator;

public class ScaleRelax extends TimeRestrictedCommand {

	public static final String NAME = "ScaleRelax";
	private CubeManipulator cubeManipulator;
	private boolean willRun;

	public ScaleRelax(int argsLength, CubeManipulator cubeManipulator) {
		super(argsLength, NAME);
		this.cubeManipulator = cubeManipulator;
		willRun = false;
	}

	@Override
	protected boolean isFinished() {
		
		return cubeManipulator.isRelaxed() || super.isFinished() || !willRun;
	}
	
	@Override
	protected void initialize () {
		super.initialize();
		GameData data = new GameData ();
		if (data.scaleIsLeft()) {
			
			if (args[0].equals("1")) {
				
				cubeManipulator.relax();
				willRun = true;
				
			}
			
			
		} else {
			
			if (args[1].equals("1")) {
				
				cubeManipulator.relax();
				willRun = true;
				
			}
			
		}
		
	}
}
