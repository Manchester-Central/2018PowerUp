package autonomous.scalecommands;

import autonomous.builder.GameData;
import autonomous.builder.TimeRestrictedCommand;

public class ScaleWait  extends TimeRestrictedCommand {
	
	public static final String NAME = "ScaleWait";
	
	public ScaleWait(int argsLength) {
		super(argsLength, NAME);
	}
	
	@Override
	protected void initialize () {
		
		super.initialize();
		
		GameData data = new GameData ();
		if (data.scaleIsLeft()) {
			
			maxTime = Long.parseLong(args[0]);
			
			
		} else {
			
			maxTime = Long.parseLong(args[1]);
			
		}
	}

}
