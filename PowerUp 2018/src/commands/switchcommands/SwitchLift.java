package commands.switchcommands;

import auto.builder.ChaosCommand;
import auto.builder.GameData;
import system.components.LinearLift;

public class SwitchLift extends ChaosCommand {

	public static final String NAME = "SwitchLift";
	private LinearLift lift;
	
	public SwitchLift(int argsLength, LinearLift lift) {
		super (argsLength, NAME);
		this.lift = lift;
	}
	
	@Override
	protected void initialize () {
		super.initialize();
		GameData data = new GameData ();
		if (data.closeSwitchIsLeft()) {
			
			lift.setTargetPosition(Double.parseDouble(args[0]));
			
		} else {
			
			lift.setTargetPosition(Double.parseDouble(args[1]));
			
		}
	}
	
	@Override
	protected boolean isFinished() {
		return lift.isAtTargetPosition();
	}
	
	

}
