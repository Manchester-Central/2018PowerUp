package Commands;

import NewAutoShell.ChaosCommand;

public abstract class TimeRestrictedCommand extends ChaosCommand {

	protected long maxTime = 1000;
	protected long startTime;
	
	public TimeRestrictedCommand(int argsLength, String NAME) {
		super(argsLength, NAME);
	}
	
	@Override
	protected boolean isFinished() {
		System.out.println(startTime + maxTime < System.currentTimeMillis());
		return startTime + maxTime < System.currentTimeMillis();
	}
	
	@Override
	protected void initialize () {
		super.initialize();
		startTime = System.currentTimeMillis();
	}

	
}
