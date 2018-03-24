package autonomous.switchcommands;

import autonomous.builder.GameData;
import autonomous.builder.TimeRestrictedCommand;

public class SwitchWait extends TimeRestrictedCommand {
	
	public static final String NAME = "SwitchWait";
	
	public SwitchWait(int argsLength) {
		super(argsLength, NAME);
	}
	
	@Override
	protected void initialize () {
		
		super.initialize();
		
		GameData data = new GameData ();
		if (data.closeSwitchIsLeft()) {
			
			maxTime = Long.parseLong(args[0]);
			
			
		} else {
			
			maxTime = Long.parseLong(args[1]);
			
		}
	}
}
