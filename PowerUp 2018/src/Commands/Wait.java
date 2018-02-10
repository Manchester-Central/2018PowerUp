package Commands;

import NewAutoShell.ChaosCommand;

public class Wait extends ChaosCommand {
	
	public static final String NAME = "Wait";

	long startTime;
	
	long waitTime;
	
	public Wait(int argsLength) {
		super(argsLength);
		waitTime = Long.parseLong(args[0]);
	}

	@Override
	protected boolean isFinished() {
		return startTime + waitTime < System.currentTimeMillis();
	}
	
	@Override
	protected void initialize () {
		
		startTime = System.currentTimeMillis();
		
	}

}
