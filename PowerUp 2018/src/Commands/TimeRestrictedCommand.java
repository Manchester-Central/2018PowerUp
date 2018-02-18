package Commands;

import NewAutoShell.ChaosCommand;

public abstract class TimeRestrictedCommand extends ChaosCommand {

	protected long maxTime = 5000;
	protected long startTime;
	
	public TimeRestrictedCommand(int argsLength) {
		super (argsLength);
	}
	
	@Override
	protected boolean isFinished() {
		return startTime + maxTime < System.currentTimeMillis();
	}
	
	@Override
	protected void initialize () {
		startTime = System.currentTimeMillis();
	}

}
