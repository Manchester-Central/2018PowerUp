package autonomous.scalecommands;

import autonomous.builder.ChaosCommand;
import autonomous.builder.GameData;
import system.components.LinearLift;

public class ScaleLift extends ChaosCommand {

	public static final String NAME = "ScaleLift";
	private LinearLift lift;
	
	public ScaleLift(int argsLength, LinearLift lift) {
		super (argsLength, NAME);
		this.lift = lift;
	}
	
	@Override
	protected void initialize () {
		super.initialize();
		GameData data = new GameData ();
		if (data.scaleIsLeft()) {
			
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
